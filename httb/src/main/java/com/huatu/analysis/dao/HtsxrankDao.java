/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.dao
 * 文件名：				HtsxrankDao.java
 * 日期：				2015年6月23日-上午10:48:13
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.huatu.analysis.model.Htsxrank;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.CommonUtil;

/**
 * 类名称：				HtsxrankDao
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月23日 上午10:48:13
 * @version 			0.0.1
 */
@Component
public class HtsxrankDao {


	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	/**
	 *
	 * save							(单个保存顺序排名)
	 * @param 		htsxrank		顺序排名
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htsxrank htsxrank) throws HttbException{
		boolean flag = true;
		try {
			//保存答题记录对象
			Insert insert = QueryBuilder.insertInto("httb", "HTSXRANK");
			insert.value("sxuid",htsxrank.getSxuid());
			insert.value("sxcount",htsxrank.getSxcount());
			insert.value("sxpoint",htsxrank.getSxpoint());
			insert.value("qrecorddate",htsxrank.getQrecorddate());
			noSqlBaseDao.executeSql(insert);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入顺序排名时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * savebatch					(批量保存顺序排名)
	 * @param 		htsxrank		顺序排名
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htsxrank> htsxrank) throws HttbException{
		//是否成功标记
		boolean flag = true;
		try {
			Insert[] inserts = new Insert[htsxrank.size()];
			for (int j = 0; j < htsxrank.size(); j++) {
				Insert insert = QueryBuilder.insertInto("httb", "HTSXRANK");
				insert.value("sxuid",htsxrank.get(j).getSxuid());
				insert.value("sxcount",htsxrank.get(j).getSxcount());
				insert.value("sxpoint",htsxrank.get(j).getSxpoint());
				insert.value("qrecorddate",htsxrank.get(j).getQrecorddate());
				inserts[j] = insert;
			}
			//批量插入
			Batch batch = QueryBuilder.batch(inserts);
			noSqlBaseDao.executeSql(batch);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "批量插入顺序排名时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * getList						(获取顺序排名集合)
	 * 								(实时查询排名时调用)
	 * @return						排名集合
	 * @throws 		HttbException
	 */
	public List<Htsxrank> getList() throws HttbException{
		List<Htsxrank> list = new ArrayList<Htsxrank>();
		try {
			Select select = QueryBuilder.select().all().from("httb", "HTSXRANK");
			List<Row> result = noSqlBaseDao.executeSearchSql(select);
			for(Row row:result){
				Htsxrank htsxrank = new Htsxrank();
				htsxrank.setSxuid(row.getString("sxuid"));
				htsxrank.setSxcount(row.getInt("sxcount"));
				htsxrank.setSxpoint(row.getString("sxpoint"));
				htsxrank.setQrecorddate(row.getString("qrecorddate"));
				list.add(htsxrank);
			}
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "查询顺序排名时发生异常", e);
		}
		return list;
	}

	/**
	 *
	 * getListIN					(根据用户id集合获取顺序排名集合)
	 * @param 		uids			用户id集合
	 * @return
	 * @throws HttbException
	 */
	public List<Htsxrank> getListUidIN(List<String> uids) throws HttbException{
		List<Htsxrank> list = null;
		if(CommonUtil.isNotNull(uids)){
			//顺序答题集合
			list = new ArrayList<Htsxrank>();
			try {
				// 分页总长
				int pageSize = 1000;
				int total = (uids.size()%pageSize) ==0 ?(uids.size()/pageSize) : (uids.size()/pageSize+1) ; // 总页数
				for(int pape=0;pape<total;pape++){//pape 当前页
					int length = pape+1==total ? uids.size() : pageSize;
					String[] ids = new String[length];
					for(int i=0;i<length;i++){
						ids[i] = uids.get(pageSize*pape+i);
					}
					Select select = QueryBuilder.select().all().from("httb", "HTSXRANK");
					//批量in查询
					select.where(QueryBuilder.in("sxuid",ids));
					select.allowFiltering();
					List<Row> result = noSqlBaseDao.executeSearchSql(select);
					//遍历查询结果
					for (Row row : result) {
						Htsxrank htsxrank = new Htsxrank();
						htsxrank.setSxuid(row.getString("sxuid"));
						htsxrank.setSxcount(row.getInt("sxcount"));
						htsxrank.setSxpoint(row.getString("sxpoint"));
						htsxrank.setQrecorddate(row.getString("qrecorddate"));
						list.add(htsxrank);
					}
				}
			} catch (Exception e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "查询顺序排名时发生异常", e);
			}
		}
		return list;
	}

	/**
	 *
	 * batchDel						(批量删除顺序答题排序)
	 * @param	 	htsxranks		顺序答题集合
	 * @return
	 * @throws 		HttbException
	 */
	public boolean batchDel() throws HttbException{
		boolean flag = true;
		StringBuffer pageSql = new StringBuffer("truncate httb.HTSXRANK ");
		try{
			noSqlBaseDao.getResultSet(pageSql.toString());
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "删除试题对象时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * getByUid						(根据用户id获取顺序排名)
	 * 								(实时查询单个用户排名时调用)
	 * @param 		uid				用户id
	 * @return
	 * @throws 		HttbException
	 */
	public List<Htsxrank> getByUid(String uid) throws HttbException{
		List<Htsxrank> htsxranks = new ArrayList<Htsxrank>();
		try {
			Select select = QueryBuilder.select().all().from("httb", "HTSXRANK");
			select.where(QueryBuilder.eq("sxuid",uid));
			select.allowFiltering();
			ResultSet result = noSqlBaseDao.executeQuery(select);
			Row row = null;
			List<Row> rows = result.all();
			if(result!=null&&rows!=null&&rows.size()>0){
				row = rows.get(0);
			}
			if(CommonUtil.isNotNull(row)){
				Htsxrank htsxrank = new Htsxrank();
				htsxrank.setSxuid(row.getString("sxuid"));
				htsxrank.setSxcount(row.getInt("sxcount"));
				htsxrank.setSxpoint(row.getString("sxpoint"));
				htsxrank.setQrecorddate(row.getString("qrecorddate"));
				htsxranks.add(htsxrank);
			}
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "根据用户id查询顺序排名时发生异常", e);
		}
		return htsxranks;
	}

	/**
	 *
	 * getByUPID					(根据用户id知识点id获取顺序排名)
	 * 								(实时查询单个用户某个知识点排名时调用)
	 * @param 		uid				用户id
	 * @param		pid				知识点id
	 * @return
	 * @throws 		HttbException
	 */
	public Htsxrank getByUPID(String uid, String pid) throws HttbException{
		Htsxrank htsxrank = new Htsxrank();
		try {
			Select select = QueryBuilder.select().all().from("httb", "HTSXRANK");
			select.where(QueryBuilder.eq("sxuid",uid));
			select.where(QueryBuilder.eq("sxpoint",pid));
			select.allowFiltering();
			ResultSet result = noSqlBaseDao.executeQuery(select);
			for (Row row : result) {
				htsxrank.setSxuid(row.getString("sxuid"));
				htsxrank.setSxcount(row.getInt("sxcount"));
				htsxrank.setSxpoint(row.getString("sxpoint"));
				htsxrank.setQrecorddate(row.getString("qrecorddate"));
			}
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "根据用户id查询顺序排名时发生异常", e);
		}
		return htsxrank;
	}
}
