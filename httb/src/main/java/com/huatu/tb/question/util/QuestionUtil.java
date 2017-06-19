/**
 * 项目名：				httb
 * 包名：				com.huatu.tb.question.util
 * 文件名：				QuestionUtil.java
 * 日期：				2015年5月10日-上午10:58:46
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.tb.question.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.ProcessPropertiesConfigUtil;

/**
 * 类名称： QuestionUtil 类描述： 试题工具类 创建人： LiXin 创建时间： 2015年5月10日 上午10:58:46
 * 
 * @version 1.0
 */
public class QuestionUtil {
	//插入数据库是img装换 ------<img\\s+(?:[^>]*)src\\s*=\\s*([^>]+)
	public static final Pattern PATTERN_IMG = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式  
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式  
    private static final String regEx_span = "<span[^>]*?>[\\s\\S]*?<\\/span>"; // 定义style的正则表达式  
    private static final String regEx_spanStyle = "<spanstyle[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式  
   // private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符 
    /**
     * 存入数据库 img 转换
     * imgLabelTransform  
     * @exception 
     * @param content
     * @return
     */
    public static String imgLabelTransform(String content){
    	String result = content;
    	if(CommonUtil.isNotNull(result)){
    		Matcher matcher = PATTERN_IMG.matcher(content);
    		while (matcher.find()) {
    			String img_sql = "";//数据库中的img代码
    			String img_html = matcher.group();//元图片代码
    			String src = matcher.group(1);
    			if (CommonUtil.isNotNull(src)) {
    				String style = getImgStyle(img_html);//样式
    				int last = src.lastIndexOf("/");//'/'最后出现的位置
    				String  img_name = src.substring(last+1, src.length());//截取图片名称
    				if(CommonUtil.isNotNull(style)){
    					img_sql = "<!--[img style=\""+style+"\"]"+img_name+"[/img]-->";
    				}else{
    					img_sql = "<!--[img]"+img_name+"[/img]-->";
    				}
    				result = result.replaceAll(img_html, img_sql);
    			}
    			
    		}
    	}    	
		return result;
    }
    /**
     * 获取img  样式
     * getImgStyle  
     * @exception 
     * @param imgHtml
     * @return
     */
    private static String getImgStyle(String imgHtml){
    	String imgStyle = "";
	    String regEx = "<img[^>]+style\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";//使用后面的需放开后面代码 <img\\s+(?:[^>]*)style\\s*=\\s*([^>]+)
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher m = pattern.matcher(imgHtml);
	    while(m.find()){
	    	imgStyle =  m.group(1);
//	    	if (imgStyle == null) {
//				continue;
//			}
//			if (imgStyle.startsWith("'")) {
//				imgStyle = imgStyle.substring(1, imgStyle.indexOf("'", 1));
//			} else if (imgStyle.startsWith("\"")) {
//				imgStyle = imgStyle.substring(1, imgStyle.indexOf("\"", 1));
//			} else {
//				imgStyle = imgStyle.split("\\s")[0];
//			}
        }
	    return imgStyle;
    }
    
	/**
	 * List转换成Str 用逗号分隔
	 * 
	 * @param list
	 * @return
	 */
	public static String ListToString(List<String> list) {
		String str = "";
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i == list.size() - 1) {
					str += list.get(i);
				} else {
					str += list.get(i) + ",";
				}
			}
		}
		return str;
	}

	/**
	 *
	 * getQCategoryCode (获取试题分类编码) (需要通过试题分类名称获取试题分类编码时调用)
	 * 
	 * @param cateName
	 *            试题分类名称
	 * @return
	 */
	public static String getQCategoryCode(String cateName) {
		// 读取upload.properties配置集
		Properties p = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("questionCatetory");
		// 遍历所有分类
		@SuppressWarnings("rawtypes")
		Iterator it = p.entrySet().iterator();
		String cateCode = cateName;
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
			// 试题分类编码
			Object code = entry.getKey();
			// 试题分类名称
			Object value = entry.getValue();
			// 如果试题分类等于传进来的参数，则赋值
			if (CommonUtil.isNotNull(value) && value.toString().equals(cateName)) {
				cateCode = code.toString();
			}
		}
		return cateCode;
	}

	/**
	 *
	 * getQCategoryName (获取试题分类名称) (需要通过试题分类编码获取试题分类名称时调用)
	 * 
	 * @param cateName
	 *            试题分类名称
	 * @return
	 */
	public static String getQCategoryName(List<String> cateCode) {
		// 读取upload.properties配置集
		Properties p = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("questionCatetory");
		StringBuffer cateNames = new StringBuffer("");
		if (CommonUtil.isNotNull(cateCode)) {
			for (int i = 0; i < cateCode.size(); i++) {
				if (cateCode.get(i) != null || !cateCode.get(i).equals("null")) {
					// 知识分类名称
					cateNames.append(p.getProperty(cateCode.get(i)));
					// 后面拼接链接符
					if (i < cateCode.size() - 1) {
						cateNames.append("#");
					}
				}

			}
		}
		return cateNames.toString();
	}
	/**
	 * 替换img样式 -- 前端可见
	 * imgTransformStyle  
	 * @exception 
	 * @param orig
	 * @return
	 */
	private static String imgTransformStyle2(String orig) {
		String des = orig;//返回样式
		String regEx = "<!\\-\\-\\[img\\s*(style=\\\"(.*)\\\")?\\s*\\]([\\w\\.]+)\\[/img\\]\\-\\->"; 
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher m = pattern.matcher(orig);
	    while(m.find()){
            String img_sql = m.group();//img在数据库中的样式
            String img_html = "";
            String style = m.group(2);//图片样式
            String src = m.group(3);//地址
            if(CommonUtil.isNotNull(style)){
            	img_html = "<img src=\"http://xxx.xxx.xxx.xxx/xxx/"+src+"\" style=\""+style+"\" />";
            }else{
            	img_html = "<img src=\"http://xxx.xxx.xxx.xxx/xxx/"+src+"\" />";
            }
            des = des.replace(img_sql, img_html);
        }
//		
//		
//		Properties typeTable = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("httbContext");
//		String type = typeTable.getProperty("app.deploy.type");
//		switch (type) {
//
//		case "100": {// 医疗			
//			String url = typeTable.getProperty("pic_yl");
//			des = des.replace("xxx.xxx.xxx.xxx/xxx",url);
//			break;
//		}
//		case "200": {// 金融
//			String url = typeTable.getProperty("pic_jr");
//			des = des.replace("xxx.xxx.xxx.xxx/xxx",url);
//			break;
//		}
//		case "300": {// 公务员
//			String url = typeTable.getProperty("pic_gwy");
//			des = des.replace("xxx.xxx.xxx.xxx/xxx",url);
//			break;
//		}
//		case "400": {// 教师
//			String url = typeTable.getProperty("pic_js");
//			des = des.replace("xxx.xxx.xxx.xxx/xxx",url);
//			break;
//		}
//		case "500": {// 事业单位
//			String url = typeTable.getProperty("pic_sydw");
//			des = des.replace("xxx.xxx.xxx.xxx/xxx",url);
//			break;
//		}
//		default: {
//			break;
//		}
//
//		}
		return des;

	}
	public static String imgTransformStyle(String orig) {
		if(!orig.contains("/img]")){
			return orig;
		}
		String des = "";		
		String[] strs = orig.split("/img]");
		for (int i = 0; i < strs.length; i++) {
			String index = strs[i];
			if (index.length() > 0) {
				if(!index.contains("[img")){
					continue;
				}
				int begin = (index.indexOf("<!--"));
				int end = (index.lastIndexOf("["));
				String index2 = index.substring(begin, end) + "[/img]-->";
				//String picsrc = index2.substring(index2.indexOf("]")+1,index2.length()-9);
				orig = orig.replace(index2,imgTransformStyle2(index2));
			}
		}
		des = orig;		
		Properties typeTable = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("httbContext");
		String type = typeTable.getProperty("app.deploy.type");
		switch (type) {

		case "100": {// 医疗			
			String url = typeTable.getProperty("pic_yl");
			des = des.replace("xxx.xxx.xxx.xxx/xxx",url);
			break;
		}
		case "200": {// 金融
			String url = typeTable.getProperty("pic_jr");
			des = des.replace("xxx.xxx.xxx.xxx/xxx",url);
			break;
		}
		case "300": {// 公务员
			String url = typeTable.getProperty("pic_gwy");
			des = des.replace("xxx.xxx.xxx.xxx/xxx",url);
			break;
		}
		case "400": {// 教师
			String url = typeTable.getProperty("pic_js");
			des = des.replace("xxx.xxx.xxx.xxx/xxx",url);
			break;
		}
		case "500": {// 事业单位
			String url = typeTable.getProperty("pic_sydw");
			des = des.replace("xxx.xxx.xxx.xxx/xxx",url);
			break;
		}
		default: {
			break;
		}

		}
		return des;

	}
	/** 
     * @param htmlStr 
     * @return 
     *  删除Html标签 
     */  
    public static String delHTMLTag(String htmlStr) {  
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);  
        Matcher m_script = p_script.matcher(htmlStr);  
        htmlStr = m_script.replaceAll(""); // 过滤script标签  
  
        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);  
        Matcher m_style = p_style.matcher(htmlStr);  
        htmlStr = m_style.replaceAll(""); // 过滤style标签  
  
        Pattern p_spans = Pattern.compile(regEx_spanStyle, Pattern.CASE_INSENSITIVE);  
        Matcher m_spans = p_spans.matcher(htmlStr);  
        htmlStr = m_spans.replaceAll(""); // 过滤html标签  
        
        Pattern p_span = Pattern.compile(regEx_span, Pattern.CASE_INSENSITIVE);  
        Matcher m_span = p_span.matcher(htmlStr);  
        htmlStr = m_span.replaceAll(""); // 过滤html标签  
  
        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);  
        Matcher m_space = p_space.matcher(htmlStr);  
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签 
        
        htmlStr = htmlStr.replaceAll("'", "<!--hxi1245nt-->");
        htmlStr = htmlStr.replaceAll(":<!--hxi1245nt-->", ":'");
        htmlStr = htmlStr.replaceAll("<!--hxi1245nt-->,", "',");
        htmlStr = htmlStr.replaceAll("<!--hxi1245nt-->", "");
        htmlStr = htmlStr.replaceAll("null", "''");
		
        return htmlStr.trim(); // 返回文本字符串  
    } 
    
//    public static void main(String args[]){
//    	String content = "<!--[img style=\"width:472px;height:208px;\"]1.png[/img]-->"
//	    		+"<!--[img style=\"width:472px;height:208px;\"]2.png[/img]-->"
//	    		+ "<!--[img]3.png[/img]-->";
//    	String list = QuestionUtil.imgLabelTransform(content);
//    	System.out.println(list);
//    	String reuslt =  QuestionUtil.imgTransformStyle(list);
//    	System.out.println(reuslt);
//    }
}
