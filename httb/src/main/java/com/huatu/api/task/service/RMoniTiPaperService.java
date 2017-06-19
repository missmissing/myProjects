/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.task.service  
 * 文件名：				MoniTiPaperService.java    
 * 日期：				2015年6月20日-上午10:01:08  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.task.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.api.task.util.TaskMarkKeyUtil;
import com.huatu.api.util.CompressUtil;
import com.huatu.api.util.ModelToVoUtil;
import com.huatu.api.version.model.TaskVersion;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.api.vo.QuestionVo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.tb.category.dao.impl.RCategoryDaoImpl;
import com.huatu.tb.category.model.CateQues;
import com.huatu.tb.paper.dao.impl.RPaperDaoImpl;
import com.huatu.tb.paper.model.Paper;
import com.huatu.tb.paper.util.ComparatorToPaper;
import com.huatu.tb.question.dao.impl.RQuestionDaoImpl;
import com.huatu.tb.question.model.Question;

/**   
 * 类名称：				MoniTiPaperService  
 * 类描述：  			模拟题定时缓存Service
 * 创建人：				LiXin
 * 创建时间：			2015年6月20日 上午10:01:08  
 * @version 		1.0
 */
@Service
public class RMoniTiPaperService {
	public  Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private IRedisService iRedisService;
	
	@Autowired
	private TaskVersionService taskVersionService;
	
	@Autowired
	private RQuestionDaoImpl questionRestDao;

	@Autowired
	private RPaperDaoImpl paperRestDao;
	@Autowired
	private RCategoryDaoImpl categoryRestDao;
	/**
	 *增量缓存模拟题列表和试卷的试题打包
	 * refreshQuesToCate  
	 * @exception 
	 * @return
	 * @throws HttbException
	 */
	@SuppressWarnings("unchecked")
	public void refreshMoniTiList(){
		boolean flag = false;
		/**新的模拟题列表集合*/
		List<Paper> newpaperList = new ArrayList<Paper>();
		/**待更新模拟试卷ids*/
		List<String> updatePids = new ArrayList<String>();
		/**待新增模拟试卷ids*/
		List<String> newPids = new ArrayList<String>();
		
		//1 获取模拟题在redis中的版本集合
		Map<String,String> moredisMap = this.getMoniPaoperVerGather();
		
		//2获取模拟题在nosql中的版本集合
		List<TaskVersion> moVerList = this.getMoniVerInfoList();
		
		//3 遍历redis中集合  --- 判断在nosql中key不存在即在redis中删除
		//教师端触发试卷下线 删除的操作
		/**3.1 redis 试卷Id集合*/
		  Set<String> redisSet = new HashSet<String>();
		  for(String paperId : moredisMap.keySet()){
			  redisSet.add(paperId);
		  }
		/**3.2 Nosql 试卷Id集合*/
			Set<String> nosqlSet = new HashSet<String>();
			for(TaskVersion tv : moVerList){
				nosqlSet.add(tv.getTkey());
			}
		/**3.3 去除已经删除的【nosql中不存在的】*/
		for(String key : redisSet){
			if(!nosqlSet.contains(key)){
				moredisMap.remove(key);//列表版本集合删除
				try {
					iRedisService.remove(TaskMarkKeyUtil.PAPER_+key);
				} catch (HttbException e) {
					log.error("在redis中删除试卷失败");
					e.printStackTrace();
				}
			}
		}
		
		//4 遍历nosql中的模拟题版本新信息
		//教师端触发 试卷重新发版和新发布
		for(TaskVersion tv : moVerList){
			//该试卷是否在redis中
			if(redisSet.contains(tv.getTkey())){
				String redisVer = moredisMap.get(tv.getTkey());
				//存在后判断 版本号是否一致
				if(!tv.getTvalue().equals(redisVer)){
					//版本不一致 为需更新的
					updatePids.add(tv.getTkey());
					//更新--模拟题题版本集合在redis中的版本map集合
					moredisMap.put(tv.getTkey(), tv.getTvalue());
				}
			}else{
				//在redis中不存在 则为新发布试卷
				newPids.add(tv.getTkey());
				//新增 --模拟题题版本集合在redis中的版本map集合
				moredisMap.put(tv.getTkey(), tv.getTvalue());
			}
		}
		
		//5将版本一致的试卷列表保留下来
		for(Paper paper : this.getPaperListToredis()){
			if(!updatePids.contains(paper.getPid())){
				newpaperList.add(paper);
			}
		}
		//6加入 更新和新增的试卷列表
		List<String> newupdatepids = new ArrayList<String>();
		newupdatepids.addAll(newPids);
		newupdatepids.addAll(updatePids);
		try {
			/**存在模拟题版本map集合*/
			iRedisService.put(TaskMarkKeyUtil.MONI_VER_GATHER,moredisMap);
			
			List<Paper> xinzengPaperList =  paperRestDao.getPaperListbyids(newupdatepids);
			newpaperList.addAll(xinzengPaperList);
			//缓存试卷列表
			// 根据排序字段排序
			ComparatorToPaper comparator = new ComparatorToPaper();
			Collections.sort(newpaperList, comparator);//根据排序字段排序
			this.setMoniListToRedis(newpaperList);
			//一下方法过滤后再存  被上面一行替代
			//iRedisService.put(TaskMarkKeyUtil.MONI_PAPER_LIST,newpaperList);
			
			for(Paper paper : xinzengPaperList){
				/**单个试卷的试题包*/
				List<QuestionVo> qvlist = new ArrayList<QuestionVo>();
				
				List<Question> qList = questionRestDao.getQuestionResultSet(paper.getPqids());
				for(Question q : qList){
					List<QuestionVo> getList = ModelToVoUtil.questtionToVo(q);
					qvlist.addAll(getList);
				}
				
				//压缩打包
				JSONArray json = JSONArray.fromObject(qvlist) ;
				iRedisService.put(TaskMarkKeyUtil.PAPER_+paper.getPid(), CompressUtil.gzip(json.toString()));
			}
		} catch (HttbException e) {
			log.error("获取增量试卷列表-缓存模拟题列表 失败");
			e.printStackTrace();
		}
	}
	/** 1
	 * 获取redis中模拟题版本集合
	 * getMoniPaoperVerGather  
	 * @exception 
	 * @return
	 */
	private Map<String,String> getMoniPaoperVerGather(){
		Map<String,String> moniVerGather = new HashMap<String, String>();
		try {
			Object obj = iRedisService.get(TaskMarkKeyUtil.MONI_VER_GATHER);
			if(CommonUtil.isNotNull(obj)){
				try{
					moniVerGather = (Map<String, String>) obj;
				}catch(Exception e){
					log.error("获取redis中模拟题版本集合失败 原因：redis中对象转map失败,\n"
							+ "增量更新失败，应急启动全部更新");
					
					e.printStackTrace();
				}
				
			}
		} catch (HttbException e) {
			log.error("获取redis中模拟题版本集合失败");
			e.printStackTrace();
		}
		return moniVerGather;
		
	}
	/** 2
	 * 获取数据库中 模拟题试题版本
	 * getMoniVerInfoList  
	 * @exception 
	 * @return
	 */
	private List<TaskVersion> getMoniVerInfoList(){
		List<TaskVersion> tvList = new ArrayList<TaskVersion>();
		try{
			Map<String,Object> filter = new HashMap<String, Object>();
			filter.put("tpapertype", "1");//模拟题标识
			tvList = taskVersionService.getVersionResultSet(filter);
		}catch(Exception e){
			log.error("获取数据库中 模拟题试题版本信息失败");
			e.printStackTrace();
		}
		return tvList;
	}
	/** 3
	 * 获取redis中的缓存数据
	 * getPaperListToredis  
	 * @exception 
	 * @return
	 */
	private List<Paper> getPaperListToredis(){ 
		List<Paper> list = new ArrayList<Paper>();
		try {
			Object obj = iRedisService.get(TaskMarkKeyUtil.MONI_PAPER_LIST);
			if(CommonUtil.isNotNull(obj)){
				try{
					list =  (List<Paper>) obj;
				}catch(Exception e){
					log.error("获取redis中模拟题列表失败 原因：redis中对象转map失败,\n"
							+ "增量更新失败，应急启动全部更新");
					
					e.printStackTrace();
				}
				
			}
		} catch (HttbException e) {
			log.error("获取redis中模拟题版本集合失败");
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * @throws HttbException 
	 * 往redis中写入模拟题list
	 * setMoniListToRedis  
	 * @exception
	 */
	private void setMoniListToRedis(List<Paper> pList) throws HttbException{
		List<CateQues> cqList = new ArrayList<CateQues>();
		Object obj = iRedisService.get(TaskMarkKeyUtil.CATE_QUES_KEY);
		if(CommonUtil.isNotNull(obj)){
			try{
				cqList = (List<CateQues>) obj;
			}catch(Exception e){
				cqList = categoryRestDao.getCateQuesList();
				//将关系保存起来
				iRedisService.put(TaskMarkKeyUtil.CATE_QUES_KEY, cqList);
			}
		}else{
			cqList = categoryRestDao.getCateQuesList();
			//将关系保存起来
			iRedisService.put(TaskMarkKeyUtil.CATE_QUES_KEY, cqList);
		}
		for(Paper paper : pList){
			List<String> qids = new ArrayList<String>();//所有的Id集合   复合题取子题
			
			for(String qid : paper.getPqids()){
				//遍历关系
				for(CateQues cq : cqList){
					if(qid.equals(cq.getQid())){
						if(CommonUtil.isNotNull(cq.getQcids())){
							qids.addAll(cq.getQcids());//复合题
						}else{
							qids.add(cq.getQid());//单选题
						}
						break;
					}
				}
			}
			paper.setPqids(qids);//重新放置数据
		}
		iRedisService.put(TaskMarkKeyUtil.MONI_PAPER_LIST,pList);
	}
}
