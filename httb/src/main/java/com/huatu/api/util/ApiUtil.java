/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.util  
 * 文件名：				ApiUtil.java    
 * 日期：				2015年6月12日-上午10:41:08  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.util;

import java.text.DecimalFormat;

/**   
 * 类名称：				ApiUtil  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年6月12日 上午10:41:08  
 * @version 		1.0
 */
public class ApiUtil {

	/**
	 * APP 请求 许可证 校验
	 * validateLicense  
	 * @exception 
	 * @param license
	 * @return
	 */
	public static boolean validateLicense(String license) {
//        if (null != license && license.trim().length() > 0 && !"null".equals(license) && Constants.LICENSE.equals(license))
//            return true;
//        return false;
		return true;
	}
	
	/**
	 * 获取百分比
	 * getpercent  
	 * @exception 
	 * @return
	 */
	public static String getpercent(float flo ){
		float num= flo*100;  
		DecimalFormat df = new DecimalFormat("0");//格式化小数  
		String s = df.format(num);//返回的是String类型 
		return s;
	}
	
}
