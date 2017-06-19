/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.common.enums  
 * 文件名：				AreaEnum.java    
 * 日期：				2015年4月28日-上午11:14:43  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.common.enums;

import java.util.HashMap;
import java.util.Map;

/**   
 * 类名称：				AreaEnum  
 * 类描述：  			地区类型（省级单位）
 * 创建人：				LiXin
 * 创建时间：			2015年4月28日 上午11:14:43  
 * @version 		1.0
 */
public enum AreaEnum {
	CHINA("全国","86"),
	BEIJING ("北京","110000"),
	TIANJIN("天津","120000"),
	HEBEI("河北","130000"),
	SHANXI("山西","140000"),
	NEIMENGGU("内蒙古","150000"),
	LIAONING("辽宁","210000"),
	JILIN("吉林","220000"),
	HEILONGJIONG("黑龙江","230000"),
	SHANGHAI("上海","310000"),
	JIANGSU("江苏","320000"),
	ZHEJIANG("浙江","330000"),
	ANHUI("安徽","340000"),
	FUJIAN("福建","350000"),
	JIANGXI("江西","360000"),
	SHANDONG("山东","370000"),
	HENAN("河南","410000"),
	HUBEI("湖北","420000"),
	HUNAN("湖南","430000"),
	GUANGDONG("广东","440000"),
	GUANGXI("广西","450000"),
	HAINAN("海南","460000"),
	CHONGQING("重庆","500000"),
	SICHUAN("四川","510000"),
	GUIZHOU("贵州","520000"),
	YUNNAN("云南","530000"),
	XIZANG("西藏","540000"),
	SHANXI2("陕西","610000"),
	GANSU("甘肃","620000"),
	QINGHAI("青海","630000"),
	NINGXIA("宁夏","640000"),
	XIJIANG("新疆","650000");
//	TAIWAN("台湾","710000"),
//	XIANGGANG("香港","810000"),
//	AOMEN("澳门","910000");
	
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
		for (AreaEnum item : AreaEnum.values()) {
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
		for (AreaEnum item : AreaEnum.values()) {
			if (item.getCode().equals(key)) {
				// 返回名称
				return item.getName();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * 创建一个新的实例 YlsqEnum.   
	 * @param Value 状态——值
	 * @param key 状态——key
	 */
	private AreaEnum(String name,String code){
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
	public static void main(String agrs[]){
		Map<String, Object> map = AreaEnum.getAllMap();
		for(String key : map.keySet()){
			System.out.println(key+","+map.get(key));
		}
	}
	
}
