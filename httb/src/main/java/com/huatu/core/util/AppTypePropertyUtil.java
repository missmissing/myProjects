/**   
 * 项目名：				httb
 * 包名：				com.huatu.core.util  
 * 文件名：				AppTypePropertyUtil.java    
 * 日期：				2015年6月17日-下午3:30:54  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.core.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.huatu.tb.common.enums.AreaEnum;
import com.huatu.tb.common.enums.Category_JR_Enum;
import com.huatu.tb.common.enums.Category_JS_Enum;
import com.huatu.tb.common.enums.Category_SYDW_Enum;
import com.huatu.tb.common.enums.Category_Y_Enum;
import com.huatu.tb.paper.model.Paper;

/**   
 * 类名称：				AppTypePropertyUtil  
 * 类描述：  			系统启动类型 属性变更  工具类
 * 创建人：				LiXin
 * 创建时间：			2015年6月17日 下午3:30:54  
 * @version 		1.0
 */
public class AppTypePropertyUtil {
	public static String GONGWUYUAN = "m300";//公务员根节点
	public static String NATIONWIDE = "301";//app端的公务员下-国家标识
	/*应用部署类型   value - key
	 * 医疗-100
	 * 金融 -200
	 * 公务员 -300
	 * 教师 -400
	 * 事业单位 -500
	 * **/
	public static int APP_TYPE = 300;//默认公务员
	/**系统启动不同启动模式下的分类集合*/
	public static Set<String> set = new HashSet<String>(); 
	
	/**
	 * 应用初始化
	 * appinit  
	 * @exception
	 */
	public static void appinit(){
		Properties p = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("httbContext");
		/** 初始化 系统部署类型 如公务员或医疗或 教师 */
		AppTypePropertyUtil.APP_TYPE = Integer.parseInt(p.getProperty(Constants.P_APP_DEPLOY_TYPE));
		
		/**系统启动是否初始化缓存数据*/
		Constants.ISINIT = Boolean.parseBoolean(p.getProperty(Constants.P_INIT_REFRESH));
		
		/**定时任务是否生效 */
		Constants.ISTIMEDTASK = Boolean.parseBoolean(p.getProperty(Constants.P_START_TIMED_TASK));
		switch (AppTypePropertyUtil.APP_TYPE) {
		case 500: {
			AppTypePropertyUtil.set = Category_JS_Enum.getAllMap().keySet();
			break; 
		}
		case 400: {
			AppTypePropertyUtil.set = Category_SYDW_Enum.getAllMap().keySet();
			break; 
		}
		case 300: {
			AppTypePropertyUtil.set = AreaEnum.getAllMap().keySet();
			break; 
		}
		case 200: {
			AppTypePropertyUtil.set = Category_JR_Enum.getAllMap().keySet();
			break; 
		}
		case 100: {
			AppTypePropertyUtil.set = Category_Y_Enum.getAllMap().keySet();
			break; 
		}
		// case 300:con.set= areaenue.getaoomap();,keyset'
		default:break; 
		}
	}
	/**
	 * 获取试题查询 分类对应字段值
	 * getPaperTypepro  
	 * @exception 
	 * @param paper
	 * @return
	 */
	//这个方法没用？
	public static List<String> getPaperTypepro(Paper paper){
		List<String> list = new ArrayList<String>();
		switch (AppTypePropertyUtil.APP_TYPE) {
		case 300: {
			list = paper.getPareas();//地区
			break; 
		}
		case 100: {
			list = paper.getPcategorys();//分类
			break; 
		}
		default:break; 
		}
		return list;
	}

}
