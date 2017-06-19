/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.init
 * 文件名：				InitSXRank.java
 * 日期：				2015年6月23日-上午10:43:54
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.testApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatu.analysis.init.InitExamAnalysis;
import com.huatu.analysis.model.Htexamresult;
import com.huatu.analysis.service.HtexamresultService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;

/**
 * 类名称：				InitSXRank
 * 类描述：  				初始化顺序做题
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月23日 上午10:43:54
 * @version 			0.0.1
 */
@RequestMapping("/httbapi/analysis")
@Controller
public class TestInitExam {

	@Autowired
	private InitExamAnalysis initExam;

	@Autowired
	private HtexamresultService htexamresultService;

	@RequestMapping(value = "/refreshExam", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Message initSXRankAnalysis() throws HttbException{
		Message message = new Message();
		try {
			initExam.initExamAnalysis();
			message.setSuccess(true);
			message.setMessage("结果保存成功");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("结果保存失败");
			e.printStackTrace();
		}
		return message;
	}

	@RequestMapping(value = "/recentExam", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Message getRankup(@RequestParam  String uid,@RequestParam  String eid, @RequestParam  int count) throws HttbException{
		Message message = new Message();
		try {
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("ruid", uid);
			filter.put("reid", eid);
			List<Htexamresult> list = htexamresultService.getRecentExam(filter, count);
			message.setSuccess(true);
			message.setMessage("用户:"+uid+"试卷id："+eid+"做了："+list.size()+"次");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("结果保存失败");
			e.printStackTrace();
		}
		return message;
	}

}
