/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.action  
 * 文件名：				QuestionApiAction.java    
 * 日期：				2015年6月11日-下午8:14:29  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.huatu.api.service.QuestionApiService;
import com.huatu.api.util.ApiUtil;
import com.huatu.api.vo.CategoryVo;
import com.huatu.api.vo.ErrorQuestionVo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;
import com.huatu.core.util.AppTypePropertyUtil;
import com.huatu.core.util.CommonUtil;
import com.huatu.ou.ascpectJ.apiLog.ApiAnnotation;


/**   
 * 类名称：				QuestionApiAction  
 * 类描述：  			试题API - rest接口
 * 创建人：				LiXin
 * 创建时间：			2015年6月11日 下午8:14:29  
 * @version 		1.0
 */
@RequestMapping("/httbapi/question")
@Controller
@Scope("prototype")
public class QuestionApiAction {
	public  Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	QuestionApiService questionApiService;
	
	/**
	 * 试题收藏
	 * collectQuestion
	 * @exception 
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/collectQuestion", method =RequestMethod.POST)
	@ResponseBody
	@ApiAnnotation(operateFuncName = "试题收藏")
	public Message collectQuestion(@RequestParam  String userno,
								   @RequestParam  String ids,
			                       @RequestParam String license) throws Exception{
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			try {
				@SuppressWarnings("serial")
				List<String> idlist = new Gson().fromJson(ids, new TypeToken<List<String>>() {}.getType());
				boolean collectSuccess = questionApiService.collectQuestion(userno, idlist);
				message.setSuccess(true);
				message.setMessage("试题收藏成功");
				message.setData(collectSuccess);
				log.info("用户"+userno+"试题收藏成功");
			} catch (HttbException e) {
				message.setSuccess(false);
				message.setMessage("试题收藏失败");
				log.error("用户"+userno+"试题收藏失败", e);
				throw new HttbException(this.getClass() + "用户"+userno+"试题收藏失败", e);
			}
		}else{
			message.setSuccess(false);
			message.setMessage("请求参数不合法,试题收藏失败");
			log.error("用户"+userno+"请求参数不合法,试题收藏失败");
		}
		return message;
	}
	
	/**
	 * 添加错题
	 * collectQuestion
	 * @exception 
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/addWrongQuestion", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiAnnotation(operateFuncName = "添加错题")
	public Message addWrongQuestion(@RequestParam  String qhuid,
									@RequestParam String qhqid,
									@RequestParam String qhqans,
									@RequestParam String qhuans,
									@RequestParam String qhtype,
									@RequestParam String qpoint,
									@RequestParam String license) throws Exception{
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			try {
				@SuppressWarnings("serial")
				List<String> qhqansList = new Gson().fromJson(qhqans, new TypeToken<List<String>>() {}.getType());
				@SuppressWarnings("serial")
				List<String> qhuansList = new Gson().fromJson(qhuans, new TypeToken<List<String>>() {}.getType());
				
				@SuppressWarnings("serial")
				List<String> qpointList = new Gson().fromJson(qpoint, new TypeToken<List<String>>() {}.getType());
				Map<String, Object> filter = new HashMap<String, Object>();
				filter.put("qhuid",qhuid);
				filter.put("qhqid",qhqid);
				filter.put("qhqans",qhqansList);
				filter.put("qhuans",qhuansList);
				filter.put("qhtype",qhtype);
				filter.put("qpoint",qpointList);
				boolean addSuccess = questionApiService.addWrongQuestion(filter);
				message.setSuccess(true);
				message.setMessage("添加错题成功");
				message.setData(addSuccess);
				log.info("添加错题成功");
			} catch (HttbException e) {
				message.setSuccess(false);
				message.setMessage("添加错题失败");
				log.error("添加错题失败", e);
				throw new HttbException(this.getClass() + "添加错题失败", e);
			}
		}else{
			message.setSuccess(false);
			message.setMessage("请求参数不合法,添加错题失败");
			log.error("请求参数不合法,添加错题失败");
		}
		return message;
	}
	
	/**
	 * 删除收藏
	 * collectQuestion
	 * @exception 
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/deleteCollectQuestion", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiAnnotation(operateFuncName = "删除收藏")
	public Message deleteCollectQuestion(@RequestParam  String userno,
										 @RequestParam String qids,
										 @RequestParam String license) throws Exception{
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			try {
				@SuppressWarnings("serial")
				List<String> qidsList = new Gson().fromJson(qids, new TypeToken<List<String>>() {}.getType());
				
				boolean collectSuccess = questionApiService.deleteCollectQuestion(userno, qidsList);
				message.setSuccess(true);
				message.setMessage("删除试题收藏成功");
				message.setData(collectSuccess);
				log.info("用户"+userno+"删除试题收藏成功");
			} catch (HttbException e) {
				message.setSuccess(false);
				message.setMessage("删除试题收藏失败");
				log.error("用户"+userno+"删除试题收藏失败", e);
				throw new HttbException(this.getClass() + "用户"+userno+"删除试题收藏失败", e);
			}
		}else{
			message.setSuccess(false);
			message.setMessage("请求参数不合法,删除试题收藏失败");
			log.error("用户"+userno+"请求参数不合法,删除试题收藏失败");
		}
		return message;
	}
	
	/**
	 * 删除错题
	 * collectQuestion
	 * @exception 
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/deleteWrongQuestion", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiAnnotation(operateFuncName = "删除错题")
	public Message deleteWrongQuestion(@RequestParam  String userno,
									   @RequestParam String ids,
									   @RequestParam String license) throws Exception{
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			try {
				String[] strs = ids.split(",");
				List<String> list  = Arrays.asList(strs);
				boolean collectSuccess = questionApiService.deleteWrongQuestion(userno, list);
				message.setSuccess(true);
				message.setMessage("删除错题成功");
				message.setData(collectSuccess);
				log.info("用户"+userno+"删除错题成功");
			} catch (HttbException e) {
				message.setSuccess(false);
				message.setMessage("删除错题失败");
				log.error("用户"+userno+"删除错题失败", e);
				throw new HttbException(this.getClass() + "用户"+userno+"删除错题失败", e);
			}
		}else{
			message.setSuccess(false);
			message.setMessage("请求参数不合法,删除错题失败");
			log.error("用户"+userno+"请求参数不合法,删除错题失败");
		}
		return message;
	}
	
	/**
	 * 获取用户错题集
	 * getWrongQuestionListByUserId
	 * @exception 
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/getWrongQuestionListByUserId", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiAnnotation(operateFuncName = "获取用户错题集")
	public Message getWrongQuestionListByUserId(
			 @RequestParam String userno,
			 @RequestParam String license) throws Exception{
		
		String id= "m"+AppTypePropertyUtil.APP_TYPE ; //id固定为0
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			if(CommonUtil.isNotNull(id) || CommonUtil.isNotNull(userno) ){  
				try {
					CategoryVo cv = questionApiService.getWrongQuestionCategory(id, userno, null);
					message.setSuccess(true);
					message.setMessage("获取错题信息成功");
					message.setData(cv);
					log.info("用户"+userno+"获取错题信息成功");
				} catch (HttbException e) {
					message.setSuccess(false);
					message.setMessage("获取错题信息失败");
					log.error("用户"+userno+"获取错题信息失败", e);
					throw new HttbException(this.getClass() + "用户"+userno+"获取错题信息失败", e);
				}
			}else{
				message.setSuccess(false);
				message.setMessage("请求参数不合法,获取章节信息失败");
				log.error("用户"+userno+"请求参数不合法,获取章节信息失败");
			}
		}else{
			message.setSuccess(false);
			message.setMessage("请求参数不合法,获取用户错题集失败");
			log.error("用户"+userno+"请求参数不合法,获取用户错题集失败");
		}
		return message;
	}
	
	/**
	 * 获取用户收藏集
	 * getCollectQuestionListByUserId
	 * @exception 
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/getCollectQuestionListByUserId", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiAnnotation(operateFuncName = "获取用户收藏集")
	public Message getCollectQuestionListByUserId(@RequestParam  String userno,
												  @RequestParam  String license) throws Exception{
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			try {
				List<String> list = questionApiService.getCollectQuestionListByUserId(userno);
				message.setSuccess(true);
				message.setMessage("获取用户收藏集成功");
				message.setData(list);
				log.info("用户"+userno+"获取用户收藏集成功");
			} catch (HttbException e) {
				message.setSuccess(false);
				message.setMessage("获取用户收藏集失败");
				log.error("用户"+userno+"获取用户收藏集失败", e);
				throw new HttbException(this.getClass() + "用户"+userno+"获取用户收藏集失败", e);
			}
		}else{
			message.setSuccess(false);
			message.setMessage("请求参数不合法,获取用户收藏集失败");
			log.error("用户"+userno+"请求参数不合法,获取用户收藏集失败");
		}
		return message;
	}
	/**
	 * 通过试题ids 获取错题集(详细)
	 * getErrorQuestions  
	 * @exception 
	 * @param qids
	 * @param userno
	 * @param license
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getErrorQuestions", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiAnnotation(operateFuncName = "通过试题ids 获取错题集(详细)")
	public Message getErrorQuestions(@RequestParam  String qids,
									 @RequestParam  String userno,
									 @RequestParam  String license) throws Exception{
		@SuppressWarnings("serial")
		List<String> qidlist = new Gson().fromJson(qids, new TypeToken<List<String>>() {}.getType());
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			try {
				List<ErrorQuestionVo> qv = questionApiService.getErrorQuestions(qidlist, userno);
				message.setSuccess(true);
				message.setMessage("通过试题ids获取错题列表成功");
				message.setData(qv);
				log.info("用户"+userno+"通过试题ids获取错题列表成功");
			} catch (Exception e) {
				message.setSuccess(false);
				message.setMessage("通过试题ids获取错题列表失败");
				log.error("用户"+userno+"通过试题ids获取错题列表失败", e);
				throw new HttbException(this.getClass() + "用户"+userno+"通过试题ids获取错题列表失败", e);
			}
		}else{
			message.setSuccess(false);
			message.setMessage("请求参数不合法,通过试题ids获取错题列表失败");
			log.error("用户"+userno+"请求参数不合法,通过试题ids获取错题列表失败");
		}
		return message;
		
	}
	
	
}
