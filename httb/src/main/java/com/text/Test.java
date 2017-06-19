/**   
 * 项目名：				httb
 * 包名：				com.text  
 * 文件名：				Test.java    
 * 日期：				2015年7月6日-下午4:16:45  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**   
 * 类名称：				Test  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年7月6日 下午4:16:45  
 * @version 		1.0
 */
public class Test {
	public static void main(String args[]) {
		StringBuffer content = new StringBuffer();
		content.append("<!--[img style=\"width:472px;height:208px;\"]1.png[/img]-->");
		content.append("<!--[img style=\"width:472px;height:208px;\"]2.png[/img]-->");
		content.append("<!--[img]3.png[/img]-->");
		Test.show(content.toString());
	}
	
	private static void show(String content) {
		String regular = "<!--([^<]*)-->"; 
		Pattern pattern = Pattern.compile(regular);
		Matcher m = pattern.matcher(content.toString());
		while (m.find()) {
			System.out.println(m.group());
			System.out.println(Test.getStyle(m.group()));
			System.out.println(Test.getAddr(m.group()));
			System.out.println("================================");
		}
	}
	
	private static String getStyle(String s) {
		String regular = "style=\"([^\"]+)\"";
		Pattern pattern = Pattern.compile(regular);
		Matcher m = pattern.matcher(s);
		if (m.find()) {
			return m.group().replaceFirst("style=\"", "").replaceFirst("\"", "");
		}
		return "";
	}
	
	private static String getAddr(String s) {
		String regular = "\\]([^\"]+)\\["; ;
		Pattern pattern = Pattern.compile(regular);
		Matcher m = pattern.matcher(s);
		if (m.find()) {
			return m.group().replaceFirst("\\]", "").replaceFirst("\\[", "");
		}
		return "";
	}

}
