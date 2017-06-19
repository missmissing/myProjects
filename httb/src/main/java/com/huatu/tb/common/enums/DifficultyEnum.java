/**
 * 项目名：				httb
 * 包名：				com.huatu.tb.common.enums
 * 文件名：				DifficultyEnum.java
 * 日期：				2015年4月28日-下午2:31:28
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.tb.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：				DifficultyEnum
 * 类描述：                                        试题难易程度对象结合
 * 创建人：				LiXin
 * 创建时间：			2015年4月28日 下午2:31:28
 * @version 		1.0
 */
public enum DifficultyEnum {
	JD ("简单","-2"),
	JY("较易","-1"),
	ZD("中等","0"),
	JN ("较难","1"),
	TN ("困难","2");

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
		for (DifficultyEnum item : DifficultyEnum.values()) {
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
		for (DifficultyEnum item : DifficultyEnum.values()) {
			if (item.getCode().equals(key)) {
				// 返回名称
				return item.getText();
			}
		}
		return null;
	}

	/**
	 *
	 * getValueByText						(根据text取得VALUE)
	 * @param 		text					文本
	 * @return  	String   				返回的value
	 */
	public static String getValueByText(String text) {
		// 遍历所有状态
		for (DifficultyEnum item : DifficultyEnum.values()) {
			if (item.getText().equals(text)) {
				// 返回名称
				return item.getCode();
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
	private DifficultyEnum(String text,String code){
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
