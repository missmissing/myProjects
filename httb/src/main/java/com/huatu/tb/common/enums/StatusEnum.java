/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.paper.enums  
 * 文件名：				StatusEnum.java    
 * 日期：				2015年5月12日-下午4:50:59  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.common.enums;

import java.util.HashMap;
import java.util.Map;

/**   
 * 类名称：				StatusEnum  
 * 类描述：  			试卷状态枚举
 * 创建人：				LiXin
 * 创建时间：			2015年5月12日 下午4:50:59  
 * @version 		1.0
 */
public enum StatusEnum {
	//0->编辑中；1->待审核；2->审核中；3->发布；4->退回
	BJZ("编辑中","0"),
	DSH("待审核","1"),
	SHZ("审核中","2"),
	FB("发布","3"),
	TH("退回","4"),
	XX("下线","5");
	/**
	 * 
	 * 创建一个新的实例 
	 * @param Value 状态——值
	 * @param key 状态——key
	 */
	private StatusEnum(String name,String code){
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
		for (StatusEnum item : StatusEnum.values()) {
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
		for (StatusEnum item : StatusEnum.values()) {
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
