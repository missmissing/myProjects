/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.common.enums  
 * 文件名：				YesNoEnum.java    
 * 日期：				2015年4月28日-下午2:15:38  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.common.enums;

import java.util.HashMap;
import java.util.Map;

/**   
 * 类名称：				YesNoEnum  
 * 类描述：  			是, 否 两个选择 枚举对象
 * 创建人：				LiXin
 * 创建时间：			2015年4月28日 下午2:15:38  
 * @version 		1.0
 */
public enum YesNoEnum {
	
	YES ("是","0"),
	NO("否","1");
	
	//状态——值
	private String text;
	//状态——key
	private String code;
		
	/**
	 * 获取全部集合 
	 * 			格式 ---key：0/value：是
	 * @return  Map<String,Object>   返回的键值容器
	 */
	public static Map<String , Object> getAllMap() {
		// 定义Map容器用以存放状态键值关系
		Map<String , Object> map = new HashMap<String , Object>();
		// 遍历所有状态
		for (YesNoEnum item : YesNoEnum.values()) {
			// 将键值关系放入Map中
			map.put(item.getCode(), item.getText());
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
		for (YesNoEnum item : YesNoEnum.values()) {
			if (item.getCode().equals(key)) {
				// 返回名称
				return item.getText();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * 创建一个新的实例 YesNoEnum.   
	 * @param Value 状态——值
	 * @param key 状态——key
	 */
	private YesNoEnum(String text,String code){
		this.text = text;
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}

