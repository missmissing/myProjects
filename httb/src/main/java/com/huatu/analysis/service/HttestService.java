/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.service
 * 文件名：				HttestService.java
 * 日期：					2015年6月22日-下午9:34:35
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service;

import java.util.List;

import com.huatu.analysis.model.Httest;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				HttestService
 * 类描述：				测试记录service
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午9:34:35
 * @version 			0.0.1
 */
public interface HttestService {
	/**
	 *
	 * save							(单个保存测试记录)
	 * @param 		httest			测试记录对象
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Httest httest) throws HttbException;

	/**
	 *
	 * savebatch					(批量保存测试记录)
	 * @param 		httest			测试记录集合
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Httest> httest) throws HttbException;
}
