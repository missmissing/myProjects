/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.service
 * 文件名：				HtexamanalysisService.java
 * 日期：					2015年6月22日-下午4:14:40
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service;

import java.util.List;

import com.huatu.analysis.model.Htexamanalysis;
import com.huatu.analysis.model.Htexamresult;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				HtexamanalysisService
 * 类描述：				考试统计分析
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午4:14:40
 * @version 			0.0.1
 */
public interface HtexamanalysisService {
	/**
	 *
	 * save							(保存单个考试统计分析)
	 * @param 		htcateanalysis	考试统计分析
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htexamanalysis htexamanalysis) throws HttbException;

	/**
	 *
	 * savebatch					(批量保存考试统计分析)
	 * @param 		htexamanalysis	考试统计分析
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htexamanalysis> htexamanalysis) throws HttbException;

	/**
	 *
	 * getListByEids				(根据试卷id集合查询考试分析集合)
	 * @param 		eids			试卷id集合
	 * @return
	 * @throws	 	HttbException
	 */
	public List<Htexamanalysis> getListByEids(List<String> eids) throws HttbException;

	/**
	 *
	 * refreshExamAnalysis			(书安心试卷分析缓存)
	 * 								(定时刷新试卷分析时调用)
	 * @param 		exams			新增试卷结果集合
	 * @param 		qrecorddate		时间戳
	 * @return
	 * @throws 		HttbException
	 */
	public boolean refreshExamAnalysis(List<Htexamresult> exams, String qrecorddate) throws HttbException;

	/**
	 *
	 * getHtexamanalysis			(根据试卷id返回试卷分析记录)
	 * 								(提交试卷时调用)
	 * @param 		examId			试卷Id
	 * @return
	 */
	public Htexamanalysis getHtexamanalysis(String examId) throws HttbException;

}
