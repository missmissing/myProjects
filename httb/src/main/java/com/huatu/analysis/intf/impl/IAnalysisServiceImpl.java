/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.intf.impl
 * 文件名：				IAnalysisServiceImpl.java
 * 日期：				2015年6月25日-下午11:09:38
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.intf.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.analysis.intf.IAnalysisService;
import com.huatu.analysis.model.Htexamresult;
import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.model.Httest;
import com.huatu.analysis.service.HtqueshisService;
import com.huatu.analysis.service.HtsxrankService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;

/**
 * 类名称：				IAnalysisServiceImpl
 * 类描述：				分析处理对外接口
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月25日 下午11:09:38
 * @version 			0.0.1
 */
@Service
public class IAnalysisServiceImpl implements IAnalysisService{
	@Autowired
	private HtsxrankService htsxrankService;

	@Autowired
	private HtqueshisService htqueshisService;

	@Override
	public boolean submitPaper(List<Htqueshis> htqueshisList,
			Htexamresult htexamresult) throws HttbException {
		return false;
	}

	@Override
	public int submitTest(List<Htqueshis> htqueshisList, Httest httest)
			throws HttbException {
		int rank = 0;
		try {
			//1 批量保存答题记录
			htqueshisService.savebatch(htqueshisList);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_ANALYSIS_SAVE, this.getClass() + "批量保存答题记录时发生异常", e);
		}
		//2 错题集
		//3 测试结果表
		//4 正确以下存储
		//5历史答题记录
		try {
			rank = htsxrankService.saveSXRank(htqueshisList);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_ANALYSIS_SAVE, this.getClass() + "保存顺序答题排序时发生异常", e);
		}
		return rank;
	}

	@Override
	public int getRankup(String uid, String cid) throws HttbException{
		int rank = 0;
		try {
			rank = htsxrankService.getSXRank(uid,cid);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_ANALYSIS, this.getClass() + "查询用户顺序答题排名时发生异常", e);
		}
		return rank;
	}

}
