package com.huatu.tb.quesInput.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huatu.core.util.CommonUtil;
import com.huatu.tb.common.enums.QuestionTypesEnum;
import com.huatu.tb.quesInput.model.QuesConstant;
import com.huatu.tb.question.model.ChildQuestion;
import com.huatu.tb.question.model.Question;

/**
 *
 * 类名称：				QuesValidateError
 * 类描述：				试题验证类
 * 创建人：				Aijunbo
 * 创建时间：				2015年5月6日 下午3:10:06
 * @version 			0.0.1
 */
public class QuesValidateError {

	public static Map<String, Object> quesValidate(Question question){
		//结果集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//结果消息结合
		List<String> massages = new ArrayList<String>();
		//验证结果
		boolean flag = true;


		//题型
		if(question.getQtype() == null){
			flag = false;
			massages.add("题型不能为空！");
		}

		//题干判断(共用备选题干可为空(公用题干))
		if((question.getQtype() == null || !question.getQtype().trim().equals(QuestionTypesEnum.gybx.getCode())) && !CommonUtil.isNotNull(question.getQcontent())){
			flag = false;
			massages.add("题干不能为空！");
		}

		//验证子题
		if(question.getQchild() != null && question.getQchild().size() > 0){
			//子题集合
			List<ChildQuestion> childs = question.getQchild();
			//遍历子题集合
			for (int i = 0; i < childs.size(); i++) {
				//子题
				ChildQuestion child = childs.get(i);
				//子题选项
				if(child.getQcchoiceList() == null || child.getQcchoiceList().size() < 1){
					flag = false;
					massages.add("选项不能为空");
				}
				//子题答案
				if(child.getQcans() == null || child.getQcans().trim().length() == 0){
					flag = false;
					massages.add("答案不能为空");
				}
				//试题类别
				String qtype = question.getQtype();
				//如果类型不为空才能判断子题干属性
				if(CommonUtil.isNotEmpty(qtype)){
					//子题干
					if((qtype.trim().equals(QuestionTypesEnum.gytg.getCode()) || qtype.trim().equals(QuestionTypesEnum.gybx.getCode())) && (child.getQccontent() == null || child.getQccontent().trim().length() == 0)){
						flag = false;
						massages.add("子题干不能为空");
					}
				}
			}
		}else{
			flag = false;
			massages.add("选项、答案不能为空");
		}

		//考试类型
		if(!CommonUtil.isNotNull(question.getQcategory())){
			flag = false;
			massages.add("考试类型不能为空！");
		}

		//知识点
		if(!CommonUtil.isNotNull(question.getQpoint())){
			flag = false;
			massages.add("知识点不能为空！");
		}

		//属性
		if(!CommonUtil.isNotNull(question.getQattribute())){
			flag = false;
			massages.add("属性不能为空！");
		}

		//作者
	/*	if(!CommonUtil.isNotNull(question.getQauthor())){
			flag = false;
			massages.add("作者不能为空！");
		}*/
		resultMap.put(QuesConstant.KEY_RESULT, flag);
		resultMap.put(QuesConstant.VAL_MASSAGE, massages);
		return resultMap;
	}


}
