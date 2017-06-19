package com.huatu.core.redis.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.redis.util.SerializeUtil;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;


public class BaseRedisDao3<K, V> {
	@Autowired
	protected RedisTemplate<K, V> redisTemplate;

	/**
	 * 设置redisTemplate
	 *
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 获取 RedisSerializer <br>
	 * ------------------------------<br>
	 */
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

	/**
	 *
	 * add							(添加对象)
	 * 								(需要缓存对象时调用)
	 * @param 		keyId			对象key值
	 * @param 		object			保存对象
	 * @return
	 * @throws 		HttbException
	 */
	public boolean put(final String keyId, final Object object) throws HttbException {
		boolean result;
		//如果已经存在，则删除
		if(get(keyId) != null){
			remove(keyId);
		}
		try {
			//执行添加
			result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection)
						throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					//序列化KEY
					byte[] keyByte = serializer.serialize(keyId);
					//序列化对象
					byte[] objByte = SerializeUtil.serialize(object);
					connection.set(keyByte, objByte);
					connection.close();
					return true;
				}
			});
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_ADD, this.getClass()+"添加Redis对象时发生异常", e);
		}

		return result;
	}

	/**
	 *
	 * delete						(删除对象)
	 * 								(根据key删除对象)
	 * @param 		keyId				对象Key
	 * @throws 		HttbException
	 */
	public boolean remove(final String keyId) throws HttbException {
		boolean result;
		if (keyId == null) {
			throw new NullPointerException("key不能为空");
		}
		try {
			result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection)
						throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					//序列化key
					byte[] keyByte = serializer.serialize(keyId);
					connection.del(keyByte);
					connection.close();
					return true;
				}
			});
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_DELETE, this.getClass()+"删除Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * delete						(批量删除)
	 * 								(批量删除对象)
	 * @param 		keyIds			对象Key集合
	 * @throws HttbException
	 */
	public boolean remove(final List<String> keyIds) throws HttbException {
		boolean result;
		//keyIds不能为空
		if (!CommonUtil.isNotNull(keyIds)) {
			return false;
		}
		try {
			result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection)
						throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					for (int i = 0; i < keyIds.size(); i++) {
						//序列化key
						byte[] keyByte = serializer.serialize(keyIds.get(i));
						connection.del(keyByte);
					}
					connection.close();
					return true;
				}
			});
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_DELETE, this.getClass()+"删除Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * refresh						(刷新对象)
	 * 								(当有缓存对象更新时，调用)
	 * @param 		keyId			对象Key
	 * @param 		object			更新对象
	 * @return
	 * @throws 		HttbException
	 */
	public boolean refresh(final String keyId, final Object object) throws HttbException {
		boolean result;
		if (get(keyId) == null) {
			throw new NullPointerException("数据行不存在, key = " + keyId);
		}
		try {
			result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection)
						throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					//序列化key
					byte[] keyByte = serializer.serialize(keyId);
					//序列化对象
					byte[] objByte = SerializeUtil.serialize(object);
					connection.set(keyByte, objByte);
					connection.close();
					return true;
				}
			});
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_UPDATE, this.getClass()+"修改Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * get							(获取对象)
	 * 								(根据传入key获取对象)
	 * @param 		keyId			对象Key
	 * @return
	 * @throws 		HttbException
	 */
	public Object get(final String keyId) throws HttbException {
		Object result;
		try {
			result = redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					//序列化key
					byte[] keyByte = serializer.serialize(keyId);
					//获取对象
					byte[] objByte = connection.get(keyByte);
					if (objByte == null) {
						return null;
					}
					//反序列化
					Object obj = SerializeUtil.unserialize(objByte);
					connection.close();
					return obj;
				}
			});
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass()+"查询Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * exists						(对象是否存在)
	 * 								(根据传入key获取对象)
	 * @param 		keyId			对象Key
	 * @return
	 * @throws 		HttbException
	 */
	public Object exists(final String keyId) throws HttbException {
		Object result;
		try {
			result = redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					//序列化key
					byte[] keyByte = serializer.serialize(keyId);
					//获取对象
					boolean isexists = connection.exists(keyByte);
					connection.close();
					return isexists;
				}
			});
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass()+"查询Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * get							(获取对象)
	 * 								(根据传入key获取对象)
	 * @param 		keyId			对象Key
	 * @return
	 * @throws 		HttbException
	 */
	public Object getKeys(final String keyId) throws HttbException {
		Object result;
		try {
			result = redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					//序列化key
					byte[] keyByte = serializer.serialize(keyId);
					//获取对象
					Set<byte[]> objByte = connection.keys(keyByte);
					if (objByte == null) {
						return null;
					}
					List<Object> objs = null;
					//如果不为空
					if(CommonUtil.isNotNull(objByte)){
						objs = new ArrayList<Object>();
						for (byte[] bs : objByte) {
							objs.add(serializer.deserialize(bs));
						}
					}
					connection.close();
					return objs;
				}
			});
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass()+"查询Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * incr							(对某个KEY自增长)
	 * 								(计算当前登录个数时调用)
	 * @param 		keyId			对象Key
	 * @return
	 * @throws 		HttbException
	 */
	public Object incr(final String keyId) throws HttbException {
		Object result;
		try {
			result = redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					//序列化key
					byte[] keyByte = serializer.serialize(keyId);
					//返回当前个数
					Object obj = connection.incr(keyByte);
					connection.close();
					return obj;
				}
			});
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass()+"执行Redis对象，自增长时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * decr							(对某个KEY自动减一)
	 * 								(计算当前登录个数时调用)
	 * @param 		keyId			对象Key
	 * @return
	 * @throws 		HttbException
	 */
	public Object decr(final String keyId) throws HttbException {
		Object result;
		try {
			result = redisTemplate.execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection)
						throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					//序列化key
					byte[] keyByte = serializer.serialize(keyId);
					Object obj = connection.decr(keyByte);
					connection.close();
					//返回当前个数
					return obj;
				}
			});
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass()+"执行Redis对象，自动减一时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * putEX						(添加对象)
	 * 								(保存对象时需要设置有效期时调用)
	 * @param 		keyId			对象key值
	 * @param 		object			保存对象
	 * @return
	 * @throws 		HttbException
	 */
	public boolean putEX(final String keyId, final Object object) throws HttbException {
		boolean result;
		//如果已经存在，则删除
		if(get(keyId) != null){
			remove(keyId);
		}
		try {
			//执行添加
			result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection)
						throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					//序列化KEY
					byte[] keyByte = serializer.serialize(keyId);
					//序列化对象
					byte[] objByte = SerializeUtil.serialize(object);
					connection.setEx(keyByte, Constants.REDIS_EXPIRE, objByte);
					connection.close();
					return true;
				}
			});
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_ADD, this.getClass()+"添加Redis对象时发生异常", e);
		}
		return result;
	}

	/**
	 *
	 * refreshEX					(刷新有效期)
	 * 								(保存对象时需要设置有效期时调用)
	 * @param 		keyId			对象key值
	 * @return
	 * @throws 		HttbException
	 */
	public boolean refreshEX(final String keyId) throws HttbException {
		boolean result;
		try {
			//执行添加
			result = redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection)
						throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					//序列化KEY
					byte[] keyByte = serializer.serialize(keyId);
					connection.expire(keyByte, Constants.REDIS_EXPIRE);
					connection.close();
					return true;
				}
			});
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_REDIS_ADD, this.getClass()+"添加Redis对象时发生异常", e);
		}
		return result;
	}
}
