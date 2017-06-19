/**   
 * 项目名：				httb
 * 包名：				com.text  
 * 文件名：				GwyQuesReleaseTopaper.java    
 * 日期：				2015年8月5日-下午2:37:57  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Message;
import com.huatu.core.util.AppTypePropertyUtil;
import com.huatu.core.util.CommonUtil;
import com.huatu.tb.category.model.CateQues;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.category.service.CategoryService;
import com.huatu.tb.paper.model.Paper;
import com.huatu.tb.paper.service.PaperService;
import com.huatu.tb.question.model.ChildQuestion;
import com.huatu.tb.question.model.Question;
import com.huatu.tb.question.service.QuestionService;

/**   
 * 类名称：				GwyQuesReleaseTopaper  
 * 类描述：                                        将已经发布的试卷中试题状态都改为已发布
 * 创建人：				LiXin
 * 创建时间：			2015年8月5日 下午2:37:57  
 * @version 		1.0
 */
@RequestMapping("/httbapi/gwyquesreleasetopaper")
@Controller
@Scope("prototype")
public class GwyQuesReleaseTopaper {
	public  Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private PaperService paperService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private CategoryService categoryService;
	
	
	
	
	/**
	 * 修改状态[仅服务于公务员]
	 * @return
	 */
	@RequestMapping(value = "/editquesstate", method = RequestMethod.GET)
	public Message editquesstate(Model model) throws HttbException{
		Message message = new Message();
		if(300 == AppTypePropertyUtil.APP_TYPE){
			try{
				Map<String,Object> filter = new HashMap<String, Object>();
				filter.put("pstatus","3");// 试卷审核状态(0->编辑中；1->待审核；2->审核中；3->发布)
			    List<Paper> paperlist = paperService.findList(filter);
			    List<Category> categoryList = categoryService.findAll();
			    
			    List<Question> sqlist = new ArrayList<Question>();
			    List<CateQues> scqlist = new ArrayList<CateQues>();
			    for(Paper paper : paperlist){
			    	List<String> quesIds = paper.getPqids();
			    	List<Question> quesList = questionService.gets(quesIds);
			    	for(Question ques : quesList){
			    		//试题审核状态(0->编辑中；1->待审核；2->审核中；3->发布；4->退回；5->下线)
			    		if(CommonUtil.isNull(ques.getQstatus()) || !ques.getQstatus().equals("3")){
			    			Question question = new Question();
			    			question.setQid(ques.getQid());//ID 
			    			question.setQstatus("3");//已经发布
			    			sqlist.add(question);//添加列表
			    			
			    			List<String> pointListmian =  new ArrayList<String>();//知识点
			    			List<String> pointList =  ques.getQpoint();
			    			if(CommonUtil.isNotNull(pointList)){
			    				pointListmian.addAll(pointList);
			    			}
			    			
			    			Set<String> pointSet = new HashSet<String>();
			    			for(String pointId : pointList){
			    				List<String> pointParentList = categoryService.getAllPid(categoryList,pointId);
			    				pointSet.addAll(pointParentList);
			    			}
			    			pointListmian.addAll(pointSet);
			    			System.out.println("试卷"+paper.getPid()+"中的试题"+question.getQid()+"等待发布 其知识点为："+pointSet.toString());
			    			if(pointListmian.size()>0){
			    				List<CateQues> cateQuesList = new ArrayList<CateQues>();
				    			for(String pointId : pointListmian){
				    				CateQues cateQues = new CateQues();
				    				cateQues.setCid(pointId);
				    				cateQues.setQid(ques.getQid());
				    				cateQues.setAttr(ques.getQarea());
				    				List<String> list = null;
				    				for(ChildQuestion c : ques.getQchild()){
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
				    			scqlist.addAll(cateQuesList);
			    			}else{
			    				CateQues cateQues = new CateQues();
			    				cateQues.setCid("m300");
			    				cateQues.setQid(ques.getQid());
			    				cateQues.setAttr(ques.getQarea());
			    				scqlist.add(cateQues);
			    			}
			    			
			    			
			    			if(sqlist.size()>900){
			    				questionService.saveBatch(sqlist);
			    				sqlist.clear();
			    			}
			    			if(scqlist.size()>950){
			    				categoryService.saveBatchToCateQues(scqlist);
			    				scqlist.clear();
			    			}
			    		}
			    	}
			    }
				log.info("将已经发布的试卷中试题状态发布成已发布成功");
				message.setSuccess(true);
				message.setMessage("状态修改成功");
				System.out.println("============ok============");
			}catch(Exception e){
				e.printStackTrace();
				log.info("将已经发布的试卷中试题状态发布成已发布失败");
				message.setSuccess(false);
				message.setMessage("状态修改失败");
			}
		}else{
			log.info("当前不是公务员库，将已经发布的试卷中试题状态发布成已发布失败");
			message.setSuccess(false);
			message.setMessage("当前不是公务员库，状态修改失败");
		}
		return message;
	}
}
