package com.huatu.tb.quesInput.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * 类名称：				QuesAttrMap
 * 类描述：  				试题模板集合
 * 创建人：				Aijunbo
 * 创建时间：				2015年5月6日 下午8:30:54
 * @version 			0.0.1
 */
public class QuesAttrMap {
	/**
	 *
	 * getItemmap						(初始化共用题干子题map题型集合)
	 * @return
	 */
	public static Map<String, String> getItemmap(){
		//初始化map
		Map<String, String> initChildMap = new LinkedHashMap<String, String>();
		//先初始化子题内容
		initChildMap.put("【子题干】","qccontent");
		initChildMap.put("【题干】","qccontent");
		initChildMap.put("【答案】","qcans");
		initChildMap.put("【解析】","qccomment");
		initChildMap.put("【拓展】","qcextension");

		return initChildMap;
	}

	/**
	 * getGYTGmap						(初始化共用题干map题型集合)
	 * @return
	 */
	public static Map<String, String> getQuesGYTGmap(){
		//初始化map
		Map<String, String> initMap = new LinkedHashMap<String, String>();
		//初始化试题内容
		initMap.put("【题型】","qtype");
		initMap.put("【难度】","qdifficulty");
		initMap.put("【题干】","qcontent");
		initMap.put("【考试类型】","qcategory");
		initMap.put("【知识点】","qpoint");
		initMap.put("【年份】","qyear");
		initMap.put("【信息源】","qfrom");
		initMap.put("【属性】","qattribute");
		initMap.put("【作者】","qauthor");
		return initMap;
	}

	/**
	 * getQuesGYBXmap				(初始化共用备选map题型集合)
	 * @return
	 */
	public static Map<String, String> getQuesGYBXmap(){
		//初始化map
		Map<String, String> initMap = new LinkedHashMap<String, String>();
		//初始化试题内容
		initMap.put("【题型】","qtype");
		initMap.put("【难度】","qdifficulty");
		initMap.put("【考试类型】","qcategory");
		initMap.put("【知识点】","qpoint");
		initMap.put("【年份】","qyear");
		initMap.put("【信息源】","qfrom");
		initMap.put("【属性】","qattribute");
		initMap.put("【作者】","qauthor");
		return initMap;
	}

}
