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
 * 类描述：  			试卷分类（医疗） 
 * 创建人：				LiXin
 * 创建时间：			2015年6月16日 上午10:20:59  
 * @version 		1.0
 */
public enum Category_Y_Enum {
	zgks("资格考试","100"),
	sydwks("事业单位考试","101");
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
		for (Category_Y_Enum item : Category_Y_Enum.values()) {
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
		for (Category_Y_Enum item : Category_Y_Enum.values()) {
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
	private Category_Y_Enum(String name,String code){
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
