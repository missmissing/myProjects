/**
 * 项目名：				httb
 * 包名：				com.huatu.core.redis.util
 * 文件名：				RedisLockUtil.java
 * 日期：				2015年8月6日-上午9:57:48
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.core.redis.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.redis.service.RedisLockService;
import com.huatu.core.util.Constants;

/**
 * 类名称：				RedisLockUtil
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：				2015年8月6日 上午9:57:48
 * @version 			0.0.1
 */
public class RedisLockUtil {
	@Autowired
	private IRedisService iRedisService;

	public Object redisLock(RedisLockService redisLockService, Object obj, String keiId) throws InterruptedException, HttbException{
		try {
			//刷新排名前先看是否锁住
			//锁标记
			boolean lock = iRedisService.exists(keiId);
			//有效时间
			Long ttl = iRedisService.getTTL(keiId);

			boolean lockFlag = false;
			//设置排它锁失败时一直遍历
			//需要在最外层套一个while循环是
			//如果几个线程同时启动，获取排它锁显示为空，会出现几个同时设置排它锁，但是中间只有一个会成功
			while(!lockFlag){
				//如果没有被锁住则刷新缓存排名
				if(!lock || ttl < 0){
					//加锁排序
					lockFlag =  redisLockService.handle(obj).isSuccess();
				}else{
					//有排它锁存在且有效，则等待排它锁失效
					while (lockFlag && ttl > 0) {
						lockFlag = iRedisService.exists(keiId);
						ttl = iRedisService.getTTL(keiId);
						//等待有效期除以2
						try {
							Thread.sleep(Constants.ANALYSIS_LOCK_EXPIRE/2);
						} catch (InterruptedException e) {
							throw new HttbException(ModelException.JAVA_THREAD_EXCEPTION, this.getClass()+"当前线程等待时发生异常", e);
						}
					}
					//加锁排序
					lockFlag = redisLockService.handle(obj).isSuccess();
				}
				Thread.sleep(Constants.ANALYSIS_LOCK_EXPIRE/2);
			}
		} catch (HttbException e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass()+"保存测试结果时发生异常", e);
		}
		return null;
	}
}
