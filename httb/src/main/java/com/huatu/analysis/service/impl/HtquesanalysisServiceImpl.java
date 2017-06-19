/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.service.impl
 * 文件名：				HtquesanalysisServiceImpl.java
 * 日期：				2015年6月22日-下午4:32:53
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.analysis.dao.HtquesanalysisDao;
import com.huatu.analysis.model.Htquesanalysis;
import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.service.HtquesanalysisService;
import com.huatu.api.version.model.TaskVersion;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;

/**
 * 类名称：				HtquesanalysisServiceImpl
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月22日 下午4:32:53
 * @version 			0.0.1
 */
@Service
public class HtquesanalysisServiceImpl implements HtquesanalysisService {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private HtquesanalysisDao htquesanalysisDao;
	@Autowired
	private TaskVersionService taskVersionService;
	@Autowired
	private IRedisService iRedisService;

	@Override
	public boolean save(Htquesanalysis htquesanalysis) throws HttbException {
		return htquesanalysisDao.save(htquesanalysis);
	}

	@Override
	public boolean savebatch(List<Htquesanalysis> htquesanalysis)
			throws HttbException {
		return htquesanalysisDao.savebatch(htquesanalysis);
	}

	@Override
	public boolean refreshQuesAnalysis(List<Htqueshis> queslist, String qrecorddate) throws HttbException{
		//把答题记录集合按试卷分好(试题id，答题记录集合)
		Map<String, List<Htqueshis>> queshisMap = new HashMap<String, List<Htqueshis>>();
		if(CommonUtil.isNotNull(queslist)){
			for (int i = 0; i < queslist.size(); i++) {
				//答题记录
				Htqueshis htqueshis = queslist.get(i);
				//如果map中已存在，则直接添加
				List<Htqueshis> mapQuesList = null;
				if(queshisMap.get(queslist.get(i).getQhqid()) != null){
					//从map中取出
					mapQuesList = queshisMap.get(htqueshis.getQhqid());
				}else{
					//从map中取出
					mapQuesList = new ArrayList<Htqueshis>();
				}
				//保存到试题集合中
				mapQuesList.add(htqueshis);
				//存入map中
				queshisMap.put(htqueshis.getQhqid(), mapQuesList);
			}
		}
		//统计分析试题集合
		Map<String, Htquesanalysis> analysisMap = analysisQueshis(queshisMap, qrecorddate);
		boolean flag  = updateQuesAnalysis(analysisMap);
		return flag;
	}

	/**
	 *
	 * analysisQueshis				(分析试题)
	 * @param 		quesMap			试题集合
	 * @param 		qrecorddate		试题版本
	 */
	private Map<String, Htquesanalysis> analysisQueshis(Map<String, List<Htqueshis>> queshisMap, String qrecorddate){
		//试题分析集合(试题id，试题分析集合)
		Map<String, Htquesanalysis> quesAnalysisMap = new HashMap<String, Htquesanalysis>();
		//遍历试题集合
		for (Map.Entry<String, List<Htqueshis>> entry:queshisMap.entrySet()) {
			//试题分析对象
			Htquesanalysis htquesanalysis = null;
			//遍历答题记录集合
			for (Htqueshis htqueshis :  entry.getValue()) {
				//如果是为空，则初始化
				if(htquesanalysis == null){
					//第一次则初始化
					htquesanalysis = new Htquesanalysis();
					//试题id
					htquesanalysis.setQid(htqueshis.getQhqid());
					//试题答案
					htquesanalysis.setQqans(htqueshis.getQhqans());
					htquesanalysis.setQansamount(1);
					int correntAmount = 0;
					//1表示答对
					if(htqueshis.getQhisright() == 1){
						correntAmount = 1;
					}
					htquesanalysis.setQcorrectamount(correntAmount);
					//用户答案
					String uans = htqueshis.getQhuans();
					if(uans != null){
						if(uans.indexOf("A") > -1 || uans.indexOf("a") > -1)
							htquesanalysis.setQchoicea(1);

						if(uans.indexOf("B") > -1 || uans.indexOf("b") > -1)
							htquesanalysis.setQchoiceb(1);

						if(uans.indexOf("C") > -1 || uans.indexOf("c") > -1)
							htquesanalysis.setQchoicec(1);

						if(uans.indexOf("D") > -1 || uans.indexOf("d") > -1)
							htquesanalysis.setQchoiced(1);

						if(uans.indexOf("E") > -1 || uans.indexOf("e") > -1)
							htquesanalysis.setQchoicee(1);

						if(uans.indexOf("F") > -1 || uans.indexOf("f") > -1)
							htquesanalysis.setQchoicef(1);

						if(uans.indexOf("G") > -1 || uans.indexOf("g") > -1)
							htquesanalysis.setQchoiceg(1);

						if(uans.indexOf("H") > -1 || uans.indexOf("h") > -1)
							htquesanalysis.setQchoiceh(1);
					}
					htquesanalysis.setQrecorddate(qrecorddate);
				}else{
					htquesanalysis.setQansamount(htquesanalysis.getQansamount()+1);
					//1表示答对
					if(htqueshis.getQhisright() == 1){
						htquesanalysis.setQcorrectamount(htquesanalysis.getQcorrectamount()+1);
					}

					//用户答案
					String uans = htqueshis.getQhuans();
					if(uans != null){
						if(uans.indexOf("A") > -1 || uans.indexOf("a") > -1)
							htquesanalysis.setQchoicea(htquesanalysis.getQchoicea()+1);

						if(uans.indexOf("B") > -1 || uans.indexOf("b") > -1)
							htquesanalysis.setQchoiceb(htquesanalysis.getQchoiceb()+1);

						if(uans.indexOf("C") > -1 || uans.indexOf("c") > -1)
							htquesanalysis.setQchoicec(htquesanalysis.getQchoicec()+1);

						if(uans.indexOf("D") > -1 || uans.indexOf("d") > -1)
							htquesanalysis.setQchoiced(htquesanalysis.getQchoiced()+1);

						if(uans.indexOf("E") > -1 || uans.indexOf("e") > -1)
							htquesanalysis.setQchoicee(htquesanalysis.getQchoicee()+1);

						if(uans.indexOf("F") > -1 || uans.indexOf("f") > -1)
							htquesanalysis.setQchoicef(htquesanalysis.getQchoicef()+1);

						if(uans.indexOf("G") > -1 || uans.indexOf("g") > -1)
							htquesanalysis.setQchoiceg(htquesanalysis.getQchoiceg()+1);

						if(uans.indexOf("H") > -1 || uans.indexOf("h") > -1)
							htquesanalysis.setQchoiceh(htquesanalysis.getQchoiceh()+1);
					}
				}
			}
			//错误率排名
			String[] errorOrder = analysisChoice(htquesanalysis);
			htquesanalysis.setQmostwrong(errorOrder[0]);
			htquesanalysis.setQsecendwrong(errorOrder[1]);

			quesAnalysisMap.put(entry.getKey(), htquesanalysis);
		}
		return quesAnalysisMap;
	}

	/**
	 *
	 * updateQuesAnalysis			(修改试题分析数据)
	 * 								(每天定时更新试题分析表时调用)
	 * @param 		analysisMap		新增加的试题分析集合
	 * @throws 		HttbException
	 */
	private boolean updateQuesAnalysis(Map<String, Htquesanalysis> analysisMap) throws HttbException{
		boolean flag = true;
		try {
			if(analysisMap != null && analysisMap.size() > 0){
				List<String> qids = new ArrayList<String>();
				qids.addAll(analysisMap.keySet());
				//查询数据库中对应的试题分析结果
				List<Htquesanalysis> analysisList = getListByQidsRedis(qids);
				List<Htquesanalysis> quesanalysis = new ArrayList<Htquesanalysis>();
				if(CommonUtil.isNotNull(analysisList)){
					//遍历数据库中已有值的分析集合
					for (int i = 0; i < analysisList.size(); i++) {
						//数据库中原试题分析
						Htquesanalysis oldAnalysis = analysisList.get(i);
						//新增试题分析
						Htquesanalysis newAnalysis = analysisMap.get(oldAnalysis.getQid());
						//答题总数
						newAnalysis.setQansamount(newAnalysis.getQansamount()+oldAnalysis.getQansamount());
						//答对次数
						newAnalysis.setQcorrectamount(newAnalysis.getQcorrectamount()+oldAnalysis.getQcorrectamount());
						newAnalysis.setQchoicea(newAnalysis.getQchoicea()+oldAnalysis.getQchoicea());
						newAnalysis.setQchoiceb(newAnalysis.getQchoiceb()+oldAnalysis.getQchoiceb());
						newAnalysis.setQchoicec(newAnalysis.getQchoicec()+oldAnalysis.getQchoicec());
						newAnalysis.setQchoiced(newAnalysis.getQchoiced()+oldAnalysis.getQchoiced());
						newAnalysis.setQchoicee(newAnalysis.getQchoicee()+oldAnalysis.getQchoicee());
						newAnalysis.setQchoicef(newAnalysis.getQchoicef()+oldAnalysis.getQchoicef());
						newAnalysis.setQchoiceg(newAnalysis.getQchoiceg()+oldAnalysis.getQchoiceg());
						newAnalysis.setQchoiceh(newAnalysis.getQchoiceh()+oldAnalysis.getQchoiceh());
						//错误率排名
						String[] errorOrder = analysisChoice(newAnalysis);
						newAnalysis.setQmostwrong(errorOrder[0]);
						newAnalysis.setQsecendwrong(errorOrder[1]);
						//设置到分析数组中
						analysisMap.put(newAnalysis.getQid(), newAnalysis);
					}
				}//把Map装在到List中
				quesanalysis.addAll(analysisMap.values());
				//批量保存到数据库中
				flag = savebatch(quesanalysis);

				//保存到redis中
				if(CommonUtil.isNotNull(quesanalysis)){
					for (int i = 0; i < quesanalysis.size(); i++) {
						//试题分析存入redis中
						boolean issave = iRedisService.hset(Constants.ANALYSIS_QUES_DATA, quesanalysis.get(i).getQid(), quesanalysis.get(i));
						if(!issave){
							log.warn("试题ID："+quesanalysis.get(i).getQid()+"的试题分析存入redis缓存失败");
						}
					}
				}
			}
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_STRING_DECODE, this.getClass()+"修改试题分析表时发生异常", e);
		}

		return flag;
	}

	public String[] analysisChoice(Htquesanalysis htquesanalysis){
		//错误率最高选项
		String[] mostError = new String[2];
		//正确选项
		char[] correct = null;
		if(htquesanalysis.getQqans() != null){
			correct = htquesanalysis.getQqans().toCharArray();
		}

		//选项数组
		List<String> choices_num = new ArrayList<String>();
		choices_num.add("H_"+ htquesanalysis.getQchoiceh());
		choices_num.add("G_"+ htquesanalysis.getQchoiceg());
		choices_num.add("F_"+ htquesanalysis.getQchoicef());
		choices_num.add("E_"+ htquesanalysis.getQchoicee());
		choices_num.add("D_"+ htquesanalysis.getQchoiced());
		choices_num.add("C_"+ htquesanalysis.getQchoicec());
		choices_num.add("B_"+ htquesanalysis.getQchoiceb());
		choices_num.add("A_"+ htquesanalysis.getQchoicea());

		//选项数组
		List<String> choices = new ArrayList<String>();
		choices.add("H");
		choices.add("G");
		choices.add("F");
		choices.add("E");
		choices.add("D");
		choices.add("C");
		choices.add("B");
		choices.add("A");

		if(correct != null){
			//移除正确选项
			for (char choice : correct) {
				//对应下标
				int index = choices.indexOf(choice+"");
				if(index >= 0){
					choices.remove(index);
					choices_num.remove(index);
				}
			}
		}


		//根据选择次数倒序排序
		Collections.sort(choices_num, new Comparator<String>() {
			@Override
			public int compare(String str1, String str2) {
				int count1 = Integer.parseInt(str1.split("_")[1]);
				int count2 = Integer.parseInt(str2.split("_")[1]);
				if (count1 < count2) {
					return 1;
				} else {
					return -1;
				}
			}
		});

		//错误率最高的
		mostError[0] = choices_num.get(0).split("_")[0];
		//错误率第二高的
		mostError[1] = choices_num.get(1).split("_")[0];
		return mostError;
	}


	@Override
	public List<Htquesanalysis> getListByQids(List<String> qids)
			throws HttbException {
		return htquesanalysisDao.getListByQids(qids);
	}

	/**
	 *
	 * getListByQidsRedis			(从redis中获取试题分析)
	 * @param 		qids			试题ID集合
	 * @return
	 * @throws 		HttbException
	 */
	public List<Htquesanalysis> getListByQidsRedis(List<String> qids) throws HttbException{
		//试题分析集合
		List<Htquesanalysis> quesList = new ArrayList<Htquesanalysis>();
		//遍历试题ID集合
		for (int i = 0; i < qids.size(); i++) {
			try {
				//从redsis中获取试题分析对象
				Object quesOjb = iRedisService.hget(Constants.ANALYSIS_QUES_DATA, qids.get(i));
				//缓存中有，则从缓存中取
				if(CommonUtil.isNotNull(quesOjb)){
					quesList.add((Htquesanalysis) quesOjb);
				}else{
					//缓存中没有则从数据库中取
					Htquesanalysis htquesanalysis = getQuesByQid(qids.get(i));
					if (CommonUtil.isNotNull(htquesanalysis)) {
						quesList.add(htquesanalysis);
					}
				}
			} catch (HttbException e) {
				throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass()+"获取试题分析时发生异常", e);
			}
		}

		return quesList;
	}

	public static void main(String[] args) {
		//选项数组
		List<String> choices_num = new ArrayList<String>();
		choices_num.add("H_0");
		choices_num.add("G_0");
		choices_num.add("F_0");
		choices_num.add("E_0");
		choices_num.add("D_0");
		choices_num.add("C_0");
		choices_num.add("B_0");
		choices_num.add("A_0");

		//根据选择次数倒序排序
		Collections.sort(choices_num, new Comparator<String>() {
			@Override
			public int compare(String str1, String str2) {
				int count1 = Integer.parseInt(str1.split("_")[1]);
				int count2 = Integer.parseInt(str2.split("_")[1]);
				if (count1 < count2) {
					return 1;
				} else {
					return -1;
				}
			}
		});

		for (int i = 0; i < choices_num.size(); i++) {
			System.out.println(choices_num.get(i));
		}
	}

	/**
	 *
	 * RefreshQuesAnalysis		(刷新试题版本和分析)
	 * 								(保存答题时调用)
	 * @param 		htqueshis		答题结果记录
	 * @param 		systemdate		系统时间
	 * @return
	 * @throws 		HttbException
	 */
	public boolean RefreshQuesAnalysis(List<Htqueshis> htqueshis, String systemdate) throws HttbException{
		boolean flag = false;
		//刷新试题分析
		flag = refreshQuesAnalysis(htqueshis, systemdate);
		//刷新成功后保存版本号
		if(flag){
			//创建版本号对象
			TaskVersion newversion = new TaskVersion();
			newversion.setTkey(Constants.ANALYSIS_VERSION_QUESTION);
			newversion.setTvalue(systemdate);
			newversion.setTdesc("试题分析版本号");
			newversion.setCreatetime(new Date());
			//设置数据库中版本号
	        taskVersionService.addVersion(newversion);
		}
		return flag;
	}

	@Override
	public Htquesanalysis getQuesByQid(String qid) throws HttbException {
		return htquesanalysisDao.getQuesByQid(qid);
	}
}
