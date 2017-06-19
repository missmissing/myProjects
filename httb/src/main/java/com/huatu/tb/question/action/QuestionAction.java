/**
 *
 */
package com.huatu.tb.question.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
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
import com.huatu.api.version.model.TaskVersion;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.api.version.util.VersionUtil;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;
import com.huatu.core.model.Page;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.core.util.SpringContextHolder;
import com.huatu.ou.user.model.User;
import com.huatu.sys.session.HtSession;
import com.huatu.sys.session.HtSessionManager;
import com.huatu.tb.category.model.CateQues;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.category.service.CategoryService;
import com.huatu.tb.common.enums.AreaEnum;
import com.huatu.tb.common.enums.AttributeEnum;
import com.huatu.tb.common.enums.DifficultyEnum;
import com.huatu.tb.common.enums.QuestionTypesEnum;
import com.huatu.tb.common.enums.StatusEnum;
import com.huatu.tb.common.util.TbCommonUtil;
import com.huatu.tb.quesInput.util.QuesAttrMapping;
import com.huatu.tb.question.model.ChildQuestion;
import com.huatu.tb.question.model.Question;
import com.huatu.tb.question.service.QuestionService;
import com.huatu.tb.question.util.QuestionUtil;

/**
 * @ClassName: 		QuestionAction
 * @Description: 	试题Action
 * @author 			LiXin
 * @date 			2015年4月20日 上午10:28:14
 * @version 		1.0
 *
 */
@Controller
@RequestMapping("question")
public class QuestionAction {
	
	public  Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TaskVersionService taskVersionService;
	
	@InitBinder("question")
	public void initBinder(WebDataBinder binder){
		binder.setFieldDefaultPrefix("question.");
	}
	/**
	 * 进入试题管理List页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("attributeJsonMap",new Gson().toJson(AttributeEnum.getAllMap()));//属性
		model.addAttribute("quetypesJsonMap",new Gson().toJson(QuestionTypesEnum.getAllMap()) );//题型
		model.addAttribute("statusJsonMap",new Gson().toJson(StatusEnum.getAllMap()));//状态
		model.addAttribute("yearList",TbCommonUtil.getYearList());//年份
		model.addAttribute("areaMap",AreaEnum.getAllMap()); //地区
		model.addAttribute("attributeMap",AttributeEnum.getAllMap());//属性
		model.addAttribute("quetypesMap",QuestionTypesEnum.getAllMap() );//题型
		model.addAttribute("statusMap",StatusEnum.getAllMap());//状态

		return "question/list";
	}
	/**
	 * 进入试题管理List页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/examineList", method = RequestMethod.GET)
	public String examineList(Model model) {
		model.addAttribute("attributeJsonMap",new Gson().toJson(AttributeEnum.getAllMap()));//属性
		model.addAttribute("quetypesJsonMap",new Gson().toJson(QuestionTypesEnum.getAllMap()) );//题型
		model.addAttribute("statusJsonMap",new Gson().toJson(StatusEnum.getAllMap()));//状态

		model.addAttribute("yearList",TbCommonUtil.getYearList());//年份
		model.addAttribute("areaMap",AreaEnum.getAllMap()); //地区
		model.addAttribute("attributeMap",AttributeEnum.getAllMap());//属性
		model.addAttribute("quetypesMap",QuestionTypesEnum.getAllMap() );//题型
		model.addAttribute("statusMap",StatusEnum.getAllMap());//状态


		return "question/examineList";
	}
	/**
	 * Ajax 分页查询(现在这个方法只能查询出已发布的试题)
	 * @param model
	 * @param border -- 边界值
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public Page<Question> query(Model model,Question question,String knowledgepoint) throws Exception {
		Map<String,Object> filter = new HashMap<String, Object>();
		//知识点不为空
		if(knowledgepoint !=null){
			filter.put("point", knowledgepoint);
		}
		//年份不为空
		if(CommonUtil.isNotNull(question.getQyear())){
			filter.put("year", question.getQyear());
		}
		//地区不为空
		if(CommonUtil.isNotNull(question.getQarea())){
			filter.put("area", question.getQarea());
		}
		//题型不为空
		if(CommonUtil.isNotNull(question.getQtype())){
			filter.put("type", question.getQtype());
		}
		//属性不为空
		if(CommonUtil.isNotNull(question.getQattribute())){
			filter.put("attribute", question.getQattribute());
		}
		//状态不为空
		if(CommonUtil.isNotNull(question.getQstatus())){
			filter.put("status", question.getQstatus());
		}
		Page<Question> page = new Page<Question>();
		try {
			long t1 = System.currentTimeMillis();
			page = questionService.findPage(filter);
			List<Question> lists = (List<Question>) page.getRows();
			List<Question> temp = new ArrayList<Question>();
			for(Question q : lists){
				//除去哪个状态就代表自己能看见这个状态下的所有信息
				if(!q.getQstatus().equals("3")){
					temp.add(q);
				}
			}
			lists.removeAll(temp);
			page.setRows(lists);			
			long t2 = System.currentTimeMillis();
		} catch (HttbException e) {
			throw new HttbException(this.getClass() + "查询试题失败", e);
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
	public Page<Question> query4examine(HttpServletRequest request,HttpServletResponse response,Model model,Question question,String knowledgepoint) throws Exception {
		Map<String,Object> filter = new HashMap<String, Object>();
		//知识点不为空
		if(knowledgepoint !=null){
			filter.put("point", knowledgepoint);
		}
		//年份不为空
		if(CommonUtil.isNotNull(question.getQyear())){
			filter.put("year", question.getQyear());
		}
		//批次号不为空
		if(CommonUtil.isNotNull(question.getQbatchnum())){
			filter.put("qbatchnum", question.getQbatchnum());
		}
		//地区不为空
		if(CommonUtil.isNotNull(question.getQarea())){
			filter.put("area", question.getQarea());
		}
		//题型不为空
		if(CommonUtil.isNotNull(question.getQtype())){
			filter.put("type", question.getQtype());
		}
		//属性不为空
		if(CommonUtil.isNotNull(question.getQattribute())){
			filter.put("attribute", question.getQattribute());
		}
		//状态不为空
		if(CommonUtil.isNotNull(question.getQstatus())){
			filter.put("status", question.getQstatus());
		}
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
		Page<Question> page = new Page<Question>();
		try {
			long t1 = System.currentTimeMillis();
			page = questionService.findPage(filter);
			List<Question> lists = (List<Question>) page.getRows();
			List<Question> temp = new ArrayList<Question>();
			for(Question q : lists){
				//除去哪个状态就代表自己能看见这个状态下的所有信息
				if((!q.getQstatus().equals("5"))&&(!q.getQstatus().equals("1"))&&(!q.getQstatus().equals("4"))&&(!q.getQstatus().equals("3"))&&(!q.getCreateuser().equals(userid))){
					temp.add(q);
				}
			}
			lists.removeAll(temp);
			page.setRows(lists);			
			long t2 = System.currentTimeMillis();
		} catch (HttbException e) {
			throw new HttbException(this.getClass() + "查询试题失败", e);
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
	@RequestMapping(value = "/query4teacher", method = RequestMethod.POST)
	@ResponseBody
	public Page<Question> query4teacher(HttpServletRequest request,HttpServletResponse response,Model model,Question question,String knowledgepoint) throws Exception {
		Map<String,Object> filter = new HashMap<String, Object>();
		//知识点不为空
		if(knowledgepoint !=null){
			filter.put("point", knowledgepoint);
		}
		//年份不为空
		if(CommonUtil.isNotNull(question.getQyear())){
			filter.put("year", question.getQyear());
		}
		//批次号不为空
		if(CommonUtil.isNotNull(question.getQbatchnum())){
			filter.put("qbatchnum", question.getQbatchnum());
		}
		//地区不为空
		if(CommonUtil.isNotNull(question.getQarea())){
			filter.put("area", question.getQarea());
		}
		//题型不为空
		if(CommonUtil.isNotNull(question.getQtype())){
			filter.put("type", question.getQtype());
		}
		//属性不为空
		if(CommonUtil.isNotNull(question.getQattribute())){
			filter.put("attribute", question.getQattribute());
		}
		//状态不为空
		if(CommonUtil.isNotNull(question.getQstatus())){
			filter.put("status", question.getQstatus());
		}
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
		Page<Question> page = new Page<Question>();
		try {
			long t1 = System.currentTimeMillis();
			page = questionService.findPage(filter);
			List<Question> lists = (List<Question>) page.getRows();
			List<Question> temp = new ArrayList<Question>();
			for(Question q : lists){
				if((!q.getQstatus().equals("3"))&&(!q.getCreateuser().equals(userid))){
					temp.add(q);
				}
			}
			lists.removeAll(temp);
			page.setRows(lists);
			long t2 = System.currentTimeMillis();
		} catch (HttbException e) {
			throw new HttbException(this.getClass() + "查询试题失败", e);
		}
		return page;
	}
	/**
	 * 进入试题管理添加选择页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		init(model);
		return "question/add";
	}
	/**
	 * 进入试题管理添加标准正常选择题页面页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/addNormal", method = RequestMethod.GET)
	public String addNormal(Model model) {
		init(model);
		return "question/addNormal";
	}
	/**
	 * 进入试题管理添加共用题干类型页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/addShareContent", method = RequestMethod.GET)
	public String addShareContent(Model model) {
		init(model);
		return "question/addShareContent";
	}
	/**
	 *
	 * 进入试题管理添加共用题干答案页面
	 * @return
	 */
	@RequestMapping(value = "/addShareOption", method = RequestMethod.GET)
	public String addShareOption(Model model) {
		init(model);
		return "question/addShareOption";
	}
	/**
	 *
	 * 进入试题管理添加试题子集(共用题干)
	 * @return
	 */
	@RequestMapping(value = "/addchildquescontent", method = RequestMethod.GET)
	public String addchildquescontent(Model model) {
		init(model);
		return "question/contentchild/addchildquestion";
	}
	/**
	 *
	 * 进入试题管理添加试题子集(共用题干)
	 * @return
	 */
	@RequestMapping(value = "/addchildquesoption", method = RequestMethod.GET)
	public String addchildquesoption(Model model) {
		init(model);
		return "question/optionchild/addchildquestion";
	}

	/**
	 * 创建试题
	 * @param request
	 * @param question
	 * @return
	 * @throws HttbException
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Message save(HttpServletRequest request,HttpServletResponse response,String jsonStr)throws HttbException{
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
			Question question = new Gson().fromJson(jsonStr, new TypeToken<Question>() {}.getType());
			question.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
			question.setCreatetime(new Date());
			question.setCreateuser(userid);
			question.setUpdatetime(new Date());
			question.setUpdateuser(userid);
			question.setQstatus("0");
			questionService.save(question);
			message.setSuccess(true);
			message.setMessage("试题保存成功！");
		
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试题保存失败！");
			throw new HttbException(this.getClass() + "试题保存失败", e);
		}
		return message;
	}
	
	/**
	 * 提交审核试题
	 * @param request
	 * @param question
	 * @return
	 * @throws HttbException
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/examine", method = RequestMethod.POST)
	@ResponseBody
	public Message examine(HttpServletRequest request,HttpServletResponse response,String jsonStr)throws HttbException{
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
			Question question = new Gson().fromJson(jsonStr, new TypeToken<Question>() {}.getType());
			question.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
			question.setCreatetime(new Date());
			question.setCreateuser(userid);
			question.setUpdatetime(new Date());
			question.setUpdateuser(userid);
            question.setQstatus("1");
			questionService.save(question);
			message.setSuccess(true);
			message.setMessage("试题提交成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试题提交失败！");
			throw new HttbException(this.getClass() + "试题提交失败", e);
		}
		return message;
	}
	
	/**
	 * 审核通过
	 * @param request
	 * @param question
	 * @return
	 * @throws HttbException
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/passExamine", method = RequestMethod.POST)
	@ResponseBody
	public Message passExamine(HttpServletRequest request,HttpServletResponse response,String jsonStr)throws HttbException{
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
			Question question = new Gson().fromJson(jsonStr, new TypeToken<Question>() {}.getType());
			question.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
			question.setCreatetime(new Date());
		//	question.setCreateuser(userid);
			question.setUpdatetime(new Date());
			question.setUpdateuser(userid);
            question.setQstatus("3");
			questionService.save(question);
			List<String> pointList =  question.getQpoint();
			Set<String> pointSet = new HashSet<String>();
			for(String pointId : pointList){
				List<String> pointParentList = categoryService.getAllPid(pointId);
				pointSet.addAll(pointParentList);
				pointSet.add(pointId);
			}
			pointList.clear();
			pointList.addAll(pointSet);
			List<CateQues> cateQuesList = new ArrayList<CateQues>();
			for(String pointId : pointList){
				CateQues cateQues = new CateQues();
				cateQues.setCid(pointId);
				cateQues.setQid(question.getQid());
				cateQues.setAttr(question.getQarea());
				List<String> list = null;
				for(ChildQuestion c : question.getQchild()){
					if(CommonUtil.isNotEmpty(c.getQcid())){
						if(list == null){
							list = new ArrayList<String>();
						}
						list.add(c.getQcid());
					}
				}
				cateQues.setQcids(list);
				cateQuesList.add(cateQues);
			}
			categoryService.saveBatchToCateQues(cateQuesList);
			message.setSuccess(true);
			message.setMessage("试题审核完成！");
			TaskVersion taskVersion = new TaskVersion(Constants.CATEGORY,VersionUtil.getVersionNumber(),"","",new Date());
			taskVersionService.addVersion(taskVersion);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试题审核失败！");
			throw new HttbException(this.getClass() + "试题审核失败", e);
		}
		return message;
	}
	
	/**
	 * 审核不通过
	 * @param request
	 * @param question
	 * @return
	 * @throws HttbException
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "/unpassExamine", method = RequestMethod.POST)
	@ResponseBody
	public Message unpassExamine(HttpServletRequest request,HttpServletResponse response,String jsonStr)throws HttbException{
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
			Question question = new Gson().fromJson(jsonStr, new TypeToken<Question>() {}.getType());
			question.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
			question.setCreatetime(new Date());
			//question.setCreateuser(userid);
			question.setUpdatetime(new Date());
			question.setUpdateuser(userid);
            question.setQstatus("4");
			questionService.save(question);
			message.setSuccess(true);
			message.setMessage("试题审核完成！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试题审核失败！");
			throw new HttbException(this.getClass() + "试题审核失败", e);
		}
		return message;
	}

	/**
	 * 试题预览
	 * @param request
	 * @param question
	 * @return
	 * @throws HttbException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model,String qid)throws HttbException, UnsupportedEncodingException{
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
				openPage = "question/viewNormal";
				if(CommonUtil.isNotNull(question.getQchild())){
					model.addAttribute("childquestion",question.getQchild().get(0));
				}
			}
			//2- 共用题干 编码2
			if(question.getQtype().equals(QuestionTypesEnum.gytg.getCode())){
				openPage = "question/viewShareContent";
			}
			//3- 共用选项 编码3
			if(question.getQtype().equals(QuestionTypesEnum.gybx.getCode())){
				if(CommonUtil.isNotNull(question.getQchild())){
					model.addAttribute("optionList",question.getQchild().get(0).getQcchoiceList());
				}
				openPage = "question/viewShareOption";
			}


		} catch (Exception e) {
			throw new HttbException(this.getClass() + "试题预览失败", e);
		}
		return openPage;
	}
	/**
	 * 编辑试题
	 * @param request
	 * @param question
	 * @return
	 * @throws HttbException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model,String qid)throws HttbException, UnsupportedEncodingException{
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
				openPage = "question/editNormal";
				if(CommonUtil.isNotNull(question.getQchild())){
					model.addAttribute("childquestion",question.getQchild().get(0));
				}
			}
			//2- 共用题干 编码2
			if(question.getQtype().equals(QuestionTypesEnum.gytg.getCode())){
				openPage = "question/editShareContent";
			}
			//3- 共用选项 编码3
			if(question.getQtype().equals(QuestionTypesEnum.gybx.getCode())){
				if(CommonUtil.isNotNull(question.getQchild())){
					model.addAttribute("optionList",question.getQchild().get(0).getQcchoiceList());
				}
				openPage = "question/editShareOption";
			}


		} catch (Exception e) {
			throw new HttbException(this.getClass() + "试题编辑失败", e);
		}
		return openPage;
	}
	
	/**
	 * 编辑试题
	 * @param request
	 * @param question
	 * @return
	 * @throws HttbException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/edit4examine", method = RequestMethod.GET)
	public String edit4examine(Model model,String qid)throws HttbException, UnsupportedEncodingException{
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
				openPage = "question/examineNormal";
				if(CommonUtil.isNotNull(question.getQchild())){
					model.addAttribute("childquestion",question.getQchild().get(0));
				}
			}
			//2- 共用题干 编码2
			if(question.getQtype().equals(QuestionTypesEnum.gytg.getCode())){
				openPage = "question/examineShareContent";
			}
			//3- 共用选项 编码3
			if(question.getQtype().equals(QuestionTypesEnum.gybx.getCode())){
				if(CommonUtil.isNotNull(question.getQchild())){
					model.addAttribute("optionList",question.getQchild().get(0).getQcchoiceList());
				}
				openPage = "question/examineShareOption";
			}
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "试题编辑失败", e);
		}
		return openPage;
	}
	/**
	 * 修改试题
	 * @param request
	 * @param question
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Message update(HttpServletRequest request,HttpServletResponse response, String jsonStr)throws HttbException{
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
			Question question = new Gson().fromJson(jsonStr, new TypeToken<Question>() {}.getType());
			question.setUpdatetime(new Date());
			question.setUpdateuser(userid);
			question.setQstatus("0");
			questionService.update(question);
			message.setSuccess(true);
			message.setMessage("试题修改成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("试题修改失败！");
			throw new HttbException(this.getClass() + "试题修改失败", e);
		}
		return message;
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
			questionService.delete(id);
			// 2 删除试题-知识点 关系
			Question question = new Question();
			question = questionService.get(id);//获取知识点
			Set<String> cidSet = new HashSet<String>();
			if(CommonUtil.isNotNull(question.getQpoint())){
				for(String point : question.getQpoint()){
					List<String> pointParentList = categoryService.getAllPid(point);//通过cid获取全部层级CId
					cidSet.addAll(pointParentList);
				}
				for(String cid : cidSet){
					categoryService.deleteCateQues(cid, id);//循环删除
				}
			}
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
			questionService.deleteToIds(Arrays.asList(idsArr));
			message.setSuccess(true);
			message.setMessage("批量删除试题成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("批量删除试题成功！");
			throw new HttbException(this.getClass() + "试题批量删除失败", e);
		}
		return message;
	}
	/**
	 * 下线试题
	 * @param request
	 * @param question
	 * @return
	 * @throws HttbException
	 */
	@RequestMapping(value = "/downQuestion", method = RequestMethod.POST)
	@ResponseBody
	public Message downQuestion(HttpServletRequest request,HttpServletResponse response, String id)throws HttbException{
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
			// 1 修改试卷状态
			Question question = new Question();
			question.setQid(id);
			question.setUpdatetime(new Date());
			question.setUpdateuser(userid);
			question.setQstatus("5");
			questionService.update(question);
			
			// 2 删除试题-知识点 关系
			question = questionService.get(id);//获取知识点
			Set<String> cidSet = new HashSet<String>();
			if(CommonUtil.isNotNull(question.getQpoint())){
				for(String point : question.getQpoint()){
					List<String> pointParentList = categoryService.getAllPid(point);//通过cid获取全部层级CId
					cidSet.addAll(pointParentList);
				}
				for(String cid : cidSet){
					categoryService.deleteCateQues(cid, id);//循环删除
				}
			}
			message.setSuccess(true);
			message.setMessage("下线试题成功！");
			TaskVersion taskVersion = new TaskVersion(Constants.CATEGORY,VersionUtil.getVersionNumber(),"","",new Date());
			taskVersionService.addVersion(taskVersion);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("下线试题失败！");
			throw new HttbException(this.getClass() + "试题下载失败", e);
		}
		return message;
	}
	/**
	 * 初始化
	 * @param model
	 * @throws HttbException
	 */
	private void init(Model model){
	    model.addAttribute("areaMap",AreaEnum.getAllMap());
	    model.addAttribute("difficultyMap",DifficultyEnum.getAllMap());
	    model.addAttribute("attributeMap",AttributeEnum.getAllMap());
	    model.addAttribute("yearList",TbCommonUtil.getYearList());
		model.addAttribute("questionCatetoryMap",QuesAttrMapping.getQuestionCategory());//分类

	}

}
