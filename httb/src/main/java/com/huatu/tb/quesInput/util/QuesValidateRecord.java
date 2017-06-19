package com.huatu.tb.quesInput.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.huatu.core.util.CommonUtil;
import com.huatu.ou.user.model.User;
import com.huatu.tb.quesInput.model.QuesConstant;
import com.huatu.tb.quesInput.service.QuesBusFactory;

/**
 *
 * 类名称：				QuesValidateRecord
 * 类描述：				试题验证类
 * 创建人：				Aijunbo
 * 创建时间：				2015年5月6日 下午3:10:06
 * @version 			0.0.1
 */
public class QuesValidateRecord {
	//读取记录集合
	private List<String> recordLines = new ArrayList<String>();

	//题干属性集合
	private Map<String, String> quesMap;

	//子题属性集合
	private Map<String, String> quesChildMap;

	//题型
	private String qtype;

	//验证结果
	private Boolean validateFlag;

	/**
	 *
	 * readTxtFile					(读取文件内容)
	 * @param 		lineText		读取文本
	 * @param		qbatchNum		批次号
	 * @param		score			每道题分数
	 * @param		user			当前登录用户
	 */
	public void readLine(String lineText, String qbatchNum, int score, User user){
		//初始化试题集合属性
		initMap(lineText);

		//将内容添加到读取记录中
		recordLines.add(lineText);
		if(lineText.trim().length() > 0){
			validateFlag = removeMapAttr(lineText);
		}

		//如果验证通过，则返回读取的试题结果
		if(validateFlag != null && validateFlag){
			/**进入第二次验证*/
			QuesBusFactory quesHandle = new QuesBusFactory();

			for (String recordLine : recordLines) {
				if(recordLine.trim().length() > 0){
					quesHandle.transQuesMap(recordLine.trim(), qbatchNum, qtype, score, user);
				}
			}

			//验证通过后,初始化所有参数
			initAll();
		}


	}

	/**
	 *
	 * initAll						(初始化所有参数)
	 * 								保存之前操作后，调用
	 */
	private void initAll(){
		//读取记录集合
		recordLines = new ArrayList<String>();

		//题干属性集合
		quesMap = null;

		//子题属性集合
		quesChildMap = null;

		//题型
		qtype = null;

		//验证结果
		validateFlag = false;
	}

	/**
	 *
	 * initMap						(初始化试题属性集合)
	 * 								(每次重新加载一道题时，调用此方法)
	 * @param lineTxt
	 */
	private void initMap(String lineTxt) {
		//判断是否新开始一道题
		if(CommonUtil.isStartWith(lineTxt,QuesConstant.QUES_START)){
			if(validateFlag != null && !validateFlag){
				/**把之前所有记录输出到异常文件中*/
				if(recordLines != null && recordLines.size() > 3){
					QuesBusFactory.quesRecords.addAll(recordLines);
				}
				initAll();
			}

			qtype = lineTxt.substring("【题型】".length());
			if(qtype != null && qtype.trim().equals(QuesConstant.QTYPE_GYBX)){
				//初始化试题map
				quesMap = QuesAttrMap.getQuesGYBXmap();
			}else{
				//初始化试题map
				quesMap = QuesAttrMap.getQuesGYTGmap();
			}

			//初始化题干map
			quesChildMap = QuesAttrMap.getItemmap();
		}

		//判断是否新开始子题
		if(CommonUtil.isStartWith(lineTxt,QuesConstant.QUESHILD_START)){
			//初始化题干map
			quesChildMap = QuesAttrMap.getItemmap();
		}
	}

	/**
	 *
	 * removeMapAttr				(移除Map中的属性)
	 * @param 		lineText		行数据
	 * @return
	 */
	private boolean removeMapAttr(String lineText){
		//判断是主题还是子题部分(true:主题，false:子题)
		boolean isQuesFlag = false;

		if(quesMap == null){
			System.out.println(lineText);
		}else{
			//遍历单选map集合
			for (String key : quesMap.keySet()) {
				//根据标记获取内容
				if(CommonUtil.isStartWith(lineText, key)){
					//移除集合中对应属性
					quesMap.remove(key);
					//设置为标记为主题干
					isQuesFlag = true;
					break;
				}
			}

			//子题属性
			if(!isQuesFlag){
				//遍历
				for (String key : quesChildMap.keySet()) {
					//根据标记获取内容
					if(CommonUtil.isStartWith(lineText, key)){
						//移除集合中对应属性
						quesChildMap.remove(key);
						break;
					}else if(lineText.matches("[A-Z].*")){
						//将内容添加到读取记录中
						break;
					}
				}
			}
			return validateMap();
		}
		return isQuesFlag;
	}

	/**
	 *
	 * validateMap					(验证试题集合)
	 * @return
	 */
	private boolean validateMap() {
		boolean quesFlag = false;

		//试题结束标记
		String quesEnd = QuesConstant.QUES_END;
		//如果结束标记不为空
		if(CommonUtil.isNull(quesMap.get(quesEnd))){
			quesFlag = true;
		}

		return quesFlag;
	}
}
