package com.huatu.core.util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DateUtil {

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取现在时间
	 *
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();

		return formatter.format(currentTime);
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 *
	 * @param dateDate
	 * @return
	 */
	public static String dateToString(Date dateDate) {
		if(dateDate!=null){
			return formatter.format(dateDate);
		}else{
			return "";
		}
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {

		ParsePosition pos = new ParsePosition(0);

		return formatter.parse(strDate, pos);
	}

	public static String date2SqlServerStarDateTime(Date startDate) {

		return formatter.format(startDate) + ".000";
	}

	public static String date2SqlServerEndDateTime(Date endDate) {

		return formatter.format(endDate) + ".999";
	}

	public static String dateToStartStr(Date date){
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String resultDate = df.format(date);
		 return resultDate;
	}

	public static String dateToEndStr(Date date){
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String resultDate = df.format(date);
		 return resultDate;
	}

}
