/**
 * 项目名：				httb
 * 包名：				com.huatu.jms.listener
 * 文件名：				MyAnalyzeListener.java
 * 日期：				2015年7月23日-上午11:03:48
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.jms.listener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huatu.api.po.PaperPo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.Constants;
import com.huatu.jms.model.RankMessage;
import com.huatu.jms.service.AnalyzeJmsService;

/**
 * 类名称：				SaveRecordListener
 * 类描述：				保存答题记录
 * 创建人：				Aijunbo
 * 创建时间：			2015年7月23日 上午11:03:48
 * @version 			0.0.1
 */
@Component
public class SaveRecordListener {
	public Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private IRedisService iRedisService;

	@Autowired
	private AnalyzeJmsService analyzeJmsService;

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
				if(iRedisService.exists(Constants.JMS_RESULT_SAVE)){
					RankMessage rm = null;
					try {
						//消息对象
						rm = (RankMessage)iRedisService.lpop(Constants.JMS_RESULT_SAVE);
					} catch (HttbException e) {
						throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass()+"获取redis消息队列时发生异常", e);
					}
					//如果消息对象不为空，则进行保存分析
					if(rm != null && rm.getObject() != null){
						//保存提交结果
						analyzeJmsService.AnalysSaveResult((PaperPo) rm.getObject());
					}else{
						log.info("加载redis消息：保存提交结果失败");
					}
				}else{
					//System.out.println("redis没有队列消息，休眠2秒");
					Thread.sleep(2000);
				}
			} catch (HttbException | InterruptedException e) {
				e.printStackTrace();
				log.error(this.getClass()+"提交测试结果时发生异常");
			}
		}
	}

}
