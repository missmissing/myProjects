/**
 * 项目名：				httb
 * 包名：				com.huatu.api.version.dao
 * 文件名：				TaskVersionDaoImpl.java
 * 日期：				2015年6月21日-下午2:55:05
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.api.version.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.huatu.api.version.dao.TaskVersionDao;
import com.huatu.api.version.model.TaskVersion;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.CommonUtil;

/**
 * 类名称： TaskVersionDaoImpl 类描述： 创建人： LiXin 创建时间： 2015年6月21日 下午2:55:05
 *
 * @version 1.0
 */
@Repository
@Scope("prototype")
public class TaskVersionDaoImpl implements TaskVersionDao {

	@Autowired
	private NoSqlBaseDao noSqlBaseDao;

	/** 获得版本 */
	@Override
	public TaskVersion getVersion(String key) throws HttbException {
		try {
			TaskVersion t = new TaskVersion();
			Select select = QueryBuilder.select().all().from("httb", "HTTASKVERSION");
			select.where(QueryBuilder.eq("tkey", key));
			select.allowFiltering();
			List<Row> result = noSqlBaseDao.executeSearchSql(select);
			for(Row row : result){
				t.setTkey(row.getString("tkey"));
				t.setTvalue(row.getString("tvalue"));
				t.setTpapertype(row.getString("tpapertype"));
				t.setCreatetime(row.getDate("createtime"));
				t.setTdesc(row.getString("tdesc"));
			}
			return t;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询版本对象时发生异常", e);
		}

	}

	/** 删除版本 */
	@Override
	public boolean deleteVersion(String key) throws HttbException {
		boolean boo = false;
		try {
			Delete delete = QueryBuilder.delete().from("httb", "HTTASKVERSION");
			delete.where(QueryBuilder.eq("tkey", key));
			noSqlBaseDao.executeSql(delete);
			boo = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "删除版本对象时发生异常", e);
		}
		return boo;
	}

	/** 添加版本 */
	@Override
	public boolean addVersion(TaskVersion taskVersion) throws HttbException {
		boolean boo = false;
		try {
			Insert insert = QueryBuilder.insertInto("httb", "HTTASKVERSION");
			if (CommonUtil.isNotNull(taskVersion.getTkey())) {
				insert.value("tkey", taskVersion.getTkey());
			}
			if (CommonUtil.isNotNull(taskVersion.getTvalue())) {
				insert.value("tvalue", taskVersion.getTvalue());
			}
			if (CommonUtil.isNotNull(taskVersion.getTpapertype())) {
				insert.value("tpapertype", taskVersion.getTpapertype());
			}
			if (CommonUtil.isNotNull(taskVersion.getTdesc())) {
				insert.value("tdesc", taskVersion.getTdesc());
			}
			if (CommonUtil.isNotNull(taskVersion.getCreatetime())) {
				insert.value("createtime", taskVersion.getCreatetime());
			}
			noSqlBaseDao.executeSql(insert);
			boo = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入版本对象时发生异常", e);
		}
		return boo;
	}

	/** 获得版本 ResultSet */
	@Override
	public ResultSet getVersionResultSet(Map<String, Object> filter) throws HttbException {
		try {
			Select select = QueryBuilder.select().all().from("httb", "HTTASKVERSION");
			if (CommonUtil.isNotNull(filter)) {
				if (filter.containsKey("tkey") && CommonUtil.isNotNull(filter.get("tkey"))) {
					select.where(QueryBuilder.eq("tkey", filter.get("tkey")));
				}
				if (filter.containsKey("tvalue") && CommonUtil.isNotNull(filter.get("tvalue"))) {
					select.where(QueryBuilder.eq("tvalue", filter.get("tvalue")));
				}
				if (filter.containsKey("tpapertype") && CommonUtil.isNotNull(filter.get("tpapertype"))) {
					select.where(QueryBuilder.eq("tpapertype", filter.get("tpapertype")));
				}
				if (filter.containsKey("createtime") && CommonUtil.isNotNull(filter.get("createtime"))) {
					select.where(QueryBuilder.eq("createtime", filter.get("createtime")));
				}
				if (filter.containsKey("tdesc") && CommonUtil.isNotNull(filter.get("tdesc"))) {
					select.where(QueryBuilder.eq("tdesc", filter.get("tdesc")));
				}
				select.allowFiltering();
			}
			return noSqlBaseDao.executeQuery(select);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询版本对象时发生异常", e);
		}
	}

}
