package com.huatu.tb.quesInput.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.ProcessPropertiesConfigUtil;
import com.huatu.tb.common.enums.AttributeEnum;
import com.huatu.tb.common.enums.DifficultyEnum;
import com.huatu.tb.common.enums.QuestionTypesEnum;
import com.huatu.tb.question.model.ChildQuestion;
import com.huatu.tb.question.model.Question;
import com.huatu.tb.question.util.QuestionUtil;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

/**
 *
 * 类名称： QuesAttrMapping 类描述： 属性匹配类(模板与试题属性对应处理类) 创建人： Aijunbo 创建时间： 2015年5月6日
 * 下午8:31:24
 * 
 * @version 0.0.1
 */
public class QuesAttrMapping {
	/**
	 *
	 * setAttr (设置试题属性)
	 * 
	 * @param index
	 *            集合下标
	 */
	public static Question setAttr(String key, String value, Question question) {
		if (question == null) {
			question = new Question();
		}
		switch (key) {
		case "qtype":
			// 【题型】--qtype
			Object qtype = QuestionTypesEnum.getAllMapInput().get(value);
			if (CommonUtil.isNotNull(qtype)) {
				question.setQtype(qtype.toString());
			}
			break;
		case "qdifficulty":
			// 【难度】--qdifficulty
			// question.setQdifficulty(DifficultyEnum.getAllMapInput().get(value).toString());
			question.setQdifficulty(DifficultyEnum.getValueByText(value));
			break;
		case "qcontent":
			// 【题干】--content
			question.setQcontent(value);
			break;
		case "qcategory":
			// 【考试类型】--qcategory
			List<String> categorys = null;
			// 判断是否为空
			if (CommonUtil.isNotEmpty(value)) {
				categorys = new ArrayList<String>();
				// 按#分割
				categorys.addAll(Arrays.asList(value.split("#")));
				// 遍历知识分类名称
				for (int i = 0; i < categorys.size(); i++) {
					// 获取properties文件中的知识分类编码
					String cateCode = QuestionUtil.getQCategoryCode(categorys.get(i));
					categorys.set(i, cateCode);
				}
			}
			question.setQcategory(categorys);
			break;
		case "qpoint":
			// 【知识点】--qpoint
			question.setQpoint(CommonUtil.getArray(value, "#"));
			break;
		case "qyear":
			if (CommonUtil.isNotEmpty(value) && value.length() > 4) {
				value = value.substring(0, 4);
			}
			// 【年份--qyear
			question.setQyear(value);
			break;
		case "qfrom":
			// 【信息源】--qfrom
			question.setQfrom(value);
			break;
		case "qattribute":
			// 【属性】--qattribute
			Object qattribute = AttributeEnum.getAllMapKV().get(value);
			if (CommonUtil.isNotNull(qattribute)) {
				question.setQattribute(qattribute.toString());
			}
			break;
		case "qauthor":
			// 【作者】--qauthor
			question.setQauthor(value);
			// QuesInput.quesMap = null;
			break;
		default:
			break;
		}
		return question;
	}

	/**
	 *
	 * setItemAttr (设置子试题属性)
	 * 
	 * @param index
	 *            集合下标
	 */
	public static ChildQuestion setItemAttr(String key, String value, ChildQuestion childQuestion) {
		if (childQuestion == null) {
			childQuestion = new ChildQuestion();
		}

		switch (key) {

		case "qctype":
			// 【子题型】--qctype
			childQuestion.setQctype(value);
			break;
		case "qccontent":
			// 【子题干】--qccontent
			childQuestion.setQccontent(value);
			break;
		case "choices":
			// 子题干选项
			if (childQuestion.getQcchoiceList() != null) {
				childQuestion.getQcchoiceList().add(value);
			} else {
				List<String> choices = new ArrayList<String>();
				choices.add(value);
				childQuestion.setQcchoiceList(choices);
			}
			break;
		case "qcans":
			// 【答案】--qcans
			childQuestion.setQcans(value);
			break;
		case "qccomment":
			// 【解析】--qccomment
			childQuestion.setQccomment(value);
			break;
		case "qcextension":
			// 【拓展】--qcextension
			childQuestion.setQcextension(value);
			break;
		default:
			break;
		}
		return childQuestion;
	}

	public static Map<String, String> getQuestionCategory() {
		Map<String, String> map = new HashMap<String, String>();
		Properties p = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("questionCatetory");
		Properties typeTable = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("httbContext");
		String type = typeTable.getProperty("app.deploy.type");
		switch (type) {

		case "100": {// 医疗
			String hszgks = p.getProperty("hszgks");
			String hs2zgks = p.getProperty("hs2zgks");
			String zghszgks = p.getProperty("zghszgks");
			String lczyysks = p.getProperty("lczyysks");
			map.put("hszgks", hszgks);
			map.put("hs2zgks", hs2zgks);
			map.put("zghszgks", zghszgks);
			map.put("lczyysks", lczyysks);
			
			break;
		}
		case "200": {// 金融
			String nxszk = p.getProperty("nxszk");
			String yhzk = p.getProperty("yhzk");
			map.put("nxszk", nxszk);
			map.put("yhzk", yhzk);
			break;
		}
		case "300": {// 公务员
			map.put("wu", "无");
			break;
		}
		case "400": {// 教师
			map.put("wu", "无");
			break;
		}
		case "500": {// 事业单位
			String sydwks = p.getProperty("sydwks");
			map.put("sydwks", sydwks);
			break;
		}
		default: {
			break;
		}

		}

		return map;
	}

}
