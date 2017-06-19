/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.dao
 * 文件名：				Httest.java
 * 日期：				2015年6月22日-下午9:23:29
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.collect.ImmutableList;
import com.huatu.analysis.model.Answerrecord;
import com.huatu.analysis.model.Httest;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.CommonUtil;

/**
 * 类名称：				Httest
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月22日 下午9:23:29
 * @version 			0.0.1
 */
@Component
public class HttestDao {

	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	/**
	 *
	 * save							(单个保存测试记录)
	 * @param 		httest	测试记录对象
	 * @return						是否保存成功
	 * @throws 		HttbException   异常对象
	 *

	 */
	public boolean save(Httest httest) throws HttbException{
		boolean flag = true;
		try {
			//保存答题记录对象
			Insert insert = QueryBuilder.insertInto("httb", "HTTEST");
			//测试id
			if(CommonUtil.isNotNull(httest.getTid())){
				insert.value("tid",httest.getTid());
			}else{
				insert.value("tid",CommonUtil.getUUID());
			}
			//测试名称
			if(CommonUtil.isNotEmpty(httest.getTname()))
				insert.value("tname",httest.getTname());
			//用户id
			if(CommonUtil.isNotEmpty(httest.getTuid()))
				insert.value("tuid",httest.getTuid());

			//保存测试记录集合
			if(CommonUtil.isNotNull(httest.getTresults())){
				List<UDTValue> udtList = new ArrayList<UDTValue>();
				UserType childsUDT = noSqlBaseDao.getNoSqlSession().getCluster().getMetadata().getKeyspace("httb").getUserType("type_ansrecord");
				for(Answerrecord ac : httest.getTresults()){
					UDTValue cqt = childsUDT.newValue();
					cqt.setString("qid", ac.getQid());
					cqt.setString("qans", ac.getQans());
					cqt.setString("uans", ac.getUans());
					cqt.setString("isright", ac.getIsright());
					cqt.setDate("atime", ac.getAtime());
					udtList.add(cqt);
				}
				insert.value("tresults",ImmutableList.copyOf(udtList));
			}
			//测试类别(2=>顺序,3=>模块)
			if(CommonUtil.isNotNull(httest.getTtype()))
				insert.value("ttype",httest.getTtype());
			//时间戳
			if(CommonUtil.isNotNull(httest.getQrecorddate()))
				insert.value("qrecorddate",httest.getQrecorddate());
			//创建时间
			if(CommonUtil.isNotNull(httest.getCreatetime()))
				insert.value("createtime",httest.getCreatetime());
			else
				insert.value("createtime",new Date());
			noSqlBaseDao.executeSql(insert);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入测试记录时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * savebatch					(批量保存测试记录)
	 * @param 		httest			测试记录集合
	 * @return						是否保存成功
	 * @throws 		HttbException	异常对象
	 */
	public boolean savebatch(List<Httest> httest) throws HttbException{
		//是否成功标记
		boolean flag = true;
		try {
			Insert[] inserts = new Insert[httest.size()];
			for (int j = 0; j < httest.size(); j++) {
				Insert insert = QueryBuilder.insertInto("httb", "HTQUESHIS");
				//测试id
				if(CommonUtil.isNotNull(httest.get(j).getTid())){
					insert.value("tid",httest.get(j).getTid());
				}else{
					insert.value("tid",CommonUtil.getUUID());
				}
				//测试名称
				if(CommonUtil.isNotEmpty(httest.get(j).getTname()))
					insert.value("tname",httest.get(j).getTname());
				//用户id
				if(CommonUtil.isNotEmpty(httest.get(j).getTuid()))
					insert.value("tuid",httest.get(j).getTuid());

				//保存测试记录集合
				if(CommonUtil.isNotNull(httest.get(j).getTresults())){
					List<UDTValue> udtList = new ArrayList<UDTValue>();
					UserType childsUDT = noSqlBaseDao.getNoSqlSession().getCluster().getMetadata().getKeyspace("httb").getUserType("type_ansrecord");
					for(Answerrecord ac : httest.get(j).getTresults()){
						UDTValue cqt = childsUDT.newValue();
						cqt.setString("qid", ac.getQid());
						cqt.setString("qans", ac.getQans());
						cqt.setString("uans", ac.getUans());
						cqt.setString("isright", ac.getIsright());
						cqt.setDate("atime", ac.getAtime());
						udtList.add(cqt);
					}
					insert.value("tresults",ImmutableList.copyOf(udtList));
				}
				//测试类别(2=>顺序,3=>模块)
				if(CommonUtil.isNotNull(httest.get(j).getTtype()))
					insert.value("ttype",httest.get(j).getTtype());
				//时间戳
				if(CommonUtil.isNotNull(httest.get(j).getQrecorddate()))
					insert.value("qrecorddate",httest.get(j).getQrecorddate());
				//创建时间
				if(CommonUtil.isNotNull(httest.get(j).getCreatetime()))
					insert.value("createtime",httest.get(j).getCreatetime());
				else
					insert.value("createtime",new Date());
				inserts[j] = insert;
			}
			//批量插入
			Batch batch = QueryBuilder.batch(inserts);
			noSqlBaseDao.executeSql(batch);
		} catch (Exception e) {
			flag = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "批量插入测试记录时发生异常", e);
		}
		return flag;
	}

}
