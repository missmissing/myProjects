package com.huatu.core.redis.test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.huatu.analysis.model.Htqueshis;
import com.huatu.api.po.PaperPo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.redis.util.SerializeUtil;
import com.huatu.core.util.Constants;
import com.huatu.ou.user.model.User;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class RedisTest {

	static JedisCluster jc;

	// static BinaryJedisCluster bjc;

	public static void main(String[] args) {

		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort("192.168.200.106", 9000));
		jedisClusterNodes.add(new HostAndPort("192.168.200.106", 9001));
		jedisClusterNodes.add(new HostAndPort("192.168.200.106", 9002));
		jedisClusterNodes.add(new HostAndPort("192.168.200.106", 9003));
		jedisClusterNodes.add(new HostAndPort("192.168.200.106", 9004));
		jedisClusterNodes.add(new HostAndPort("192.168.200.106", 9005));
		/*jedisClusterNodes.add(new HostAndPort("192.168.200.43", 7000));
		jedisClusterNodes.add(new HostAndPort("192.168.200.43", 7001));
		jedisClusterNodes.add(new HostAndPort("192.168.200.43", 7002));
		jedisClusterNodes.add(new HostAndPort("192.168.200.45", 7000));
		jedisClusterNodes.add(new HostAndPort("192.168.200.45", 7001));
		jedisClusterNodes.add(new HostAndPort("192.168.200.45", 7002));*/
		/*jedisClusterNodes.add(new HostAndPort("192.168.200.40", 7000));
		jedisClusterNodes.add(new HostAndPort("192.168.200.40", 7001));
		jedisClusterNodes.add(new HostAndPort("192.168.200.40", 7001));
		jedisClusterNodes.add(new HostAndPort("192.168.200.39", 7000));
		jedisClusterNodes.add(new HostAndPort("192.168.200.39", 7001));
		jedisClusterNodes.add(new HostAndPort("192.168.200.39", 7002));*/

		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxTotal(65535);
		poolConfig.setMaxIdle(1000);
		poolConfig.setMaxWaitMillis(10000);
		poolConfig.setTimeBetweenEvictionRunsMillis(60000);
		poolConfig.setMinEvictableIdleTimeMillis(60000);
		poolConfig.setTestOnBorrow(true);

		jc = new JedisCluster(jedisClusterNodes, poolConfig);
		User u = new User();
		u.setId("111");
		u.setUname("ajbo");
		User u2 = new User();
		u2.setId("222");
		u2.setUname("ajbo2");

		jc.hset("h1", "u1", serializeable(u));
		jc.hset("h1", "u2", serializeable(u2));

		User un = (User) unSerializeable(jc.hget("h1", "u1"));
		System.out.println(un.getId());
		System.out.println(un.getUname());

		User un2 = (User) unSerializeable(jc.hget("h1", "u2"));
		System.out.println(un2.getId());
		System.out.println(un2.getUname());

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}







	private static void testPool(){
		Map<String, JedisPool> mapNodes = jc.getClusterNodes();
		//System.out.println(mapNodes.get("192.168.200.106:9000"));
		for (String keys : mapNodes.keySet()) {
			System.out.println(keys+":"+mapNodes.get(keys));
		}
	}

	/**
	 * serializeable(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param paperPo
	 * @return
	 */
	private static String serializeable(Object obj) {
		byte[] objByte = SerializeUtil.serialize(obj);
		int len = objByte.length;
		char[] objchar = new char[len];
		for (int j = 0; j < objByte.length; j++) {
			objchar[j] = (char) objByte[j];
		}
		String objString = new String(objchar);
		return objString;
	}

	public static Object unSerializeable(String objStringBack){
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
		return SerializeUtil.unserialize(objByte);
	}

}
