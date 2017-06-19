/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.action  
 * 文件名：				CategoryApiAction.java    
 * 日期：				2015年6月11日-下午8:13:04  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.phpaction;

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
import com.huatu.tb.common.enums.AreaEnum;

/**   
 * 类名称：				CategoryphpApiAction  
 * 类描述：  			章节API-PHP端rest接口
 * 创建人：				LiXin
 * 创建时间：			2015年6月11日 下午8:13:04  
 * @version 		1.0
 */
@RequestMapping("/httbphpapi/category")
@Controller
@Scope("prototype")
public class CategoryPhpApiAction {
	public  Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private CategoryApiService categoryApiService;
	
	/**
	 * 通过章节id获取其下所有所有试题【返回数据压缩加密】
	 * @exception  getQuestions
	 * @param  id 知识点ID
	 * @param  area  地区
	 * @param  udid  标识码      php - 0    app - 1
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/getQuestions", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void getQuestions(@HttpConstraint HttpServletRequest request, @HttpConstraint HttpServletResponse response,
								@RequestParam  String id,
								@RequestParam(value = "area", required = false)  String area,
								@RequestParam String udid,
								@RequestParam String license){
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
					log.error("通过章节id获取其下所有所有试题失败", e);
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
