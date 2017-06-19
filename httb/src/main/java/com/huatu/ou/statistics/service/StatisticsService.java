package com.huatu.ou.statistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.ou.menu.dao.MenuDao;
import com.huatu.ou.statistics.dao.StatisticsDao;


@Service
public class StatisticsService {
	
	@Autowired
	private StatisticsDao statisticsDao;
	
	public long queryHistoryNum() {
		return statisticsDao.queryHistoryNum();
	}

}
