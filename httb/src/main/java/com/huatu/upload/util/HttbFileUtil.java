package com.huatu.upload.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.ProcessPropertiesConfigUtil;

/**
 *
 * 类名称：								HTFileUtil
 * 类描述：  								httn文件工具类
 * 创建人：								Aijunbo
 * 创建时间：								2014-11-24 下午7:24:45
 * @version 							0.0.1
 */
public class HttbFileUtil {

	private static Logger log = Logger.getLogger(HttbFileUtil.class);


	/**
	 *
	 * getServerPath(取得服务器路径)
	 * @param 			request					HttpServletRequest
	 * @return  		String   				服务器路径
	 * @exception
	 * @since  			1.0.0
	 */
	public static String getServerPath(HttpServletRequest request) {
		// 使用StringBuffer拼接服务器路径
		StringBuffer path = new StringBuffer();
		// 拼接协议名称
		path.append(request.getScheme() + "://");
		// 拼接服务器IP
		path.append(request.getServerName() + ":");
		// 拼接服务端口
		path.append(request.getServerPort());
		// 拼接工程上下文
		path.append(request.getContextPath());
		// 返回服务器路径
		return path.toString();
	}

	/**
	 *
	 * getUploadPath(取得系统配置的上传保存临时目录和最终目录)
	 * @param 			request					HttpServletRequest
	 * @return  		Map<String,Object>		返回的配置参数
	 * @exception
	 */
	public static Map<String, Object> getUploadPath(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		//根据不同题库，选择不同的图片服务器
		String imageurl = "";
		Properties typeTable = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("httbContext");
		String type = typeTable.getProperty("app.deploy.type");
		switch (type) {

		case "100": {// 医疗			
			String url = typeTable.getProperty("pic_yl");
			imageurl = "http://"+url+"/";
			break;
		}
		case "200": {// 金融
			String url = typeTable.getProperty("pic_jr");
			imageurl = "http://"+url+"/";
			break;
		}
		case "300": {// 公务员
			String url = typeTable.getProperty("pic_gwy");
			imageurl = "http://"+url+"/";
			break;
		}
		case "400": {// 教师
			String url = typeTable.getProperty("pic_js");
			imageurl = "http://"+url+"/";
			break;
		}
		case "500": {// 事业单位
			String url = typeTable.getProperty("pic_sydw");
			imageurl = "http://"+url+"/";
			break;
		}
		default: {
			break;
		}

		}
		//******************end*****************************
		// 读取upload.properties配置集
		//Properties p = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("upload");

		// 取得文件上传保存的临时目录，初始化为服务器上的目录
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String projectName = request.getContextPath().substring(1);
		//图片存储路径
		String filePath = realPath.substring(0,realPath.lastIndexOf(projectName))+"image";
		// 若有配置则启用配置的临时目录
		//String imageurl = p.getProperty("IMAGE_URL");
		map.put("filePath", filePath);
		map.put("imageurl", imageurl);
		return map;
	}

	/**
	 *
	 * getServerPath(取得服务器上指定目录)
	 * @param 			catalogue				指定文件夹
	 * @param 			request					HttpServletRequest
	 * @return  		String   				返回的服务器上的指定文件夹路径
	 * @exception
	 */
	public static String getServerPath(String catalogue, HttpServletRequest request) {
		// 取得服务器上的指定目录文件夹并返回
 		return request.getSession().getServletContext().getRealPath(catalogue);
	}

	/**
	 *
	 * createFileMkdirs(依据目录路径创建目录)
	 * @param 			path					目录路径
	 * @return  		File   					返回的目录File
	 * @exception
	 */
	public static File createFileMkdirs(String path) {
		// 若目录不为空则进行创建操作
		if (CommonUtil.isNotEmpty(path)) {
			// 根据给定的uri创建file
			File dir = new File(path);
			// 判断目录不存在，则创建出该目录
			if (!dir.exists()) {
				// 创建目录
				dir.mkdirs();
			}
			return dir;
		}
		// 目录为空则不创建目录，返回null
		return null;
	}

	/**
	 *
	 * getFileVisitPath(取得文件访问路径)
	 * @param 			request				HttpServletRequest
	 * @param 			path				数据库表中保存的文件路径
	 * @return  		String   			文件访问路径
	 * @exception
	 */
	public static String getFileVisitPath(HttpServletRequest request, String path) {
		// 判断path是否存在
		if(CommonUtil.isEmpty(path)){
			return null;
		}
		// 取得文件上传保存目录配置参数集
		Map<String, Object> map = HttbFileUtil.getUploadPath(request);
		// 取得文件名
		String fileName = path.substring(path.lastIndexOf("/") + 1);
		// 取得文件访问路径
		String filePath = HttbFileUtil.getFileRealPath(request, map, fileName);
		// 返回文件访问路径
		return filePath;
	}

	/**
	 *
	 * getFileRealPath(取得临时文件访问路径)
	 * @param 			request				HttpServletRequest
	 * @param 			map					系统上传目录配置参数集
	 * @param 			FileName			上传的新文件名称
	 * @return  		String   			临时文件访问路径
	 * @exception
	 */
	public static String getFileRealPath(HttpServletRequest request, Map<String, Object> map, String fileName) {
		String urlpath = (String) map.get("imageurl")+fileName;
		// 取得properties配置的临时保存路径，并返回临时文件访问路径
		return urlpath;
	}




}
