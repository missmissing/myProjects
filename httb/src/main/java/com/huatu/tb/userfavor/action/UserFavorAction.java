/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.userfavor.action  
 * 文件名：				UserFavorAction.java    
 * 日期：				2015年5月13日-上午11:31:00  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.userfavor.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Page;
import com.huatu.tb.question.model.Question;
import com.huatu.tb.userfavor.model.UserFavor;
import com.huatu.tb.userfavor.service.UserFavorService;

/**   
 * 类名称：				UserFavorAction  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年5月13日 上午11:31:00  
 * @version 		1.0
 */
@Controller
@RequestMapping("userfavor")
public class UserFavorAction {
	@Autowired
	private UserFavorService userFavorService;
	/**
	 * 进入试题管理List页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		return "question/list";
	}
	/**
	 * Ajax 分页查询
	 * @param model
	 * @param border -- 边界值 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public Page<UserFavor> query(Model model,UserFavor userFavor) throws Exception {
		Map<String,Object> filter = new HashMap<String, Object>();
		
		Page<UserFavor> page = new Page<UserFavor>();
		try {
			page = userFavorService.findPage(filter);
		} catch (HttbException e) {
			throw new HttbException(this.getClass() + "userFavorService失败", e);
		}
		return page;
	}
	
}
