/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.dao
 * 文件名：				HtexamanalysisDao.java
 * 日期：				2015年6月22日-下午3:16:08
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
import com.huatu.analysis.model.Htexamanalysis;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.CommonUtil;

/**
 * 类名称：				HtexamanalysisDao
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月22日 下午3:16:08
 * @version 			0.0.1
 */
@Component
public class HtexamanalysisDao {
	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	/**
	 *
	 * save							(单个保存考试结果)
	 * @param 		htexamanalysis	考试结果
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htexamanalysis htexamanalysis) throws HttbException{
		boolean flag = true;
		try {
			Insert insert = QueryBuilder.insertInto("httb", "HTEXAMANALYSIS");
			insert.value("eid",htexamanalysis.getEid());
			insert.value("eaveragescore",htexamanalysis.getEaveragescore());
			insert.value("eansamount",htexamanalysis.getEansamount());
			insert.value("eaveragetime",htexamanalysis.getEaveragetime());
			insert.value("qrecorddate",htexamanalysis.getQrecorddate());
			noSqlBaseDao.executeSql(insert);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入考试结果时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * savebatch					(批量保存答题记录)
	 * @param 		htexamanalysis	考试结果
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htexamanalysis> htexamanalysis) throws HttbException{
		//是否成功标记
		boolean flag = true;
		try {
			Insert[] inserts = new Insert[htexamanalysis.size()];
			for (int j = 0; j < htexamanalysis.size(); j++) {
				Insert insert = QueryBuilder.insertInto("httb", "HTEXAMANALYSIS");
				insert.value("eid",htexamanalysis.get(j).getEid());
				insert.value("eaveragescore",htexamanalysis.get(j).getEaveragescore());
				insert.value("eansamount",htexamanalysis.get(j).getEansamount());
				insert.value("eaveragetime",htexamanalysis.get(j).getEaveragetime());
				insert.value("qrecorddate",htexamanalysis.get(j).getQrecorddate());
				inserts[j] = insert;
			}
			//批量插入
			Batch batch = QueryBuilder.batch(inserts);
			noSqlBaseDao.executeSql(batch);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "批量插入考试结果时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * getListByEids				(根据试卷id集合查询考试分析集合)
	 * @param 		eids			试卷id集合
	 * @return
	 * @throws	 	HttbException
	 */
	public List<Htexamanalysis> getListByEids(List<String> eids) throws HttbException{
		List<Htexamanalysis> list = null;
		if(CommonUtil.isNotNull(eids)){
			//顺序答题集合
			list = new ArrayList<Htexamanalysis>();
			try {
				// 分页总长
				int pageSize = 1000;
				int total = (eids.size()%pageSize) ==0 ?(eids.size()/pageSize) : (eids.size()/pageSize+1) ; // 总页数
				for(int pape=0;pape<total;pape++){//pape 当前页
					int length = pape+1==total ? (eids.size()%pageSize) : pageSize;
					String[] ids = new String[length];
					for(int i=0;i<length;i++){
						ids[i] = eids.get(pageSize*pape+i);
					}
					Select select = QueryBuilder.select().all().from("httb", "HTEXAMANALYSIS");
					//批量in查询
					select.where(QueryBuilder.in("eid",ids));
					select.allowFiltering();
					List<Row> result = noSqlBaseDao.executeSearchSql(select);
					//遍历查询结果
					for (Row row : result) {
						Htexamanalysis htexamanalysis = new Htexamanalysis();
						htexamanalysis.setEid(row.getString("eid"));
						htexamanalysis.setEansamount(row.getInt("eansamount"));
						htexamanalysis.setEaveragescore(row.getFloat("eaveragescore"));
						htexamanalysis.setQrecorddate(row.getString("qrecorddate"));
						list.add(htexamanalysis);
					}
				}
			} catch (Exception e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询考试结果分析集合时发生异常", e);
			}
		}
		return list;
	}

	/**
	 *
	 * getHtexamanalysis			(根据试卷id返回试卷分析记录)
	 * 								(提交试卷时调用)
	 * @param 		examId			试卷Id
	 * @return
	 * @throws HttbException
	 */
	public Htexamanalysis getHtexamanalysis(String examId) throws HttbException{
		Htexamanalysis htexamanalysis = null;
		try {
			Select select = QueryBuilder.select().all().from("httb", "HTEXAMANALYSIS");
			select.where(QueryBuilder.eq("eid", examId));
			select.allowFiltering();
			List<Row> result = noSqlBaseDao.executeSearchSql(select);
			//遍历查询结果
			for (Row row : result) {
				htexamanalysis = new Htexamanalysis();
				htexamanalysis.setEid(row.getString("eid"));
				htexamanalysis.setEansamount(row.getInt("eansamount"));
				htexamanalysis.setEaveragescore(row.getFloat("eaveragescore"));
				htexamanalysis.setQrecorddate(row.getString("qrecorddate"));
			}
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询考试结果分析时发生异常", e);
		}

		return htexamanalysis;
	}
}
