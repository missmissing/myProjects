package com.huatu.core.service.impl;

import org.apache.log4j.Logger;

import com.huatu.core.dao.SqlBaseDao;
import com.huatu.core.service.BaseService;
import com.huatu.demo.BaseDao;

/**
 *
 * 类名称：				BaseServiceImpl
 * 类描述：  				基类service类
 * 创建人：				Aijunbo
 * 创建时间：				2015年4月1日 下午2:40:59
 * @version 			0.0.1
 */
public class BaseServiceImpl<E> implements BaseService<E> {

	protected static final Logger log = Logger.getLogger(BaseServiceImpl.class);

	/**
	 * 没有check复选框
	 */
	protected static String check = null;

	protected SqlBaseDao baseDao;

	public SqlBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(SqlBaseDao baseDao) {
		this.baseDao = baseDao;
	}

}
