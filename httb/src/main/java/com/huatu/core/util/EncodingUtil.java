package com.huatu.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;

/**
 *
 * 类名称：							EncodingUtil
 * 类描述： 	 						转码，处理中文乱码
 * 创建人：							Aijunbo
 * 创建时间：							2014-11-14 上午11:09:49
 */
public class EncodingUtil {

	/**
	 * 解码
	 * @param 		obj				需要转码的字符窜
	 * @return		String			处理后的字符串
	 * @throws 		HttbException
	 */
	public static String decode(String obj) throws HttbException {
		try {
			if (!CommonUtil.isNotNull(obj)) {
				return null;
			}
			// 将传入参数进行中文转码并返回
			return URLDecoder.decode(obj, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new HttbException(ModelException.JAVA_STRING_DECODE, "字符串解码时发生异常", e);
		}
	}
	/**
	 * 编码
	 * @param 		obj 			需要编码的字符窜
	 * @return 						处理后的字符串
	 * @throws 		HttbException
	 */
	public static String encode(String obj)throws HttbException{
		try {
			if (!CommonUtil.isNotNull(obj)) {
				return null;
			}
			// 将传入参数进行中文编码码并返回
			return URLEncoder.encode(obj, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new HttbException(ModelException.JAVA_STRING_DECODE, "字符串解码时发生异常", e);
		}
	}

}
