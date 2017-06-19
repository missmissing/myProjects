/**
 * 项目名：				httb
 * 包名：				com.huatu.api.action
 * 文件名：				AnalyzeApiAction.java
 * 日期：				2015年6月11日-下午8:25:04
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.api.action;

import java.util.ArrayList;
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
import com.huatu.analysis.model.Htexamanalysis;
import com.huatu.analysis.model.Htexamresult;
import com.huatu.analysis.model.Htquesanalysis;
import com.huatu.analysis.service.HtexamanalysisService;
import com.huatu.analysis.service.HtexamresultService;
import com.huatu.analysis.service.HtquesanalysisService;
import com.huatu.analysis.service.HtsxrankService;
import com.huatu.api.po.PaperPo;
import com.huatu.api.po.QuestionPo;
import com.huatu.api.service.AnalyzeApiService;
import com.huatu.api.util.ApiUtil;
import com.huatu.api.vo.PaperAttrVo;
import com.huatu.api.vo.QuesAttrVo;
import com.huatu.api.vo.SXRankVo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Message;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.jms.model.RankMessage;
import com.huatu.jms.service.AnalyzeJmsService;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.category.service.CategoryService;

/**
 * 类名称：				AnalyzeApiAction
 * 类描述：  			分析API - REST接口
 * 创建人：				LiXin
 * 创建时间：			2015年6月11日 下午8:25:04
 * @version 		1.0
 */
@RequestMapping("/httbapi/analyze")
@Controller
@Scope("prototype")
public class AnalyzeApiAction {

	public  Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private AnalyzeApiService analyzeApiService;
	@Autowired
	private HtquesanalysisService htquesanalysisService;
	@Autowired
	private HtexamanalysisService htexamanalysisService;
	@Autowired
	private HtexamresultService htexamresultService;
	@Autowired
	private AnalyzeJmsService analyzeJmsService;
	@Autowired
	private IRedisService iRedisService;
	@Autowired
	private HtsxrankService htsxrankService;
	@Autowired
	private CategoryService categoryService;


	/**
	 * 获取试题分析属性
	 * @exception  getQuesAttr
	 * @param  List 试题列表
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/getQuesAttr", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Message getQuesAttr(@RequestParam String list,
							   @RequestParam String license){
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			if(CommonUtil.isNotNull(list)){
				try {
					@SuppressWarnings("serial")
					List<String> pidlist = new Gson().fromJson(list, new TypeToken<List<String>>() {}.getType());
					List<QuesAttrVo> qattrlist = analyzeApiService.getQuesAttr(pidlist);
					message.setSuccess(true);
					message.setMessage("获取试题分析属性成功");
					message.setData(qattrlist);
				} catch (HttbException e) {
					message.setSuccess(false);
					message.setMessage("获取试题分析属性失败");
					e.printStackTrace();
					log.error("获取试题分析属性失败", e);
				}
			}else{
				message.setSuccess(false);
				message.setMessage("请求参数不合法,获取试题分析属性失败");
				log.error("请求参数不合法,获取试题分析属性失败");
			}

		}else{
			message.setSuccess(false);
			message.setMessage("许可证错误,认证失败");
			log.error("许可证错误,认证失败");
		}
		return message;
	}

	/**
	 *
	 * getSXRank						(获取知识点顺序排名)
	 * 									(获取顺序答题知识点排名是调用)
	 * @param 		uid					用户id
	 * @param 		cid					知识点id
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value="/getsxrank", method={RequestMethod.GET})
	@ResponseBody
	public Message getSXRank(@RequestParam String uid, @RequestParam String cid) throws HttbException{
		Message message = new Message();
		List<SXRankVo> rankvos = new ArrayList<SXRankVo>();
		if(CommonUtil.isNotEmpty(uid)){
			//如果知识点ID不为空
			if(CommonUtil.isNotEmpty(cid)){
				SXRankVo sr = new SXRankVo();
				try {
					//根节点ID
					String rootId = categoryService.getRootId(cid);
					//设置节点id
					sr.setCid(rootId);
					//该用户知识点排名
					int rank = htsxrankService.getSXRank(uid, rootId);
					//设置该节点排名
					sr.setRank(rank);
					rankvos.add(sr);
					message.setData(rankvos);
				} catch (Exception e) {
					message.setMessage("获取顺序排名失败");
					message.setSuccess(false);
					throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass()+"获取单个顺序排名时发生异常", e);
				}
			}else{
				try {
					//遍历所有根节点排名
					//根节点集合
					List<Category> rootlist = categoryService.getRootCates();
					if(CommonUtil.isNotNull(rootlist)){
						for (int i = 0; i < rootlist.size(); i++) {
							//根节点ID
							String rootId = rootlist.get(i).getCid();
							SXRankVo sr = new SXRankVo();
							sr.setCid(rootId);
							//该用户知识点排名
							int rank = htsxrankService.getSXRank(uid, rootId);
							//设置该节点排名
							sr.setRank(rank);
							rankvos.add(sr);
						}
						message.setData(rankvos);
					}
				} catch (Exception e) {
					message.setMessage("获取顺序排名失败");
					message.setSuccess(false);
					throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass()+"获取多个顺序排名时发生异常", e);
				}
			}
		}
		message.setMessage("获取顺序排名成功");
		message.setSuccess(true);
		return message;
	}

	/**
	 * 保存答题结果返回分析数据
	 * SetAnswerAndgetResult
	 * @exception
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/setAnswerAndgetResult", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Message SetAnswerAndgetResult(@RequestParam String testpaper,
										 @RequestParam String license){
		Message message = new Message();
		if(ApiUtil.validateLicense(license)){
			@SuppressWarnings("serial")
			PaperPo testpaperObj = new Gson().fromJson(testpaper, new TypeToken<PaperPo>() {}.getType());
			try{
				//0 随机联系  1 顺序练习  2 模拟题  3 真题
				int type= Integer.parseInt(testpaperObj.getType());
				if(type ==0 || type == 1){
					message = this.saveTest(testpaperObj);
				}else{/**真题 模拟题*/
					message = this.saveExam(testpaperObj);
				}

			}catch(Exception e){
				e.printStackTrace();
				message.setSuccess(false);
				message.setMessage("保存答题结果,获取分析数据失败");
			}

		}else{
			message.setSuccess(false);
			message.setMessage("许可证错误,认证失败");
			log.error("许可证错误,认证失败");
		}
		return message;
	}
	/**
	 * 保存联系
	 * saveExam
	 * @exception
	 * @param testpaperObj
	 * @return
	 */
	private Message saveTest(PaperPo testpaperObj){
		Message message = new Message();
		try{
			/*//1 保存专题
			//2保存 答题记录
			analyzeApiService.saveDati(testpaperObj);
			//3 保存错题
			analyzeApiService.saveMyErrorQues(testpaperObj);*/
			RankMessage rankMessage = new RankMessage();
			rankMessage.setObject(testpaperObj);
			//1：表示顺序答题
			rankMessage.setJmsType("1");
			//boolean isSend = analyzeJmsService.sendMessage(rankMessage);
			boolean isSend = iRedisService.rpush(Constants.JMS_RESULT_SAVE, rankMessage);

			//4 试题分析
			List<QuesAttrVo> list = new ArrayList<QuesAttrVo>();
			List<String> qlist = new ArrayList<String>();//所有的试题ID
			List<String> yjfx = new ArrayList<String>();//已经有的
			for(QuestionPo qp : testpaperObj.getQueslist()){
				qlist.add(qp.getQid());
			}
			List<Htquesanalysis> qaList = htquesanalysisService.getListByQids(qlist);
			for(Htquesanalysis qa : qaList){
				QuesAttrVo qav = new QuesAttrVo();
				qav.setQid(qa.getQid());
				yjfx.add(qa.getQid());//已经分析
				qav.setFallibility(qa.getQmostwrong());
				qav.setAnswercount(""+qa.getQansamount());
				if(CommonUtil.isNotNull(qa.getQcorrectamount())){
					try{
						int qcorrectamount =qa.getQcorrectamount();
						int count = qa.getQansamount();
						qav.setPrecision(ApiUtil.getpercent((float)qcorrectamount/count)+"%");//准确率
					}catch(Exception e){
						e.printStackTrace();
						qav.setPrecision("100%");//准确率
					}
				}else{
					qav.setPrecision("100%");//准确率
				}
				list.add(qav);
			}
			for(String qids : qlist){
				if(!yjfx.contains(qids)){
					for(QuestionPo qp : testpaperObj.getQueslist()){
						if(qp.getQid().equals(qids) && qp.getIserror().equals("1")){// 0- 错误  1 正确 -1 为空
							QuesAttrVo qav = new QuesAttrVo();
							qav.setQid(qp.getQid());
							qav.setFallibility("");
							qav.setAnswercount("1");
							qav.setPrecision("100%");//准确率
							list.add(qav);
						} else if(qp.getQid().equals(qids) && qp.getIserror().equals("0")){
							QuesAttrVo qav = new QuesAttrVo();
							qav.setQid(qp.getQid());
							qav.setFallibility(qp.getUseranswers().toString());
							qav.setAnswercount("1");
							qav.setPrecision("0%");//准确率
							list.add(qav);
						}else if(qp.getQid().equals(qids) && qp.getIserror().equals("-1")){
							QuesAttrVo qav = new QuesAttrVo();
							qav.setQid(qp.getQid());
							qav.setFallibility("");
							qav.setAnswercount("1");
							qav.setPrecision("0%");//准确率
							list.add(qav);
						}
					}
				}
			}

			message.setSuccess(true);
			message.setMessage("保存答题结果,获取分析数据成功");
			message.setData(list);
		}catch(HttbException e){
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("保存答题结果,获取分析数据失败");
		}
		return message;

	}
	/**
	 * 保存考试
	 * saveExam
	 * @exception
	 * @param testpaperObj
	 * @return
	 */
	private Message  saveExam(PaperPo testpaperObj){
		Message message = new Message();
		PaperAttrVo pav = new PaperAttrVo();
		try{
			/*//1 保存考试
			analyzeApiService.saveKaoshi(testpaperObj);
			//2保存 答题记录
			analyzeApiService.saveDati(testpaperObj);
			//3 保存错题
			analyzeApiService.saveMyErrorQues(testpaperObj);*/

			RankMessage rankMessage = new RankMessage();
			rankMessage.setObject(testpaperObj);
			rankMessage.setJmsType("1");
			//boolean isSend = analyzeJmsService.sendMessage(rankMessage);
			boolean isSend = iRedisService.rpush(Constants.JMS_RESULT_SAVE, rankMessage);


			List<String> yjfx = new ArrayList<String>();//已经有的
			//4 试卷分析
			Htexamanalysis  ea = htexamanalysisService.getHtexamanalysis(testpaperObj.getPid());
			if(CommonUtil.isNull(ea)){
				/**试卷平均用时*/
				pav.setPid(testpaperObj.getPid());/**试卷id*/
				List<QuestionPo> queslist = testpaperObj.getQueslist();
				int count = queslist.size();//答题总个数
				int correctcount = 0;//正确答案
				for(QuestionPo qp : queslist){
					if(qp.getIserror().equals("1")){//是否错误  0- 错误  1 正确 -1 为空
						correctcount++;
					}
				}

				pav.setAveragescore(ApiUtil.getpercent((float)correctcount/count));/**试卷平均分*/
				pav.setAveragetime(testpaperObj.getSecond());/**试卷时间*/
				pav.setHeadcount("1");/**试卷答题次数*/
			}else{
				/**试卷平均用时*/
				pav.setPid(testpaperObj.getPid());/**试卷id*/
				pav.setAveragescore(""+ea.getEaveragescore());/**试卷平均分*/
				pav.setAveragetime(""+ea.getEaveragetime());/**试卷时间*/
				pav.setHeadcount(""+ea.getEansamount());/**试卷答题次数*/
			}

			//5 试题分析
			List<QuesAttrVo> list = new ArrayList<QuesAttrVo>();
			List<String> qlist = new ArrayList<String>();
			for(QuestionPo qp : testpaperObj.getQueslist()){
				qlist.add(qp.getQid());
			}
			List<Htquesanalysis> qaList = htquesanalysisService.getListByQids(qlist);
			for(Htquesanalysis qa : qaList){
				QuesAttrVo qav = new QuesAttrVo();
				qav.setQid(qa.getQid());
				yjfx.add(qa.getQid());//已经分析
				qav.setFallibility(qa.getQmostwrong());
				qav.setAnswercount(""+qa.getQansamount());
				if(CommonUtil.isNotNull(qa.getQcorrectamount())){
					try{
						int qcorrectamount =qa.getQcorrectamount();
						int count = qa.getQansamount();
						qav.setPrecision(ApiUtil.getpercent((float)qcorrectamount/count)+"%");//准确率
					}catch(Exception e){
						e.printStackTrace();
						qav.setPrecision("100%");//准确率
					}
				}else{
					qav.setPrecision("100%");//准确率
				}
				list.add(qav);
			}
			for(String qids : qlist){
				if(!yjfx.contains(qids)){
					for(QuestionPo qp : testpaperObj.getQueslist()){
						if(qp.getQid().equals(qids) && qp.getIserror().equals("1")){// 0- 错误  1 正确 -1 为空
							QuesAttrVo qav = new QuesAttrVo();
							qav.setQid(qp.getQid());
							qav.setFallibility("");
							qav.setAnswercount("1");
							qav.setPrecision("100%");//准确率
							list.add(qav);
						} else if(qp.getQid().equals(qids) && qp.getIserror().equals("0")){
							QuesAttrVo qav = new QuesAttrVo();
							qav.setQid(qp.getQid());
							qav.setFallibility(qp.getUseranswers().toString());
							qav.setAnswercount("1");
							qav.setPrecision("0%");//准确率
							list.add(qav);
						}else if(qp.getQid().equals(qids) && qp.getIserror().equals("-1")){
							QuesAttrVo qav = new QuesAttrVo();
							qav.setQid(qp.getQid());
							qav.setFallibility("");
							qav.setAnswercount("1");
							qav.setPrecision("0%");//准确率
							list.add(qav);
						}
					}
				}
			}
			pav.setQavList(list);
			/********************begin app折线图 **************************************/
			List<String> uqs = new ArrayList<String>();//个人近十次记录
			List<String> pqs = new ArrayList<String>();//大家近十次记录
			Map<String,Object> filter = new HashMap<String, Object>();

			filter.put("reid", testpaperObj.getPid());//试卷Id
			List<Htexamresult> presult =  htexamresultService.getRecentExam(filter,10);
			for(Htexamresult her : presult){
				pqs.add(her.getRexamresult()+"");
			}

			filter.put("ruid", testpaperObj.getUserno());//用户id
			List<Htexamresult> uresult =  htexamresultService.getRecentExam(filter,10);
			for(Htexamresult her : uresult){
				uqs.add(her.getRexamresult()+"");
			}
			pav.setMeantendlist(pqs);//大家历史记录
			pav.setUtendlist(uqs);//我的最近记录
			/********************end app折线图 **************************************/

			message.setSuccess(true);
			message.setMessage("保存答题结果,获取分析数据成功");
			message.setData(pav);
		}catch(HttbException e){
			e.printStackTrace();
			message.setSuccess(false);
			message.setMessage("保存答题结果,获取分析数据失败");
		}
		return message;
	}


}
