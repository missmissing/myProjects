/**
 * 项目名：				httb
 * 包名：				com.huatu.jms.listener
 * 文件名：				MyAnalyzeListener.java
 * 日期：				2015年7月23日-上午11:03:48
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.jms.listener;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.service.HtsxrankService;
import com.huatu.api.po.PaperPo;
import com.huatu.api.service.AnalyzeApiService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Message;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.Constants;
import com.huatu.jms.service.AnalyzeJmsService;

/**
 * 类名称：				MyAnalyzeListener
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年7月23日 上午11:03:48
 * @version 			0.0.1
 */
@Component
public class AnalyzeRankListener {
	public Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private IRedisService iRedisService;
	@Autowired
	private AnalyzeJmsService analyzeJmsService;
	@Autowired
	private AnalyzeApiService analyzeApiService;
	@Autowired
	private HtsxrankService htsxrankService;

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
				if(iRedisService.exists(Constants.JMS_ANALYZE_RANK)){
					PaperPo paperObj = null;
					try {
						//消息对象
						paperObj = (PaperPo) iRedisService.lpop(Constants.JMS_ANALYZE_RANK);
					} catch (HttbException e) {
						throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass()+"获取redis消息队列时发生异常", e);
					}
					//如果消息对象不为空，则进行保存分析
					if(paperObj != null ){
						analyzeRank(paperObj);
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
	 *
	 * listen						(监听方法)
	 * 								(JMS监听器自动调用)
	 * @param 		testpaperObj	顺序答题结果集
	 * @return
	 * @throws 		HttbException
	 * @throws 		InterruptedException
	 */
	private void analyzeRank(PaperPo paperObj) throws HttbException, InterruptedException {
		boolean lockFlag = false;

		//锁标记
		boolean lock = iRedisService.exists(Constants.ANALYSIS_RANK_LOCK);
		//有效时间
		Long ttl = iRedisService.getTTL(Constants.ANALYSIS_RANK_LOCK);
		//需要在最外层套一个while循环是
		//如果几个线程同时启动，获取排它锁显示为空，会出现几个同时设置排它锁，但是中间只有一个会成功
		while(!lockFlag){
			//如果没有被锁住则刷新缓存排名
			if(!lock || ttl < 0){
				//加锁排序
				lockFlag = lock4Rank(paperObj).isSuccess();
			}else{
				//有排它锁存在且有效，则等待排它锁失效
				while (lockFlag && ttl > 0) {
					lockFlag = iRedisService.exists(Constants.ANALYSIS_RANK_LOCK);
					ttl = iRedisService.getTTL(Constants.ANALYSIS_RANK_LOCK);
					//等待有效期除以2
					try {
						Thread.sleep(Constants.ANALYSIS_LOCK_EXPIRE/2);
					} catch (InterruptedException e) {
						throw new HttbException(ModelException.JAVA_THREAD_EXCEPTION, this.getClass()+"当前线程等待时发生异常", e);
					}
				}
				//加锁排序
				lockFlag = lock4Rank(paperObj).isSuccess();
			}
			Thread.sleep(Constants.ANALYSIS_LOCK_EXPIRE/2);
		}
	}

	/**
	 * lock4Rank					(锁住顺序排序缓存进行排序)
	 * @param 		testpaperObj	答题对象
	 * @throws 		HttbException
	 */
	private Message lock4Rank(PaperPo paperObj) throws HttbException {
		Message message = new Message();
		//设置排他锁
		boolean flag  = iRedisService.putEX(Constants.ANALYSIS_RANK_LOCK, "true", Constants.ANALYSIS_LOCK_EXPIRE);
		message.setSuccess(flag);
		if(flag){
			//答题记录集合
			List<Htqueshis> htqueshis = analyzeJmsService.getQueshis(paperObj);

			//保存排名
			int rank = htsxrankService.saveSXRank(htqueshis);
			//返回排名
			message.setData(rank);
			log.info(paperObj.getUserno()+"当前排名："+rank);
		}
		return message;
	}
}
