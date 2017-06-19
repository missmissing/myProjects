/**
 * 项目名：				httb
 * 包名：				com.huatu.jms.listener
 * 文件名：				MyAnalyzeListener.java
 * 日期：				2015年7月23日-上午11:03:48
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.jms.listener;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huatu.analysis.model.Answerrecord;
import com.huatu.analysis.model.Htexamresult;
import com.huatu.analysis.service.HtexamanalysisService;
import com.huatu.analysis.service.HtexamresultService;
import com.huatu.analysis.util.AnaCommonUtil;
import com.huatu.api.po.PaperPo;
import com.huatu.api.po.QuestionPo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Message;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.jms.service.AnalyzeJmsService;

/**
 * 类名称：				AnalyzePaperListener
 * 类描述：				试卷分析监听器
 * 创建人：				Aijunbo
 * 创建时间：				2015年7月23日 上午11:03:48
 * @version 			0.0.1
 */
@Component
public class AnalyzePaperListener {
	public Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private IRedisService iRedisService;
	@Autowired
	private AnalyzeJmsService analyzeJmsService;
	@Autowired
	private HtexamanalysisService htexamanalysisService;
	@Autowired
	private HtexamresultService htexamresultService;

	/**
	 *
	 * listen						(监听redis的队列)
	 * 								(程序启动时调用)
	 * @throws 						HttbException
	 */
	public void listen() throws HttbException{
		//循环调用
		while (true) {
			try {
				if(iRedisService.exists(Constants.JMS_ANALYZE_PAPER)){
					PaperPo paperObj = null;
					try {
						//消息对象
						paperObj = (PaperPo) iRedisService.lpop(Constants.JMS_ANALYZE_PAPER);
					} catch (HttbException e) {
						throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass()+"获取redis消息队列时发生异常", e);
					}
					//如果消息对象不为空，则进行试题分析
					if(paperObj != null ){
						analyzePaper(paperObj);
					}else{
						log.info("加载redis消息：保存提交结果失败");
					}
				}else{
					Thread.sleep(2000);
				}
			} catch (HttbException | InterruptedException e) {
				e.printStackTrace();
				log.error(this.getClass()+"提交测试结果时发生异常");
			}
		}
	}

	/**
	 * analyzePaper					(分析试卷)
	 * 								(保存答题结果时调用)
	 * @param 		paperObj		答题结果
	 * @throws 		HttbException
	 * @throws InterruptedException
	 */
	private void analyzePaper(PaperPo paperObj) throws HttbException, InterruptedException {
		boolean lockFlag = false;

		//锁标记
		boolean lock = iRedisService.exists(Constants.ANALYSIS_PAPER_LOCK);
		//有效时间
		Long ttl = iRedisService.getTTL(Constants.ANALYSIS_PAPER_LOCK);
		//需要在最外层套一个while循环是
		//如果几个线程同时启动，获取排它锁显示为空，会出现几个同时设置排它锁，但是中间只有一个会成功
		while(!lockFlag){
			//如果没有被锁住则刷新缓存排名
			if(!lock || ttl < 0){
				//加锁排序
				lockFlag = lock4Paper(paperObj).isSuccess();
			}else{
				//有排它锁存在且有效，则等待排它锁失效
				while (lockFlag && ttl > 0) {
					lockFlag = iRedisService.exists(Constants.ANALYSIS_PAPER_LOCK);
					ttl = iRedisService.getTTL(Constants.ANALYSIS_PAPER_LOCK);
					//等待有效期除以2
					try {
						Thread.sleep(Constants.ANALYSIS_LOCK_EXPIRE/2);
					} catch (InterruptedException e) {
						throw new HttbException(ModelException.JAVA_THREAD_EXCEPTION, this.getClass()+"当前线程等待时发生异常", e);
					}
				}
				//加锁排序
				lockFlag = lock4Paper(paperObj).isSuccess();
			}
			Thread.sleep(Constants.ANALYSIS_LOCK_EXPIRE/2);
		}
	}


	/**
	 * lock4Ques					(加锁分析试题)
	 * @param 		paperObj		答题结果对象
	 * @return
	 * @throws 		HttbException
	 */
	private Message lock4Paper(PaperPo paperObj) throws HttbException {
		Message message = new Message();
		//设置排他锁
		boolean flag  = iRedisService.putEX(Constants.ANALYSIS_PAPER_LOCK, "true", Constants.ANALYSIS_LOCK_EXPIRE);
		message.setSuccess(flag);

		if(flag){
			//获取当前系统时间戳
			String systemdate = AnaCommonUtil.getRecordDate();
			List<Htexamresult> exams = new ArrayList<Htexamresult>();
			//获取试卷
			Htexamresult paper = getPaper(paperObj);
			//添加到集合中
			if(CommonUtil.isNotNull(paper)){
				exams.add(paper);
			}
			//刷新试卷分析
			htexamanalysisService.refreshExamAnalysis(exams, systemdate);
			//刷新成功后，删除锁标识
			iRedisService.remove(Constants.ANALYSIS_PAPER_LOCK);
		}
		return message;
	}

	/**
	 *
	 * getPaper						(根据答题记录获取试卷)
	 * @param 		testpaper		试卷
	 * @return
	 * @throws 		HttbException
	 */
	private Htexamresult getPaper(PaperPo testpaper) throws HttbException {
		int rightNum =0;
		Htexamresult hter = new Htexamresult();
		hter.setRid(CommonUtil.getUUID());
		hter.setRuid(testpaper.getUserno());/**用户ID*/
		hter.setReid(testpaper.getPid());/**关联的考试主键*/
		if("2".equals(testpaper.getType())){//类型  0 随机练习  1 顺序练习  2 模拟题  3 真题
			hter.setRexamtype("1");
		}else{
			hter.setRexamtype("0");
		}
		if(CommonUtil.isNotNull(testpaper.getSecond())){
			hter.setRexamconsume(Integer.parseInt(testpaper.getSecond()));
		}
		hter.setRstatus("1");
		hter.setQrecorddate(AnaCommonUtil.getRecordDate());
		/**用户提交的答案记录*/
		List<Answerrecord> ansList = new ArrayList<Answerrecord>();
		for(QuestionPo qp : testpaper.getQueslist()){
			Answerrecord answerrecord = new Answerrecord();
			answerrecord.setQid(qp.getQid());
			String qans ="";
			for(String qa : qp.getQusanswer()){
				qans+=qa;
			}
			answerrecord.setQans(qans);
			String uans ="";
			for(String qa : qp.getUseranswers()){
				uans+=qa;
			}
			answerrecord.setUans(uans);

			/**是否正确(0=>正确，1=>不正确)*/
			if("1".equals(qp.getIserror())){
				rightNum++;/**是否错误  0- 错误  1 正确 -1 为空*/
				answerrecord.setIsright("0");
			}else{
				answerrecord.setIsright("1");
			}
			ansList.add(answerrecord);
		}
		if(CommonUtil.isNotNull(testpaper.getQueslist())&&testpaper.getQueslist().size()>0){
			float source= (float)rightNum/testpaper.getQueslist().size();
			hter.setRexamresult((float)(Math.round(source*10000))/100);///**考试结果*/
		}
		return hter;
	}
}
