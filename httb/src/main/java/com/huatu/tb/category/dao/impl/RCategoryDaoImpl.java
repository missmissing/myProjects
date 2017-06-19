/**
 * 项目名：				httb
 * 包名：				com.huatu.tb.category.dao.impl
 * 文件名：				RCategoryDaoImpl.java
 * 日期：				2015年5月25日-下午6:07:54
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.tb.category.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.huatu.api.task.util.TaskMarkKeyUtil;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.tb.category.model.CateQues;
import com.huatu.tb.category.model.Category;

/**
 * 类名称：				RCategoryDaoImpl
 * 类描述：  			知识点章节Dao层 rest实现类
 * 创建人：				LiXin
 * 创建时间：			2015年5月25日 下午6:07:54
 * @version 		1.0
 */
@Repository
@Scope("prototype")
public class RCategoryDaoImpl {
	@Autowired
	private IRedisService iRedisService;
	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	/**
	 * 获取全部知识点 章节 ResultSet 对象
	 * getCategoryResultSet
	 * @exception
	 * @return
	 * @throws HttbException
	 */
	public List<Category> getCategoryList() throws HttbException{
		List<Category> categorys = new ArrayList<Category>();
		Select select = QueryBuilder.select().column("CID").column("CPID").column("CNAME").column("CEXPLAIN").column("CORDERNUM").column("clevels").from("httb", "HTCATEGORY");
		ResultSet resultSet  = noSqlBaseDao.executeQuery(select);
		for (Row row : resultSet) {
			Category category = new Category();
			category.setCid(row.getString("cid"));
			category.setCname(row.getString("cname"));
			category.setCpid(row.getString("cpid"));
			category.setCexplain(row.getString("cexplain"));
			category.setCordernum(row.getInt("cordernum"));
			categorys.add(category);
		}
		return categorys;
	}
	/**
	 * 获取知识点描述
	 * getCateExplainById
	 * @exception
	 * @param cid
	 * @return
	 * @throws HttbException
	 */
	public String getCateExplainById(String cid) throws HttbException{
		Select select = QueryBuilder.select("CEXPLAIN").from("httb", "HTCATEGORY");
		select.where(QueryBuilder.eq("cid",cid));
		ResultSet resultSet = noSqlBaseDao.executeQuery(select);
		return resultSet.all().get(0).getString("CEXPLAIN");
	}
	/**
	 * 通过章节 试题关系
	 * getQueNumByCid
	 * @exception
	 * @param cid
	 * @return
	 * @throws HttbException
	 */
	public List<CateQues>  getCateQuesList()throws HttbException{
		List<CateQues> qidList = new ArrayList<CateQues>();
		Select select = QueryBuilder.select().column("cid").column("qid").
				column("attr").column("qcids").from("httb", "HTCATE_QUES");
		List<Row> resultSet = null;
		try{
			resultSet = noSqlBaseDao.executeSearchSql(select);
			for (Row row : resultSet) {
				CateQues cq = new CateQues();
				cq.setCid(row.getString("cid"));
				cq.setQid(row.getString("qid"));
				cq.setAttr(row.getString("attr"));
				cq.setQcids(row.getList("qcids", String.class));
				qidList.add(cq);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return qidList;
	}
	/**
	 * 通过层级获取章节Id【根节点层级为1 以此类推】
	 * getCateByLevels
	 * @exception
	 * @param levels
	 * @return
	 * @throws HttbException
	 */
	public List<String> getCateByLevels(String levels)throws HttbException{
		List<String> list = new ArrayList<String>();
		Select select = QueryBuilder.select().column("CID").from("httb", "HTCATEGORY");
		select.where(QueryBuilder.eq("clevels",levels));
		select.allowFiltering();
		ResultSet resultSet = noSqlBaseDao.executeQuery(select);
		for (Row row : resultSet) {
			list.add(row.getString("cid"));
		}
		return list;
	}
	/**
	 * 通过章节ID和属性 获取试题ID集合
	 * getQidsBycid
	 * @exception
	 * @param cid
	 * @param attr  -- 地域
	 * @return
	 * @throws HttbException
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getQidsBycid(String cid,String attr)throws HttbException{
		Set<String> set = new HashSet<String>();
		List<CateQues> qidList = null;
		//先从redis中获取
		qidList = (List<CateQues>) iRedisService.get(TaskMarkKeyUtil.CATE_QUES_KEY);
		if(CommonUtil.isNull(qidList)){
			qidList = getCateQuesList();
		}
		//遍历过滤
		for(CateQues cq : qidList){
			if(cq.getCid().equals(cid)){
				if(CommonUtil.isNotNull(attr)){
					if(attr.equals(cq.getAttr())){
						set.add(cq.getQid());
					}
				}else{
					set.add(cq.getQid());
				}
			}
		}
		return set;
	}

}
