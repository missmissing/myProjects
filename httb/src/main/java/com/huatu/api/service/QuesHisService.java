package com.huatu.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huatu.api.dao.QuesHisDao;
import com.huatu.api.vo.Queshis;
import com.huatu.core.exception.HttbException;

@Component
public class QuesHisService {
	@Autowired
	private QuesHisDao quesHisDao;
	public boolean save(Queshis queshis) throws HttbException {
		return quesHisDao.insert(queshis);
	}

	public boolean saveBatch(List<Queshis> list) throws HttbException {
		return quesHisDao.insertBatch(list);
	}
}
