/**
 * 项目名：				httb
 * 包名：				com.huatu.jms.service.Impl
 * 文件名：				AnalyzeServiceImpl.java
 * 日期：				2015年7月8日-下午7:03:40
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.jms.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.service.HtquesanalysisService;
import com.huatu.analysis.service.HtsxrankService;
import com.huatu.analysis.util.AnaCommonUtil;
import com.huatu.api.po.PaperPo;
import com.huatu.api.po.QuestionPo;
import com.huatu.api.service.AnalyzeApiService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.jms.model.RankMessage;
import com.huatu.jms.service.AnalyzeJmsService;

/**
 * 类名称：				AnalyzeJmsServiceImpl
 * 类描述：				分析
 * 创建人：				Aijunbo
 * 创建时间：				2015年7月8日 下午7:03:40
 * @version 			0.0.1
 */
@Service
public class AnalyzeJmsServiceImpl implements AnalyzeJmsService{
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private AnalyzeApiService analyzeApiService;
	@Autowired
	private HtsxrankService htsxrankService;
	@Autowired
	private IRedisService iRedisService;
	@Autowired
	private HtquesanalysisService htquesanalysisService;

	/**
	 *
	 * AnalysSaveResult				(保存分析结果)
	 * 								(JMS监听器自动调用)
	 * @param 		testpaperObj	顺序答题结果集
	 * @return
	 * @throws HttbException
	 * @throws InterruptedException
	 */
	@Override
	public void AnalysSaveResult(PaperPo testpaperObj) throws HttbException, InterruptedException {
		int type= Integer.parseInt(testpaperObj.getType());
		if(type ==0 || type == 1){
			try {
				/**随机练习，顺序练习*/
				//保存 答题记录
				analyzeApiService.saveDati(testpaperObj);
				// 保存错题
				analyzeApiService.saveMyErrorQues(testpaperObj);
				//顺序答题
				if(type == 1){
					//顺序排名消息队列
					boolean isrankSend = iRedisService.rpush(Constants.JMS_ANALYZE_RANK, testpaperObj);
					if(!isrankSend){
						log.warn("发送顺序排名消息时发生异常");
					}
				}


			} catch (HttbException e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass()+"保存测试结果时发生异常", e);
			}
		}else{
			try {
				/**真题 模拟题*/
				//1 保存考试
				analyzeApiService.saveKaoshi(testpaperObj);
				//2保存 答题记录
				analyzeApiService.saveDati(testpaperObj);
				//3 保存错题
				analyzeApiService.saveMyErrorQues(testpaperObj);

				//试卷分析消息队列
				boolean ispaperSend = iRedisService.rpush(Constants.JMS_ANALYZE_PAPER, testpaperObj);
				if(!ispaperSend){
					log.warn("发送试卷分析消息时发生异常");
				}

			} catch (HttbException e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass()+"保存考试结果时发生异常", e);
			}
		}
		//试题分析消息队列
		boolean isquesSend = iRedisService.rpush(Constants.JMS_ANALYZE_QUES, testpaperObj);
		if(!isquesSend){
			log.warn("发送试题分析消息时发生异常");
		}
	}

	/**
	 *
	 * sendMessage					(发送消息顺序答题分析消息)
	 * 								(保存顺序答题时调用)
	 * @param 		rankMessage		顺序答题消息对象
	 * @return
	 */
	@Override
	public boolean sendMessage(final RankMessage rankMessage) {
		//消息发送成功标识(默认成功)
		boolean flag = true;
		try {
			//调用jmsTemplate发送消息
			/*jmsTemplate.send(queueDestination, new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createObjectMessage((Serializable) rankMessage);
				}
			});*/
		} catch (Exception e) {
			flag = false;
			throw e;
		}
		return flag;
	}

	/**
	 *
	 * getQueshis					(转换答题结果记录)
	 * @param 		testpaper		答题结果
	 * @return
	 */
	@Override
	public List<Htqueshis> getQueshis(PaperPo testpaper) {
		int type = 0;// 0 随机练习 1 顺序练习 2 模拟题 3 真题 ---app
		/** 试题类型(0=>真题,1=>模拟,2=>顺序,3=>模块) --- nosql */
		List<String> qhpoint = new ArrayList<String>();// 知识点

		if ("0".equals(testpaper.getType())) {
			if (CommonUtil.isNotNull(testpaper.getPoint())) {
				qhpoint.add(testpaper.getPoint());
			}
			type = 3;
		} else if ("1".equals(testpaper.getType())) {
			if (CommonUtil.isNotNull(testpaper.getPoint())) {
				qhpoint.add(testpaper.getPoint());
			}
			type = 2;
		} else if ("2".equals(testpaper.getType())) {
			type = 1;
		} else {
			type = 0;
		}
		/** 用户提交的答案记录 */
		List<Htqueshis> queList = new ArrayList<Htqueshis>();
		for (QuestionPo qp : testpaper.getQueslist()) {
			Htqueshis hqs = new Htqueshis();
			hqs.setQhid(CommonUtil.getUUID());
			/** 用户ID */
			hqs.setQhuid(qp.getUserno());
			/** 试题ID */
			hqs.setQhqid(qp.getQid());
			String qans = "";
			for (String qa : qp.getQusanswer()) {
				qans += qa;
			}
			hqs.setQhqans(qans);
			String uans = "";
			for (String qa : qp.getUseranswers()) {
				uans += qa;
			}
			hqs.setQhuans(uans);
			/** 试题类型(0=>真题,1=>模拟,2=>顺序,3=>模块) */
			hqs.setQhtype(type);
			/** 知识点id */
			hqs.setQhpoint(qhpoint);
			// 时间戳
			hqs.setQrecorddate(AnaCommonUtil.getRecordDate());
			/** 创建时间 */
			hqs.setCreatetime(new Date());

			// 是否错误 0- 错误 1 正确 -1 为空
			if (!"-1".equals(qp.getIserror())) {// 为空不填写
				/** nosql 是否正确(0=>正确，1=>不正确) */
				if ("1".equals(qp.getIserror())) {
					hqs.setQhisright(0);
				} else {
					hqs.setQhisright(1);
				}
				queList.add(hqs);
			}

		}
		return queList;
	}


}
