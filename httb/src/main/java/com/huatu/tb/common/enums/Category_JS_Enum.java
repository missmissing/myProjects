/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.common.enums  
 * 文件名：				CategoryEnum.java    
 * 日期：				2015年6月16日-上午10:20:59  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.common.enums;

import java.util.HashMap;
import java.util.Map;

/**   
 * 类名称：				CategoryEnum  
 * 类描述：  			试卷分类（金融） 
 * 创建人：				ydp
 * 创建时间：			2015年6月29日
 * @version 		1.0
 */
public enum Category_JS_Enum {
	ty("体育","yj006"),
	yinyue("音乐","yj007"),
	zhsz("综合素质","yj0014"),
	yj("幼教","yj009"),
	yw("语文","yj0010"),
	ms("美术","yj004"),
	jyjxzsynl("教育教学知识与能力","yj0012"),
	jyzhzs("教育综合知识","yj0015"),
	yingyu("英语","yj008"),
	sx("数学","yj005"),
	bjzsynl("保教知识与能力","yj0013"),
	jyzsynl("教育知识与能力","yj0011");
	//状态——值
	private String name;
	//状态——key
	private String code;
		
	/**
	 * 获取全部地域集合 
	 * 			格式 ---key：11/value：北京
	 * @return  Map<String,Object>   返回的键值容器
	 */
	public static Map<String , Object> getAllMap() {
		// 定义Map容器用以存放状态键值关系
		Map<String , Object> map = new HashMap<String , Object>();
		// 遍历所有状态
		for (Category_JS_Enum item : Category_JS_Enum.values()) {
			// 将键值关系放入Map中
			map.put(item.getCode(), item.getName());
		}
		// 返回容器
		return map;
	}

	/**
	 * 
	 * getValueByKey(根据KEY取得VALUE)  
	 * @param 		key						所取得key值
	 * @return  	String   				返回的value
	 */
	public static String getValueByKey(String key) {
		// 遍历所有状态
		for (Category_JS_Enum item : Category_JS_Enum.values()) {
			if (item.getCode().equals(key)) {
				// 返回名称
				return item.getName();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param Value 状态——值
	 * @param key 状态——key
	 */
	private Category_JS_Enum(String name,String code){
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
