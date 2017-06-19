package com.huatu.tb.quesInput.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.ou.user.model.User;
import com.huatu.tb.quesInput.util.QuesAttrMap;
import com.huatu.tb.quesInput.util.QuesAttrMapping;
import com.huatu.tb.question.model.ChildQuestion;
import com.huatu.tb.question.model.Question;

/**
 *
 * 类名称：				QuesGYBX
 * 类描述：				共用备选项处理类
 * 创建人：				Aijunbo
 * 创建时间：				2015年5月5日 上午10:05:49
 * @version 			0.0.1
 */
public class QuesGYBX{
	private static List<String> gybxs;

	//子题干集合
	public static List<ChildQuestion> qchilds = new ArrayList<ChildQuestion>();

	//试题对象
	public static Question question = new Question();

	//子题实体
	public static ChildQuestion childQuestion = new ChildQuestion();

	//共用备选选项
	private static List<String> qcchoices;

	/**
	 *
	 * transGXTGmap					(共享题干转换)
	 *	 							(将遍历的每一行封装到实体属性中)
	 * @param 		str				读取一行内容
	 * @param		qbatchNum		导题批次号
	 * @param		score			分值
	 * @param		user			当前系统登录用户
	 */
	public static Question transQuesMap(String str, String qbatchNum, int score, User user) {
		//判断是主题还是子题部分(true:主题，false:子题)
		boolean flag = false;

		//判断是否是题型(为试题开头部分)
		if(str.startsWith("【题型】") && (QuesBusFactory.quesMap == null || QuesBusFactory.quesMap.size() <= 7 )){
			//新建试题实体
			question = new Question();

			//初始化试题信息
			initQues(qbatchNum,user);

			//新建试题子题实体
			childQuestion = new ChildQuestion();

			//如果是第一行，则重新获取一个
			QuesBusFactory.quesMap = QuesAttrMap.getQuesGYBXmap();
			//获取子题干
			QuesBusFactory.itemMap = QuesAttrMap.getItemmap();
		}

		//遍历单选map集合
		for (String key : QuesBusFactory.quesMap.keySet()) {
			//根据标记获取内容
			if(CommonUtil.isStartWith(str, key)){
				//截取内容
				String valueStr = str.substring(key.length()).trim();
				//将内容设置到对象属性中
				question = QuesAttrMapping.setAttr(QuesBusFactory.quesMap.get(key), valueStr, question);
				QuesBusFactory.quesMap.remove(key);

				//从集合中移除已添加过的属性，防止重复遍历
				if(key.equals("【作者】")){
					//子题集合设置到实体对象中
					question.setQchild(qchilds);
					qchilds = new ArrayList<ChildQuestion>();
					//全局备用选项清空
					gybxs = null;
				}
				//设置为标记为主题干
				flag = true;
				break;
			}
		}

		if(!flag){
			//遍历
			for (String key : QuesBusFactory.itemMap.keySet()) {
				//根据标记获取内容
				if(CommonUtil.isStartWith(str, key)){
					//截取内容
					String valueStr = str.substring(key.length());
					//将内容设置到对象属性中
					childQuestion = QuesAttrMapping.setItemAttr(QuesBusFactory.itemMap.get(key), valueStr, childQuestion);
					//第一次加载子题时，把备选项进行记录，以后每次赋值给每个子题
					if(key.equals("【题干】") && gybxs == null){
						gybxs = childQuestion.getQcchoiceList();
					}else if(key.equals("【题干】") && gybxs != null){
						//第二次及以后加载，可用第一次加载的记录选项
						childQuestion.setQcchoiceList(gybxs);
					}
					//从集合中移除已添加过的属性，防止重复遍历
					break;
				}else if(str.matches("[A-Z].*")){
					//如果是[A-Z]. 是选项
					String valueStr = str.substring(2).trim();
					//将内容设置到对象属性中
					childQuestion = QuesAttrMapping.setItemAttr("choices", valueStr, childQuestion);
					break;
				}
			}
			if(CommonUtil.isStartWith(str, "【拓展】")){

				//设置共用备选项备选项
				if(CommonUtil.isNull(qcchoices) && CommonUtil.isNotNull(childQuestion.getQcchoiceList())){
					qcchoices = childQuestion.getQcchoiceList();
				}else if(CommonUtil.isNotNull(qcchoices) && CommonUtil.isNull(childQuestion.getQcchoiceList())){
					childQuestion.setQcchoiceList(qcchoices);
				}
				//设置分值
				childQuestion.setQcscore(score);
				childQuestion.setQcid(CommonUtil.getUUID());
				//添加子试题集合
				qchilds.add(childQuestion);
				childQuestion = new ChildQuestion();
			}
		}
		return question;
	}

	/**
	 *
	 * initQues						(创建试题是需要初始化的信息)
	 * 								创建试题时调用
	 * @param 		qbatchNum		导题目批次号
	 * @param		user			当前登录用户
	 */
	private static void initQues(String qbatchNum, User user) {
		//设置试题ID
		question.setQid(CommonUtil.getUUID());
		//设置创建时间
		question.setCreatetime(new Date());
		if(CommonUtil.isNotNull(user)){
			//设置创建人
			question.setCreateuser(user.getId());
			//如果用户组织机构不为空
			if(CommonUtil.isNotNull(user.getOrganization())){
				//设置组织机构
				question.setQorg(user.getOrganization().getId());
			}

		}
		//设置试题批次号
		question.setQbatchnum(qbatchNum);
		//设置是否失效
		question.setTombstone(Constants.TOMBSTONE_DeleteFlag_NO);
		//设置试题试题状态(现在发版阶段-先把导入的试题状态改为已发布)=========================================发版后记得改回来
		question.setQstatus("3");
	}

}
