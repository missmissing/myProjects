/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.service
 * 文件名：				HtquesanalysisService.java
 * 日期：					2015年6月22日-下午4:15:01
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service;

import java.util.List;

import com.huatu.analysis.model.Htquesanalysis;
import com.huatu.analysis.model.Htqueshis;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				HtquesanalysisService
 * 类描述：				试题信息统计分析
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午4:15:01
 * @version 			0.0.1
 */
public interface HtquesanalysisService {
	/**
	 *
	 * save							(保存单个试题信息统计分析)
	 * @param 		htquesanalysis	试题信息统计分析
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htquesanalysis htquesanalysis) throws HttbException;

	/**
	 *
	 * savebatch					(批量保存试题信息统计分析)
	 * @param 		htquesanalysis	试题信息统计分析
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htquesanalysis> htquesanalysis) throws HttbException;

	/**
	 *
	 * getListByQids				(根据试题id获取试题分析记录)
	 * @param 		qids			试题id集合
	 * @return
	 * @throws 		HttbException
	 */
	public List<Htquesanalysis> getListByQids(List<String> qids) throws HttbException;

	/**
	 *
	 * refreshQuesAnalysis			(这里用一句话描述这个方法的作用)
	 * @param 		queslist		试题集合
	 * @param 		qrecorddate		版本号
	 * @return
	 * @throws 		HttbException
	 */
	public boolean refreshQuesAnalysis(List<Htqueshis> queslist, String qrecorddate) throws HttbException;

	/**
	 *
	 * RefreshQuesAnalysis		(刷新试题版本和分析)
	 * 								(保存答题时调用)
	 * @param 		htqueshis		答题结果记录
	 * @param 		systemdate		系统时间
	 * @return
	 * @throws 		HttbException
	 */
	public boolean RefreshQuesAnalysis(List<Htqueshis> htqueshis, String systemdate) throws HttbException;

	/**
	 *
	 * getQuesByQid					(根据试题id查询试题分析)
	 * @param 		qid				试题id
	 * @return
	 * @throws	 	HttbException
	 */
	public Htquesanalysis getQuesByQid(String qid) throws HttbException;
}
