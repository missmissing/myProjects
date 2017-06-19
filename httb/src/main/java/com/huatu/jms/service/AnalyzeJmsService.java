/**
 * 项目名：				httb
 * 包名：					com.huatu.jms.service
 * 文件名：				AnalyzeService.java
 * 日期：					2015年7月8日-下午6:45:01
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.jms.service;

import java.util.List;

import com.huatu.analysis.model.Htqueshis;
import com.huatu.api.po.PaperPo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;
import com.huatu.jms.model.RankMessage;

/**
 * 类名称：				AnalyzeJmsService
 * 类描述：  				分析模块service
 * 创建人：				Aijunbo
 * 创建时间：				2015年7月8日 下午6:45:01
 * @version 			0.0.1
 */
public interface AnalyzeJmsService {
	/**
	 *
	 * getQueshis					(转换答题结果记录)
	 * @param 		testpaper		答题结果
	 * @return
	 */
	public List<Htqueshis> getQueshis(PaperPo testpaper);

	/**
	 *
	 * AnalysSaveResult				((保存分析结果)
	 * 								(JMS监听器自动调用)
	 * @param 		testpaperObj	顺序答题结果集
	 * @throws 		HttbException
	 * @throws InterruptedException
	 */
	public void AnalysSaveResult(PaperPo testpaperObj) throws HttbException, InterruptedException;
	/**
	 *
	 * sendMessage					(发送消息顺序答题分析消息)
	 * 								(保存顺序答题时调用)
	 * @param 		rankMessage		顺序答题消息对象
	 * @return
	 */
	public boolean sendMessage(RankMessage rankMessage);
}
