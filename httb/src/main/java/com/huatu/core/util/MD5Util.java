/**   
 * 项目名：				httb
 * 包名：				com.huatu.core.util  
 * 文件名：				MD5Util.java    
 * 日期：				2015年5月11日-下午4:01:18  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 		类名称： MD5Util 
 * 		类描述：MD5加密   --MD5 32位 大写
 *		创建人： LiXin 
 * 		创建时间： 2015年5月11日 下午4:01:18
 * 		@version 1.0
 */
public class MD5Util {
	// 全局数组
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public MD5Util() {
	}

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}
	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}
	/**
	 * 
	 * getMD5Code加密
	 * @param strObj--原值
	 * @return
	 */
	public static String getMD5Code(String strObj) {
		String resultString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString;
	}
	/**
	 * 
	 * validateMD5Code 校验加密字符串  
	 * @param newCode   --未加密的字符串
	 * @param md5Code   --已加密的字符串
	 * @return
	 */
	public static Boolean validateMD5Code(String newCode,String md5Code) {
		Boolean boo = false;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			String resultString = byteToString(md.digest(newCode.getBytes()));
			if(resultString.equals(md5Code)){
				boo = true;
			}
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return boo;
	}
}
