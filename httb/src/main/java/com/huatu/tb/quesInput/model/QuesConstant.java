package com.huatu.tb.quesInput.model;
/**
 *
 * 类名称：				QuesConstant
 * 类描述：				导题常量类
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月10日 下午6:44:44
 * @version 			0.0.1
 */
public class QuesConstant {
	//输出地址
	public static String OUTPUTPATH = "D:\\logs\\";

	//返回检验结果
	public static String KEY_RESULT = "KEY_RESULT";

	//批次号前缀
	public static String BATCH_NUM = "BN";

	//返回检验消息
	public static String VAL_MASSAGE = "VAL_MASSAGE";

	//题目开始标记
	public static String QUES_START = "【题型】";

	//题目结束标记
	public static String QUES_END = "【作者】";

	//子题目开始标记
	public static String QUESHILD_START = "【子题干】";

	/**题型常量Start*/
	//单选题
	public static String QTYPE_DANXUAN = "单选题";

	//多选题
	public static String QTYPE_DUOXUAN = "多选题";

	//共用题干
	public static String QTYPE_GYTG = "共用题干";

	//共用备选
	public static String QTYPE_GYBX = "共用备选";
	/**题型常量End*/

	//错题存入rediskey名
	public static String QUESERRORS_REDIS = "quesErrors";

	//错题记录存入rediskey名
	public static String QUESRECORDS_REDIS = "quesRecords";

}
