/**
 * 项目名：				httb
 * 包名：				com.huatu.core.redis.service
 * 文件名：				RedisLockService.java
 * 日期：				2015年8月5日-下午8:40:32
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.core.redis.service;

import com.huatu.core.model.Message;

/**
 * 类名称：				RedisLockService
 * 类描述：  				redis锁service类
 * 创建人：				Aijunbo
 * 创建时间：				2015年8月5日 下午8:40:32
 * @version 			0.0.1
 */
public interface RedisLockService {
	/**
	 *
	 * handleMethod						(执行方法)
	 * 									(解锁时需要执行的方法)
	 * @param 		obj					参数对象
	 * @return
	 */
	public Message handle(Object obj);
}
