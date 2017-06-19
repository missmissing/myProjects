package com.huatu.core.redis.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
	/**
	 *
	 * serialize					(序列化)
	 * @param 		byte[]			二进制数组
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * unserialize					(反序列化)
	 * @param 		bytes			二进制数组
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
