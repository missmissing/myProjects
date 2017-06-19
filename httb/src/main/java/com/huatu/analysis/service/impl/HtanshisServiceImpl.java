/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.service.impl
 * 文件名：				HtanshisServiceImpl.java
 * 日期：				2015年6月22日-下午4:26:59
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.analysis.dao.HtanshisDao;
import com.huatu.analysis.model.Htanshis;
import com.huatu.analysis.service.HtanshisService;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				HtanshisServiceImpl
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午4:26:59
 * @version 			0.0.1
 */
@Service
public class HtanshisServiceImpl implements HtanshisService {
	@Autowired
	private HtanshisDao htanshisDao;
	@Override
	public boolean save(Htanshis htanshis) throws HttbException {
		return htanshisDao.save(htanshis);
	}

	@Override
	public boolean savebatch(List<Htanshis> htanshis) throws HttbException {
		return htanshisDao.savebatch(htanshis);
	}

}
