/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.service.impl
 * 文件名：				HtexamanalysisServiceImpl.java
 * 日期：				2015年6月22日-下午4:30:43
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.analysis.dao.HtexamanalysisDao;
import com.huatu.analysis.model.Htexamanalysis;
import com.huatu.analysis.model.Htexamresult;
import com.huatu.analysis.service.HtexamanalysisService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;

/**
 * 类名称：				HtexamanalysisServiceImpl
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月22日 下午4:30:43
 * @version 			0.0.1
 */
@Service
public class HtexamanalysisServiceImpl implements HtexamanalysisService {
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private HtexamanalysisDao htexamanalysisDao;
	@Autowired
	private IRedisService iRedisService;

	@Override
	public boolean save(Htexamanalysis htexamanalysis) throws HttbException {
		return htexamanalysisDao.save(htexamanalysis);
	}

	@Override
	public boolean savebatch(List<Htexamanalysis> htexamanalysis)
			throws HttbException {
		return htexamanalysisDao.savebatch(htexamanalysis);
	}

	@Override
	public List<Htexamanalysis> getListByEids(List<String> eids)
			throws HttbException {
		return htexamanalysisDao.getListByEids(eids);
	}

	/**
	 *
	 * getListByEidsRedis			(根据试卷ID集合从缓存中加载试卷分析)
	 * @param 		eids			试卷ID集合
	 * @return
	 * @throws 		HttbException
	 */
	public List<Htexamanalysis> getListByEidsRedis(List<String> eids)
			throws HttbException {
		//试卷分析集合
		List<Htexamanalysis> list = new ArrayList<Htexamanalysis>();
		if(CommonUtil.isNotNull(eids)){
			//遍历试卷ID集合
			for (int i = 0; i < eids.size(); i++) {
				//从缓存中获取
				Object paperOjb = iRedisService.hget(Constants.ANALYSIS_PAPER_DATA, eids.get(i));
				//添加到试卷分析集合
				if(CommonUtil.isNotNull(paperOjb)){
					list.add((Htexamanalysis)paperOjb);
				}else{
					Htexamanalysis htexamanalysis =  htexamanalysisDao.getHtexamanalysis(eids.get(i));
					if(CommonUtil.isNotNull(htexamanalysis)){
						list.add(htexamanalysis);
					}
				}
			}
		}
		return htexamanalysisDao.getListByEids(eids);
	}

	@Override
	public boolean refreshExamAnalysis(List<Htexamresult> exams, String qrecorddate) throws HttbException{
		//把考试结果集合按试卷分好(试题id，试卷结果集合)
		Map<String, List<Htexamresult>> examsMap = new HashMap<String, List<Htexamresult>>();
		if(CommonUtil.isNotNull(exams)){
			for (int i = 0; i < exams.size(); i++) {
				//考试结果
				Htexamresult htexamresult = exams.get(i);
				//如果map中已存在，则直接添加
				List<Htexamresult> examList = null;
				if(examsMap.get(exams.get(i).getReid()) != null){
					//从map中取出
					examList = examsMap.get(htexamresult.getReid());
				}else{
					//从map中取出
					examList = new ArrayList<Htexamresult>();
				}
				//保存到考试集合中
				examList.add(htexamresult);
				//存入map中
				examsMap.put(htexamresult.getReid(), examList);
			}
		}

		Map<String, Htexamanalysis> analysisMap = analysisExam(examsMap, qrecorddate);
		boolean flag  = updateExamAnalysis(analysisMap);
		return flag;
	}

	/**
	 *
	 * analysisExam					(试卷分析)
	 * @param 		examsMap		试卷集合
	 * @param 		qrecorddate		试卷版本
	 */
	private Map<String, Htexamanalysis> analysisExam(Map<String, List<Htexamresult>> examsMap, String qrecorddate){
		//把考试结果分析集合按试卷分好(试题id，试卷结果分析集合)
		Map<String, Htexamanalysis> examsAnalysisMap = new HashMap<String, Htexamanalysis>();
		//遍历试卷结果集合
		for (Map.Entry<String, List<Htexamresult>> entry:examsMap.entrySet()) {
			//同一试卷的答题记录
			List<Htexamresult> examList = entry.getValue();
			//该试卷答题次数
			int ansCount = examList.size();
			//总分
			float totalScore = 0;
			//平均分
			float avScore = 0;
			//总耗时
			int totalTime = 0;
			//平均耗时
			int avTime = 0;
			//遍历集合
			for (int e = 0; e < examList.size(); e++) {
				Htexamresult exam = examList.get(e);
				//累加试卷得分
				totalScore += exam.getRexamresult();
				//累加试卷耗时
				totalTime += exam.getRexamconsume();
			}
			//平均分
			avScore = totalScore/ansCount;
			//平均耗时
			avTime = totalTime/ansCount;
			Htexamanalysis htexamanalysis = new Htexamanalysis();
			//试卷id
			htexamanalysis.setEid(entry.getKey());
			//试卷平均分
			htexamanalysis.setEaveragescore(avScore);
			//试卷平均耗时
			htexamanalysis.setEaveragetime(avTime);
			//试卷答题数
			htexamanalysis.setEansamount(ansCount);
			//设置版本号
			htexamanalysis.setQrecorddate(qrecorddate);
			//设置到集合中
			examsAnalysisMap.put(entry.getKey(), htexamanalysis);
		}
		return examsAnalysisMap;
	}

	/**
	 *
	 * updateExamAnalysis			(刷新数据库中试卷分析缓存)
	 * 								(每天定时更新试卷分析表时调用)
	 * @param 		analysisMap		新增加的试卷分析集合
	 * @throws 		HttbException
	 */
	private boolean updateExamAnalysis(Map<String, Htexamanalysis> analysisMap) throws HttbException{
		boolean flag = true;
		if(analysisMap != null && analysisMap.size() > 0){
			List<String> eids = new ArrayList<String>();
			eids.addAll(analysisMap.keySet());
			//查询数据库中对应的试卷答题结果
			List<Htexamanalysis> analysisList = getListByEidsRedis(eids);

			//答卷总数
			int totalCount = 0;
			//原答卷总数
			int oldCount = 0;
			//新答卷总数
			int newCount = 0;

			//试卷总分
			long totalScore = 0;
			//试卷原平均分
			float oldScore = 0;
			//试卷新平均分
			float newScore = 0;

			//试卷总耗时
			long totalTime = 0;
			//试卷原平均耗时
			int oldTime = 0;
			//试卷新平均耗时
			int newTime = 0;
			//遍历数据库中已有值的分析集合
			for (int i = 0; i < analysisList.size(); i++) {
				//数据库中原试卷分析
				Htexamanalysis oldAnalysis = analysisList.get(i);
				//新增试卷分析
				Htexamanalysis newAnalysis = analysisMap.get(oldAnalysis.getEid());

				oldCount = oldAnalysis.getEansamount();
				newCount = newAnalysis.getEansamount();;
				oldScore = oldAnalysis.getEaveragescore();
				newScore = newAnalysis.getEaveragescore();
				oldTime = oldAnalysis.getEaveragetime();
				newTime = newAnalysis.getEaveragetime();

				//总答题数
				totalCount = oldCount+newCount;
				//总分
				totalScore = (long) (oldScore*oldCount+newScore*newCount);
				//总耗时
				totalTime = oldTime*oldCount+newTime*newCount;

				newAnalysis.setEansamount(totalCount);
				newAnalysis.setEaveragescore(totalScore/totalCount);
				newAnalysis.setEaveragetime((int) (totalTime/totalCount));
				//设置到分析数组中
				analysisMap.put(newAnalysis.getEid(), newAnalysis);
			}
			List<Htexamanalysis> htexamanalysis = new ArrayList<Htexamanalysis>();
			//把Map装在到List中
			htexamanalysis.addAll(analysisMap.values());
			//批量保存到数据库中
			flag = savebatch(htexamanalysis);

			//保存到redis中
			if(CommonUtil.isNotNull(htexamanalysis)){
				for (int i = 0; i < htexamanalysis.size(); i++) {
					//试题分析存入redis中
					boolean issave = iRedisService.hset(Constants.ANALYSIS_PAPER_DATA, htexamanalysis.get(i).getEid(), htexamanalysis.get(i));
					if(!issave){
						log.warn("试题ID："+htexamanalysis.get(i).getEid()+"的试卷分析存入redis缓存失败");
					}
				}
			}
		}
		return flag;
	}

	@Override
	public Htexamanalysis getHtexamanalysis(String examId) throws HttbException {
		return htexamanalysisDao.getHtexamanalysis(examId);
	}

}
