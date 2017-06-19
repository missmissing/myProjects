/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.service.impl
 * 文件名：				HtcateanalysisServiceImpl.java
 * 日期：				2015年6月22日-下午4:29:40
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.analysis.dao.HtcateanalysisDao;
import com.huatu.analysis.model.Htcateanalysis;
import com.huatu.analysis.service.HtcateanalysisService;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				HtcateanalysisServiceImpl
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月22日 下午4:29:40
 * @version 			0.0.1
 */
@Service
public class HtcateanalysisServiceImpl implements HtcateanalysisService {
	@Autowired
	private HtcateanalysisDao htcateanalysisDao;

	@Override
	public boolean save(Htcateanalysis htcateanalysis) throws HttbException {
		return htcateanalysisDao.save(htcateanalysis);
	}

	@Override
	public boolean savebatch(List<Htcateanalysis> htcateanalysis)
			throws HttbException {
		return htcateanalysisDao.savebatch(htcateanalysis);
	}

}
