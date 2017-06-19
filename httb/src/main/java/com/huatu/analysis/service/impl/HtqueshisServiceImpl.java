/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.service.impl
 * 文件名：				HtqueshisServiceImpl.java
 * 日期：					2015年6月21日-下午12:04:25
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.analysis.dao.HtqueshisDao;
import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.service.HtqueshisService;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				HtqueshisServiceImpl
 * 类描述：				试题历史记录表service处理类
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月21日 下午12:04:25
 * @version 			0.0.1
 */
@Service
public class HtqueshisServiceImpl implements HtqueshisService {
	@Autowired
	private HtqueshisDao htqueshisDao;

	@Override
	public boolean save(Htqueshis htqueshis) throws HttbException {
		return htqueshisDao.save(htqueshis);
	}

	@Override
	public boolean savebatch(List<Htqueshis> htqueshis) throws HttbException {
		return htqueshisDao.savebatch(htqueshis);
	}

	@Override
	public List<Htqueshis> getlist(Map<String, Object> filter)
			throws HttbException {
		List<Htqueshis> list = null;
		//查询结果集长度
		long count = htqueshisDao.getCount(filter);
		//结果集大于0时查询结果集
		if(count > 0){
			list = htqueshisDao.getlist(filter,count);
		}
		return list;
	}

}
