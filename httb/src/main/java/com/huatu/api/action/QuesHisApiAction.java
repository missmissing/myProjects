/**
 * 项目名：				httb
 * 包名：				com.huatu.api.action
 * 文件名：				PaperApiAction.java
 * 日期：				2015年6月11日-下午8:18:24
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.api.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatu.api.service.QuesHisService;
import com.huatu.api.vo.Queshis;
import com.huatu.core.model.Message;
import com.huatu.core.util.CommonUtil;

/**
 * 类名称：				PaperApiAction
 * 类描述：  				考试结果保存
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月13日 下午14:40:24
 * @version 			1.0
 */
@RequestMapping("/httbapi/queshis")
@Controller
@Scope("prototype")
public class QuesHisApiAction {
	public  Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private QuesHisService quesHisService;

	@RequestMapping(value = "/saveques", method = RequestMethod.GET)
	@ResponseBody
	public Message getSubsetById(){
		Message message = new Message();
			try {
				boolean flag = quesHisService.saveBatch(getlist());
				message.setSuccess(flag);
				message.setMessage("结果保存成功");
				log.info("结果保存成功");
			} catch (Exception e) {
				message.setSuccess(false);
				message.setMessage("结果保存失败");
				e.printStackTrace();
				log.error("结果保存失败", e);
			}
		return message;
	}

	private List<Queshis> getlist (){
		List<Queshis> list = new ArrayList<Queshis>();
		for (int i = 1; i <= 5; i++) {
			Queshis qh = new Queshis();
			qh.setQhid(CommonUtil.getUUID());
			qh.setQhuid(i+"");
			qh.setQhqid(i+"");
			qh.setQhuans("A");
			qh.setQhqans("A");
			qh.setQhisright("true");
			qh.setQhtype("mn");
			qh.setCreatetime(new Date());
			list.add(qh);
		}

		for (int i = 6; i <= 10; i++) {
			Queshis qh = new Queshis();
			qh.setQhid(CommonUtil.getUUID());
			qh.setQhuid(i%2+"");
			qh.setQhqid(i%2+"");
			qh.setQhuans("B");
			qh.setQhqans("C");
			qh.setQhisright("false");
			qh.setQhtype("zt");
			qh.setCreatetime(new Date());
			list.add(qh);
		}

		for (int i = 10; i <= 15; i++) {
			Queshis qh = new Queshis();
			qh.setQhid(CommonUtil.getUUID());
			qh.setQhuid(i%2+"");
			qh.setQhqid(i%2+"");
			qh.setQhuans("C");
			qh.setQhqans("C");
			qh.setQhisright("true");
			qh.setQhtype("mk");
			qh.setCreatetime(new Date());
			list.add(qh);
		}

		for (int i = 10; i <= 15; i++) {
			Queshis qh = new Queshis();
			qh.setQhid(CommonUtil.getUUID());
			qh.setQhuid(i%3+"");
			qh.setQhqid(i%3+"");
			qh.setQhuans("A");
			qh.setQhqans("D");
			qh.setQhisright("false");
			qh.setQhtype("sx");
			qh.setCreatetime(new Date());
			list.add(qh);
		}
		return list;
	}

}
