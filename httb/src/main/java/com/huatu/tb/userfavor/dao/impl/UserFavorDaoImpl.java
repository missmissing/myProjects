/**
 * 项目名：				httb
 * 包名：				com.huatu.tb.userfavor.dao
 * 文件名：				UserFavorDaoImpl.java
 * 日期：				2015年5月13日-下午12:00:50
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.tb.userfavor.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Update;
import com.google.common.collect.ImmutableList;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Page;
import com.huatu.core.util.CommonUtil;
import com.huatu.tb.userfavor.dao.UserFavorDao;
import com.huatu.tb.userfavor.model.Favorite;
import com.huatu.tb.userfavor.model.UserFavor;

/**
 * 类名称：				UserFavorDaoImpl
 * 类描述：
 * 创建人：				LiXin
 * 创建时间：			2015年5月13日 下午12:00:50
 * @version 		1.0
 */
@Repository
public class UserFavorDaoImpl implements UserFavorDao {
	@Autowired
	private NoSqlBaseDao noSqlBaseDao;

	@Override
	public UserFavor get(String id) throws HttbException {
		UserFavor  userFavor  = new UserFavor();
		try{
			Select select = QueryBuilder.select().all().from("httb", "HTFAVOR");
			select.where(QueryBuilder.eq("uid",id));
			ResultSet result = noSqlBaseDao.executeQuery(select);
			Row row = null;
			List<Row> rows = result.all();
			if(result!=null&&rows!=null&&rows.size()>0){
				row = rows.get(0);
			}
			if(CommonUtil.isNotNull(row)){
				userFavor.setUid(row.getString("uid"));// 用户ID
				userFavor.setFhisfavors(row.getList("fhisfavors", Favorite.class));//试题收藏夹
			}
		}catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "获取用户收藏时发生异常", e);
		}

		return userFavor;
	}

	@Override
	public boolean delete(String id) throws HttbException {
		boolean boo = false;
		try{
			Delete delete  = QueryBuilder.delete().from("httb", "HTFAVOR");
			delete.where(QueryBuilder.eq("uid",id));
			noSqlBaseDao.executeSql(delete);
			boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "删除用户收藏对象时发生异常", e);
		}
		return boo;
	}

	@Override
	public boolean insert(UserFavor favor) throws HttbException {
		boolean boo = false;
		try{
			Insert insert = QueryBuilder.insertInto("httb", "HTFAVOR");
			insert.value("uid",favor.getUid());	//用户ID
			if(CommonUtil.isNotNull(favor.getFhisfavors())){
				List<UDTValue> udtList = new ArrayList<UDTValue>();
				UserType childsUDT = noSqlBaseDao.getNoSqlSession().getCluster().getMetadata().getKeyspace("httb").getUserType("type_answer");
				for(Favorite fv : favor.getFhisfavors()){
					UDTValue cqt = childsUDT.newValue();
					cqt.setString("qid",fv.getQid());//试题ID
					cqt.setDate("afavortime",fv.getAfavortime());//收藏集合
					udtList.add(cqt);
				}
				insert.value("fhisfavors",ImmutableList.copyOf(udtList));
			}
			noSqlBaseDao.executeSql(insert);
			boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入用户收藏时发生异常", e);
		}

		return boo;
	}

	@Override
	public boolean update(UserFavor favor) throws HttbException {
		boolean boo = false;
		try{
			Update update = QueryBuilder.update("httb", "HTFAVOR");
			if(CommonUtil.isNotNull(favor.getFhisfavors())){
				update.with(QueryBuilder.set("fhisfavors",favor.getFhisfavors()));//收藏集合
			}

			noSqlBaseDao.executeSql(update);
			boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_UPDATE, this.getClass() + "插入用户收藏时发生异常", e);
		}
		return boo;
	}

	@Override
	public ResultSet getPagerResultSet(Map<String, Object> filter) throws HttbException {

		return null;
	}

	@Override
	public ResultSet getPagerResultSet(Page page, Map<String, Object> filter) throws HttbException {
		// TODO Auto-generated method stub
		return null;
	}

}
