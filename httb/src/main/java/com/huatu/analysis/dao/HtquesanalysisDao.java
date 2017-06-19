/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.dao
 * 文件名：				HtquesanalysisDao.java
 * 日期：					2015年6月22日-下午3:27:40
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.huatu.analysis.model.Htquesanalysis;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.CommonUtil;

/**
 * 类名称：				HtquesanalysisDao
 * 类描述：				试题信息统计分析
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午3:27:40
 * @version 			0.0.1
 */
@Component
public class HtquesanalysisDao {
	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	/**
	 * save							(单个保存试题分析记录)
	 * @param 		htquesanalysis	试题分析记录
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htquesanalysis htquesanalysis) throws HttbException{
		boolean flag = true;
		try {
			//保存答题记录对象
			Insert insert = QueryBuilder.insertInto("httb", "HTQUESANALYSIS");
			insert.value("qid",htquesanalysis.getQid());
			insert.value("qqans",htquesanalysis.getQqans());
			insert.value("qansamount",htquesanalysis.getQansamount());
			insert.value("qcorrectamount",htquesanalysis.getQcorrectamount());
			insert.value("qchoicea",htquesanalysis.getQchoicea());
			insert.value("qchoiceb",htquesanalysis.getQchoiceb());
			insert.value("qchoicec",htquesanalysis.getQchoicec());
			insert.value("qchoiced",htquesanalysis.getQchoiced());
			insert.value("qchoicee",htquesanalysis.getQchoicee());
			insert.value("qchoicef",htquesanalysis.getQchoicef());
			insert.value("qchoiceg",htquesanalysis.getQchoiceg());
			insert.value("qchoiceh",htquesanalysis.getQchoiceh());
			insert.value("qrecorddate",htquesanalysis.getQrecorddate());
			insert.value("qmostwrong",htquesanalysis.getQmostwrong());
			insert.value("qsecendwrong",htquesanalysis.getQsecendwrong());
			noSqlBaseDao.executeSql(insert);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入试题分析记录时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * savebatch					(批量保存试题分析记录)
	 * @param 		htquesanalysis	试题分析记录
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htquesanalysis> htquesanalysis) throws HttbException{
		//是否成功标记
		boolean flag = true;
		try {
			Insert[] inserts = new Insert[htquesanalysis.size()];
			for (int j = 0; j < htquesanalysis.size(); j++) {
				Insert insert = QueryBuilder.insertInto("httb", "HTQUESANALYSIS");
				insert.value("qid",htquesanalysis.get(j).getQid());
				insert.value("qqans",htquesanalysis.get(j).getQqans());
				insert.value("qansamount",htquesanalysis.get(j).getQansamount());
				insert.value("qcorrectamount",htquesanalysis.get(j).getQcorrectamount());
				insert.value("qchoicea",htquesanalysis.get(j).getQchoicea());
				insert.value("qchoiceb",htquesanalysis.get(j).getQchoiceb());
				insert.value("qchoicec",htquesanalysis.get(j).getQchoicec());
				insert.value("qchoiced",htquesanalysis.get(j).getQchoiced());
				insert.value("qchoicee",htquesanalysis.get(j).getQchoicee());
				insert.value("qchoicef",htquesanalysis.get(j).getQchoicef());
				insert.value("qchoiceg",htquesanalysis.get(j).getQchoiceg());
				insert.value("qchoiceh",htquesanalysis.get(j).getQchoiceh());
				insert.value("qrecorddate",htquesanalysis.get(j).getQrecorddate());
				insert.value("qmostwrong",htquesanalysis.get(j).getQmostwrong());
				insert.value("qsecendwrong",htquesanalysis.get(j).getQsecendwrong());
				inserts[j] = insert;
			}
			//批量插入
			Batch batch = QueryBuilder.batch(inserts);
			noSqlBaseDao.executeSql(batch);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "批量插入试题分析记录时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * getListByQids				(根据试题id集合查询试题分析集合)
	 * @param 		qids			试题id集合
	 * @return
	 * @throws	 	HttbException
	 */
	public List<Htquesanalysis> getListByQids(List<String> qids) throws HttbException{
		List<Htquesanalysis> list = null;
		if(CommonUtil.isNotNull(qids)){
			//试题分析集合
			list = new ArrayList<Htquesanalysis>();
			try {
				// 分页总长
				int pageSize = 1000;
				int total = (qids.size()%pageSize) ==0 ?(qids.size()/pageSize) : (qids.size()/pageSize+1) ; // 总页数
				for(int pape=0;pape<total;pape++){//pape 当前页
					int length = pape+1==total ? (qids.size()%pageSize) : pageSize;
					Object[] ids = new String[length];
					for(int i=0;i<length;i++){
						ids[i] = qids.get(pageSize*pape+i);
					}
					Select select = QueryBuilder.select().all().from("httb", "HTQUESANALYSIS");
					//批量in查询
					select.where(QueryBuilder.in("qid",ids));
					select.allowFiltering();
					List<Row> result = noSqlBaseDao.executeSearchSql(select);
					//遍历查询结果
					for (Row row : result) {
						Htquesanalysis htquesanalysis = new Htquesanalysis();
						htquesanalysis.setQid(row.getString("qid"));
						htquesanalysis.setQqans(row.getString("qqans"));
						htquesanalysis.setQansamount(row.getInt("qansamount"));
						htquesanalysis.setQcorrectamount(row.getInt("qcorrectamount"));
						htquesanalysis.setQchoicea(row.getInt("qchoicea"));
						htquesanalysis.setQchoiceb(row.getInt("qchoiceb"));
						htquesanalysis.setQchoicec(row.getInt("qchoicec"));
						htquesanalysis.setQchoiced(row.getInt("qchoiced"));
						htquesanalysis.setQchoicee(row.getInt("qchoicee"));
						htquesanalysis.setQchoicef(row.getInt("qchoicef"));
						htquesanalysis.setQchoiceg(row.getInt("qchoiceg"));
						htquesanalysis.setQchoiceh(row.getInt("qchoiceh"));

						htquesanalysis.setQrecorddate(row.getString("qrecorddate"));
						htquesanalysis.setQmostwrong(row.getString("qmostwrong"));
						htquesanalysis.setQsecendwrong(row.getString("qsecendwrong"));
						list.add(htquesanalysis);
					}
				}
			} catch (Exception e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询试题分析集合时发生异常", e);
			}
		}
		return list;
	}

	/**
	 *
	 * getQuesByQid					(根据试题id查询试题分析)
	 * @param 		qid				试题id
	 * @return
	 * @throws	 	HttbException
	 */
	public Htquesanalysis getQuesByQid(String qid) throws HttbException{
		Htquesanalysis htquesanalysis = null;
		if(CommonUtil.isNotEmpty(qid)){
			try {
					Select select = QueryBuilder.select().all().from("httb", "HTQUESANALYSIS");
					//批量in查询
					select.where(QueryBuilder.eq("qid",qid));
					select.allowFiltering();
					List<Row> result = noSqlBaseDao.executeSearchSql(select);
					//遍历查询结果
					for (Row row : result) {
						htquesanalysis = new Htquesanalysis();
						htquesanalysis.setQid(row.getString("qid"));
						htquesanalysis.setQqans(row.getString("qqans"));
						htquesanalysis.setQansamount(row.getInt("qansamount"));
						htquesanalysis.setQcorrectamount(row.getInt("qcorrectamount"));
						htquesanalysis.setQchoicea(row.getInt("qchoicea"));
						htquesanalysis.setQchoiceb(row.getInt("qchoiceb"));
						htquesanalysis.setQchoicec(row.getInt("qchoicec"));
						htquesanalysis.setQchoiced(row.getInt("qchoiced"));
						htquesanalysis.setQchoicee(row.getInt("qchoicee"));
						htquesanalysis.setQchoicef(row.getInt("qchoicef"));
						htquesanalysis.setQchoiceg(row.getInt("qchoiceg"));
						htquesanalysis.setQchoiceh(row.getInt("qchoiceh"));

						htquesanalysis.setQrecorddate(row.getString("qrecorddate"));
						htquesanalysis.setQmostwrong(row.getString("qmostwrong"));
						htquesanalysis.setQsecendwrong(row.getString("qsecendwrong"));
					}
			} catch (Exception e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询试题分析时发生异常", e);
			}
		}
		return htquesanalysis;
	}
}
