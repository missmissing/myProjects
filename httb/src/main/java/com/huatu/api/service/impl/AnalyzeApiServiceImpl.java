/**
 * 项目名：				httb
 * 包名：				com.huatu.api.service.impl
 * 文件名：				AnalyzeApiServiceImpl.java
 * 日期：				2015年6月13日-下午4:32:46
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.analysis.model.Answerrecord;
import com.huatu.analysis.model.Htanshis;
import com.huatu.analysis.model.Htexamresult;
import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.service.HtanshisService;
import com.huatu.analysis.service.HtexamresultService;
import com.huatu.analysis.service.HtqueshisService;
import com.huatu.analysis.util.AnaCommonUtil;
import com.huatu.api.po.PaperPo;
import com.huatu.api.po.QuestionPo;
import com.huatu.api.service.AnalyzeApiService;
import com.huatu.api.service.QuestionApiService;
import com.huatu.api.vo.QuesAttrVo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.util.CommonUtil;

/**
 * 类名称：				AnalyzeApiServiceImpl
 * 类描述：
 * 创建人：				LiXin
 * 创建时间：			2015年6月13日 下午4:32:46
 * @version 		1.0
 */
@Service
public class AnalyzeApiServiceImpl implements AnalyzeApiService {

	@Autowired
	private HtexamresultService examresultService;
	@Autowired
	private HtqueshisService queshisService;

	@Autowired
	private QuestionApiService questionApiService;
	@Autowired
	private HtanshisService htanshisService;
	@Override
	public List<QuesAttrVo> getQuesAttr(List<String> list) throws HttbException {
		return null;
	}
	@Override
	public void saveKaoshi(PaperPo testpaper) throws HttbException {
		int rightNum =0;
		Htexamresult hter = new Htexamresult();
		hter.setRid(CommonUtil.getUUID());
		hter.setRuid(testpaper.getUserno());/**用户ID*/
		hter.setReid(testpaper.getPid());/**关联的考试主键*/
		if("2".equals(testpaper.getType())){//类型  0 随机练习  1 顺序练习  2 模拟题  3 真题
			hter.setRexamtype("1");
		}else{
			hter.setRexamtype("0");
		}
		if(CommonUtil.isNotNull(testpaper.getSecond())){
			hter.setRexamconsume(Integer.parseInt(testpaper.getSecond()));
		}
		hter.setRstatus("1");
		hter.setQrecorddate(AnaCommonUtil.getRecordDate());
		/**用户提交的答案记录*/
		List<Answerrecord> ansList = new ArrayList<Answerrecord>();
		for(QuestionPo qp : testpaper.getQueslist()){
			Answerrecord answerrecord = new Answerrecord();
			answerrecord.setQid(qp.getQid());
			String qans ="";
			for(String qa : qp.getQusanswer()){
				qans+=qa;
			}
			answerrecord.setQans(qans);
			String uans ="";
			for(String qa : qp.getUseranswers()){
				uans+=qa;
			}
			answerrecord.setUans(uans);

			/**是否正确(0=>正确，1=>不正确)*/
			if("1".equals(qp.getIserror())){
				rightNum++;/**是否错误  0- 错误  1 正确 -1 为空*/
				answerrecord.setIsright("0");
			}else{
				answerrecord.setIsright("1");
			}
			ansList.add(answerrecord);
		}
		if(CommonUtil.isNotNull(testpaper.getQueslist())&&testpaper.getQueslist().size()>0){
			float source= (float)rightNum/testpaper.getQueslist().size();
			hter.setRexamresult((float)(Math.round(source*10000))/100);///**考试结果*/
		}
		examresultService.save(hter);
	}
	@Override
	public void saveDati(PaperPo testpaper) throws HttbException {
		int type = 0;// 0 随机练习  1 顺序练习  2 模拟题  3 真题  ---app
					 /**试题类型(0=>真题,1=>模拟,2=>顺序,3=>模块) --- nosql*/
		List<String> qhpoint = new ArrayList<String>();//知识点

		if("0".equals(testpaper.getType())){
			if(CommonUtil.isNotNull(testpaper.getPoint()) ){
				qhpoint.add(testpaper.getPoint());
			}
			type =3;
		}else if ("1".equals(testpaper.getType())){
			if(CommonUtil.isNotNull(testpaper.getPoint()) ){
				qhpoint.add(testpaper.getPoint());
			}
			type =2;
		}else if ("2".equals(testpaper.getType())){
			type =1;
	    }else{
	        type =0;
	    }
		/**用户提交的答案记录*/
		List<Htqueshis> queList = new ArrayList<Htqueshis>();
		for(QuestionPo qp : testpaper.getQueslist()){
			Htqueshis hqs = new Htqueshis();
			hqs.setQhid(CommonUtil.getUUID());
			/**用户ID*/
			hqs.setQhuid(qp.getUserno());
			/**试题ID*/
			hqs.setQhqid(qp.getQid());
			String qans ="";
			for(String qa : qp.getQusanswer()){
				qans+=qa;
			}
			hqs.setQhqans(qans);
			String uans ="";
			for(String qa : qp.getUseranswers()){
				uans+=qa;
			}
			hqs.setQhuans(uans);
			/**试题类型(0=>真题,1=>模拟,2=>顺序,3=>模块)*/
			hqs.setQhtype(type);
			/**知识点id*/
			hqs.setQhpoint(qhpoint);
			//时间戳
			hqs.setQrecorddate(AnaCommonUtil.getRecordDate());
			/**创建时间*/
			hqs.setCreatetime(new Date());

			//是否错误  0- 错误  1 正确 -1 为空
			if(! "-1".equals(qp.getIserror())){//为空不填写
				/**nosql  是否正确(0=>正确，1=>不正确)*/
				if("1".equals(qp.getIserror())){
					hqs.setQhisright(0);
				}else{
					hqs.setQhisright(1);
				}
				queList.add(hqs);
			}

		}
		queshisService.savebatch(queList);
	}
	@Override
	public void saveMyErrorQues(PaperPo testpaper) throws HttbException {
		Map<String, Object> filter = new HashMap<String, Object>();
		/**用户提交的答案记录*/
		List<Htqueshis> queList = new ArrayList<Htqueshis>();
		for(QuestionPo qp : testpaper.getQueslist()){
			//是否错误  0- 错误  1 正确 -1 为空
			if(!"-1".equals(qp.getIserror())){//为空不填写
				/**nosql  是否正确(0=>正确，1=>不正确)*/
				if("0".equals(qp.getIserror())){
					filter.put("qhuid",qp.getUserno());
					filter.put("qhqid",qp.getQid());
					filter.put("qhqans",qp.getQusanswer());
					filter.put("qhuans",qp.getUseranswers());
				}
			}
		}
		if(CommonUtil.isNotNull(filter)&& filter.size()>0){
			questionApiService.addWrongQuestion(filter);
		}
	}
	@Override
	public void saveUserHistory(PaperPo testpaper) throws HttbException {
		Htanshis htanshis = new Htanshis();
		/**用户ID*/
		htanshis.setAuid(testpaper.getUserno());
		htanshis.setQrecorddate(AnaCommonUtil.getRecordDate());
		htanshisService.save(htanshis);
	}


}
