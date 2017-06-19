/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.dao
 * 文件名：				HtqueshisDao.java
 * 日期：				2015年6月21日-下午2:25:13
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
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.util.AnaCommonUtil;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.CommonUtil;

/**
 * 类名称：				HtqueshisDao
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月21日 下午2:25:13
 * @version 			0.0.1
 */
@Component
public class HtqueshisDao {
	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	/**
	 *
	 * save							(单个保存答题记录)
	 * @param 		htqueshis		答题记录对象
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htqueshis htqueshis) throws HttbException{
		boolean flag = true;
		try {
			//保存答题记录对象
			Insert insert = QueryBuilder.insertInto("httb", "HTQUESHIS");
			insert.value("qhid",htqueshis.getQhid());// 试题主键
			insert.value("qhqid",htqueshis.getQhqid());
			insert.value("qhuid",htqueshis.getQhuid());
			insert.value("qhqans",htqueshis.getQhqans());
			insert.value("qhuans",htqueshis.getQhuans());
			insert.value("qhisright",htqueshis.getQhisright());
			insert.value("qhtype",htqueshis.getQhtype());
			insert.value("qhpoint",htqueshis.getQhpoint());

			//时间戳
			if(CommonUtil.isNotEmpty(htqueshis.getQrecorddate()))
				insert.value("qrecorddate",htqueshis.getQrecorddate());
			else
				insert.value("qrecorddate",AnaCommonUtil.getRecordDate());

			//时间戳
			if(CommonUtil.isNotNull(htqueshis.getCreatetime()))
				insert.value("qrecorddate",htqueshis.getCreatetime());
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
	 * @param 		htqueshis		答题记录集合
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htqueshis> htqueshis) throws HttbException{
		//是否成功标记
		boolean flag = true;
		try {
			Insert[] inserts = new Insert[htqueshis.size()];
			for (int j = 0; j < htqueshis.size(); j++) {
				Insert insert = QueryBuilder.insertInto("httb", "HTQUESHIS");
				insert.value("qhid",htqueshis.get(j).getQhid());// 试题主键
				insert.value("qhqid",htqueshis.get(j).getQhqid());
				insert.value("qhuid",htqueshis.get(j).getQhuid());
				insert.value("qhqans",htqueshis.get(j).getQhqans());
				insert.value("qhuans",htqueshis.get(j).getQhuans());
				insert.value("qhisright",htqueshis.get(j).getQhisright());
				insert.value("qhtype",htqueshis.get(j).getQhtype());
				insert.value("qhpoint",htqueshis.get(j).getQhpoint());
				//时间戳
				if(CommonUtil.isNotEmpty(htqueshis.get(j).getQrecorddate()))
					insert.value("qrecorddate",htqueshis.get(j).getQrecorddate());
				else
					insert.value("qrecorddate",AnaCommonUtil.getRecordDate());

				//时间戳
				if(CommonUtil.isNotNull(htqueshis.get(j).getCreatetime()))
					insert.value("createtime",htqueshis.get(j).getCreatetime());
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
	 * getlist						(根据条件查找答题记录)
	 * @param 		filter			过滤条件
	 * @return
	 * @throws 		HttbException
	 */
	public List<Htqueshis> getlist(Map<String,Object> filter, long count) throws HttbException{
		List<Htqueshis> list = new ArrayList<Htqueshis>();
		try {
			String qhid = null;
			//每页差多少条
			int pageSize = 10000;
			int total = (int) ((count%pageSize) ==0 ?(count/pageSize) : (count/pageSize+1)) ; // 总页数
			for(int pape=0;pape<total;pape++){
				StringBuffer pageSql = new StringBuffer("SELECT * FROM httb.HTQUESHIS ");
				if(filter != null && filter.size() > 0){
					pageSql.append(" where ");
					if(qhid != null){
						pageSql.append("  token(qhid) > token('"+qhid+"')");
					}
					//用户id
					if(CommonUtil.isNotNull(filter.get("qhuid"))){
						pageSql.append(" qhuid = '"+filter.get("qhuid")+"' and ");
					}

					//试题id
					if(CommonUtil.isNotNull(filter.get("qhqid"))){
						pageSql.append(" qhqid = '"+filter.get("qhqid")+"' and ");
					}

					//是否正确(0=>正确,1=>不正确)
					if(CommonUtil.isNotNull(filter.get("qhisright"))){
						pageSql.append(" qhisright = "+filter.get("qhisright")+" and ");
					}

					//试题类型(0=>真题,1=>模拟,2=>顺序,3=>模块)
					if(CommonUtil.isNotNull(filter.get("qhtype"))){
						pageSql.append(" qhtype = "+filter.get("qhtype")+" and ");
					}

					//知识点id
					if(CommonUtil.isNotNull(filter.get("qhpoint"))){
						pageSql.append(" qhpoint contains '"+filter.get("qhpoint")+"' and ");
					}

					//知识点id
					if(CommonUtil.isNotNull(filter.get("qrecorddate"))){
						pageSql.append(" qrecorddate >= '"+filter.get("qrecorddate")+"' and ");
					}
				}else{
					if(qhid != null){
						pageSql.append(" where  token(qhid) > token('"+qhid+"')");
					}
				}
				pageSql.append("limit "+pageSize+" allow filtering;");
				//执行sql
				List<Row> result = noSqlBaseDao.getAllRows(AnaCommonUtil.formatSQLStr(pageSql.toString()));
				//将ResultSet转成试题集合
				for (Row row : result) {
					Htqueshis htqueshis = new Htqueshis();
					htqueshis.setQhid(row.getString("qhid"));
					htqueshis.setQhuid(row.getString("qhuid"));
					htqueshis.setQhqid(row.getString("qhqid"));
					htqueshis.setQhqans(row.getString("qhqans"));
					htqueshis.setQhuans(row.getString("qhuans"));
					htqueshis.setQhisright(row.getInt("qhisright"));
					htqueshis.setQhtype(row.getInt("qhtype"));
					htqueshis.setQhpoint(row.getList("qhpoint", String.class));
					htqueshis.setQrecorddate(row.getString("qrecorddate"));
					htqueshis.setCreatetime(row.getDate("createtime"));
					list.add(htqueshis);
				}
				if(list.size() > 0){
					qhid = list.get(list.size()-1).getQhid();
				}

			}

		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询答题记录时发生异常", e);
		}
		return list;
	}

	/**
	 *
	 * getCount						(根据条件查找答题记录长度)
	 * @param 		filter			过滤条件
	 * @return
	 * @throws 		HttbException
	 */
	public long getCount(Map<String,Object> filter) throws HttbException{
		long count = 0;
		try {
			StringBuffer pageSql = new StringBuffer("SELECT count(1) FROM httb.HTQUESHIS ");
			if(filter != null && filter.size() > 0){
				pageSql.append(" where ");
			}
			//用户id
			if(CommonUtil.isNotNull(filter.get("qhuid"))){
				pageSql.append(" qhuid = '"+filter.get("qhuid")+"' and ");
			}

			//试题id
			if(CommonUtil.isNotNull(filter.get("qhqid"))){
				pageSql.append(" qhqid = '"+filter.get("qhqid")+"' and ");
			}

			//是否正确(0=>正确,1=>不正确)
			if(CommonUtil.isNotNull(filter.get("qhisright"))){
				pageSql.append(" qhisright = "+filter.get("qhisright")+" and ");
			}

			//试题类型(0=>真题,1=>模拟,2=>顺序,3=>模块)
			if(CommonUtil.isNotNull(filter.get("qhtype"))){
				pageSql.append(" qhtype = "+filter.get("qhtype")+" and ");
			}

			//知识点id
			if(CommonUtil.isNotNull(filter.get("qhpoint"))){
				pageSql.append(" qhpoint contains '"+filter.get("qhpoint")+"' and ");
			}

			//知识点id
			if(CommonUtil.isNotNull(filter.get("qrecorddate"))){
				pageSql.append(" qrecorddate >= '"+filter.get("qrecorddate")+"' and ");
			}

			pageSql.append("  allow filtering;");
			//执行sql
			ResultSet result = noSqlBaseDao.getResultSet(AnaCommonUtil.formatSQLStr(pageSql.toString()));
			//将ResultSet转成试题集合
			for (Row row : result) {
				count = row.getLong("count");
			}
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询答题记录长度时发生异常", e);
		}
		return count;
	}
}
