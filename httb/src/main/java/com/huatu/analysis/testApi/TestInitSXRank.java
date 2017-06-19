/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.init
 * 文件名：				InitSXRank.java
 * 日期：				2015年6月23日-上午10:43:54
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.testApi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatu.analysis.init.InitSXRankAnalysis;
import com.huatu.analysis.intf.IAnalysisService;
import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.service.HtqueshisService;
import com.huatu.analysis.service.HtsxrankService;
import com.huatu.analysis.util.AnaCommonUtil;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;

/**
 * 类名称：				InitSXRank
 * 类描述：  				初始化顺序做题
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月23日 上午10:43:54
 * @version 			0.0.1
 */
@RequestMapping("/httbapi/analysis")
@Controller
public class TestInitSXRank {
	@Autowired
	private IRedisService iRedisService;

	@Autowired
	private HtsxrankService htsxrankService;

	@Autowired
	private TaskVersionService taskVersionService;

	@Autowired
	private HtqueshisService htqueshisService;

	@Autowired
	private IAnalysisService ianalysisService;

	@Autowired
	private InitSXRankAnalysis initSXRank;

	/**
	 *
	 * initSXRankAnalysis			(增量分析顺序排序表)
	 * 								(程序启动或定时扫描时调用)
	 * @return
	 * @throws 		HttbException
	 */
	@RequestMapping(value = "/rank", method = {RequestMethod.GET,RequestMethod.POST})
	public void initSXRankAnalysis() throws HttbException{
		initSXRank.initSXRankAnalysis();
	}


	@RequestMapping(value = "/getRank", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Message getRank(@RequestParam  String uid, @RequestParam  String pointid) throws HttbException{
		Message message = new Message();
		try {
			List<Htqueshis> list = new ArrayList<Htqueshis>();
			Htqueshis htqueshis = new Htqueshis();
			htqueshis.setQhid(CommonUtil.getUUID());
			htqueshis.setQhuid(uid);
			List<String> qp = new ArrayList<String>();
			qp.add(pointid);
			htqueshis.setQhpoint(qp);
			htqueshis.setQhqid("1");
			htqueshis.setQhisright(0);
			htqueshis.setQhtype(2);
			htqueshis.setQrecorddate(AnaCommonUtil.getRecordDate());
			list.add(htqueshis);

			Htqueshis htqueshis2 = new Htqueshis();
			htqueshis2.setQhid(CommonUtil.getUUID());
			htqueshis2.setQhuid(uid);
			List<String> qp2 = new ArrayList<String>();
			qp2.add(pointid);
			htqueshis2.setQhpoint(qp2);
			htqueshis2.setQhqid("1");
			htqueshis2.setQhisright(0);
			htqueshis2.setQhtype(2);
			htqueshis2.setQrecorddate(AnaCommonUtil.getRecordDate());
			list.add(htqueshis2);

			Htqueshis htqueshis3 = new Htqueshis();
			htqueshis3.setQhid(CommonUtil.getUUID());
			htqueshis3.setQhuid(uid);
			List<String> qp3 = new ArrayList<String>();
			qp3.add(pointid);
			htqueshis3.setQhpoint(qp3);
			htqueshis3.setQhqid("1");
			htqueshis3.setQhisright(0);
			htqueshis3.setQhtype(2);
			htqueshis3.setQrecorddate(AnaCommonUtil.getRecordDate());
			list.add(htqueshis3);

			Htqueshis htqueshis4 = new Htqueshis();
			htqueshis4.setQhid(CommonUtil.getUUID());
			htqueshis4.setQhuid(uid);
			List<String> qp4 = new ArrayList<String>();
			qp4.add(pointid);
			htqueshis4.setQhpoint(qp4);
			htqueshis4.setQhqid("1");
			htqueshis4.setQhisright(0);
			htqueshis4.setQhtype(2);
			htqueshis4.setQrecorddate(AnaCommonUtil.getRecordDate());
			list.add(htqueshis4);

			Htqueshis htqueshis5 = new Htqueshis();
			htqueshis5.setQhid(CommonUtil.getUUID());
			htqueshis5.setQhuid(uid);
			List<String> qp5 = new ArrayList<String>();
			qp5.add(pointid);
			htqueshis5.setQhpoint(qp5);
			htqueshis5.setQhqid("1");
			htqueshis5.setQhisright(0);
			htqueshis5.setQhtype(2);
			htqueshis5.setQrecorddate(AnaCommonUtil.getRecordDate());
			list.add(htqueshis5);

			int rank = ianalysisService.submitTest(list, null);
			message.setSuccess(true);
			message.setMessage("用户:"+uid+"的当前名次为："+rank);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("结果保存失败");
			e.printStackTrace();
		}
		return message;
	}

	@RequestMapping(value = "/getRankup", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Message getRankup(@RequestParam  String uid,@RequestParam  String pid) throws HttbException{
		Message message = new Message();
		try {
			int rank = ianalysisService.getRankup(uid,pid);
			message.setSuccess(true);
			message.setMessage("用户:"+uid+"的当前名次为："+rank);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("结果保存失败");
			e.printStackTrace();
		}
		return message;
	}
}
