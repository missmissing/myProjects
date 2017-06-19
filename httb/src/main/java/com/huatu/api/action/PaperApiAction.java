/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.action  
 * 文件名：				PaperApiAction.java    
 * 日期：				2015年6月11日-下午8:18:24  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.action;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.HttpConstraint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatu.api.service.PaperApiService;
import com.huatu.api.util.ApiUtil;
import com.huatu.api.vo.PaperVo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;
import com.huatu.core.util.AppTypePropertyUtil;
import com.huatu.core.util.CommonUtil;
import com.huatu.ou.ascpectJ.apiLog.ApiAnnotation;
import com.huatu.tb.common.enums.AreaEnum;

/**   
 * 类名称：				PaperApiAction  
 * 类描述：  			试卷API - REST接口
 * 创建人：				LiXin
 * 创建时间：			2015年6月11日 下午8:18:24  
 * @version 		1.0
 */
@RequestMapping("/httbapi/paper")
@Controller
@Scope("prototype")
public class PaperApiAction {
	public  Logger log = Logger.getLogger(this.getClass());
    @Autowired
	private PaperApiService paperApiService; 
	/**
	 * 获取试卷列表
	 * getSJ  
	 * @exception --通过相关信息查询试题列表
	 * @param attribute -- 试卷属性(0==>真题，1==>模拟题)
	 * @param tclassify -- 一级分类： （如公务员下的：国家公务员,地区公务员  ,三支一干 ...）
	 * @param keyword  -- 查询关键字
	 * @param area  -- 地区： 仅在公务员下 地区公务员为必填
	 * @param count -- 查询个数（默认20）
	 * @param pageindex -- 第几页  1 2 3 4 ....
	 * @param license
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getPapers", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiAnnotation(operateFuncName = "获取试卷列表")
	public Message getPapers(@RequestParam String attribute,
			@RequestParam String tclassify,
			@RequestParam(value="keyword", required = false) String keyword,
			@RequestParam(value="area", required = false) String area,
			@RequestParam(value="count", required = false) String count,
			@RequestParam String pageindex,
			@RequestParam String license) throws Exception{
		int pcount = CommonUtil.isNotEmpty(count) ? Integer.parseInt(count) : 20;//每页多少条
		
		//公务员特殊处理 地区
		if(AppTypePropertyUtil.APP_TYPE ==300){
			if(AppTypePropertyUtil.NATIONWIDE.equals(tclassify)){
				area = AreaEnum.CHINA.getCode();
			}
        }
		
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			if(CommonUtil.isNotNull(attribute) && CommonUtil.isNotNull(tclassify)){
				Map<String,Object> filter = new HashMap<String, Object>();
				filter.put("attribute", attribute);	//属性 0==>真题，1==>模拟题
				filter.put("tclassify", tclassify);	//一级级分类
				
				if(CommonUtil.isNotNull(area)){
					filter.put("area", area);	//地区
				}
				
				if(CommonUtil.isNotNull(keyword)){
					filter.put("keyword", keyword);	//关键字
				}
				if(CommonUtil.isNotNull(pcount)){
					filter.put("count", pcount);	//条数
				}				
				if(CommonUtil.isNotNull(pageindex)){
					filter.put("pageindex", pageindex);	//第几页
				}
				
				try {
					List<PaperVo> list = paperApiService.getPaperList(filter);
					message.setSuccess(true);
					message.setMessage("获取试卷列表成功");
					message.setData(list);
					log.info("获取试卷列表成功");
				} catch (HttbException e) {
					message.setSuccess(false);
					message.setMessage("获取试卷列表失败");
					throw new HttbException(this.getClass() + "获取试卷列表失败", e);
				}
			}else{
				message.setSuccess(false);
				message.setMessage("请求参数不合法,获取试卷列表失败");
				log.error("请求参数不合法,获取试卷列表失败");
			}
		}else{
			message.setSuccess(false);
			message.setMessage("许可证错误,认证失败");
		}
		return message;
	}
	/**
	 * 根据试卷ID获取试题 【返回数据压缩加密】
	 * @exception 根据试卷ID获取试卷下所有试题 
	 * @param id --试卷ID
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/getQuestionsById", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiAnnotation(operateFuncName = "根据试卷ID获取试题 【返回数据压缩加密】")
	public void getQuestionsById(@HttpConstraint HttpServletRequest request, @HttpConstraint HttpServletResponse response,
									@RequestParam  String id,
									@RequestParam(value = "license", required = false) String license) throws Exception{
		if(ApiUtil.validateLicense(license)){
			if(CommonUtil.isNotNull(id)){
				try {
					//设置response的编码方式
			    	 response.setContentType("text/plain; charset=utf-8");
			         response.setCharacterEncoding("UTF-8");
			         try {
			             byte[] buffer = paperApiService.getQuestionsByPaperId(id).getBytes();
			             // 清空response
			             response.reset();
			             OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				         //设置附加文件名(解决中文乱码)
				         response.setHeader("Content-Disposition","attachment;filename=" + new String("paper.txt".getBytes("gbk"), "iso-8859-1"));
				         //设置长度
				         response.setHeader("content-length",buffer.length+"");
			            
			             toClient.write(buffer);
			             toClient.flush();
			             toClient.close();
			         } catch (IOException ex) {
			             ex.printStackTrace();
			         }
					log.info("根据试卷ID获取试题压缩包成功");
				} catch (HttbException e) {
					throw new HttbException(this.getClass() + "根据试卷ID获取试题压缩包失败", e);
				}
			}else{
				log.error("请求参数不合法,获取试题压缩包失败");
			}
		}
	}
}
