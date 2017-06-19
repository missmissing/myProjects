/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.service
 * 文件名：				HtsxrankService.java
 * 日期：				2015年6月23日-上午10:53:53
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service;

import java.util.List;

import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.model.Htsxrank;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				HtsxrankService
 * 类描述：				顺序排名
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月23日 上午10:53:53
 * @version 			0.0.1
 */
public interface HtsxrankService {
	/**
	 *
	 * getSXRank					(获取顺序排名)
	 * @param 		uid				用户id
	 * @param		cid				知识点id
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public int getSXRank(String uid, String cid) throws HttbException;

	/**
	 *
	 * getSXRank					(获取顺序排名)
	 * @param 		htqueshis		答题记录集合
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public int saveSXRank(List<Htqueshis> htqueshis) throws HttbException;

	/**
	 *
	 * savebatch					(批量保存顺序排名)
	 * @param 		htsxrank		顺序排名对象
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htsxrank> htsxrank) throws HttbException;

	/**
	 *
	 * savebatch					(批量保存顺序排名)
	 * @param 		saveQueshisList 答题记录
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public List<Htsxrank> saveQueshisList(List<Htqueshis> htqueshis) throws HttbException;

	/**
	 *
	 * getList						(获取顺序排名集合)
	 * 								(实时查询排名时调用)
	 * @return						排名集合
	 * @throws 		HttbException
	 */
	public List<Htsxrank> getList() throws HttbException;

	/**
	 *
	 * getByUid						(根据用户id获取顺序排名)
	 * 								(实时查询单个用户排名时调用)
	 * @param 		uid				用户id
	 * @return
	 * @throws 		HttbException
	 */
	public List<Htsxrank> getByUid(String uid) throws HttbException;

	/**
	 *
	 * batchDel						(批量删除顺序排名)
	 * @return
	 * @throws HttbException
	 */
	public boolean batchDel() throws HttbException;
}
