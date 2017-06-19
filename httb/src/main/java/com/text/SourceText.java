/**   
 * 项目名：				httb
 * 包名：				com.text  
 * 文件名：				SourceText.java    
 * 日期：				2015年7月14日-下午4:39:43  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**   
 * 类名称：				SourceText  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年7月14日 下午4:39:43  
 * @version 		1.0
 */
public class SourceText {

	public static void main(String args[]){
//		float num= (float)10/120;  
//		DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
//		String s = df.format(num);//返回的是String类型
//		System.out.println(num*100);
		
		List<String> list = new ArrayList<String>();
		list.add("2342");
		list.add("2342");
		list.add("2342");
		list.add("2342");
		list.add("2342");
		list.add("2342");
		list.add("2342");
		int rightNum = 2;
		float source= (float)rightNum/list.size();  
		DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
		String s = df.format(source*100);//返回的是String类型
		System.out.println(source);
		System.out.println( (float)(Math.round(source*10000))/100);
		
	}
}
