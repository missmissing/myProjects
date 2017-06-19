/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.dao
 * 文件名：				HtexamresultDao.java
 * 日期：				2015年6月22日-下午5:09:08
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.huatu.analysis.model.Answerrecord;
import com.huatu.analysis.model.Htexamresult;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.CommonUtil;

/**
 * 类名称：				HtexamresultDao
 * 类描述：				保存试卷考试结果
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午5:09:08
 * @version 			0.0.1
 */
@Component
public class HtexamresultDao {
	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	/**
	 *
	 * save							(单个保存答题记录)
	 * @param 		htexamresult	答题记录对象
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htexamresult htexamresult) throws HttbException{
		boolean flag = true;
		try {
			//保存答题记录对象
			Insert insert = QueryBuilder.insertInto("httb", "HTEXAMRESULT");
			//试卷结果id
			if(CommonUtil.isNotNull(htexamresult.getRid())){
				insert.value("rid",htexamresult.getRid());
			}else{
				insert.value("rid",CommonUtil.getUUID());
			}
			//用户id
			if(CommonUtil.isNotEmpty(htexamresult.getRuid()))
				insert.value("ruid",htexamresult.getRuid());
			//试卷id
			if(CommonUtil.isNotEmpty(htexamresult.getReid()))
				insert.value("reid",htexamresult.getReid());
			//试卷名称
			if(CommonUtil.isNotEmpty(htexamresult.getRname()))
				insert.value("rname",htexamresult.getRname());
			//试卷类型 (0=>真题,1=>模拟)
			if(CommonUtil.isNotEmpty(htexamresult.getRexamtype()))
				insert.value("rexamtype",htexamresult.getRexamtype());
			//考试结果(分数)
			if(CommonUtil.isNotNull(htexamresult.getRexamresult()))
				insert.value("rexamresult",htexamresult.getRexamresult());
			//考试耗时(单位/秒)
			if(CommonUtil.isNotNull(htexamresult.getRexamconsume()))
				insert.value("rexamconsume",htexamresult.getRexamconsume());
			//保存试卷答题记录集合
			if(CommonUtil.isNotNull(htexamresult.getRresults())){
				List<UDTValue> udtList = new ArrayList<UDTValue>();
				UserType childsUDT = noSqlBaseDao.getNoSqlSession().getCluster().getMetadata().getKeyspace("httb").getUserType("type_ansrecord");
				for(Answerrecord ac : htexamresult.getRresults()){
					UDTValue cqt = childsUDT.newValue();
					cqt.setString("qid", ac.getQid());
					cqt.setString("qans", ac.getQans());
					cqt.setString("uans", ac.getUans());
					cqt.setString("isright", ac.getIsright());
					cqt.setDate("atime", ac.getAtime());
					udtList.add(cqt);
				}
				insert.value("rresults",ImmutableList.copyOf(udtList));
			}
			//考试状态(0->答题中；1->答题完成)
			if(CommonUtil.isNotNull(htexamresult.getRstatus()))
				insert.value("rstatus",htexamresult.getRstatus());
			//时间戳
			if(CommonUtil.isNotNull(htexamresult.getQrecorddate()))
				insert.value("qrecorddate",htexamresult.getQrecorddate());
			//创建时间
			if(CommonUtil.isNotNull(htexamresult.getCreatetime()))
				insert.value("createtime",htexamresult.getCreatetime());
			else
				insert.value("createtime",new Date());

			noSqlBaseDao.executeSql(insert);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入答题记录时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * savebatch					(批量保存答题记录)
	 * @param 		htexamresult	答题记录集合
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htexamresult> htexamresult) throws HttbException{
		//是否成功标记
		boolean flag = true;
		try {
			Insert[] inserts = new Insert[htexamresult.size()];
			for (int j = 0; j < htexamresult.size(); j++) {
				Insert insert = QueryBuilder.insertInto("httb", "HTQUESHIS");
				//试卷结果id
				if(CommonUtil.isNotNull(htexamresult.get(j).getRid())){
					insert.value("rid",htexamresult.get(j).getRid());
				}else{
					insert.value("rid",CommonUtil.getUUID());
				}
				//用户id
				if(CommonUtil.isNotEmpty(htexamresult.get(j).getRuid()))
					insert.value("ruid",htexamresult.get(j).getRuid());
				//试卷id
				if(CommonUtil.isNotEmpty(htexamresult.get(j).getReid()))
					insert.value("reid",htexamresult.get(j).getReid());
				//试卷名称
				if(CommonUtil.isNotEmpty(htexamresult.get(j).getRname()))
					insert.value("rname",htexamresult.get(j).getRname());
				//试卷类型 (0=>真题,1=>模拟)
				if(CommonUtil.isNotEmpty(htexamresult.get(j).getRexamtype()))
					insert.value("rexamtype",htexamresult.get(j).getRexamtype());
				//考试结果(分数)
				if(CommonUtil.isNotNull(htexamresult.get(j).getRexamresult()))
					insert.value("rexamresult",htexamresult.get(j).getRexamresult());
				//考试耗时(单位/秒)
				if(CommonUtil.isNotNull(htexamresult.get(j).getRexamconsume()))
					insert.value("rexamconsume",htexamresult.get(j).getRexamconsume());
				//保存试卷答题记录集合
				if(CommonUtil.isNotNull(htexamresult.get(j).getRresults())){
					List<UDTValue> udtList = new ArrayList<UDTValue>();
					UserType childsUDT = noSqlBaseDao.getNoSqlSession().getCluster().getMetadata().getKeyspace("httb").getUserType("type_ansrecord");
					for(Answerrecord ac : htexamresult.get(j).getRresults()){
						UDTValue cqt = childsUDT.newValue();
						cqt.setString("qid", ac.getQid());
						cqt.setString("qans", ac.getQans());
						cqt.setString("uans", ac.getUans());
						cqt.setString("isright", ac.getIsright());
						cqt.setDate("atime", ac.getAtime());
						udtList.add(cqt);
					}
					insert.value("rresults",ImmutableList.copyOf(udtList));
				}
				//考试状态(0->答题中；1->答题完成)
				if(CommonUtil.isNotNull(htexamresult.get(j).getRstatus()))
					insert.value("rstatus",htexamresult.get(j).getRstatus());
				//时间戳
				if(CommonUtil.isNotNull(htexamresult.get(j).getQrecorddate()))
					insert.value("qrecorddate",htexamresult.get(j).getQrecorddate());
				//创建时间
				if(CommonUtil.isNotNull(htexamresult.get(j).getCreatetime()))
					insert.value("createtime",htexamresult.get(j).getCreatetime());
				else
					insert.value("createtime",new Date());
				inserts[j] = insert;
			}
			//批量插入
			Batch batch = QueryBuilder.batch(inserts);
			noSqlBaseDao.executeSql(batch);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "批量插入答题记录时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * getlist						(根据条件查找试卷结果记录)
	 * @param 		filter			过滤条件
	 * @return
	 * @throws 		HttbException
	 */
	public List<Htexamresult> getlist(Map<String,Object> filter) throws HttbException{
		//考试集合
		List<Htexamresult> examList = new ArrayList<Htexamresult>();
		try {
			Select select = QueryBuilder.select().all().from("httb", "HTEXAMRESULT");
			//用户id
			if(CommonUtil.isNotNull(filter.get("ruid"))){
				select.where(QueryBuilder.eq("ruid",filter.get("ruid")));
			}

			//试卷id
			if(CommonUtil.isNotNull(filter.get("reid"))){
				select.where(QueryBuilder.eq("reid",filter.get("reid")));
			}

			//考试类型(0=>真题,1=>模拟)
			if(CommonUtil.isNotNull(filter.get("rexamtype"))){
				select.where(QueryBuilder.eq("rexamtype",filter.get("rexamtype")));
			}

			//试题类型(0=>真题,1=>模拟)
			if(CommonUtil.isNotNull(filter.get("qrecorddate"))){
				select.where(QueryBuilder.gte("qrecorddate",filter.get("qrecorddate")));
			}

			select.allowFiltering();
			List<Row> result = noSqlBaseDao.executeSearchSql(select);
			//将ResultSet转成试题集合
			for (Row row : result) {
				Htexamresult htexamresult = new Htexamresult();
				htexamresult.setRid(row.getString("rid"));
				htexamresult.setRuid(row.getString("ruid"));
				htexamresult.setReid(row.getString("reid"));
				htexamresult.setRname(row.getString("rname"));
				htexamresult.setRexamtype(row.getString("rexamtype"));
				htexamresult.setRexamresult(row.getFloat("rexamresult"));
				htexamresult.setRexamconsume(row.getInt("rexamconsume"));
				@SuppressWarnings("serial")
				List<Answerrecord>  alist = new Gson().fromJson(row.getList("rresults",UDTValue.class).toString(), new TypeToken<List<Answerrecord>>() {}.getType());
				htexamresult.setRresults(alist);
				htexamresult.setRstatus(row.getString("rstatus"));
				htexamresult.setQrecorddate(row.getString("qrecorddate"));
				htexamresult.setCreatetime(row.getDate("createtime"));
				examList.add(htexamresult);
			}
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询考试记录时发生异常", e);
		}

		return examList;
	}
}
