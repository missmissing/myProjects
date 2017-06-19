/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.service.impl
 * 文件名：				HttestService.java
 * 日期：				2015年6月22日-下午9:40:41
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.analysis.dao.HttestDao;
import com.huatu.analysis.model.Httest;
import com.huatu.analysis.service.HttestService;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				HttestService
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月22日 下午9:40:41
 * @version 			0.0.1
 */
@Service
public class HttestServiceImpl implements HttestService {
	@Autowired
	private HttestDao httestDao;

	@Override
	public boolean save(Httest httest) throws HttbException {
		return httestDao.save(httest);
	}

	@Override
	public boolean savebatch(List<Httest> httest) throws HttbException {
		return httestDao.savebatch(httest);
	}

}
