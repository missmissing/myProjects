/**
 * 项目名：				httb
 * 包名：				com.huatu.redis.service.impl
 * 文件名：				BaseRedisServiceImpl.java
 * 日期：				2015年5月10日-上午11:33:10
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.core.redis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.huatu.core.exception.HttbException;
import com.huatu.core.redis.dao.BaseRedisDao;
import com.huatu.core.redis.service.IRedisService;

/**
 * 类名称：				BaseRedisServiceImpl
 * 类描述：				redis业务层基础类
 * 创建人：				Aijunbo
 * 创建时间：				2015年5月10日 上午11:33:10
 * @version 			0.0.1
 */
@Service
@Scope("prototype")
public class IRedisServiceImpl implements IRedisService {
	@Autowired
	private BaseRedisDao<String, Object> baseRedisDao;
	/**
	 *
	 * add							(添加对象)
	 * 								(需要缓存对象时调用)
	 * @param 		keyId			对象key值
	 * @param 		object			保存对象
	 * @return
	 * @throws HttbException
	 */
	@Override
	public boolean put(String keyId, Object object) throws HttbException {
		return baseRedisDao.put(keyId, object);
	}

	/**
	 *
	 * delete						(删除对象)
	 * 								(根据key删除对象)
	 * @param 		key				对象keyId
	 * @throws HttbException
	 */
	@Override
	public boolean remove(String keyId) throws HttbException {
		return baseRedisDao.remove(keyId);
	}

	/**
	 *
	 * delete						(批量删除)
	 * 								(批量删除对象)
	 * @param 		keyIds			对象Key集合
	 * @throws HttbException
	 */
	@Override
	public void remove(List<String> keyIds) throws HttbException {
		baseRedisDao.remove(keyIds);
	}

	/**
	 *
	 * refresh						(修改对象)
	 * 								(当有缓存对象更新时，调用)
	 * @param 		keyId			对象Key
	 * @param 		object			更新对象
	 * @return
	 * @throws 		HttbException
	 */
	@Override
	public boolean refresh(String keyId, Object object) throws HttbException {
		return baseRedisDao.refresh(keyId, object);
	}

	/**
	 *
	 * get							(获取对象)
	 * 								(根据传入key获取对象)
	 * @param 		keyId			对象Key
	 * @return
	 * @throws 		HttbException
	 */
	@Override
	public Object get(String keyId) throws HttbException {
		return baseRedisDao.get(keyId);
	}

	/**
	 *
	 * incr							(对某个KEY自增长)
	 * 								(计算当前登录个数时调用)
	 * @param 		keyId			对象Key
	 * @return
	 * @throws 		HttbException
	 */
	@Override
	public Object incr(String keyId) throws HttbException {
		return baseRedisDao.incr(keyId);
	}

	/**
	 *
	 * decr							(对某个KEY自动减一)
	 * 								(计算当前登录个数时调用)
	 * @param 		keyId			对象Key
	 * @return
	 * @throws 		HttbException
	 */
	@Override
	public Object decr(String keyId) throws HttbException {
		return baseRedisDao.decr(keyId);
	}

	@Override
	public boolean putEX(String keyId, Object object) throws HttbException {
		return baseRedisDao.putEX(keyId, object);
	}

	@Override
	public boolean refreshEX(String keyId) throws HttbException {
		return baseRedisDao.refreshEX(keyId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getKeys(String keyId) throws HttbException {
		String sum = (String) baseRedisDao.getString(keyId);
		List list = new ArrayList();
		if(null != sum){
			list.add(sum);
		}else{
			list.add("0");
		}

		return list;
	}

	@Override
	public Boolean exists(String keyId) throws HttbException {
		return baseRedisDao.exists(keyId);
	}

	@Override
	public boolean putString(String keyId, String string) throws HttbException {
		return baseRedisDao.putString(keyId, string);
	}

	@Override
	public String getString(String keyId) throws HttbException {
		return baseRedisDao.getString(keyId);
	}


	@Override
	public boolean rpush(String keyId, Object object) throws HttbException {
		return baseRedisDao.rpush(keyId, object);
	}

	@Override
	public Object lpop(String keyId) throws HttbException {
		return baseRedisDao.lpop(keyId);
	}

	@Override
	public boolean putNX(String keyId, Object object) throws HttbException {
		return baseRedisDao.putNX(keyId, object);
	}

	@Override
	public boolean expire(String keyId, int time) throws HttbException {
		return baseRedisDao.expire(keyId, time);
	}

	@Override
	public Long getTTL(String keyId) throws HttbException {
		return baseRedisDao.getTTL(keyId);
	}

	@Override
	public boolean putEX(String keyId, Object object, int secend)
			throws HttbException {
		return baseRedisDao.putEX(keyId, object, secend);
	}

	@Override
	public boolean hset(String keyId, String field, Object object)
			throws HttbException {
		return baseRedisDao.hset(keyId, field, object);
	}

	@Override
	public Object hget(String keyId, String field) throws HttbException {
		return baseRedisDao.hget(keyId, field);
	}
}
