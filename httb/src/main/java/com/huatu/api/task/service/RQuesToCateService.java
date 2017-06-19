/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.task.service  
 * 文件名：				QuesToCateService.java    
 * 日期：				2015年6月18日-上午9:48:15  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.api.task.util.TaskMarkKeyUtil;
import com.huatu.api.util.CompressUtil;
import com.huatu.api.util.ModelToVoUtil;
import com.huatu.api.version.model.TaskVersion;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.api.version.util.VersionUtil;
import com.huatu.api.vo.QuestionVo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.AppTypePropertyUtil;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.tb.category.dao.impl.RCategoryDaoImpl;
import com.huatu.tb.question.dao.impl.RQuestionDaoImpl;
import com.huatu.tb.question.model.Question;

/**   
 * 类名称：				QuesToCateService  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年6月18日 上午9:48:15  
 * @version 		1.0
 */
@Service
public class RQuesToCateService {
	@Autowired
	private IRedisService iRedisService;
	@Autowired
	private RQuestionDaoImpl questionRestDao;
	@Autowired
	private RCategoryDaoImpl categoryRestDao;
	@Autowired
	private TaskVersionService taskVersionService;
	/**
	 *缓存章节对应试题
	 * refreshQuesToCate  
	 * @exception 
	 * @return
	 * @throws HttbException
	 */
	public void refreshQuesToCate() throws HttbException {
		String quescate_redis_ver = (String) iRedisService.get(TaskMarkKeyUtil.QUESCATE_VER_GATHER);//章节对应试题打包操作在redis中的版本
		String cate_nosql_ver = null;//章节在nosql中版本【******试题和章节用的一个标示】
		TaskVersion tv = taskVersionService.getVersion(Constants.CATEGORY);
		if(CommonUtil.isNotNull(tv)){
			cate_nosql_ver = tv.getTvalue();
		}
		//如果两个有一个为空 那么刷新
		if(CommonUtil.isNull(cate_nosql_ver)|| CommonUtil.isNull(quescate_redis_ver)){
			//1 刷新版本信息
			String vernumber = VersionUtil.getVersionNumber();
			iRedisService.put(TaskMarkKeyUtil.QUESCATE_VER_GATHER,vernumber);
			TaskVersion catever = new TaskVersion();
			catever.setTkey(Constants.CATEGORY);
			catever.setTvalue(vernumber);
			catever.setTdesc("刷新缓存");
			taskVersionService.addVersion(catever);
			//2 刷新业务数据
			updateQuesCate();
			
		}else{
			if(!quescate_redis_ver.equals(cate_nosql_ver)){
				//1 刷新redis缓存版本
				iRedisService.put(TaskMarkKeyUtil.CATE_VER_GATHER,cate_nosql_ver);
				
				//2 刷新业务数据
				updateQuesCate();
			}
		}
	}
	//更新章节下的试题
	private void updateQuesCate()throws HttbException{
		if(AppTypePropertyUtil.APP_TYPE == 300){
			/**1 获取二级的所有章节ID*/
			List<String> cidList = categoryRestDao.getCateByLevels("2");
			for(String area : AppTypePropertyUtil.set){
				/**2 通过关系表获取试题id集合*/
				for(String cid : cidList){//章节id
					List<QuestionVo> qvoList = new ArrayList<QuestionVo>();
					
					Set<String> qidSet = categoryRestDao.getQidsBycid(cid,area);
					if(qidSet != null && qidSet.size()>0){
						List<String> qidlist = new ArrayList<String>(qidSet);
						/** 2.1通过试题集合获取试题列表*/
						List<Question> qlist = questionRestDao.getQuestionResultSet(qidlist);
						/** 2.2 试题转换*/
						for(Question q : qlist){
							List<QuestionVo> qvList = ModelToVoUtil.questtionToVo(q);
							qvoList.addAll(qvList);
						}
						/** 2.3 压缩打包*/
						JSONArray json = JSONArray.fromObject(qvoList) ;
						iRedisService.put(TaskMarkKeyUtil.CATEQUES_+cid+area, CompressUtil.gzip(json.toString()));
					}
				}
			}
		}else{
			/**1 获取三级的所有章节ID*/
			List<String> cidList = categoryRestDao.getCateByLevels("3");
			
			/**2 通过关系表获取试题id集合*/
			for(String cid : cidList){//章节id
				List<QuestionVo> qvoList = new ArrayList<QuestionVo>();
				
				Set<String> qidSet = categoryRestDao.getQidsBycid(cid, "");
				if(qidSet != null && qidSet.size()>0){
					List<String> qidlist = new ArrayList<String>(qidSet);
					/** 2.1通过试题集合获取试题列表*/
					List<Question> qlist = questionRestDao.getQuestionResultSet(qidlist);
					/** 2.2 试题转换*/
					for(Question q : qlist){
						List<QuestionVo> qvList = ModelToVoUtil.questtionToVo(q);
						qvoList.addAll(qvList);
					}
					/** 2.3 压缩打包*/
					JSONArray json = JSONArray.fromObject(qvoList) ;
					iRedisService.put(TaskMarkKeyUtil.CATEQUES_+cid, CompressUtil.gzip(json.toString()));
				}else{
					iRedisService.put(TaskMarkKeyUtil.CATEQUES_+cid, "");
				}
				
				
			}
		}
	}
	
}
