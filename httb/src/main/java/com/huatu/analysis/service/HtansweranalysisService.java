/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.service
 * 文件名：				HtansweranalysisService.java
 * 日期：					2015年6月22日-下午4:13:53
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service;

import java.util.List;

import com.huatu.analysis.model.Htansweranalysis;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				HtansweranalysisService
 * 类描述：				用户答题统计分析
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午4:13:53
 * @version 			0.0.1
 */
public interface HtansweranalysisService {
	/**
	 *
	 * save							(保存单个用户答题统计分析)
	 * @param 		htansweranalysis用户答题统计分析
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htansweranalysis htansweranalysis) throws HttbException;

	/**
	 *
	 * savebatch					(批量保存用户答题统计分析)
	 * @param 		htansweranalysis用户答题统计分析
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htansweranalysis> htansweranalysis) throws HttbException;
}
