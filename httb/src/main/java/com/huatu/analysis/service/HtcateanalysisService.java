/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.service
 * 文件名：				HtcateanalysisService.java
 * 日期：					2015年6月22日-下午4:14:22
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service;

import java.util.List;

import com.huatu.analysis.model.Htcateanalysis;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				HtcateanalysisService
 * 类描述：				知识分类统计分析
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午4:14:22
 * @version 			0.0.1
 */
public interface HtcateanalysisService {
	/**
	 *
	 * save							(保存单个知识分类统计分析)
	 * @param 		htcateanalysis	知识分类统计分析
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htcateanalysis htcateanalysis) throws HttbException;

	/**
	 *
	 * savebatch					(批量保存知识分类统计分析)
	 * @param 		htcateanalysis	知识分类统计分析
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htcateanalysis> htcateanalysis) throws HttbException;
}
