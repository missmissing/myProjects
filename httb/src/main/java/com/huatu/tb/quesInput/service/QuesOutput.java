package com.huatu.tb.quesInput.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.huatu.core.util.CommonUtil;
import com.huatu.tb.common.enums.AttributeEnum;
import com.huatu.tb.common.enums.DifficultyEnum;
import com.huatu.tb.common.enums.QuestionTypesEnum;
import com.huatu.tb.quesInput.model.QuesConstant;
import com.huatu.tb.quesInput.model.QuesError;
import com.huatu.tb.question.model.ChildQuestion;
import com.huatu.tb.question.model.Question;
import com.huatu.tb.question.util.QuestionUtil;

/**
 *
 * 类名称：				QuesOutput
 * 类描述：				文件导出
 * 创建人：				Aijunbo
 * 创建时间：				2015年5月6日 上午9:05:26
 * @version 			0.0.1
 */
public class QuesOutput {

	/**
	 *
	 * outputRecordLine				(输出不符合试题模板记录)
	 * @param 		recordLines		试题记录信息
	 */
	public static String outputRecordLine(List<String> records){
		StringBuffer strRecord = new StringBuffer("");
		//遍历输出记录信息
		for (int i = 0; i < records.size(); i++) {
			strRecord.append(records.get(i));
			strRecord.append("\r\n");
		}
		strRecord.append("\r\n");
		return strRecord.toString();
	}

	/**
	 *
	 * outputQuesErrors				(输出异常题目信息)
	 * 								(有必填项为空时输出)
	 * @param 		errors			异常试题集合
	 */
	public static String outputQuesErrors(List<QuesError> errors){
		StringBuffer strError = new StringBuffer("");
		//遍历输出记录信息
		for (int i = 0; i < errors.size(); i++) {
			strError.append(writeQues(errors.get(i)));
			strError.append("\r\n");
		}
		return strError.toString();
	}

	/**
	 *
	 * writeQues					(带标签输出试题)
	 * @param 		error			异常试题
	 * @param 		out				输出流
	 */
	private static String writeQues(QuesError error){
		StringBuffer strError = new StringBuffer("");
		//异常试题
		Question question = error.getQuestion();
		//异常原因
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) error.getResultMap().get(QuesConstant.VAL_MASSAGE);
		//按格式输出试题
		strError.append("【题型】"+QuestionTypesEnum.getAllMap().get(question.getQtype())+"\r\n");
		strError.append("【难度】"+DifficultyEnum.getValueByKey(question.getQdifficulty())+"\r\n");
		strError.append("【题干】"+question.getQcontent()+"\r\n");
		List<ChildQuestion> childQuestions = question.getQchild();
		for(int i = 0; i< childQuestions.size(); i++){
			strError.append("【子题型】"+childQuestions.get(i).getQctype()+"\r\n");
			strError.append("【子题干】"+childQuestions.get(i).getQccontent()+"\r\n");
			//strError.append("【选项】"+childQuestions.get(i).getQcchoiceList()+"\r\n");
			//循环输出选项
			strError.append(transChoices(childQuestions.get(i).getQcchoiceList()));
			strError.append("【答案】"+childQuestions.get(i).getQcans()+"\r\n");
			strError.append("【解析】"+childQuestions.get(i).getQccomment()+"\r\n");
			strError.append("【拓展】"+childQuestions.get(i).getQcextension()+"\r\n");
		}
		strError.append("【考试类型】"+QuestionUtil.getQCategoryName(question.getQcategory())+"\r\n");
		strError.append("【知识点】"+StringUtils.collectionToDelimitedString(question.getQpoint(), "#")+"\r\n");
		strError.append("【年份】"+question.getQyear()+"\r\n");
		strError.append("【信息源】"+question.getQfrom()+"\r\n");
		strError.append("【属性】"+AttributeEnum.getAllMap().get(question.getQattribute())+"\r\n");
		strError.append("【作者】"+question.getQauthor()+"\r\n");
		strError.append("【导入异常原因】"+messages+"\r\n");
		return strError.toString();
	}

	/**
	 *
	 * transChoices					(转换选项)
	 * @param 		choices			选项集合
	 * @param 		out				输出流
	 * @throws IOException
	 */
	private static String transChoices(List<String> choices){
		StringBuffer childError = new StringBuffer("");
		//选项前字母
		List<String> prefixs = initChoices();
		if(CommonUtil.isNotNull(choices)){
			for (int i=0;i<choices.size();i++) {
				childError.append(prefixs.get(i)+choices.get(i)+"\r\n");
			}
		}
		return childError.toString();
	}

	/**
	 *
	 * initChoices					(初始化选项前字母)
	 * @return
	 */
	private static List<String> initChoices(){
		List<String> list = new ArrayList<String>();
		for (int i = 65; i < 91; i++) {
			list.add((char) i+".");
		}
		return list;
	}

}
