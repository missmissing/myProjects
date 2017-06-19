package com.huatu.core.redis.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisCluster;

import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.redis.util.SerializeUtil;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;

@Component
public class BaseRedisDao<K, V> {

	@Autowired
	protected JedisCluster jc;

	public JedisCluster getJc() {
		return jc;
	}

	public void setJc(JedisCluster jc) {
		this.jc = jc;
	}

	/**
	 *
	 * add (添加对象) (需要缓存对象时调用)
	 *
	 * @param keyId
	 *            对象key值
	 * @param object
	 *            保存对象
	 * @return
	 * @throws HttbException
	 */
	public boolean put(final String keyId, final Object object) throws HttbException {
		boolean result = false;
		// 如果已经存在，则删除
		if (get(keyId) != null) {
			remove(keyId);
		}
		try {
			// 执行添加
			byte[] objByte = SerializeUtil.serialize(object);
			int len = objByte.length;
			char[] objchar = new char[len];
			for (int i = 0; i < objByte.length; i++) {
				objchar[i] = (char) objByte[i];
			}
			String objString = new String(objchar);
			jc.set(keyId, objString);
			result = true;

		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_ADD, this.getClass() + "添加Redis对象时发生异常", e);
		}
		return result;
	}

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
	public boolean putNX(final String keyId, final Object object) throws HttbException {
		boolean result = false;
		try {
			// 执行添加
			byte[] objByte = SerializeUtil.serialize(object);
			int len = objByte.length;
			char[] objchar = new char[len];
			for (int i = 0; i < objByte.length; i++) {
				objchar[i] = (char) objByte[i];
			}
			String objString = new String(objchar);
			long flag = jc.setnx(keyId, objString);
			result = flag > 0 ? true : false;

		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_ADD, this.getClass() + "添加Redis对象时发生异常", e);
		}
		return result;
	}

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
	public boolean expire(final String keyId, final int time) throws HttbException {
		boolean result = false;
		try {
			jc.expire(keyId, time);
			result = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_ADD, this.getClass() + "添加Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * delete (删除对象) (根据key删除对象)
	 *
	 * @param keyId
	 *            对象Key
	 * @throws HttbException
	 */
	public boolean remove(final String keyId) throws HttbException {
		boolean result = false;
		if (keyId == null) {
			throw new NullPointerException("key不能为空");
		}
		try {
			jc.del(keyId);
			result = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_DELETE, this.getClass() + "删除Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * delete (批量删除) (批量删除对象)
	 *
	 * @param keyIds
	 *            对象Key集合
	 * @throws HttbException
	 */
	public boolean remove(final List<String> keyIds) throws HttbException {
		boolean result = false;
		// keyIds不能为空
		if (!CommonUtil.isNotNull(keyIds)) {
			return false;
		}
		try {
			for (int i = 0; i < keyIds.size(); i++) {
				String key = keyIds.get(i);
				remove(key);
			}
			result = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_DELETE, this.getClass() + "删除Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * refresh (刷新对象) (当有缓存对象更新时，调用)
	 *
	 * @param keyId
	 *            对象Key
	 * @param object
	 *            更新对象
	 * @return
	 * @throws HttbException
	 */
	public boolean refresh(final String keyId, final Object object) throws HttbException {
		boolean result = false;
		if (get(keyId) == null) {
			throw new NullPointerException("数据行不存在, key = " + keyId);
		}
		try {
			put(keyId, object);
			result = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_UPDATE, this.getClass() + "修改Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * get (获取对象) (根据传入key获取对象)
	 *
	 * @param keyId
	 *            对象Key
	 * @return
	 * @throws HttbException
	 */
	public Object get(final String keyId) throws HttbException {
		Object result;
		try {
			String objStringBack = jc.get(keyId);
			if (null == objStringBack)
				return null;
			// 把String再次转换成byte[]
			char[] objchar = objStringBack.toCharArray();
			int len = objchar.length;
			byte[] objByte = new byte[len];
			for (int i = 0; i < objByte.length; i++) {
				objByte[i] = (byte) objchar[i];
			}
			// 反序列化
			result = SerializeUtil.unserialize(objByte);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass() + "查询Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * exists (对象是否存在) (根据传入key获取对象)
	 *
	 * @param keyId
	 *            对象Key
	 * @return
	 * @throws HttbException
	 */
	public Boolean exists(String keyId) throws HttbException {
		Boolean ex = null;
		try {
			ex = jc.exists(keyId);

		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass() + "查询Redis对象时发生异常", e);
		}
		return ex;
	}



	/**
	 *
	 * incr (对某个KEY自增长) (计算当前登录个数时调用)
	 *
	 * @param keyId
	 *            对象Key
	 * @return
	 * @throws HttbException
	 */
	public Object incr(final String keyId) throws HttbException {
		Object result = null;
		try {
			if (null == keyId) {
				return result;
			} else {
				jc.incr(keyId);
				result = getString(keyId);
			}
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass() + "执行Redis对象，自增长时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * decr (对某个KEY自动减一) (计算当前登录个数时调用)
	 *
	 * @param keyId
	 *            对象Key
	 * @return
	 * @throws HttbException
	 */
	public Object decr(final String keyId) throws HttbException {
		Object result = null;
		try {
			if (null == keyId) {
				return result;
			}else{
				jc.decr(keyId);
				result = getString(keyId);
			}
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass() + "执行Redis对象，自动减一时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * putEX (添加对象) (保存对象时需要设置有效期时调用)
	 *
	 * @param keyId
	 *            对象key值
	 * @param object
	 *            保存对象
	 * @return
	 * @throws HttbException
	 */
	public boolean putEX(final String keyId, final Object object) throws HttbException {
		boolean result = false;
		// 如果已经存在，则删除
		if (get(keyId) != null) {
			remove(keyId);
		}
		try {
			// 执行添加
			put(keyId, object);
			jc.expire(keyId, (int) Constants.REDIS_EXPIRE);
			result = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_ADD, this.getClass() + "添加Redis对象时发生异常", e);
		}
		return result;
	}


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
	public boolean putEX(final String keyId, final Object object, final int secend) throws HttbException {
		boolean result = false;
		// 如果已经存在，则删除
		if (get(keyId) != null) {
			remove(keyId);
		}
		try {
			// 执行添加
			put(keyId, object);
			jc.expire(keyId, secend);
			result = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_ADD, this.getClass() + "添加Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * refreshEX (刷新有效期) (保存对象时需要设置有效期时调用)
	 *
	 * @param keyId
	 *            对象key值
	 * @return
	 * @throws HttbException
	 */
	public boolean refreshEX(final String keyId) throws HttbException {
		boolean result = false;
		try {
			jc.expire(keyId, (int) Constants.REDIS_EXPIRE);
			result = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_ADD, this.getClass() + "添加Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * add (添加对象) (需要缓存对象时调用)
	 *
	 * @param keyId
	 *            对象key值
	 * @param object
	 *            保存对象
	 * @return
	 * @throws HttbException
	 */
	public boolean putString(final String keyId, final String string) throws HttbException {
		boolean result = false;
		// 如果已经存在，则删除
		if (get(keyId) != null) {
			remove(keyId);
		}
		try {
			// 执行添加
			jc.set(keyId, string);
			result = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_ADD, this.getClass() + "添加Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * get (获取对象) (根据传入key获取对象)
	 *
	 * @param keyId
	 *            对象Key
	 * @return
	 * @throws HttbException
	 */
	public String getString(final String keyId) throws HttbException {
		String result;
		try {
			 result = jc.get(keyId);
			 if(result == null){
				 result = "";
			 }
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass() + "查询Redis对象时发生异常", e);
		}
		return result;
	}

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
	public boolean rpush(final String keyId, final Object object) throws HttbException {
		boolean result = false;

		try {
			// 执行添加
			byte[] objByte = SerializeUtil.serialize(object);
			int len = objByte.length;
			char[] objchar = new char[len];
			for (int i = 0; i < objByte.length; i++) {
				objchar[i] = (char) objByte[i];
			}
			String objString = new String(objchar);
			jc.rpush(keyId, objString);
			result = true;

		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_ADD, this.getClass() + "添加Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * lpop	 	(根据传入key获取对象返回并删除名称为key的list中的首元素)
	 *
	 * @param 	keyId	对象Key
	 * @return
	 * @throws HttbException
	 */
	public Object lpop(final String keyId) throws HttbException {
		Object result;
		try {
			String objStringBack = jc.lpop(keyId);
			if (null == objStringBack)
				return null;

			// 反序列化
			result = SerializeUtil.unSerializeable(objStringBack);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass() + "查询Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * getTTL						(查询对象有效时间)
	 * @param 		keyId			对象Key
	 * @return
	 * @throws 		HttbException
	 */
	public Long getTTL(String keyId) throws HttbException{
		Long ttl;
		try {
			ttl = jc.ttl(keyId);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass() + "查询Redis对象有效时间时发生异常", e);
		}
		return ttl;
	}

	/**
	 *
	 * hset							(设置hash键值)
	 * @param 		keyId			对象Key
	 * @param 		field			map键
	 * @param 		object			map值
	 * @return
	 * @throws 		HttbException
	 */
	public boolean hset(final String keyId, final String field,final Object object) throws HttbException {
		boolean result = false;

		try {
			// 执行添加
			byte[] objByte = SerializeUtil.serialize(object);
			int len = objByte.length;
			char[] objchar = new char[len];
			for (int i = 0; i < objByte.length; i++) {
				objchar[i] = (char) objByte[i];
			}
			String objString = new String(objchar);
			jc.hset(keyId, field, objString);
			result = true;

		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_ADD, this.getClass() + "添加Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * hget							(设置hash键值)
	 * @param 		keyId			对象Key
	 * @param 		field			map键
	 * @return
	 * @throws HttbException
	 */
	public Object hget(final String keyId, final String field) throws HttbException {
		Object result;
		try {
			String objStringBack = jc.hget(keyId, field);
			if (null == objStringBack)
				return null;
			// 把String再次转换成byte[]
			char[] objchar = objStringBack.toCharArray();
			int len = objchar.length;
			byte[] objByte = new byte[len];
			for (int i = 0; i < objByte.length; i++) {
				objByte[i] = (byte) objchar[i];
			}
			// 反序列化
			result = SerializeUtil.unserialize(objByte);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass() + "查询Redis对象时发生异常", e);
		}
		return result;
	}
}
