/**
 * 项目名：				httb
 * 包名：				com.huatu.core.redis.service.impl
 * 文件名：				RedisLockServiceImpl.java
 * 日期：				2015年8月5日-下午9:06:51
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.core.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.huatu.core.model.Message;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.redis.service.RedisLockService;

/**
 * 类名称：				RedisLockServiceImpl
 * 类描述：				redis锁service类
 * 创建人：				Aijunbo
 * 创建时间：				2015年8月5日 下午9:06:51
 * @version 			0.0.1
 */
public class RedisLockServiceImpl implements RedisLockService{
	@Autowired
	private IRedisService iRedisService;

	@Override
	public Message handle(Object obj)  {

		return null;
	}
}
