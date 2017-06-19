/**
 * 项目名：				httb
 * 包名：				com.huatu.core.redis.test
 * 文件名：				TestRedis.java
 * 日期：				2015年7月14日-下午7:48:27
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.core.redis.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;
import com.huatu.core.redis.dao.BaseRedisDao;

/**
 * 类名称：				TestRedis
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年7月14日 下午7:48:27
 * @version 			0.0.1
 */
@Controller
@RequestMapping("/httbapi/redis")
public class TestRedis {
	@Autowired
	private BaseRedisDao baseRedisDao;

	@RequestMapping(value = "/test", method = {RequestMethod.GET})
	@ResponseBody
	public Message execute(@RequestParam String val) throws HttbException{
		Message message = new Message();

		boolean flag = baseRedisDao.put("testa", val);
		System.out.println("===========================================flag:"+flag);
		String str = (String) baseRedisDao.get("testa");
		System.out.println("===========================================str:"+str);
		message.setSuccess(flag);
		message.setMessage("str:"+str);
		return message;
	}

}
