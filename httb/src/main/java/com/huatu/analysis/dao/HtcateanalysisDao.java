/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.dao
 * 文件名：				Htcateanalysis.java
 * 日期：					2015年6月22日-下午2:57:44
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.huatu.analysis.model.Htcateanalysis;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;

/**
 * 类名称：				Htcateanalysis
 * 类描述：  				知识分类统计分析
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午2:57:44
 * @version 			0.0.1
 */
@Component
public class HtcateanalysisDao {
	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	/**
	 *
	 * save							(单个保存知识分类统计)
	 * @param 		htcateanalysis	知识分类统计分析
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htcateanalysis htcateanalysis) throws HttbException{
		boolean flag = true;
		try {
			//保存答题记录对象
			Insert insert = QueryBuilder.insertInto("httb", "HTCATEANALYSIS");
			insert.value("cid",htcateanalysis.getCid());
			insert.value("cquesamount",htcateanalysis.getCquesamount());
			insert.value("uid",htcateanalysis.getUid());
			insert.value("qrecorddate",htcateanalysis.getQrecorddate());
			noSqlBaseDao.executeSql(insert);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入知识分类统计时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * savebatch					(批量保存知识分类统计)
	 * @param 		htcateanalysis	知识分类统计分析
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htcateanalysis> htcateanalysis) throws HttbException{
		//是否成功标记
		boolean flag = true;
		try {
			Insert[] inserts = new Insert[htcateanalysis.size()];
			for (int j = 0; j < htcateanalysis.size(); j++) {
				Insert insert = QueryBuilder.insertInto("httb", "HTCATEANALYSIS");
				insert.value("cid",htcateanalysis.get(j).getCid());
				insert.value("cquesamount",htcateanalysis.get(j).getCquesamount());
				insert.value("uid",htcateanalysis.get(j).getUid());
				insert.value("qrecorddate",htcateanalysis.get(j).getQrecorddate());
				inserts[j] = insert;
			}
			//批量插入
			Batch batch = QueryBuilder.batch(inserts);
			noSqlBaseDao.executeSql(batch);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "批量插入知识分类统计时发生异常", e);
		}
		return flag;
	}
}
