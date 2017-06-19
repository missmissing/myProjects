/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.dao
 * 文件名：				HtansweranalysisDao.java
 * 日期：					2015年6月22日-下午2:33:09
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.huatu.analysis.model.Htansweranalysis;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;

/**
 * 类名称：				HtansweranalysisDao
 * 类描述：  				用户答题统计分析
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午2:33:09
 * @version 			0.0.1
 */
@Component
public class HtansweranalysisDao {

	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	/**
	 *
	 * save							(单个保存答题记录)
	 * @param 		ansAnalysis		用户答题记录
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htansweranalysis ansAnalysis) throws HttbException{
		boolean flag = true;
		try {
			//保存答题记录对象
			Insert insert = QueryBuilder.insertInto("httb", "HTANSWERANALYSIS");
			insert.value("auid",ansAnalysis.getAuid());
			insert.value("aquesamount",ansAnalysis.getAquesamount());
			insert.value("qcorrectamount",ansAnalysis.getQcorrectamount());
			insert.value("qrecorddate",ansAnalysis.getQrecorddate());
			noSqlBaseDao.executeSql(insert);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入用户答题记录时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * savebatch					(批量保存答题记录)
	 * @param 		ansAnalysis		用户答题记录
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htansweranalysis> ansAnalysis) throws HttbException{
		//是否成功标记
		boolean flag = true;
		try {
			Insert[] inserts = new Insert[ansAnalysis.size()];
			for (int j = 0; j < ansAnalysis.size(); j++) {
				Insert insert = QueryBuilder.insertInto("httb", "HTANSWERANALYSIS");
				insert.value("auid",ansAnalysis.get(j).getAuid());
				insert.value("aquesamount",ansAnalysis.get(j).getAquesamount());
				insert.value("qcorrectamount",ansAnalysis.get(j).getQcorrectamount());
				insert.value("qrecorddate",ansAnalysis.get(j).getQrecorddate());
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
}
