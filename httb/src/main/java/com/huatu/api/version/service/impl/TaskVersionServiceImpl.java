/**
 * 项目名：				httb
 * 包名：				com.huatu.api.version.service.impl
 * 文件名：				TaskVersionServiceImpl.java
 * 日期：				2015年6月21日-下午2:53:38
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.api.version.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.huatu.api.version.dao.TaskVersionDao;
import com.huatu.api.version.model.TaskVersion;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.core.exception.HttbException;

/**
 * 类名称：				TaskVersionServiceImpl
 * 类描述：
 * 创建人：				LiXin
 * 创建时间：			2015年6月21日 下午2:53:38
 * @version 		1.0
 */
@Service("taskVersionService")
@Scope("prototype")
public class TaskVersionServiceImpl implements TaskVersionService {
	@Autowired
	private TaskVersionDao taskVersionDao;

	/**获得版本*/
	@Override
	public TaskVersion getVersion(String id) throws HttbException {
		return taskVersionDao.getVersion(id);
	}

	/**删除版本*/
	@Override
	public boolean deleteVersion(String id) throws HttbException {
		return taskVersionDao.deleteVersion(id);
	}

	/**添加版本*/
	@Override
	public boolean addVersion(TaskVersion taskVersion) throws HttbException{
		return taskVersionDao.addVersion(taskVersion);
	}

	/**获得版本ResultSet*/
	@Override
	public List<TaskVersion> getVersionResultSet(Map<String, Object> condition)
			throws HttbException {
		List<TaskVersion> list = new ArrayList<TaskVersion>();
		ResultSet resultSet  = taskVersionDao.getVersionResultSet(condition);
		for(Row row : resultSet){
			TaskVersion tv = new TaskVersion();
			tv.setTkey(row.getString("tkey"));//标记key
			tv.setTvalue(row.getString("tvalue"));//标记value
			tv.setTpapertype(row.getString("tpapertype"));//试卷类型(是否真题，模拟题)
			tv.setTdesc(row.getString("tdesc"));//描述
			tv.setCreatetime(row.getDate("createtime"));//创建时间
			list.add(tv);
		}
		return list;
	}

}
