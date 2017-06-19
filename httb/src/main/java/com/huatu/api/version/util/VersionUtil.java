/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.version.util  
 * 文件名：				VersionUtil.java    
 * 日期：				2015年6月24日-上午10:44:37  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.version.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**   
 * 类名称：				VersionUtil  
 * 类描述：  			服务于版本的相关工具方法
 * 创建人：				LiXin
 * 创建时间：			2015年6月24日 上午10:44:37  
 * @version 		1.0
 */
public class VersionUtil {
	
	/**
	 * 获取版本号
	 * getVersionNumber  
	 * @exception 
	 * @return
	 */
	public static String getVersionNumber(){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return fmt.format(new Date());
	}
}
