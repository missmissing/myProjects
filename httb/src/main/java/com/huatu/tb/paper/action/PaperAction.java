/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.paper.action  
 * 文件名：				PaperAction.java    
 * 日期：				2015年5月12日-下午1:45:35  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.paper.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.huatu.api.task.util.TaskMarkKeyUtil;
import com.huatu.api.version.model.TaskVersion;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.api.version.util.VersionUtil;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;
import com.huatu.core.model.Page;
import com.huatu.core.util.AppTypePropertyUtil;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.core.util.SpringContextHolder;
import com.huatu.ou.organization.model.Organization;
import com.huatu.ou.organization.service.OrganizationService;
import com.huatu.ou.user.model.User;
import com.huatu.sys.session.HtSession;
import com.huatu.sys.session.HtSessionManager;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.category.service.CategoryService;
import com.huatu.tb.common.enums.AreaEnum;
import com.huatu.tb.common.enums.AttributeEnum;
import com.huatu.tb.common.enums.Category_GWY_Enum;
import com.huatu.tb.common.enums.Category_JR_Enum;
import com.huatu.tb.common.enums.Category_JS_Enum;
import com.huatu.tb.common.enums.Category_SYDW_Enum;
import com.huatu.tb.common.enums.Category_Y_Enum;
import com.huatu.tb.common.enums.QuestionTypesEnum;
import com.huatu.tb.common.enums.StatusEnum;
import com.huatu.tb.common.util.TbCommonUtil;
import com.huatu.tb.paper.model.Paper;
import com.huatu.tb.paper.service.PaperService;
import com.huatu.tb.question.model.Question;
import com.huatu.tb.question.service.QuestionService;
import com.huatu.tb.question.util.QuestionUtil;

/**   
 * 类名称：				PaperAction  
 * 类描述：  			试卷Action
 * 创建人：				LiXin
 * 创建时间：			2015年5月12日 下午1:45:35  
 * @version 		1.0
 */
@Controller
@RequestMapping("paper")
public class PaperAction {
	
	public  Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private PaperService paperService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private TaskVersionService taskVersionService;
	
	@InitBinder("paper")
	public void initBinder(WebDataBinder binder){
		binder.setFieldDefaultPrefix("paper.");
	}
	/**
	 *进入试卷List页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		
		model.addAttribute("attributeJsonMap",new Gson().toJson(AttributeEnum.getAllMap()));//属性
		model.addAttribute("quetypesJsonMap",new Gson().toJson(QuestionTypesEnum.getAllMap()) );//题型
		model.addAttribute("statusJsonMap",new Gson().toJson(StatusEnum.getAllMap()));//状态
		model.addAttribute("areaJsonMap",new Gson().toJson(AreaEnum.getAllMap()));//地区
		model.addAttribute("areaMap",AreaEnum.getAllMap());
		model.addAttribute("statusMap",StatusEnum.getAllMap());//状态
		model.addAttribute("attributeMap",AttributeEnum.getAllMap());//属性
	    switch (AppTypePropertyUtil.APP_TYPE) {
		case 500: {
			model.addAttribute("category_Y_Enum",Category_JS_Enum.getAllMap());
			break; 
		}
		case 400: {
			model.addAttribute("category_Y_Enum",Category_SYDW_Enum.getAllMap());
			break; 
		}
		case 300: {
			model.addAttribute("category_Y_Enum",Category_GWY_Enum.getAllMap());
			break; 
		}
		case 200: {
			model.addAttribute("category_Y_Enum",Category_JR_Enum.getAllMap());
			break; 
		}
		case 100: {
			model.addAttribute("category_Y_Enum",Category_Y_Enum.getAllMap());
			break; 
		}
		// case 300:con.set= areaenue.getaoomap();,keyset'
		default:break; 
		}
		return "paper/list";
	}
	
	/**
	 *进入试卷List页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/examineList", method = RequestMethod.GET)
	public String examineList(Model model) {
		
		model.addAttribute("attributeJsonMap",new Gson().toJson(AttributeEnum.getAllMap()));//属性
		model.addAttribute("quetypesJsonMap",new Gson().toJson(QuestionTypesEnum.getAllMap()) );//题型
		model.addAttribute("statusJsonMap",new Gson().toJson(StatusEnum.getAllMap()));//状态
		model.addAttribute("areaJsonMap",new Gson().toJson(AreaEnum.getAllMap()));//地区
		model.addAttribute("areaMap",AreaEnum.getAllMap());
		model.addAttribute("statusMap",StatusEnum.getAllMap());//状态
		model.addAttribute("attributeMap",AttributeEnum.getAllMap());//属性
	    switch (AppTypePropertyUtil.APP_TYPE) {
		case 500: {
			model.addAttribute("category_Y_Enum",Category_JS_Enum.getAllMap());
			break; 
		}
		case 400: {
			model.addAttribute("category_Y_Enum",Category_SYDW_Enum.getAllMap());
			break; 
		}
		case 300: {
			model.addAttribute("category_Y_Enum",Category_GWY_Enum.getAllMap());
			break; 
		}
		case 200: {
			model.addAttribute("category_Y_Enum",Category_JR_Enum.getAllMap());
			break; 
		}
		case 100: {
			model.addAttribute("category_Y_Enum",Category_Y_Enum.getAllMap());
			break; 
		}
		// case 300:con.set= areaenue.getaoomap();,keyset'
		default:break; 
		}
		return "paper/examineList";
	}
	/**
	 *	选择试题
	 * 
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public String select(Model model,String pid) throws HttbException {
		Paper paper = paperService.get(pid);
		model.addAttribute("paper",paper);//属性
		
		Map<String,String> queMap = new HashMap<String, String>();//存放 试题id-试题类型
		//通过试题ID获取试题详细详细
		List<Question> qlist = questionService.gets(paper.getPqids());
		for(Question ques : qlist){
			queMap.put(ques.getQid(),QuestionTypesEnum.getValueByKey(ques.getQtype()));
		}
		model.addAttribute("quetypeMap",queMap);//试题类型map
		
		model.addAttribute("yearList",TbCommonUtil.getYearList());//年份
		model.addAttribute("areaMap",AreaEnum.getAllMap()); //地区
		model.addAttribute("attributeMap",AttributeEnum.getAllMap());//属性
		model.addAttribute("quetypesMap",QuestionTypesEnum.getAllMap() );//题型
		model.addAttribute("statusMap",StatusEnum.getAllMap());//状态
		
		model.addAttribute("attributeJsonMap",new Gson().toJson(AttributeEnum.getAllMap()));//属性
		model.addAttribute("quetypesJsonMap",new Gson().toJson(QuestionTypesEnum.getAllMap()) );//题型
		model.addAttribute("statusJsonMap",new Gson().toJson(StatusEnum.getAllMap()));//状态
		
		return "paper/select";
	}
	
	/**
	 *	选择试题
	 * 
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/examineSelect", method = RequestMethod.GET)
	public String examineSelect(Model model,String pid) throws HttbException {
		Paper paper = paperService.get(pid);
		model.addAttribute("paper",paper);//属性
		
		Map<String,String> queMap = new HashMap<String, String>();//存放 试题id-试题类型
		//通过试题ID获取试题详细详细
		List<Question> qlist = questionService.gets(paper.getPqids());
		for(Question ques : qlist){
			queMap.put(ques.getQid(),QuestionTypesEnum.getValueByKey(ques.getQtype()));
		}
		model.addAttribute("quetypeMap",queMap);//试题类型map
		
		
		model.addAttribute("yearList",TbCommonUtil.getYearList());//年份
		model.addAttribute("areaMap",AreaEnum.getAllMap()); //地区
		model.addAttribute("attributeMap",AttributeEnum.getAllMap());//属性
		model.addAttribute("quetypesMap",QuestionTypesEnum.getAllMap() );//题型
		model.addAttribute("statusMap",StatusEnum.getAllMap());//状态
		
		model.addAttribute("attributeJsonMap",new Gson().toJson(AttributeEnum.getAllMap()));//属性
		model.addAttribute("quetypesJsonMap",new Gson().toJson(QuestionTypesEnum.getAllMap()) );//题型
		model.addAttribute("statusJsonMap",new Gson().toJson(StatusEnum.getAllMap()));//状态
		
		return "paper/examineSelect";
	}
	/**
	 * 删除试题
	 * @param request
	 * @param question
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Message delete(HttpServletRequest request, String id)throws HttbException{
		Message message = new Message();
		try {
			paperService.delete(id);
			message.setSuccess(true);
			message.setMessage("试题删除成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试题删除失败！");
			throw new HttbException(this.getClass() + "试题删除失败", e);	
		}
		return message;
	}
	/**
	 * 批量删除试题
	 * @param request
	 * @param ids
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public Message deleteBatch(HttpServletRequest request, String ids)throws HttbException{
		Message message = new Message();
		try {
			String[] idsArr = ids.split(",");
			paperService.deleteToIds(Arrays.asList(idsArr));
			message.setSuccess(true);
			message.setMessage("批量删除试题成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("批量删除试题失败！");
			throw new HttbException(this.getClass() + "批量删除试题失败", e);	
		}
		return message;
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
	public Page<Paper> query(Model model,Paper paper) throws Exception {
		Page<Paper> page = new Page<Paper>();
		Map<String,Object> filter = new HashMap<String, Object>();
		if(CommonUtil.isNotNull(paper.getPyear())){// 年份 
			filter.put("pyear", paper.getPyear());
		}
//		if(CommonUtil.isNotNull(paper.getParea())){// 地域 
//			filter.put("parea", paper.getParea());
//		}
		if(CommonUtil.isNotNull(paper.getPstatus())){// 试卷审核状态(0->编辑中；1->待审核；2->审核中；3->发布)
			filter.put("pstatus", paper.getPstatus());
		}
		if(CommonUtil.isNotNull(paper.getPattribute())){// 试卷 属性 0 真题 1 模拟题
			filter.put("pattribute", paper.getPattribute());
		}
		if(CommonUtil.isNotNull(paper.getPsubtitle())){// 试卷简标题
			filter.put("psubtitle", paper.getPsubtitle());
		}
		
		try {
			page.setRows(paperService.findList(filter));
		} catch (HttbException e) {
			throw new HttbException(this.getClass() + "分页查询试题异常", e);	
		}
		return page;
	}
	
	/**
	 * Ajax 分页查询
	 * @param model
	 * @param border -- 边界值 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/query4examine", method = RequestMethod.POST)
	@ResponseBody
	public Page<Paper> query4examine(HttpServletRequest request,HttpServletResponse response,Model model,Paper paper) throws Exception {
		Page<Paper> page = new Page<Paper>();
		Map<String,Object> filter = new HashMap<String, Object>();
		if(CommonUtil.isNotNull(paper.getPyear())){// 年份 
			filter.put("pyear", paper.getPyear());
		}
//		if(CommonUtil.isNotNull(paper.getParea())){// 地域 
//			filter.put("parea", paper.getParea());
//		}
		if(CommonUtil.isNotNull(paper.getPstatus())){// 试卷审核状态(0->编辑中；1->待审核；2->审核中；3->发布)
			filter.put("pstatus", paper.getPstatus());
		}
		if(CommonUtil.isNotNull(paper.getPattribute())){// 试卷 属性 0 真题 1 模拟题
			filter.put("pattribute", paper.getPattribute());
		}
		if(CommonUtil.isNotNull(paper.getPsubtitle())){// 试卷简标题
			filter.put("psubtitle", paper.getPsubtitle());
		}
		
		try {
			HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
			User user = null;
			HtSession htsession = null;
			try {
				htsession = htSessionManager.getSession(request,response);
			} catch (HttbException e1) {
				log.error("查询htsession异常！", e1);
			}
			if(htsession!=null){
		       user = (User)htsession.getAttribute(Constants.SESSION_CURRENT_USER);
			}
			String userid = user.getId();
			filter.put("userid", userid);
			page.setRows(paperService.findList(filter));
			List<Paper> lists = (List<Paper>) page.getRows();
			List<Paper> temp = new ArrayList<Paper>();
			for(Paper q : lists){
				//除去哪个状态就代表自己能看见这个状态下的所有信息
				if((!q.getPstatus().equals("1"))&&(!q.getPstatus().equals("4"))&&(!q.getPstatus().equals("3"))&&(!q.getPstatus().equals("5"))&&(!q.getCreateuser().equals(userid))){
					temp.add(q);
				}
			}
			if(CommonUtil.isNotNull(paper.getParea())){// 地域 
				for(Paper p : lists){
					List<String> paList = p.getPareas();
					if(!paList.contains(paper.getParea())){
						temp.add(p);
					}
				  
				}
			}	
			if(CommonUtil.isNotNull(paper.getPcategorys().get(0))){// 试卷分类
				for(Paper p : lists){
					List<String> paList = p.getPcategorys();
					if(!paList.contains(paper.getPcategorys().get(0))){
						temp.add(p);
					}
				  
				}
			}			
			lists.removeAll(temp);
			page.setRows(lists);			
		} catch (HttbException e) {
			throw new HttbException(this.getClass() + "分页查询试题异常", e);
		}
		return page;
	}
	
	/**
	 * Ajax 分页查询
	 * @param model
	 * @param border -- 边界值 
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/query4teacher", method = RequestMethod.POST)
	@ResponseBody
	public Page<Paper> query4teacher(HttpServletRequest request,HttpServletResponse response,Model model,Paper paper) throws HttbException {
		Page<Paper> page = new Page<Paper>();
		Map<String,Object> filter = new HashMap<String, Object>();
		if(CommonUtil.isNotNull(paper.getPyear())){// 年份 
			filter.put("pyear", paper.getPyear());
		}
//		if(CommonUtil.isNotNull(paper.getParea())){// 地域 
//			filter.put("parea", paper.getParea());
//		}
		if(CommonUtil.isNotNull(paper.getPstatus())){// 试卷审核状态(0->编辑中；1->待审核；2->审核中；3->发布)
			filter.put("pstatus", paper.getPstatus());
		}
		if(CommonUtil.isNotNull(paper.getPattribute())){// 试卷 属性 0 真题 1 模拟题
			filter.put("pattribute", paper.getPattribute());
		}
		if(CommonUtil.isNotNull(paper.getPsubtitle())){// 试卷简标题
			filter.put("psubtitle", paper.getPsubtitle());
		}
		
		try {
			HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
			User user = null;
			HtSession htsession = null;
			try {
				htsession = htSessionManager.getSession(request,response);
			} catch (HttbException e1) {
				log.error("查询htsession异常！", e1);
			}
			if(htsession!=null){
		       user = (User)htsession.getAttribute(Constants.SESSION_CURRENT_USER);
			}
			String userid = user.getId();
			filter.put("userid", userid);
			page.setRows(paperService.findList(filter));
			List<Paper> lists = (List<Paper>) page.getRows();
			List<Paper> temp = new ArrayList<Paper>();
			for(Paper q : lists){
				//除去哪个状态就代表自己能看见这个状态下的所有信息
				if((!q.getPstatus().equals("3"))&&(!q.getCreateuser().equals(userid))){
					temp.add(q);
				}
			}
			if(CommonUtil.isNotNull(paper.getParea())){// 地域 
				for(Paper p : lists){
					List<String> paList = p.getPareas();
					if(!paList.contains(paper.getParea())){
						temp.add(p);
					}
				  
				}
			}			
			if(CommonUtil.isNotNull(paper.getPcategorys().get(0))){// 试卷分类
				for(Paper p : lists){
					List<String> paList = p.getPcategorys();
					if(!paList.contains(paper.getPcategorys().get(0))){
						temp.add(p);
					}
				  
				}
			}			
			lists.removeAll(temp);
			page.setRows(lists);		
		} catch (HttbException e) {
			throw new HttbException(this.getClass() + "分页查询试题异常", e);
		}
		return page;
	}
	/**
	 * 进入试卷添加
	 * 
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		init(model);
		return "paper/add";
	}
	
	/**
	 * 试题预览
	 * @param request
	 * @param question
	 * @return
	 * @throws HttbException
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/viewques", method = RequestMethod.GET)
	public String viewques(Model model,String qid)throws HttbException, UnsupportedEncodingException{
		String openPage = "";//默认页面
		init(model);
		try {
			Question question = questionService.get(qid);
			model.addAttribute("question", question);
			
			/*知识点ID*/
			model.addAttribute("qpointids", QuestionUtil.ListToString(question.getQpoint()));
			
			/*知识点名称*/
			Map<String,Object> filter = new HashMap<String, Object>();
			filter.put("cids", question.getQpoint());
			List<Category> cateList = categoryService.findList(filter);
			List<String> pointNameList = new ArrayList<String>();
			for(Category cate : cateList){
				pointNameList.add(cate.getCname());
			}
			model.addAttribute("qpointnames", QuestionUtil.ListToString(pointNameList));
			
			
			//1- 单选 或 多选 编码0,1
			if(question.getQtype().equals(QuestionTypesEnum.danx.getCode()) || question.getQtype().equals(QuestionTypesEnum.duox.getCode())){
				openPage = "paper/view/viewNormal";
				if(CommonUtil.isNotNull(question.getQchild())){
					model.addAttribute("childquestion",question.getQchild().get(0));
				}
			}
			//2- 共用题干 编码2
			if(question.getQtype().equals(QuestionTypesEnum.gytg.getCode())){
				openPage = "paper/view/viewShareContent";
			}
			//3- 共用选项 编码3
			if(question.getQtype().equals(QuestionTypesEnum.gybx.getCode())){
				if(CommonUtil.isNotNull(question.getQchild())){
					model.addAttribute("optionList",question.getQchild().get(0).getQcchoiceList());
				}
				openPage = "paper/view/viewShareOption";
			}
			
			
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "试题预览", e);
		}
		return openPage;
	}
	
	/**
	 * 保存试卷
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Message save(HttpServletRequest request,HttpServletResponse response,Model model,Paper paper,String qfrom,String qdiscussion,String qskill,String qvideourl) throws HttbException {
		Message message = new Message();
		HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
		User user = null;
		HtSession htsession = null;
		try {
			htsession = htSessionManager.getSession(request,response);
		} catch (HttbException e1) {
			log.error("查询htsession异常！", e1);
		}
		if(htsession!=null){
	       user = (User)htsession.getAttribute(Constants.SESSION_CURRENT_USER);
		}
		String userid = user.getId();
		try {
			paper.setPstatus(StatusEnum.BJZ.getCode());//试卷状态 编辑中
			paper.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
			paper.setCreatetime(new Date());
			paper.setCreateuser(userid);
			paper.setUpdatetime(new Date());
			paper.setUpdateuser(userid);
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("qfrom",qfrom );
			map.put("qdiscussion", qdiscussion);
			map.put("qskill", qskill);
			map.put("qvideourl",qvideourl );
			paper.setPattrs(map);
			
			paperService.save(paper);
			message.setSuccess(true);
			message.setMessage("试卷保存成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试卷保存失败！");
			throw new HttbException(this.getClass() + "保存试卷失败", e);
		}
		return message;
	}
	/**
	 * 修改试卷
	 * @param model
	 * @param paper  --试题对象
	 * @param qids --试题id 用逗号分隔的字符串
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Message update(HttpServletRequest request,HttpServletResponse response,Model model,Paper paper,String qids,String qfrom,String qdiscussion,String qskill,String qvideourl) throws HttbException {
		Message message = new Message();
		HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
		User user = null;
		HtSession htsession = null;
		try {
			htsession = htSessionManager.getSession(request,response);
		} catch (HttbException e1) {
			log.error("查询htsession异常！", e1);
		}
		if(htsession!=null){
	       user = (User)htsession.getAttribute(Constants.SESSION_CURRENT_USER);
		}
		String userid = user.getId();
		try {
			if(CommonUtil.isNotNull(qids)){
				paper.setPqids(Arrays.asList(qids.split(",")));//试题集合
			}
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("qfrom",qfrom );
			map.put("qdiscussion", qdiscussion);
			map.put("qskill", qskill);
			map.put("qvideourl",qvideourl );
			paper.setPattrs(map);
			paper.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
			paper.setUpdatetime(new Date());
			paper.setUpdateuser(userid);
			paperService.update(paper);
			message.setSuccess(true);
			message.setMessage("试卷修改成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试卷修改失败！");
			throw new HttbException(this.getClass() + "修改试卷失败", e);
		}
		return message;
	}
	/**
	 * 修改试卷试题集合列表
	 * @param model
	 * @param paper  --试题对象
	 * @param qids --试题id 用逗号分隔的字符串
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/updateQuesList", method = RequestMethod.POST)
	@ResponseBody
	public Message updateQuesList(HttpServletRequest request,HttpServletResponse response,Model model,Paper paper,String qids) throws HttbException {
		Message message = new Message();
		HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
		User user = null;
		HtSession htsession = null;
		try {
			htsession = htSessionManager.getSession(request,response);
		} catch (HttbException e1) {
			log.error("查询htsession异常！", e1);
		}
		if(htsession!=null){
	       user = (User)htsession.getAttribute(Constants.SESSION_CURRENT_USER);
		}
		String userid = user.getId();
		try {
			if(CommonUtil.isNotNull(qids)){
				paper.setPqids(Arrays.asList(qids.split(",")));//试题集合
			}else{
				paper.setPqids(null);//试题集合
			}
			paper.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
			paper.setUpdatetime(new Date());
			paper.setUpdateuser(userid);
			paper.setPstatus(StatusEnum.BJZ.getCode());//试卷状态 发布
			paperService.updateQuesList(paper);
			message.setSuccess(true);
			message.setMessage("试卷修改成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试卷修改失败！");
			throw new HttbException(this.getClass() + "试卷修改失败", e);
		}
		return message;
	}
	
	/**
	 * 提交审核
	 * @param model
	 * @param paper  --试题对象
	 * @param qids --试题id 用逗号分隔的字符串
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/importpaper", method = RequestMethod.POST)
	@ResponseBody
	public Message importpaper(HttpServletRequest request, HttpServletResponse response,Model model,Paper paper,String qids) throws HttbException {
		Message message = new Message();
		HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
		User user = null;
		HtSession htsession = null;
		try {
			htsession = htSessionManager.getSession(request,response);
		} catch (HttbException e1) {
			log.error("查询htsession异常！", e1);
		}
		if(htsession!=null){
	       user = (User)htsession.getAttribute(Constants.SESSION_CURRENT_USER);
		}
		String userid = user.getId();
		try {
			if(CommonUtil.isNotNull(qids)){
				paper.setPqids(Arrays.asList(qids.split(",")));//试题集合
			}
			paper.setPstatus(StatusEnum.DSH.getCode());//试卷状态 待审核
			paper.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
			paper.setUpdatetime(new Date());
			paper.setUpdateuser(userid);
			paperService.updateQuesList(paper);
			message.setSuccess(true);
			message.setMessage("试卷提交成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试卷提交失败！");
			throw new HttbException(this.getClass() + "试卷提交失败", e);
		}
		return message;
	}
	
	/**
	 * 审核通过
	 * @param model
	 * @param paper  --试题对象
	 * @param qids --试题id 用逗号分隔的字符串
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/passexamine", method = RequestMethod.POST)
	@ResponseBody
	public Message passexamine(HttpServletRequest request,HttpServletResponse response,Model model,Paper paper,String qids) throws HttbException {
		Message message = new Message();
		HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
		User user = null;
		HtSession htsession = null;
		try {
			htsession = htSessionManager.getSession(request,response);
		} catch (HttbException e1) {
			log.error("查询htsession异常！", e1);
		}
		if(htsession!=null){
	       user = (User)htsession.getAttribute(Constants.SESSION_CURRENT_USER);
		}
		String userid = user.getId();
		try {
			if(CommonUtil.isNotNull(qids)){
				paper.setPqids(Arrays.asList(qids.split(",")));//试题集合
			}
			paper.setPstatus(StatusEnum.FB.getCode());//试卷状态 发布
			paper.setPauditopinion("fbok");
			paper.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
			paper.setUpdatetime(new Date());
			paper.setUpdateuser(userid);
			paperService.updateQuesList(paper);
			message.setSuccess(true);
			message.setMessage("试卷审核成功！");
			//添加版本号
			Paper paper2 = paperService.get(paper.getPid()); 
			TaskVersion taskVersion;
			if(paper2.getPattribute().equals("0")){
				taskVersion = new TaskVersion(paper.getPid(),VersionUtil.getVersionNumber(),"0","",new Date());
			}else{
				taskVersion = new TaskVersion(paper.getPid(),VersionUtil.getVersionNumber(),"1","",new Date());
			}						
			taskVersionService.addVersion(taskVersion);
			//end
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试卷审核失败！");
			throw new HttbException(this.getClass() + "试卷审核失败", e);
		}
		return message;
	}
	
	/**
	 * 退回
	 * @param model
	 * @param paper  --试题对象
	 * @param qids --试题id 用逗号分隔的字符串
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/unpassexamine", method = RequestMethod.POST)
	@ResponseBody
	public Message unpassexamine(HttpServletRequest request,HttpServletResponse response,Model model,Paper paper,String qids,String unpassexam) throws Exception {
		Message message = new Message();
		HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
		User user = null;
		HtSession htsession = null;
		try {
			htsession = htSessionManager.getSession(request,response);
		} catch (HttbException e1) {
			log.error("查询htsession异常！", e1);
		}
		if(htsession!=null){
	       user = (User)htsession.getAttribute(Constants.SESSION_CURRENT_USER);
		}
		String userid = user.getId();
		try {
			if(CommonUtil.isNotNull(qids)){
				paper.setPqids(Arrays.asList(qids.split(",")));//试题集合
			}
			paper.setPstatus(StatusEnum.TH.getCode());//试卷状态 退回
			paper.setPauditopinion(unpassexam);
			paper.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
			paper.setUpdatetime(new Date());
			paper.setUpdateuser(userid);
			paperService.updateQuesList(paper);
			message.setSuccess(true);
			message.setMessage("试卷退回成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试卷退回失败！");
			throw new HttbException(this.getClass() + "试卷退回失败", e);
		}
		return message;
	}
	/**
	 * 给试卷分配,整理 试题
	 * quesStr - 试题json字符串
	 * pid - 试卷ID
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/allotQuestion", method = RequestMethod.POST)
	@ResponseBody
	public Message allotQuestion(HttpServletRequest request,HttpServletResponse response,Model model,String quesStr,String pid) throws Exception {
		Message message = new Message();
		HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
		User user = null;
		HtSession htsession = null;
		try {
			htsession = htSessionManager.getSession(request,response);
		} catch (HttbException e1) {
			log.error("查询htsession异常！", e1);
		}
		if(htsession!=null){
	       user = (User)htsession.getAttribute(Constants.SESSION_CURRENT_USER);
		}
		String userid = user.getId();
		Paper paper = new Paper();
		try {
			List<String> questionList = new Gson().fromJson(quesStr, new TypeToken<List<String>>() {}.getType());
			paper.setPid(pid);
			paper.setPqids(questionList);
			paper.setUpdateuser(userid);
			paper.setUpdatetime(new Date());
			paperService.update(paper);
			message.setSuccess(true);
			message.setMessage("试卷修改成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试卷修改失败！");
			throw new HttbException(this.getClass() + "给试卷分配,整理 试题失败", e);
		}
		return message;
	}
	/**
	 * 进入试卷编辑
	 * pId - 试卷Id
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model,String pid) throws HttbException {
		Paper paper = paperService.get(pid);
		model.addAttribute("paper", paper);
		init(model);
		return "paper/edit";
	}
	/**
	 * 初始化
	 * @param model
	 * @throws HttbException 
	 */
	private void init(Model model){
		try {
			List<Organization> list = organizationService.queryOrganizations(null);
			model.addAttribute("orglist",list);
		} catch (HttbException e) {
			e.printStackTrace();
		}
	    model.addAttribute("areaMap",AreaEnum.getAllMap());
	    model.addAttribute("statusMap",StatusEnum.getAllMap());
	    model.addAttribute("attributeMap",AttributeEnum.getAllMap());
	    model.addAttribute("yearList",TbCommonUtil.getYearList());
	   // model.addAttribute("category_Y_Enum",Category_Y_Enum.getAllMap());
	    switch (AppTypePropertyUtil.APP_TYPE) {
		case 500: {
			model.addAttribute("category_Y_Enum",Category_JS_Enum.getAllMap());
			break; 
		}
		case 400: {
			model.addAttribute("category_Y_Enum",Category_SYDW_Enum.getAllMap());
			break; 
		}
		case 300: {
			model.addAttribute("category_Y_Enum",Category_GWY_Enum.getAllMap());
			break; 
		}
		case 200: {
			model.addAttribute("category_Y_Enum",Category_JR_Enum.getAllMap());
			break; 
		}
		case 100: {
			model.addAttribute("category_Y_Enum",Category_Y_Enum.getAllMap());
			break; 
		}
		// case 300:con.set= areaenue.getaoomap();,keyset'
		default:break; 
		}
	    
	    
	}
	
	
	/**
	 * 下线
	 * @param model
	 * @param paper  --试题对象
	 * @param qids --试题id 用逗号分隔的字符串
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/downPaper", method = RequestMethod.POST)
	@ResponseBody
	public Message downPaper(HttpServletRequest request,HttpServletResponse response,Model model,String id,String pattribute) throws Exception {
		Message message = new Message();
		HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
		User user = null;
		HtSession htsession = null;
		try {
			htsession = htSessionManager.getSession(request,response);
		} catch (HttbException e1) {
			log.error("查询htsession异常！", e1);
		}
		if(htsession!=null){
	       user = (User)htsession.getAttribute(Constants.SESSION_CURRENT_USER);
		}
		String userid = user.getId();
		try {
			Paper paper = new Paper();
			paper.setPid(id);
			paper.setPstatus(StatusEnum.XX.getCode());//试卷状态 发布
			paper.setPauditopinion("");
			paper.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
			paper.setUpdatetime(new Date());
			paper.setUpdateuser(userid);
			paperService.updateQuesList(paper);
			message.setSuccess(true);
			message.setMessage("试卷下线成功！");
			//删除版本号			
			taskVersionService.deleteVersion(paper.getPid());
			//end
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试卷下线失败！");
			throw new HttbException(this.getClass() + "试卷下线失败", e);
		}
		return message;
	}
	
}
