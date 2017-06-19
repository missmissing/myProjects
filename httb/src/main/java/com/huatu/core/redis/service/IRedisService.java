package com.huatu.core.redis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.huatu.core.exception.HttbException;

/**
 *
 * 类名称：				BaseRedisService
 * 类描述：				redis基础service类
 * 创建人：				Aijunbo
 * 创建时间：				2015年5月10日 上午11:00:30
 * @version 			0.0.1
 */
@Service
public interface IRedisService {

	/**
	 *
	 * add							(添加对象)
	 * 								(需要缓存对象时调用)
	 * @param 		keyId			对象key值
	 * @param 		object			保存对象
	 * @return
	 * @throws 		HttbException
	 */
	public boolean put(final String keyId, final Object object) throws HttbException ;

	/**
	 *
	 * delete						(删除对象)
	 * 								(根据key删除对象)
	 * @param 		keyId			对象keyId
	 * @return
	 */
	public boolean remove(String keyId) throws HttbException;

	/**
	 *
	 * delete						(批量删除)
	 * 								(批量删除对象)
	 * @param 		keyIds			对象Key集合
	 */
	public void remove(List<String> keyIds) throws HttbException;

	/**
	 *
	 * refresh						(修改对象)
	 * 								(当有缓存对象更新时，调用)
	 * @param 		keyId			对象Key
	 * @param 		object			更新对象
	 * @return
	 */
	public boolean refresh(final String keyId, final Object object) throws HttbException;

	/**
	 *
	 * get							(获取对象)
	 * 								(根据传入key获取对象)
	 * @param 		keyId			对象Key
	 * @return
	 */
	public Object get(final String keyId) throws HttbException;

	/**
	 *
	 * getKeys						(模糊查询)
	 * 								(根据传入key获取对象)
	 * @param 		keyId			对象Key
	 * @return
	 */
	public List<Object> getKeys(final String keyId) throws HttbException;

	/**
	 *
	 * incr							(对某个KEY自增长)
	 * 								(计算当前登录个数时调用)
	 * @param 		keyId			对象Key
	 * @return
	 * @throws 		HttbException
	 */
	public Object incr(final String keyId) throws HttbException;

	/**
	 *
	 * decr							(对某个KEY自动减一)
	 * 								(计算当前登录个数时调用)
	 * @param 		keyId			对象Key
	 * @return
	 * @throws 		HttbException
	 */
	public Object decr(final String keyId) throws HttbException;

	/**
	 *
	 * putEX						(添加对象)
	 * 								(保存对象时需要设置有效期时调用)
	 * @param 		keyId			对象key值
	 * @param 		object			保存对象
	 * @return
	 * @throws 		HttbException
	 */
	public boolean putEX(final String keyId, final Object object) throws HttbException;

	/**
	 *
	 * refreshEX					(刷新有效期)
	 * 								(保存对象时需要设置有效期时调用)
	 * @param 		keyId			对象key值
	 * @return
	 * @throws 		HttbException
	 */
	public boolean refreshEX(final String keyId) throws HttbException;

	/**
	 *
	 * exists						(对象是否存在)
	 * 								(根据传入key获取对象)
	 * @param 		keyId			对象Key
	 * @return
	 * @throws 		HttbException
	 */
	public Boolean exists(final String keyId) throws HttbException ;


	/**
	 *
	 * putString							(添加对象)
	 * 								(需要缓存对象时调用)
	 * @param 		keyId			对象key值
	 * @param 		object			保存对象
	 * @return
	 * @throws 		HttbException
	 */
	public boolean putString(final String keyId, final String string) throws HttbException ;

	/**
	 *
	 * getString							(获取对象)
	 * 								(需要缓存对象时调用)
	 * @param 		keyId			对象key值
	 * @return
	 * @throws 		HttbException
	 */
	public String getString(final String keyId) throws HttbException ;

	/**
	 *
	 * rpush (在名称为keyId的list尾添加一个值为object的元素)
	 *
	 * @param keyId
	 *            对象key值
	 * @param object
	 *            保存对象
	 * @return
	 * @throws HttbException
	 */
	public boolean rpush(final String keyId, final Object object) throws HttbException ;

	/**
	 *
	 * lpop	 	(根据传入key获取对象返回并删除名称为key的list中的首元素)
	 *
	 * @param 	keyId	对象Key
	 * @return
	 * @throws HttbException
	 */
	public Object lpop(final String keyId) throws HttbException;

	/**
	 *
	 * putNX (设置对象加锁)
	 *
	 * @param keyId
	 *            对象key值
	 * @param object
	 *            保存对象
	 * @return
	 * @throws HttbException
	 */
	public boolean putNX(final String keyId, final Object object) throws HttbException;

	/**
	 *
	 * putEX (添加对象) (保存对象时需要设置有效期时调用)
	 *
	 * @param keyId
	 *            对象key值
	 * @param object
	 *            保存对象
	 * @param secend
	 * 			  有效期
	 * @return
	 * @throws HttbException
	 */
	public boolean putEX(final String keyId, final Object object, final int secend) throws HttbException;

	/**
	 *
	 * expire (设置对象加锁)
	 *
	 * @param keyId
	 *            对象key值
	 * @param time
	 *            到期时间
	 * @return
	 * @throws HttbException
	 */
	public boolean expire(final String keyId, final int time) throws HttbException;

	/**
	 *
	 * getTTL						(查询对象有效时间)
	 * @param 		keyId			对象Key
	 * @return
	 * @throws 		HttbException
	 */
	public Long getTTL(String keyId) throws HttbException;

	/**
	 *
	 * hset							(设置hash键值)
	 * @param 		keyId			对象Key
	 * @param 		field			map键
	 * @param 		object			map值
	 * @return
	 * @throws 		HttbException
	 */
	public boolean hset(final String keyId, final String field,final Object object) throws HttbException ;

	/**
	 *
	 * hget							(设置hash键值)
	 * @param 		keyId			对象Key
	 * @param 		field			map键
	 * @return
	 * @throws HttbException
	 */
	public Object hget(final String keyId, final String field) throws HttbException ;
}
