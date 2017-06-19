/**
 *
 */
package com.huatu.tb.category.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Update;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Page;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.tb.category.dao.CategoryDao;
import com.huatu.tb.category.model.CateQues;
import com.huatu.tb.category.model.Category;

/**
 * @ClassName:   CategoryDaoImpl
 * @Description: 知识点分类DAO实现类
 * @author       Aijunbo
 * @date         2015年4月17日 下午4:34:12
 * @version      0.0.1
 *
 */
@Repository
public class CategoryDaoImpl implements CategoryDao {

	/** 批量插入试题-知识点关系*/
	private static final String batchInsertSql_cq = "INSERT INTO HTTB.HTCATE_QUES(CID,QID,ATTR,QCIDS) VALUES(?,?,?,?);";

	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	@Override
	public Category get(String id) throws HttbException {

		Category  category = new Category();
		Select select = QueryBuilder.select().all().from("httb", "HTCATEGORY");
		select.where(QueryBuilder.eq("cid",id));
		select.allowFiltering();
		ResultSet result = noSqlBaseDao.executeQuery(select);
		Row row = null;
		List<Row> rows = result.all();
		if(result!=null&&rows!=null&&rows.size()>0){
			row = rows.get(0);
		}
		if(CommonUtil.isNotNull(row)){
			category.setCid(row.getString("cid"));
			category.setCname(row.getString("cname"));
			category.setCpid(row.getString("cpid"));
			category.setCexplain(row.getString("cexplain"));
			category.setCordernum(row.getInt("cordernum"));
			category.setCreatetime(row.getDate("createtime"));
			category.setCreateuser(row.getString("createuser"));
			category.setUpdatetime(row.getDate("updatetime"));
			category.setUpdateuser(row.getString("updateuser"));
			category.setTombstone(row.getString("tombstone"));
		}
		return category;
	}
	@Override
	public boolean delete(String id) throws HttbException {
		boolean boo = false;
		try{
			Delete delete  = QueryBuilder.delete().from("httb", "HTCATEGORY");
			delete.where(QueryBuilder.eq("cid",id));
			noSqlBaseDao.executeSql(delete);
			boo= true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "删除章节对象时发生异常", e);
		}
		return boo;
	}
	@Override
	public boolean deleteBatch(List<String> ids) throws HttbException {
		boolean boo = false;
		try{
			Object[] obj = new Object[ids.size()];
			for(int i=0;i<ids.size();i++){
				obj[i] = ids.get(i);
			}
			Delete delete  = QueryBuilder.delete().from("httb", "HTCATEGORY");
			delete.where(QueryBuilder.in("cid",obj));
			noSqlBaseDao.executeSql(delete);
			boo= true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "删除章节对象时发生异常", e);
		}
		return boo;
	}
	@Override
	public boolean insert(Category category) throws HttbException {
		boolean boo  = false;
		try{
			Insert insert = QueryBuilder.insertInto("httb", "HTCATEGORY");
			if(CommonUtil.isNotNull(category.getCid())){
				insert.value("cid",category.getCid());
			}else{
				insert.value("cid",CommonUtil.getUUID());
			}
			if(CommonUtil.isNotNull(category.getCname())){
				insert.value("cname",category.getCname());
			}
			if(CommonUtil.isNotNull(category.getCpid())){
				insert.value("cpid",category.getCpid());
			}
			if(CommonUtil.isNotNull(category.getCexplain())){
				insert.value("cexplain",category.getCexplain());
			}
			if(CommonUtil.isNotNull(category.getCordernum())){
				insert.value("cordernum",category.getCordernum());
			}
			if(CommonUtil.isNotNull(category.getClevels())){
				insert.value("clevels",category.getClevels());
			}
			if(CommonUtil.isNotNull(category.getCreatetime())){
				insert.value("createtime",category.getCreatetime());
			}
			if(CommonUtil.isNotNull(category.getCreateuser())){
				insert.value("createuser",category.getCreateuser());
			}
			if(CommonUtil.isNotNull(category.getUpdatetime())){
				insert.value("updatetime",category.getUpdatetime());
			}
			if(CommonUtil.isNotNull(category.getUpdateuser())){
				insert.value("updateuser",category.getUpdateuser());
			}
			if(CommonUtil.isNotNull(category.getTombstone())){
				insert.value("tombstone",category.getTombstone());
			}
			noSqlBaseDao.executeSql(insert);
			boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入章节对象时发生异常", e);
		}
		return boo;
	}
	@Override
	public boolean update(Category category) throws HttbException {
		boolean boo = false;
		try{
			Update update = QueryBuilder.update("httb", "HTCATEGORY");
			if(CommonUtil.isNotNull(category.getCname())){
				update.with(QueryBuilder.set("cname", category.getCname()));
			}
			if(CommonUtil.isNotNull(category.getCpid())){
				update.with(QueryBuilder.set("cpid",category.getCpid()));
			}
			if(CommonUtil.isNotNull(category.getCexplain())){
				update.with(QueryBuilder.set("cexplain",category.getCexplain()));
			}
			if(CommonUtil.isNotNull(category.getCordernum())){
				update.with(QueryBuilder.set("cordernum",category.getCordernum()));
			}
			if(CommonUtil.isNotNull(category.getClevels())){
				update.with(QueryBuilder.set("clevels",category.getCordernum()));
			}
			if(CommonUtil.isNotNull(category.getCreatetime())){
				update.with(QueryBuilder.set("createtime",category.getCreatetime()));
			}
			if(CommonUtil.isNotNull(category.getCreateuser())){
				update.with(QueryBuilder.set("createuser",category.getCreateuser()));
			}
			if(CommonUtil.isNotNull(category.getUpdatetime())){
				update.with(QueryBuilder.set("updatetime",category.getUpdatetime()));
			}
			if(CommonUtil.isNotNull(category.getUpdateuser())){
				update.with(QueryBuilder.set("updateuser",category.getUpdateuser()));
			}
			if(CommonUtil.isNotNull(category.getTombstone())){
				update.with(QueryBuilder.set("tombstone",category.getTombstone()));
			}
			update.where(QueryBuilder.eq("cid",category.getCid()));
			noSqlBaseDao.executeSql(update);
			boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_UPDATE, this.getClass() + "修改章节对象时发生异常", e);
		}
		return boo;
	}

	@Override
	public List<Row> getCategoryResultSet(Map<String, Object> filter) throws HttbException {
		Select select = QueryBuilder.select().all().from("httb", "HTCATEGORY");
		//select.where(QueryBuilder.eq("tombstone",Constants.TOMBSTONE_DeleteFlag_NO));
		if(CommonUtil.isNotNull(filter)){
			if(filter.containsKey("cids") && CommonUtil.isNotNull(filter.get("cids"))){
				@SuppressWarnings("unchecked")
				List<String> cidList = (List<String>) filter.get("cids");
				Object[] obj = new Object[cidList.size()];
				for(int i=0;i<cidList.size();i++){
					obj[i] = cidList.get(i);
				}
				select.where(QueryBuilder.in("cid",obj));
			}

			if(filter.containsKey("clevels")){
				select.where(QueryBuilder.eq("clevels", filter.get("clevels")));
			}
		}
		select.allowFiltering();
		List<Row> rowlist = noSqlBaseDao.executeSearchSql(select);
		return rowlist;
	}
	@Override
	public ResultSet getCategoryResultSet(Page page, Map<String, Object> filter) throws HttbException {
		return null;
	}
	/**
	 *
	 * saveQuesCate					(保存试题与知识点关系)
	 * 								(创建试题时需要保存试题和知识点关系)
	 * @param 		params			试题ID-知识点ID
	 * @throws 		HttbException
	 */
	@Override
	public boolean saveCateQues(List<Object[]> params) throws HttbException {
		return noSqlBaseDao.batchExecuteSqlParams(batchInsertSql_cq, params);
	}
	@Override
	public boolean insertBatchToCateQues(List<CateQues> list) throws HttbException {
		boolean boo  = false;
		try{
		  Insert[] inserts = new Insert[list.size()];
		  int i =0;
		  for(CateQues cq : list ){
			  Insert insert =  QueryBuilder.insertInto("httb", "HTCATE_QUES");
				if(CommonUtil.isNotNull(cq.getCid())){
					insert.value("cid",cq.getCid());
				}
				if(CommonUtil.isNotNull(cq.getQid())){
					insert.value("qid", cq.getQid());// 年份
				}
				if(CommonUtil.isNotNull(cq.getAttr())){
					insert.value("attr",cq.getAttr());
				}
				if(CommonUtil.isNotNull(cq.getQcids())){
					insert.value("qcids",cq.getQcids());
				}
				inserts[i] = insert;
				i++;
		  }
		  Batch batch = QueryBuilder.batch(inserts);
		  noSqlBaseDao.executeSql(batch);
		  boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入试题对象时发生异常", e);
		}
		return boo;
	}
	@Override
	public boolean deleteCateQues(String cid, String qid) throws HttbException {
		boolean boo = false;
		try{
			Delete delete  = QueryBuilder.delete().from("httb", "HTCATE_QUES");
			delete.where(QueryBuilder.eq("cid",cid));
			if(CommonUtil.isNotNull(qid)){
				delete.where(QueryBuilder.eq("qid",qid));
			}
			noSqlBaseDao.executeSql(delete);
			boo= true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "删除知识点关系时发生异常", e);
		}
		return boo;
	}
}
