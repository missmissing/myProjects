/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.service
 * 文件名：				Htexamresult.java
 * 日期：				2015年6月22日-下午9:36:27
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service;

import java.util.List;
import java.util.Map;

import com.huatu.analysis.model.Htexamresult;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				Htexamresult
 * 类描述：				考试记录处理service
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午9:36:27
 * @version 			0.0.1
 */
public interface HtexamresultService {
	/**
	 *
	 * save							(单个保存考试记录)
	 * @param 		htexamresult	考试记录对象
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htexamresult htexamresult) throws HttbException;

	/**
	 *
	 * savebatch					(批量保存考试记录)
	 * @param 		htexamresult	考试记录集合
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htexamresult> htexamresult) throws HttbException;

	/**
	 *
	 * getlist						(根据条件查找试卷结果记录)
	 * @param 		filter			过滤条件
	 * @return
	 * @throws 		HttbException
	 */
	public List<Htexamresult> getlist(Map<String,Object> filter) throws HttbException;

	/**
	 *
	 * getRecentExam				(获取最近考试结果条数)
	 * 								(提交试卷时需要获取最近若干次答题记录)
	 * @param 		filter			查询条件
	 * @param		recentCount		查询条数
	 * @return
	 * @throws 		HttbException
	 */
	public List<Htexamresult> getRecentExam(Map<String, Object> filter, int recentCount)
			throws HttbException;
}
