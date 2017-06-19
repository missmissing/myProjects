package com.huatu.tb.quesInput.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.ou.user.model.User;
import com.huatu.tb.quesInput.model.QuesConstant;
import com.huatu.tb.quesInput.model.QuesError;
import com.huatu.tb.quesInput.model.QuesInputMessage;
import com.huatu.tb.quesInput.util.QuesValidateRecord;
import com.huatu.tb.question.model.Question;

/**
 *
 * 类名称：				QuesInput
 * 类描述：  				试题导入工具类
 * 创建人：				Aijunbo
 * 创建时间：				2015年5月5日 下午2:09:32
 * @version 			0.0.1
 */
@Component
public class QuesInput {
	@Autowired
	private IRedisService iRedisService;
	/**
	 *
	 * readTxtFile					(读取文件内容)
	 * @param 		filePath		文件路径
	 * @param		qbatchNum		批次号
	 * @param		score			每道题分数
	 * @param		user			当前登录用户
	 * @throws HttbException
	 */
	@SuppressWarnings("finally")
	public QuesInputMessage readTxtFile(InputStream inputStream, String qbatchNum, int score, User user) throws HttbException {
		//返回试题集合
		QuesInputMessage quesReturnList = new QuesInputMessage();

		try {
			//编码类型
			String encoding = "UTF-8";
			if (inputStream != null) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(inputStream, encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;

				//试题处理类
				QuesValidateRecord validator = new QuesValidateRecord();

				//循环遍历
				while ((lineTxt = bufferedReader.readLine()) != null) {
					validator.readLine(lineTxt, qbatchNum, score, user);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			throw new HttbException(ModelException.FILE_INPUT_ERROR,this.getClass()+"读取文件内容出错",e);
		} finally{
			//导入试题集合
			quesReturnList.setQuestions(QuesBusFactory.quesList);
			//重置试题集合
			QuesBusFactory.quesList = new ArrayList<Question>();
			//错题记录名称
			String fileName = "errorlog"+CommonUtil.formatDate(new Date(), "yyyyMMddhhmmss");


			//如果异常集合不为空,则输出到异常文件
			if(CommonUtil.isNotNull(QuesBusFactory.quesErrors)){
				//错题集合在redis中key名
				String errorsName = fileName+QuesConstant.QUESERRORS_REDIS;
				try {
					//错题记录存入redis
					iRedisService.putEX(errorsName, QuesBusFactory.quesErrors);
				} catch (HttbException e) {
					throw new HttbException(ModelException.FILE_INPUT_ERROR,this.getClass()+"错题记录存入redis时发生异常",e);
				}
				//设置错题记录名称(必填项为空)
				quesReturnList.setErrorName(fileName);
				//重置试题异常集合
				QuesBusFactory.quesErrors = new ArrayList<QuesError>();
			}
			//如果缺少标签试题集合不为空
			if(CommonUtil.isNotNull(QuesBusFactory.quesRecords)){

				String recordsName = fileName+QuesConstant.QUESRECORDS_REDIS;
				try {
					//错题记录存入redis
					iRedisService.putEX(recordsName, QuesBusFactory.quesRecords);
				} catch (HttbException e) {
					throw new HttbException(ModelException.FILE_INPUT_ERROR,this.getClass()+"错题记录存入redis时发生异常",e);
				}

				//设置错题记录名称(缺少标签)
				quesReturnList.setErrorName(fileName);
				//重置记录缺少标签试题集合
				QuesBusFactory.quesRecords = new ArrayList<String>();
			}
			return quesReturnList;
		}
	}
}
