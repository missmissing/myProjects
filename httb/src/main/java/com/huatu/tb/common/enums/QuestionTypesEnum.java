/**
 * 项目名：				httb
 * 包名：				com.huatu.tb.common.enums
 * 文件名：				QuestionTypesEnum.java
 * 日期：				2015年5月4日-上午11:36:49
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.tb.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：				QuestionTypesEnum
 * 类描述：  			题型
 * 创建人：				LiXin
 * 创建时间：			2015年5月4日 上午11:36:49
 * @version 		1.0
 */
public enum QuestionTypesEnum {
	danx("单选题","0"),
	duox("多选题","1"),
	gytg("共用题干","2"),
	gybx ("共用备选","3");

	//状态——值
	private String text;
	//状态——key
	private String code;

	/**
	 * 获取全部集合
	 * 			格式 ---key：0/value：中等
	 * @return  Map<String,Object>   返回的键值容器
	 */
	public static Map<String , Object> getAllMap() {
		// 定义Map容器用以存放状态键值关系
		Map<String , Object> map = new HashMap<String , Object>();
		// 遍历所有状态
		for (QuestionTypesEnum item : QuestionTypesEnum.values()) {
			// 将键值关系放入Map中
			map.put(item.getCode(), item.getText());
		}
		// 返回容器
		return map;
	}

	/**
	 * 获取全部集合
	 * 			格式 ---key：中等/value：0
	 * @return  Map<String,Object>   返回的键值容器
	 */
	public static Map<String , Object> getAllMapInput() {
		// 定义Map容器用以存放状态键值关系
		Map<String , Object> map = new HashMap<String , Object>();
		// 遍历所有状态
		for (QuestionTypesEnum item : QuestionTypesEnum.values()) {
			// 将键值关系放入Map中
			map.put(item.getText(), item.getCode());
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
		for (QuestionTypesEnum item : QuestionTypesEnum.values()) {
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
	private QuestionTypesEnum(String text,String code){
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
