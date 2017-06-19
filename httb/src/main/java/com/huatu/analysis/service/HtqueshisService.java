/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.service
 * 文件名：				HtqueshisService.java
 * 日期：					2015年6月21日-下午12:03:08
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service;

import java.util.List;
import java.util.Map;

import com.huatu.analysis.model.Htqueshis;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				HtqueshisService
 * 类描述：				试题历史记录表service接口类
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月21日 下午12:03:08
 * @version 			0.0.1
 */
public interface HtqueshisService {
	/**
	 *
	 * save							(单个保存答题记录)
	 * @param 		htqueshis		答题记录对象
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htqueshis htqueshis) throws HttbException;

	/**
	 *
	 * savebatch					(批量保存答题记录)
	 * @param 		htqueshis		答题记录集合
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htqueshis> htqueshis) throws HttbException;

	/**
	 *
	 * getlist						(根据条件查找答题记录)
	 * @param 		filter			过滤条件
	 * @return
	 * @throws 		HttbException
	 */
	public List<Htqueshis> getlist(Map<String,Object> filter) throws HttbException;
}
