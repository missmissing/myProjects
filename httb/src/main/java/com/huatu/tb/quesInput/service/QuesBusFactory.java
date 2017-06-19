package com.huatu.tb.quesInput.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huatu.ou.user.model.User;
import com.huatu.tb.common.enums.QuestionTypesEnum;
import com.huatu.tb.quesInput.model.QuesConstant;
import com.huatu.tb.quesInput.model.QuesError;
import com.huatu.tb.quesInput.util.QuesValidateError;
import com.huatu.tb.question.model.Question;

/**
 *
 * 类名称：				QuesBusFactory
 * 类描述：				试题业务工厂
 * 创建人：				Aijunbo
 * 创建时间：				2015年5月5日 上午9:24:26
 * @version 			0.0.1
 */
public  class QuesBusFactory {
	//需要插入题目集合
	public static List<Question> quesList = new ArrayList<Question>();

	//记录异常题目
	public static List<QuesError> quesErrors = new ArrayList<QuesError>();

	//记录缺标签试题
	public static List<String> quesRecords = new ArrayList<String>();

	//共用题干集合
	public static Map<String, String> quesMap = null;

	//子题干集合
	public static Map<String, String> itemMap = null;

	//试题对象
	public static Question question = new Question();

	public static String qtype = "" ;

	/**
	 *
	 * transGXTGmap					(共享题干转换)
	 *	 							(将遍历的每一行封装到实体属性中)
	 * @param 		str				读取一行内容
	 * @param		qbatchNum		批次号
	 * @param		qtype			题型
	 * @param		score			分值
	 * @param		user			系统当前登录人
	 */
	public void transQuesMap(String lineTxt, String qbatchNum, String qtype, int score, User user){
		if(question == null){
			question = new Question();
		}

		if(qtype.trim().equals(QuestionTypesEnum.gybx.getText())){
			//共用备选
			question = QuesGYBX.transQuesMap(lineTxt, qbatchNum, score, user);
		}else{
			//单选题、多选题、共用题干
			question = QuesGYTG.transQuesMap(lineTxt, qbatchNum, score, user);
		}

		//结束一道题时，检测之前的题是否已保存到试题集合
		if(lineTxt.startsWith("【作者】")){
			validateQuesEnd();
		}
	}

	/**
	 *
	 * checkQuesEnd					(结束一道题时，检测试题是否已保存)
	 * 								如果未保存，可能是上道题没有【作者】结尾标记，需要保存到异常集合中
	 */
	private void validateQuesEnd() {
		if(question != null ){
			//验证试题
			Map<String, Object> resultMap = QuesValidateError.quesValidate(question);
			//如果为true:表示没有异常
			if((boolean) resultMap.get(QuesConstant.KEY_RESULT)){
				quesList.add(question);
				question = null;
				qtype = null ;
			}else{
				QuesError error = new QuesError();
				error.setQuestion(question);
				error.setResultMap(resultMap);
				quesErrors.add(error);
			}
		}
	}


}
