/**
 * 项目名：				httb
 * 包名：				com.huatu.api.service.impl
 * 文件名：				CategoryApiServiceImpl.java
 * 日期：				2015年6月12日-上午10:04:49
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.api.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.Row;
import com.huatu.api.service.CategoryApiService;
import com.huatu.api.task.util.TaskMarkKeyUtil;
import com.huatu.api.util.ComparatorCategoryVo;
import com.huatu.api.vo.CategoryVo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.ProcessPropertiesConfigUtil;
import com.huatu.tb.category.dao.impl.RCategoryDaoImpl;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.paper.dao.impl.RPaperDaoImpl;
import com.huatu.tb.question.dao.impl.RQuestionDaoImpl;

/**
 * 类名称：				CategoryApiServiceImpl
 * 类描述：  			章节服务实现类
 * 创建人：				LiXin
 * 创建时间：			2015年6月12日 上午10:04:49
 * @version 		1.0
 */
@Service
public class CategoryApiServiceImpl implements CategoryApiService {
	@Autowired
	private RQuestionDaoImpl questionRestDao;

	@Autowired
	private RPaperDaoImpl paperRestDao;

	@Autowired
	private RCategoryDaoImpl categoryRestDao;

	@Autowired
	private IRedisService iRedisService;

	public JSONArray getTbClassList() throws HttbException {
		// 读取appmenujson.properties配置集
		Properties p = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("appmenujson");
		//json
		String appmenujson = p.getProperty("appmenujson");
		JSONArray json = JSONArray.fromObject(appmenujson) ;
		return json;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CategoryVo getSubsetById(String id,String userno,String area) throws HttbException {
		//首先在缓存中获取
		List<CategoryVo> lists = new ArrayList<CategoryVo>();
		lists = (List<CategoryVo>) iRedisService.get(TaskMarkKeyUtil.CATEGORY_KEY+area);
		if(CommonUtil.isNull(lists)){
			//如果缓存失败在数据库中获取
			lists = getCategoryVoList(area);
		}
		//续4,重置组装集合【形成父子包含的格式  便于转JSON】
		return settleDataFormat(lists,id);
	}
	@Override
	public String getQuestions(String cateid, String area) throws HttbException {
		/*if(CommonUtil.isNotNull(area)){
			String queStr = (String) iRedisService.get(TaskMarkKeyUtil.CATEQUES_+cateid+area);
			return queStr;
		}else{
			String queStr = (String) iRedisService.get(TaskMarkKeyUtil.CATEQUES_+cateid);
			return queStr;
		}*/
		String redisId = "";
		if(CommonUtil.isNotNull(area)){
			redisId=TaskMarkKeyUtil.CATEQUES_+cateid+area;
		}else{
			redisId=TaskMarkKeyUtil.CATEQUES_+cateid;
		}
		Object questions = iRedisService.get(redisId);
		if(null!=questions)
			return (String)questions;
		else
			throw new HttbException("通过cateid在redis中获取试题集合，结果为空，cateid is["+cateid+"]");
	}
	/**
	 * 获取全部章节VO列表
	 * getCategoryVoList
	 * @exception
	 * @return
	 * @throws HttbException
	 */
	@SuppressWarnings("unchecked")
	private List<CategoryVo> getCategoryVoList(String attr) throws HttbException {
		List<Category> cgList = categoryRestDao.getCategoryList();
		List<CategoryVo> lists = new ArrayList<CategoryVo>();
		// 1,遍历全部张章节 获取基本消息
		for (Category categ : cgList) {
			CategoryVo categoryVo = new CategoryVo();
			categoryVo.setCid(categ.getCid());
			
			categoryVo.setPid(categ.getCpid());
			
			categoryVo.setName(categ.getCname());//名称
			categoryVo.setExplain(categ.getCexplain()); //说明********要不要加********
			categoryVo.setOrdernum(categ.getCordernum()); //排序字段
			lists.add(categoryVo);
		}
		//2,根据排序字段排序
		ComparatorCategoryVo comparator = new ComparatorCategoryVo();
		Collections.sort(lists, comparator);//根据排序字段排序
		
		//3,遍历知识点章节集合 获取试题数量和试题ID集合
		for(CategoryVo cateV : lists){
			Set<String> set = categoryRestDao.getQidsBycid(cateV.getCid(), attr);
			cateV.setCount(set.size()+"");//知识点下的试题数量
			cateV.setQids(new ArrayList<String>(set));//试题ID集合
		}
		return lists;
	}
	/**
	 * 重新定义数据格式
	 * settleDataFormat
	 * @exception
	 * @return
	 */
	private CategoryVo settleDataFormat(List<CategoryVo> list , String id){
		Boolean boo = true;
		for(CategoryVo cq : list){
			if(cq.getCid().equals(id)){
				boo = false;
				return setObject(list,cq);
			}
		}
		if(boo){
			CategoryVo cv = new CategoryVo();
			cv.setCid("0");
			return setObjectAll(list,cv);
		}
		return null;
	}
	/**
	 * 递归生成父子集数据
	 * setObject
	 * @exception
	 * @param list
	 * @param cq
	 * @return
	 */
	private CategoryVo setObject(List<CategoryVo> list , CategoryVo cq){
		List<CategoryVo> childCV = new ArrayList<CategoryVo>();
		for(CategoryVo cvo : list){
			if(cq.getCid().equals(cvo.getPid())){
				childCV.add(setObject(list,cvo));
			}
		}
		cq.setChildren(childCV);
		return cq;
	}
	/**
	 * 递归生成父子集数据
	 * setObject
	 * @exception
	 * @param list
	 * @param cq
	 * @return
	 */
	private CategoryVo setObjectAll(List<CategoryVo> list , CategoryVo cq){
		List<CategoryVo> childCV = new ArrayList<CategoryVo>();
		for(CategoryVo cvo : list){
			if(cq.getCid().equals(cvo.getPid())){
				childCV.add(setObject(list,cvo));
			}
		}
		cq.setChildren(childCV);
		return cq;
	}

}
