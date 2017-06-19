/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis
 * 文件名：				SaveDataService.java
 * 日期：				2015年6月22日-下午4:39:44
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.intf;

import java.util.List;

import com.huatu.analysis.model.Htexamresult;
import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.model.Httest;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				SaveDataService
 * 类描述：  				保存数据
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午4:39:44
 * @version 			0.0.1
 */
public interface IAnalysisService {
	/**
	 *
	 * submitPaper					(保存试卷-模拟题、真题)
	 * 								(保存试卷时调用)
	 * @param 		htqueshisList	答题记录集合
	 * @param 		htexamresult	试卷信息
	 * @return
	 * @throws 		HttbException
	 */
	public boolean submitPaper(List<Htqueshis> htqueshisList, Htexamresult htexamresult) throws HttbException;

	/**
	 *
	 * submitTest					(保存测试-顺序练习、模块练习)
	 * 								(保存测试题时调用)
	 * @param 		htqueshisList	答题记录集合
	 * @param 		httest			试卷信息
	 * @return
	 * @throws 		HttbException
	 */
	public int submitTest(List<Htqueshis> htqueshisList, Httest httest) throws HttbException;

	/**
	 *
	 * getRankup					(根据用户id,知识点id获取排名)
	 * @param 		uid				用户Id
	 * @param 		cid				知识点id
	 * @return
	 * @throws HttbException
	 */
	public int getRankup(String uid, String cid) throws HttbException;
}
