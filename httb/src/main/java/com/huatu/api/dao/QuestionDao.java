package com.huatu.api.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.huatu.api.vo.ErrorQuestionVo;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.CommonUtil;

@Component
public class QuestionDao {

	@Autowired
	private NoSqlBaseDao noSqlBaseDao;

	public boolean collectQuestion(String userid, List<String> questionids)
			throws HttbException {
		boolean res = false;
		try {
			Insert[] inserts = new Insert[questionids.size()];
			int i = 0;
			for (String qid : questionids) {
				Insert insert = QueryBuilder.insertInto("httb", "HTFAVOR");
				insert.value("uid", userid);
				insert.value("qid", qid);
				insert.value("createtime", new Date());
				inserts[i] = insert;
				i++;
			}
			Batch batch = QueryBuilder.batch(inserts);
			noSqlBaseDao.executeSql(batch);
			res = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT,
					this.getClass() + "插入收藏对象时发生异常", e);
		}
		return res;
	}

	public List<String> getCollectQuestionListByUserId(String userid)
			throws HttbException {
		List<String> list = new ArrayList<String>();
		try {
			Select select = QueryBuilder.select().all().from("httb", "HTFAVOR");
			if (CommonUtil.isNotNull(userid)) {
				select.where(QueryBuilder.eq("uid", userid));
			}
			select.allowFiltering();
			ResultSet result = noSqlBaseDao.executeQuery(select);
			if (result.all() == null) {
				return list;
			}
			for (Row row : result.all()) {
				list.add(row.getString("qid"));
			}
			return list;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH,
					this.getClass() + "查询错题对象时发生异常", e);
		}

	}

	public boolean deleteCollectQuestion(String userid, List<String> qids)
			throws HttbException {
		boolean boo = false;
		try {
			Object[] obj = new Object[qids.size()];
			for (int i = 0; i < qids.size(); i++) {
				obj[i] = qids.get(i);
			}
			Delete delete = QueryBuilder.delete().from("httb", "HTFAVOR");
			delete.where(QueryBuilder.eq("uid", userid));
			delete.where(QueryBuilder.in("pid", obj));
			noSqlBaseDao.executeSql(delete);
			boo = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE,
					this.getClass() + "删除收藏对象时发生异常", e);
		}
		return boo;
	}

	/****************************** 错题相关 *****************************/
	public List<String> getWrongQuestionListByUserId(String userno)
			throws HttbException {
		List<String> list = new ArrayList<String>();
		try {
			Select select = QueryBuilder.select().column("qhqid").from("httb", "HTERROR");
			if (CommonUtil.isNotNull(userno)) {
				select.where(QueryBuilder.eq("qhuid", userno));
			}
			select.allowFiltering();
			// String select =
			// " select qhqid from httb.hterror where qhuid = '2' allow filtering;";
			// ResultSet result = noSqlBaseDao.getResultSet(select);
			ResultSet result = noSqlBaseDao.executeQuery(select);
			for (Row row : result) {
				list.add(row.getString("qhqid"));
			}
			return list;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH,
					this.getClass() + "查询错题对象时发生异常", e);
		}
	}

	public boolean addWrongQuestion(Map<String, Object> filter)
			throws HttbException {
		boolean boo = false;
		try {
			Insert insert = QueryBuilder.insertInto("httb", "HTERROR");
			insert.value("qhid", CommonUtil.getUUID()); // 错题主键

			if (CommonUtil.isNotNull(filter.get("qhuid"))) {
				insert.value("qhuid", filter.get("qhuid")); // 用户id
			}
			if (CommonUtil.isNotNull(filter.get("qhqid"))) {
				insert.value("qhqid", filter.get("qhqid")); // 试题id
			}
			if (CommonUtil.isNotNull(filter.get("qhqans"))) {
				insert.value("qhqans", filter.get("qhqans")); // 试题答案
			}
			if (CommonUtil.isNotNull(filter.get("qhuans"))) {
				insert.value("qhuans", filter.get("qhuans")); // 用户答案
			}
			if (CommonUtil.isNotNull(filter.get("qhtype"))) {// 类型
				insert.value("qhtype", filter.get("qhtype"));
			}
			if (CommonUtil.isNotNull(filter.get("qpoint"))) {// 知识点
				insert.value("qpoint", filter.get("qpoint"));
			}
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			String today = fmt.format(new Date()).toString();
			insert.value("qrecorddate", Integer.parseInt(today));

			noSqlBaseDao.executeSql(insert);
			boo = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT,
					this.getClass() + "插入错题对象时发生异常", e);
		}

		return boo;
	}

	public boolean deleteWrongQuestion(String userid, List<String> qids)
			throws HttbException {
		boolean boo = false;
		try {
			Object[] obj = new Object[qids.size()];
			for (int i = 0; i < qids.size(); i++) {
				obj[i] = qids.get(i);
			}
			Delete delete = QueryBuilder.delete().from("httb", "HTERROR");
			delete.where(QueryBuilder.eq("qhuid", userid));
			delete.where(QueryBuilder.in("qhqid", obj));
			noSqlBaseDao.executeSql(delete);
			boo = true;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE,
					this.getClass() + "删除错题对象时发生异常", e);
		}
		return boo;
	}

	public Map<String,Object> getWrongQuestionMapByUserId(String userno)
			throws HttbException {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Select select = QueryBuilder.select().all().from("httb", "HTERROR");
			if (CommonUtil.isNotNull(userno)) {
				select.where(QueryBuilder.eq("qhuid", userno));
			}
			select.allowFiltering();
			// String select =
			// " select qhqid from httb.hterror where qhuid = '2' allow filtering;";
			// ResultSet result = noSqlBaseDao.getResultSet(select);
			ResultSet result = noSqlBaseDao.executeQuery(select);
			for (Row row : result) {
				map.put(row.getString("qhqid"),row.getString("qhuans"));
			}
			return map;
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH,
					this.getClass() + "查询错题对象(包含用户错误选项)时发生异常", e);
		}
	}
	/****************************** end **************************************/
}
