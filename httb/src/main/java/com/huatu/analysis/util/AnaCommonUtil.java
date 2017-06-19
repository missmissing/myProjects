/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.util
 * 文件名：				AnaCommonUtil.java
 * 日期：				2015年6月24日-上午10:14:56
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.util;

import java.util.Calendar;

import com.huatu.core.util.CommonUtil;

/**
 * 类名称：				AnaCommonUtil
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月24日 上午10:14:56
 * @version 			0.0.1
 */
public class AnaCommonUtil {
	/**
	 *
	 * getRecordDate				(获取记录时间戳)年月日时分秒
	 * 								(插入分析记录时调用)
	 * @return
	 */
	public static String getRecordDate(){
		//年月日时分秒
		Calendar cal = Calendar.getInstance();
		String year = CommonUtil.getYear(cal);
		String month = CommonUtil.getMonth(cal);
		String date = CommonUtil.getDay(cal);
		String hour = CommonUtil.getHour(cal);
		String mm = CommonUtil.getMinute(cal);
		String ss = CommonUtil.getSecond(cal);
		return year+month+date+hour+mm+ss;
	}

	/**
	 *
	 * getRecordDate				(获取记录时间戳)年月日
	 * 								(插入分析记录时调用)
	 * @return
	 */
	public static String getRecordDay(){
		//年月日时分秒
		Calendar cal = Calendar.getInstance();
		String year = CommonUtil.getYear(cal);
		String month = CommonUtil.getMonth(cal);
		String date = CommonUtil.getDay(cal);
		return year+month+date;
	}

	/**
	 *
	 * formatSQLStr					(把SQL语句中最后一个and去掉)
	 * @param 		str				执行SQL
	 * @return
	 */
	public static String formatSQLStr(String str){
		int index = str.lastIndexOf(" and ");
		if(index >= 0){
			String strs = str.substring(0,index);
			String stre = str.substring(index+5);
			str = strs + stre;
		}

		return str;
	}
}
