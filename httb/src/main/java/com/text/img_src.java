/**   
 * 项目名：				httb
 * 包名：				com.text  
 * 文件名：				img_src.java    
 * 日期：				2015年7月6日-下午4:24:32  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名称： img_src 类描述： 创建人： LiXin 创建时间： 2015年7月6日 下午4:24:32
 * 
 * @version 1.0
 */
public class img_src {

	public static void main(String[] args) {
		String html = "<img alt=\"\" data-cke-saved-src=\"http://tpjs.huatu.com/400/1436169092149.jpg\" src=\"http://tpjs.huatu.com/400/1436169092149.jpg\" style=\"border-width: 2px; border-style: solid; margin: 23px; float: left; width: 23px; height: 23px;\" ><img alt=\"\" data-cke-saved-src=\"http://tpjs.huatu.com/400/1436169092149.jpg\" src=\"http://tpjs.huatu.com/400/1436169092149.jpg\" style=\"border-width: 2px; border-style: solid; margin: 23px; float: left; width: 23px; height: 23px;\" ><img alt=\"\" data-cke-saved-src=\"http://tpjs.huatu.com/400/1436169092149.jpg\" src=\"http://tpjs.huatu.com/400/1436169092149.jpg\" style=\"border-width: 2px; border-style: solid; margin: 23px; float: left; width: 23px; height: 23px;\" ><img alt=\"\" data-cke-saved-src=\"http://tpjs.huatu.com/400/1436169092149.jpg\" src=\"http://tpjs.huatu.com/400/1436169092149.jpg\" style=\"border-width: 2px; border-style: solid; margin: 23px; float: left; width: 23px; height: 23px;\" >";

		System.out.println(getImgSrc(html));
	}
		//     <img\\s+(?:[^>]*)src\\s*=\\s*([^>]+)  <img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>
	public static final Pattern PATTERN = Pattern.compile("<img[^>]+style\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

	public static List getImgSrc(String html) {
		Matcher matcher = PATTERN.matcher(html);
		List list = new ArrayList();
		while (matcher.find()) {
			String group = matcher.group(1);
			int last = group.lastIndexOf("/");
			System.out.println(group.substring(last+1, group.length())+"--");
			if (group == null) {
				continue;
			}
		}
		return list;
	}

}
