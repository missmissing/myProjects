/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.service.impl
 * 文件名：				HtansweranalysisServiceImpl.java
 * 日期：				2015年6月22日-下午4:28:26
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.analysis.dao.HtansweranalysisDao;
import com.huatu.analysis.model.Htansweranalysis;
import com.huatu.analysis.service.HtansweranalysisService;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				HtansweranalysisServiceImpl
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月22日 下午4:28:26
 * @version 			0.0.1
 */
@Service
public class HtansweranalysisServiceImpl implements HtansweranalysisService {
	@Autowired
	private HtansweranalysisDao htansweranalysisDao;
	@Override
	public boolean save(Htansweranalysis htansweranalysis) throws HttbException {
		return htansweranalysisDao.save(htansweranalysis);
	}

	@Override
	public boolean savebatch(List<Htansweranalysis> htansweranalysis)
			throws HttbException {
		return htansweranalysisDao.savebatch(htansweranalysis);
	}

}
