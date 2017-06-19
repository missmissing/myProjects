/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.init
 * 文件名：				InitSXRank.java
 * 日期：				2015年6月23日-上午10:43:54
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.testApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatu.analysis.init.InitQuesAnalysis;
import com.huatu.analysis.service.HtexamresultService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;

/**
 * 类名称：				InitSXRank
 * 类描述：  				初始化顺序做题
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月23日 上午10:43:54
 * @version 			0.0.1
 */
@RequestMapping("/httbapi/analysis")
@Controller
public class TestInitQues {

	@Autowired
	private InitQuesAnalysis initQuesAnalysis;

	@Autowired
	private HtexamresultService htexamresultService;

	@RequestMapping(value = "/refreshQues", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Message initSXRankAnalysis() throws HttbException{
		Message message = new Message();
		try {
			initQuesAnalysis.initExamAnalysis();
			message.setSuccess(true);
			message.setMessage("结果保存成功");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("结果保存失败");
			e.printStackTrace();
		}
		return message;
	}
}
