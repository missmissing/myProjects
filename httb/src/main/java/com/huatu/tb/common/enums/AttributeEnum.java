/**
 * 项目名：				httb
 * 包名：				com.huatu.tb.paper
 * 文件名：				AttributuEnums.java
 * 日期：				2015年5月12日-下午4:43:16
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.tb.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：				AttributuEnums
 * 类描述：  			属性枚举
 * 创建人：				LiXin
 * 创建时间：			2015年5月12日 下午4:43:16
 * @version 		1.0
 */
public enum AttributeEnum {
	//(0=>真题,1=>模拟,2=>顺序,3=>模块)
	ZHENTI("真题","0"),
	MONITI("模拟题","1");
	//SHUNXU("顺序","2"),
	//MOKUAI("模块","3")
	/**
	 *
	 * 创建一个新的实例
	 * @param Value 状态——值
	 * @param key 状态——key
	 */
	private AttributeEnum(String name,String code){
		this.name = name;
		this.code = code;
	}
	/**
	 * 获取全部地域集合
	 * 			格式 ---key：11/value：北京
	 * @return  Map<String,Object>   返回的键值容器
	 */
	public static Map<String , Object> getAllMap() {
		// 定义Map容器用以存放状态键值关系
		Map<String , Object> map = new HashMap<String , Object>();
		// 遍历所有状态
		for (AttributeEnum item : AttributeEnum.values()) {
			// 将键值关系放入Map中
			map.put(item.getCode(), item.getName());
		}
		// 返回容器
		return map;
	}
	/**
	 * 获取全部地域集合
	 * 			格式 ---key：北京 /value：0112030
	 * @return  Map<String,Object>   返回的键值容器
	 */
	public static Map<String , Object> getAllMapKV() {
		// 定义Map容器用以存放状态键值关系
		Map<String , Object> map = new HashMap<String , Object>();
		// 遍历所有状态
		for (AttributeEnum item : AttributeEnum.values()) {
			// 将键值关系放入Map中
			map.put(item.getName(),item.getCode());
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
		for (AttributeEnum item : AttributeEnum.values()) {
			if (item.getCode().equals(key)) {
				// 返回名称
				return item.getName();
			}
		}
		return null;
	}
	private String name;//属性名称
	private String code;//属性标识
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
