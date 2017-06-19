/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.action  
 * 文件名：				CategoryApiAction.java    
 * 日期：				2015年6月11日-下午8:13:04  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.action;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.annotation.HttpConstraint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatu.api.service.CategoryApiService;
import com.huatu.api.util.ApiUtil;
import com.huatu.api.vo.CategoryVo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;
import com.huatu.core.util.AppTypePropertyUtil;
import com.huatu.core.util.CommonUtil;
import com.huatu.ou.ascpectJ.apiLog.ApiAnnotation;
import com.huatu.tb.common.enums.AreaEnum;

/**   
 * 类名称：				CategoryApiAction  
 * 类描述：  			章节API-REST接口
 * 创建人：				LiXin
 * 创建时间：			2015年6月11日 下午8:13:04  
 * @version 		1.0
 */
@RequestMapping("/httbapi/category")
@Controller
@Scope("prototype")
public class CategoryApiAction {
	public  Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private CategoryApiService categoryApiService;
	/**
	 * 获取题库分类列表（大类及一级分析 如公务员及其下地区公务员）
	 * getTbClassList  
	 * @exception 
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/getTbClassList", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiAnnotation(operateFuncName = "获取题库菜单")
	public Message getTbClassList(@RequestParam String license) throws HttbException{
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			try {
				JSONArray appmenujson = categoryApiService.getTbClassList();
				message.setSuccess(true);
				message.setMessage("获取题库菜单成功");
				message.setData(appmenujson);
				log.info("获取题库菜单成功");
			} catch (HttbException e) {
				message.setSuccess(false);
				message.setMessage("获取题库菜单失败");
				e.printStackTrace();
				throw new HttbException(this.getClass() + "获取题库菜单失败", e);	
			}
		}else{
			message.setSuccess(false);
			message.setMessage("请求参数不合法,获取题库菜单失败");
			log.error("请求参数不合法,获取题库菜单失败");
		}
		return message;
	}
	/**
	 * 获取该节点下所有章节信息
	 * @exception  根据知识分类ID，获取该分类下所有知识分类
	 * @param  id 知识点ID
	 * @param  userno 用户no
	 * @param  area  地区 -- 用户公务员(国家 北京)
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/getSubsetById", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiAnnotation(operateFuncName = "获取该节点下所有章节信息")
	public Message getSubsetById(@RequestParam  String id,
								 @RequestParam String userno,
								 @RequestParam(value = "area", required = false) String area,
								 @RequestParam String license) throws HttbException{
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			if(CommonUtil.isNotNull(id) || CommonUtil.isNotNull(userno) ){  
				try {
					if(AppTypePropertyUtil.APP_TYPE ==300){
						if(AppTypePropertyUtil.NATIONWIDE.equals(id)){
							area = AreaEnum.CHINA.getCode();
						}
						id =AppTypePropertyUtil.GONGWUYUAN;
			        }else{
			        	area = "";
			        }
					CategoryVo cv = categoryApiService.getSubsetById(id,userno,area);
					message.setSuccess(true);
					message.setMessage("获取章节信息成功");
					message.setData(cv);
					log.info("获取章节信息成功");
				} catch (HttbException e) {
					message.setSuccess(false);
					message.setMessage("获取章节信息失败");
					e.printStackTrace();
					throw new HttbException(this.getClass() + "获取章节信息失败", e);	
				}
			}else{
				message.setSuccess(false);
				message.setMessage("请求参数不合法,获取章节信息失败");
				log.error("请求参数不合法,获取章节信息失败");
			}
			
		}else{
			message.setSuccess(false);
			message.setMessage("许可证错误,认证失败");
			log.error("许可证错误,认证失败");
		}
		return message;
	}
	/**
	 * 通过章节id获取其下所有所有试题【返回数据压缩加密】
	 * @exception  getQuestions
	 * @param  id 知识点ID
	 * @param  area  地区
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/getQuestions", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiAnnotation(operateFuncName = "通过章节id获取其下所有所有试题【返回数据压缩加密】")
	public void getQuestions(@HttpConstraint HttpServletRequest request, @HttpConstraint HttpServletResponse response,
								@RequestParam  String id,
								@RequestParam(value = "area", required = false)  String area,
								@RequestParam String license) throws HttbException{
		long startTime = System.currentTimeMillis();    //获取开始时间
		if(ApiUtil.validateLicense(license)){
			if(CommonUtil.isNotNull(id)){  
				try {
					//设置response的编码方式
			    	 response.setContentType("text/plain; charset=utf-8");
			         response.setCharacterEncoding("UTF-8");
			         try {
			        	 /*如果为国家 默认加上国家编码  原因：redis中缓存国家的数据时key+国家编码*/
			        	 if(AppTypePropertyUtil.APP_TYPE ==300 && CommonUtil.isNull(area)){
			        		area = AreaEnum.CHINA.getCode();
			        	 }
			             InputStream   in_nocode   =   new   ByteArrayInputStream(categoryApiService.getQuestions(id, area).getBytes());   
			            // InputStream   in_withcode   =   new   ByteArrayInputStream(str.getBytes("UTF-8"));  
			             // 清空response
			             response.reset();
			             OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				         //设置附加文件名(解决中文乱码)
				         response.setHeader("Content-Disposition","attachment;filename=" + new String("category.txt".getBytes("gbk"), "iso-8859-1"));
				         //设置长度
				         response.setHeader("content-length",in_nocode.available()+"");
				         long endTime = System.currentTimeMillis();    //获取结束时间
				         write(in_nocode,toClient,true);
				         log.error(new Date()+"通过章节id获取其下所有所有试题功能 用时:"+(endTime - startTime) + "ms");
			         } catch (IOException ex) {
			             ex.printStackTrace();
			         }
				} catch (HttbException e) {
					e.printStackTrace();
					throw new HttbException(this.getClass() + "通过章节id获取其下所有所有试题失败", e);	
				}
			}else{
				log.error("请求参数不合法,通过章节id获取其下所有所有试题失败");
			}
			
		}else{
			log.error("许可证错误,认证失败");
		}
	}
	 /**
     * 自动从inputstream里面读数据，然后写到outputstream里面去。
     * @param input inputstream
     * @param output outputstream
     * @param close 读完后是否自动关闭流。
     * */
     public static void write(InputStream input, OutputStream output,boolean close)
            throws IOException {
        byte[] b = new byte[1536];
        int len = input.read(b);
        while (len != -1) {
            output.write(b, 0, len);
            len = input.read(b);
        }
        
        output.flush();
        if (close) {
            input.close();    
            output.close();
        }
    }
}
