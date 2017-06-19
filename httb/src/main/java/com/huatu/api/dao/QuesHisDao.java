package com.huatu.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.huatu.api.vo.Queshis;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.CommonUtil;

@Component
public class QuesHisDao {
	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	public boolean insert(Queshis queshis) throws HttbException {
		boolean boo  = false;
		try{
			Insert insert = QueryBuilder.insertInto("testhttb", "HTQUESHIS");
			if(CommonUtil.isNotNull(queshis.getQhid())){
				insert.value("qhid",queshis.getQhid());
			}else{
				insert.value("qhid",CommonUtil.getUUID());
			}

			if(CommonUtil.isNotNull(queshis.getQhqid())){
				insert.value("qhqid", queshis.getQhqid());
			}

			if(CommonUtil.isNotNull(queshis.getQhuid())){
				insert.value("qhuid", queshis.getQhuid());
			}

			if(CommonUtil.isNotNull(queshis.getQhqans())){
				insert.value("qhqans", queshis.getQhuid());
			}

			if(CommonUtil.isNotNull(queshis.getQhuans())){
				insert.value("qhuans", queshis.getQhuans());
			}

			if(CommonUtil.isNotNull(queshis.getQhisright())){
				insert.value("", queshis.getQhisright());
			}

			if(CommonUtil.isNotNull(queshis.getQhtype())){
				insert.value("qhtype", queshis.getQhtype());
			}

			if(CommonUtil.isNotNull(queshis.getCreatetime())){
				insert.value("createtime", queshis.getCreatetime());
			}

			noSqlBaseDao.executeSql(insert);
			boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "保存试题对象时发生异常", e);
		}
		return boo;
	}


	public boolean insertBatch(List<Queshis> queshis) throws HttbException {
		boolean boo  = false;
		try{
			 Insert[] inserts = new Insert[queshis.size()];
			for (int i = 0; i < queshis.size(); i++) {

				Insert insert = QueryBuilder.insertInto("testhttb", "HTQUESHIS");
				if(CommonUtil.isNotNull(queshis.get(i).getQhid())){
					insert.value("qhid",queshis.get(i).getQhid());
				}else{
					insert.value("qhid",CommonUtil.getUUID());
				}

				if(CommonUtil.isNotNull(queshis.get(i).getQhqid())){
					insert.value("qhqid", queshis.get(i).getQhqid());
				}

				if(CommonUtil.isNotNull(queshis.get(i).getQhuid())){
					insert.value("qhuid", queshis.get(i).getQhuid());
				}

				if(CommonUtil.isNotNull(queshis.get(i).getQhqans())){
					insert.value("qhqans", queshis.get(i).getQhqans());
				}

				if(CommonUtil.isNotNull(queshis.get(i).getQhuans())){
					insert.value("qhuans", queshis.get(i).getQhuans());
				}

				if(CommonUtil.isNotNull(queshis.get(i).getQhisright())){
					insert.value("qhisright", queshis.get(i).getQhisright());
				}

				if(CommonUtil.isNotNull(queshis.get(i).getQhtype())){
					insert.value("qhtype", queshis.get(i).getQhtype());
				}

				if(CommonUtil.isNotNull(queshis.get(i).getCreatetime())){
					insert.value("createtime", queshis.get(i).getCreatetime());
				}
				inserts[i] = insert;
			}

			Batch batch = QueryBuilder.batch(inserts);
			noSqlBaseDao.executeSql(batch);
			boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "保存试题对象时发生异常", e);
		}
		return boo;
	}
}
