/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.dao
 * 文件名：				HtanshisDao.java
 * 日期：				2015年6月22日-下午2:23:46
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.huatu.analysis.model.Htanshis;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;

/**
 * 类名称：				HtanshisDao
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月22日 下午2:23:46
 * @version 			0.0.1
 */
@Component
public class HtanshisDao {

	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	/**
	 *
	 * save							(单个保存答题记录)
	 * @param 		htanshis		答题记录对象
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 */
	public boolean save(Htanshis htanshis) throws HttbException{
		boolean flag = true;
		try {
			//保存答题记录对象
			Insert insert = QueryBuilder.insertInto("httb", "HTANSHIS");
			insert.value("auid",htanshis.getAuid());// 用户ID主键
			insert.value("qrecorddate",htanshis.getQrecorddate());
			noSqlBaseDao.executeSql(insert);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入答题用户记录时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * savebatch					(批量保存答题记录)
	 * @param 		htanshis		答题用户记录集合
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Htanshis> htanshis) throws HttbException{
		//是否成功标记
		boolean flag = true;
		try {
			Insert[] inserts = new Insert[htanshis.size()];
			for (int j = 0; j < htanshis.size(); j++) {
				Insert insert = QueryBuilder.insertInto("httb", "HTANSHIS");
				insert.value("auid",htanshis.get(j).getAuid());// 用户ID主键
				insert.value("qrecorddate",htanshis.get(j).getQrecorddate());
				inserts[j] = insert;
			}
			//批量插入
			Batch batch = QueryBuilder.batch(inserts);
			noSqlBaseDao.executeSql(batch);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "批量插入答题用户记录时发生异常", e);
		}
		return flag;
	}

}
