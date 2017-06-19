/**
 * 项目名：				httb
 * 包名：				com.huatu.jms.testApi
 * 文件名：				AnalyzeApi.java
 * 日期：				2015年7月9日-下午5:07:15
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.jms.testApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.huatu.api.po.PaperPo;
import com.huatu.core.model.Message;
import com.huatu.jms.model.RankMessage;
import com.huatu.jms.service.AnalyzeJmsService;

/**
 * 类名称：				AnalyzeApi
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年7月9日 下午5:07:15
 * @version 			0.0.1
 */
@Controller
@RequestMapping("/httbapi/jms")
public class AnalyzeApi {
	@Autowired
	private AnalyzeJmsService analyzeJmsService;

	@RequestMapping(value = "/send", method = {RequestMethod.POST})
	@ResponseBody
	public Message sendMessage(@RequestParam String testpaper,
			 @RequestParam String license){
		Message message = new Message();
		try {
			RankMessage rankMessage = new RankMessage();
			PaperPo testpaperObj = new Gson().fromJson(testpaper, new TypeToken<PaperPo>() {}.getType());
			rankMessage.setObject(testpaperObj);
			rankMessage.setJmsType("1");
			analyzeJmsService.sendMessage(rankMessage);
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
