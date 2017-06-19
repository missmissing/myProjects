/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.util  
 * 文件名：				ModelToVo.java    
 * 日期：				2015年6月18日-上午11:28:12  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.huatu.api.vo.PaperVo;
import com.huatu.api.vo.QuestionVo;
import com.huatu.tb.common.enums.AreaEnum;
import com.huatu.tb.common.enums.AttributeEnum;
import com.huatu.tb.common.enums.QuestionTypesEnum;
import com.huatu.tb.paper.model.Paper;
import com.huatu.tb.question.model.ChildQuestion;
import com.huatu.tb.question.model.Question;

/**   
 * 类名称：				ModelToVo  
 * 类描述：  			持久对象转Vo
 * 创建人：				LiXin
 * 创建时间：			2015年6月18日 上午11:28:12  
 * @version 		1.0
 */
public class ModelToVoUtil {

	public static List<QuestionVo> questtionToVo(Question q){
		List<QuestionVo> qvArr = new ArrayList<QuestionVo>();
		/**共用题干*/
		if(QuestionTypesEnum.gytg.getCode().equals(q.getQtype())){
			for(int j =0 ; j<q.getQchild().size();j++){
				QuestionVo qv = new QuestionVo();
				ChildQuestion cq  = q.getQchild().get(j);
				qv.setQid(cq.getQcid());//子试题ID
				qv.setCode(""); //试题标识
				qv.setContent(q.getQcontent());//题干
				qv.setQuestion(cq.getQccontent());//问题
				qv.setQpoint(q.getQpoint());//知识点,考点---中文呈现
				qv.setYear(q.getQyear());//年份
				qv.setArea(AreaEnum.getValueByKey(q.getQarea()));//区域---中文呈现
				qv.setDifficulty(q.getQdifficulty());//难度---中文呈现
				qv.setAttribute(AttributeEnum.getValueByKey( q.getQattribute()));//属性( 真题 、 模拟题)---中文呈现
				qv.setParaphrase(q.getQparaphrase());//释义
				qv.setSource(q.getQfrom());//信息源
				qv.setSkill(q.getQskill());//答题技巧
				qv.setAuthor(q.getQauthor());//作者
				qv.setDiscussion(q.getQdiscussion());//讨论
				qv.setChoices(cq.getQcchoiceList());//选项结合
				//答案拆分成list
				char[] ansc = cq.getQcans().toCharArray();
				List<String> answersList = new ArrayList<String>();
				for(char ans : ansc){
					answersList.add(ans+"");
				}
				qv.setAnswers(answersList);//答案集合
				qv.setComment(cq.getQccomment()); //解析
				qv.setExtension(cq.getQcextension());//拓展
				qv.setScore(cq.getQcscore());//分值
				qvArr.add(qv);
			}
		/**共用备选*/
		}else if(QuestionTypesEnum.gybx.getCode().equals(q.getQtype())){
			for(int j =0 ; j<q.getQchild().size();j++){
				QuestionVo qv = new QuestionVo();
				ChildQuestion cq  = q.getQchild().get(j);
				qv.setQid(cq.getQcid());//试题ID
				qv.setCode(""); //试题标识
				qv.setContent("");//题干
				qv.setQuestion(cq.getQccontent());//问题
				qv.setQpoint(q.getQpoint());//知识点,考点---中文呈现
				qv.setYear(q.getQyear());//年份
				qv.setArea(AreaEnum.getValueByKey(q.getQarea()));//区域---中文呈现
				qv.setDifficulty(q.getQdifficulty());//难度---中文呈现
				qv.setAttribute(AttributeEnum.getValueByKey( q.getQattribute()));//属性( 真题 、 模拟题)---中文呈现
				qv.setParaphrase(q.getQparaphrase());//释义
				qv.setSource(q.getQfrom());//信息源
				qv.setSkill(q.getQskill());//答题技巧
				qv.setAuthor(q.getQauthor());//作者
				qv.setDiscussion(q.getQdiscussion());//讨论
				qv.setChoices(cq.getQcchoiceList());//选项结合
				//答案拆分成list
				char[] ansc = cq.getQcans().toCharArray();
				List<String> answersList = new ArrayList<String>();
				for(char ans : ansc){
					answersList.add(ans+"");
				}
				qv.setAnswers(answersList);//答案集合
				qv.setComment(cq.getQccomment()); //解析
				qv.setExtension(cq.getQcextension());//拓展
				qv.setScore(cq.getQcscore());//分值
				qvArr.add(qv);
			}
		}else{
			QuestionVo qv = new QuestionVo();
			qv.setQid(q.getQid());//试题ID
			qv.setCode(""); //试题标识
			qv.setContent("");//题干
			qv.setQuestion(q.getQcontent());//问题
			qv.setQpoint(q.getQpoint());//知识点,考点---中文呈现
			qv.setYear(q.getQyear());//年份
			qv.setArea(AreaEnum.getValueByKey(q.getQarea()));//区域---中文呈现
			qv.setDifficulty(q.getQdifficulty());//难度---中文呈现
			qv.setAttribute(AttributeEnum.getValueByKey( q.getQattribute()));//属性( 真题 、 模拟题)---中文呈现
			qv.setParaphrase(q.getQparaphrase());//释义
			qv.setSource(q.getQfrom());//信息源
			qv.setSkill(q.getQskill());//答题技巧
			qv.setAuthor(q.getQauthor());//作者
			qv.setDiscussion(q.getQdiscussion());//讨论
			
			ChildQuestion cq  = q.getQchild().get(0);
			qv.setChoices(cq.getQcchoiceList());//选项结合
			//答案拆分成list
			char[] ansc = cq.getQcans().toCharArray();
			List<String> answersList = new ArrayList<String>();
			for(char ans : ansc){
				answersList.add(ans+"");
			}
			qv.setAnswers(answersList);//答案集合
			qv.setComment(cq.getQccomment()); //解析
			qv.setExtension(cq.getQcextension());//拓展
			qv.setScore(cq.getQcscore());//分值
			qvArr.add(qv);
		}
		
		return qvArr;
	}
	/**
	 * 试卷转换
	 * paperToVo  
	 * @exception 
	 * @param paper
	 * @return
	 */
	public static PaperVo paperToVo(Paper paper){
		PaperVo pv = new PaperVo();
		pv.setPid(paper.getPid());
		pv.setName(paper.getPtitle());
		pv.setAttribute((String)AttributeEnum.getAllMap().get(paper.getPattribute()));
		return pv;
	}

}
