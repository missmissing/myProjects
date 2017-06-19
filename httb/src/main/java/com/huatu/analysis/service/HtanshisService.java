/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.service
 * 文件名：				HtanshisService.java
 * 日期：					2015年6月22日-下午4:13:25
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service;

import java.util.List;

import com.huatu.analysis.model.Htanshis;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				HtanshisService
 * 类描述：				答题用户历史
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午4:13:25
 * @version 			0.0.1
 */
public interface HtanshisService {
	/**
	 *
	 * save							(单个答题用户)
	 * @param 		htanshis		答题用户
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htanshis htanshis) throws HttbException;

	/**
	 *
	 * savebatch					(批量保存答题用户)
	 * @param 		htanshis		答题用户
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htanshis> htanshis) throws HttbException;
}
