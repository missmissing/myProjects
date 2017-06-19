/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.task.service  
 * 文件名：				CategoryService.java    
 * 日期：				2015年6月18日-下午2:43:07  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.task.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.api.task.util.TaskMarkKeyUtil;
import com.huatu.api.util.ComparatorCategoryVo;
import com.huatu.api.version.model.TaskVersion;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.api.version.util.VersionUtil;
import com.huatu.api.vo.CategoryVo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.AppTypePropertyUtil;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.tb.category.dao.impl.RCategoryDaoImpl;
import com.huatu.tb.category.model.CateQues;
import com.huatu.tb.category.model.Category;

/**   
 * 类名称：				CategoryService  
 * 类描述：  			章节缓存 -- (获取根节点默认0 缓存)
 * 创建人：				LiXin
 * 创建时间：			2015年6月18日 下午2:43:07  
 * @version 		1.0
 */
@Service
public class RCategoryService {

	@Autowired
	private IRedisService iredisCategory;
	@Autowired
	private IRedisService iRedisService;
	@Autowired
	private RCategoryDaoImpl categoryRestDao;
	@Autowired
	private TaskVersionService taskVersionService;

	/**
	 *
	 * refreshRedisCategoty			(刷新知识点缓存)
	 * 								(知识点有更新时，调用)
	 * @return
	 * @throws 		HttbException
	 */
	public void refreshCategroy() throws HttbException {
		String cate_redis_ver = (String) iRedisService.get(TaskMarkKeyUtil.CATE_VER_GATHER);//章节在redis中的版本
		String cate_nosql_ver = null;//章节在nosql中版本
		TaskVersion tv = taskVersionService.getVersion(Constants.CATEGORY);
		if(CommonUtil.isNotNull(tv)){
			cate_nosql_ver = tv.getTvalue();
		}
		//如果两个有一个为空 那么刷新
		if(CommonUtil.isNull(cate_nosql_ver)|| CommonUtil.isNull(cate_redis_ver)){
			//1 刷新版本信息
			String vernumber = VersionUtil.getVersionNumber();
			iRedisService.put(TaskMarkKeyUtil.CATE_VER_GATHER,vernumber);
			TaskVersion catever = new TaskVersion();
			catever.setTkey(Constants.CATEGORY);
			catever.setTvalue(vernumber);
			catever.setTdesc("刷新缓存");
			taskVersionService.addVersion(catever);
			//2 刷新业务数据
			updateCate(vernumber);
			
		}else{
			if(!cate_redis_ver.equals(cate_nosql_ver)){
				//1 刷新redis缓存版本
				iRedisService.put(TaskMarkKeyUtil.CATE_VER_GATHER,cate_nosql_ver);
				
				//2 刷新业务数据
				updateCate(cate_nosql_ver);
			}
		}
		
	}
	//刷新章节树缓存
	private void updateCate(String vernumber) throws HttbException{
		List<CategoryVo> cateList = this.getCatVoAllList();//全部章节VO列表
		
		List<CateQues> cqList = this.getCateQuesAllList();//全部章节试题列表
		//将关系保存起来
		iRedisService.put(TaskMarkKeyUtil.CATE_QUES_KEY, cqList);
		//公务员
		if(300 == AppTypePropertyUtil.APP_TYPE){
			for(String area : AppTypePropertyUtil.set){
				//遍历设置试题个数 及 添加版本号信息
				for(CategoryVo cv : cateList){
					List<String> qids = getQidList(cv.getCid(),area,cqList);
					cv.setQids(qids);
					cv.setCount(qids.size()+"");
					cv.setVersions(vernumber);//当前版本号
				}
				if(CommonUtil.isNotNull(cateList)){
					try {
						//添加到redis库中
						iRedisService.put(TaskMarkKeyUtil.CATEGORY_KEY+area, cateList);
					} catch (Exception e) {
						throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入公务员知识点缓存数据对象到Redis时发生异常", e);
					}
				}
			}
			//因为获取错题集的时候不分地域 获取全部的错题  所以添加一个独立缓存全部章节树的数据
				//遍历设置试题个数
				for(CategoryVo cv : cateList){
					List<String> qids = getQidList(cv.getCid(),null,cqList);
					cv.setQids(qids);
					cv.setCount(qids.size()+"");
				}
				if(CommonUtil.isNotNull(cateList)){
					try {
						//添加到redis库中
						iRedisService.put(TaskMarkKeyUtil.CATEGORY_KEY, cateList);
					} catch (Exception e) {
						throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入公务员知识点缓存数据对象到Redis时发生异常", e);
					}
				}
			//end
		}else{
			//遍历设置试题个数
			for(CategoryVo cv : cateList){
				
				List<String> qids = getQidList(cv.getCid(),null,cqList);
				cv.setQids(qids);
				cv.setCount(qids.size()+"");
				if("200100100104101".equals(cv.getCid())){
					System.out.println(qids.size());
				}
				cv.setVersions(vernumber);//当前版本号
			}
			if(CommonUtil.isNotNull(cateList)){
				try {
					//章节 知识点 缓存
					iRedisService.put(TaskMarkKeyUtil.CATEGORY_KEY, cateList);
				} catch (Exception e) {
					throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入知识点缓存数据对象到Redis时发生异常", e);
				}
			}
			
		}
	}
	
	
	/**
	 * 获取指定章节下试题id集合
	 * getQidList  
	 * @exception 
	 * @param cid -- 章节id
	 * @param area -- 地区
	 * @param cqlist  -- 关系集合
	 * @return
	 */
	public List<String> getQidList(String cid,String area,List<CateQues> cqList){
		Set<String> qids = new HashSet<String>();
		if(CommonUtil.isNotNull(area)){
			for(CateQues cateq : cqList){
				if(cateq.getCid().equals(cid) && area.equals(cateq.getAttr())){
					if(CommonUtil.isNotNull(cateq.getQcids())&& cateq.getQcids().size()>0){
						qids.addAll(cateq.getQcids());
					}else{
						qids.add(cateq.getQid());
					}
				}
			}
		}else{
			for(CateQues cateq : cqList){
				if(cateq.getCid().equals(cid)){
					if(CommonUtil.isNotNull(cateq.getQcids())&& cateq.getQcids().size()>0){
						qids.addAll(cateq.getQcids());
					}else{
						qids.add(cateq.getQid());
					}
				}
			}
		}
		List<String> result = new ArrayList<String>(qids);
		return result;
	}
	
	/**
	 * 获取全部章节树Vo
	 * getCategoryAllList  
	 * @exception 
	 * @return
	 * @throws HttbException
	 */
	@SuppressWarnings("unchecked")
	private List<CategoryVo> getCatVoAllList() throws HttbException{
		List<CategoryVo> lists = new ArrayList<CategoryVo>();
		
		/**保留一份章节原始数据放到redis中  分析中有使用                      begin*/
		List<Category> categorys = categoryRestDao.getCategoryList();
		for (Category category : categorys) {
			// 1,遍历全部张章节 获取基本消息
			CategoryVo cv = new CategoryVo();
			cv.setCid(category.getCid());
			cv.setPid(category.getCpid());
			cv.setName(category.getCname());//名称
			cv.setExplain(category.getCexplain()); //说明
			cv.setOrdernum(category.getCordernum()); //排序字段
			//是否有下载包
			if(AppTypePropertyUtil.APP_TYPE == 300 && "2".equals(category.getClevels())){
				cv.setIsdownload("0");//有下载包
			}else if("3".equals(category.getClevels())){
				cv.setIsdownload("0");//有下载包
			}
			lists.add(cv);
		}
		iRedisService.put(Constants.ANALYSIS_CATEGORY, categorys);
		/**保留一份章节原始数据放到redis中  分析中有使用                      end*/
		
		//2 根据排序字段排序
		ComparatorCategoryVo comparator = new ComparatorCategoryVo();
		Collections.sort(lists, comparator);//根据排序字段排序
		return lists;
	}
	/**
	 * 获取全部章节试题关系列表
	 * getCategoryAllList  
	 * @exception 
	 * @return
	 * @throws HttbException
	 */
	private List<CateQues> getCateQuesAllList() throws HttbException{
		List<CateQues> cqList = new ArrayList<CateQues>();
		cqList = categoryRestDao.getCateQuesList();
		return cqList;
	}

}
