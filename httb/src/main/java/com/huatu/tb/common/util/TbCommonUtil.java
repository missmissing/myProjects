/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.common.util  
 * 文件名：				CommonUtil.java    
 * 日期：				2015年5月8日-下午3:47:57  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**   
 * 类名称：				CommonUtil  
 * 类描述：  			业务系统工具类
 * 创建人：				LiXin
 * 创建时间：			2015年5月8日 下午3:47:57  
 * @version 		1.0
 */
public class TbCommonUtil {
	/**
	 * 获取年份列表
	 * getYearList  
	 * @return
	 */
	public static List<String> getYearList(){
		List<String> yList = new ArrayList<String>();
		Calendar  calendar = Calendar.getInstance();
		int thisYear = calendar.get(Calendar.YEAR);
		for(int i=thisYear+2;i>thisYear-16;i--){
			yList.add(i+"");
		}
		return yList;
	}
}
