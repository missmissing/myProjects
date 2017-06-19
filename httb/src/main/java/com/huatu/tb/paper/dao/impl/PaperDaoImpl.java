/**
 * 项目名：				httb
 * 包名：				com.huatu.tb.paper.dao.impl
 * 文件名：				PaperDaoImpl.java
 * 日期：				2015年5月11日-下午5:20:35
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.tb.paper.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
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
import com.huatu.tb.paper.dao.PaperDao;
import com.huatu.tb.paper.model.Paper;

/**
 * 类名称：				PaperDaoImpl
 * 类描述：  			试卷dao实现类
 * 创建人：				LiXin
 * 创建时间：			2015年5月11日 下午5:20:35
 * @version 		1.0
 */
@Repository
public class PaperDaoImpl implements PaperDao {
	@Autowired
	private NoSqlBaseDao noSqlBaseDao;

	@Override
	public Paper get(String id) throws HttbException {
		Paper  paper = null;
		try{
			paper = new Paper();
			Select select = QueryBuilder.select().all().from("httb", "HTPAPER");
			select.where(QueryBuilder.eq("pid",id));
			select.allowFiltering();
			ResultSet result = noSqlBaseDao.executeQuery(select);
			Row row = null;
			List<Row> rows = result.all();
			if(result!=null&&rows!=null&&rows.size()>0){
				row = rows.get(0);
			}
			if(CommonUtil.isNotNull(row)){
				paper.setPid(row.getString("pid"));// 试卷主键
				paper.setPsubtitle(row.getString("psubtitle"));// 副标题
				paper.setPtitle(row.getString("ptitle"));// 试卷标题
				paper.setPyear(row.getString("pyear"));// 年份 　
				paper.setPqids(row.getList("pqids", String.class));// 试题ID集合
				paper.setPareas(row.getList("pareas", String.class));// 区域
				paper.setPcategorys(row.getList("pexamtype", String.class));// 考试类型
				paper.setPcategorys(row.getList("pcategorys", String.class));// 一级分类
				paper.setPorgs(row.getList("porgs", String.class));// 分校
				paper.setPtimelimit(row.getInt("ptimelimit"));//时限
				paper.setPattribute(row.getString("pattribute")); // 试卷属性(0==>真题，1==>模拟题)
				paper.setPattrs(row.getMap("pattrs", String.class, Object.class));// 试卷扩展信息(JSON Map)
				paper.setPstatus(row.getString("pstatus"));// 试卷审核状态(0->编辑中；1->待审核；2->审核中；3->发布) 0
				paper.setTombstone(row.getString("tombstone"));// 假删除标识(0->有效；1->删除)
				paper.setUpdatetime(row.getDate("updatetime"));// 试卷数据最近更新时间
				paper.setUpdateuser(row.getString("updateuser"));// 修改人账号
				paper.setCreatetime(row.getDate("createtime"));// 试卷数据创建时间
				paper.setCreateuser(row.getString("createuser"));// 创建人ID
				paper.setPauditopinion(row.getString("pauditopinion"));// 审核意见

			}
		}catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "获取试卷对象时发生异常", e);
		}

		return paper;
	}

	@Override
	public boolean delete(String id) throws HttbException {
		boolean boo = false;
		try{
			Delete delete  = QueryBuilder.delete().from("httb", "HTPAPER");
			delete.where(QueryBuilder.eq("pid",id));
			noSqlBaseDao.executeSql(delete);
			boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "删除试卷对象时发生异常", e);
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
			Delete delete  = QueryBuilder.delete().from("httb", "HTPAPER");
			delete.where(QueryBuilder.in("pid",obj));
			noSqlBaseDao.executeSql(delete);
			boo= true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "删除试卷对象时发生异常", e);
		}
		return boo;
	}

	@Override
	public boolean insert(Paper paper) throws HttbException {
		boolean boo = false;
		try{
			Insert insert = QueryBuilder.insertInto("httb", "HTPAPER");
			if(CommonUtil.isNotNull(paper.getPid())){
				insert.value("pid",paper.getPid());	// 试卷主键
			}else{
				insert.value("pid",CommonUtil.getUUID());	// 试卷主键
			}

			if(CommonUtil.isNotNull(paper.getPsubtitle())){
				insert.value("psubtitle",paper.getPsubtitle());	// 副标题
			}
			if(CommonUtil.isNotNull(paper.getPtitle())){
				insert.value("ptitle",paper.getPtitle());	// 试卷标题
			}
			if(CommonUtil.isNotNull(paper.getPyear())){
				insert.value("pyear",paper.getPyear());	 // 年份
			}
			if(CommonUtil.isNotNull(paper.getPqids())){
				insert.value("pqids",paper.getPqids());	 // 试题ID集合
			}
			if(CommonUtil.isNotNull(paper.getPareas())){
				insert.value("pareas",paper.getPareas());	 // 区域
			}
			if(CommonUtil.isNotNull(paper.getPcategorys())){
				insert.value("pcategorys",paper.getPcategorys());	 // 一级分类
			}
			if(CommonUtil.isNotNull(paper.getPexamtype())){
				insert.value("pexamtype",paper.getPcategorys());	 // 考试类型
			}
			if(CommonUtil.isNotNull(paper.getPtimelimit())){
				insert.value("ptimelimit",paper.getPtimelimit());	 // 时限
			}
			if(CommonUtil.isNotNull(paper.getPorgs())){
				insert.value("porgs",paper.getPorgs());	 // 分校
			}
			if(CommonUtil.isNotNull(paper.getPattribute())){
				insert.value("pattribute",paper.getPattribute());// 试卷属性(0==>真题，1==>模拟题)
			}
			if(CommonUtil.isNotNull(paper.getPattrs())){
				insert.value("pattrs",paper.getPattrs());// 试卷扩展信息(JSON Map)
			}
			if(CommonUtil.isNotNull(paper.getPstatus())){
				insert.value("pstatus",paper.getPstatus());// 试卷审核状态(0->编辑中；1->待审核；2->审核中；3->发布) 0
			}
			if(CommonUtil.isNotNull(paper.getTombstone())){
				insert.value("tombstone",paper.getTombstone());// 假删除标识(0->有效；1->删除)
			}
			if(CommonUtil.isNotNull(paper.getUpdatetime())){
				insert.value("updatetime",paper.getUpdatetime());// 试卷数据最近更新时间
			}
			if(CommonUtil.isNotNull(paper.getUpdateuser())){
				insert.value("updateuser",paper.getUpdateuser());// 修改人账号
			}
			if(CommonUtil.isNotNull(paper.getCreatetime())){
				insert.value("createtime",paper.getCreatetime());// 试卷数据创建时间
			}
			if(CommonUtil.isNotNull(paper.getCreateuser())){
				insert.value("createuser",paper.getCreateuser());// 创建人ID
			}
			noSqlBaseDao.executeSql(insert);
			boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入试卷对象时发生异常", e);
		}

		return boo;
	}

	@Override
	public boolean update(Paper paper) throws HttbException {
		boolean boo = false;
		try{
			Update update = QueryBuilder.update("httb", "HTPAPER");
			if(CommonUtil.isNotNull(paper.getPsubtitle())){
				update.with(QueryBuilder.set("psubtitle",paper.getPsubtitle()));// 副标题
			}
			if(CommonUtil.isNotNull(paper.getPtitle())){
				update.with(QueryBuilder.set("ptitle",paper.getPtitle()));// 试卷标题
			}
			if(CommonUtil.isNotNull(paper.getPyear())){
				update.with(QueryBuilder.set("pyear",paper.getPyear()));// 年份
			}
			if(CommonUtil.isNotNull(paper.getPareas())){
				update.with(QueryBuilder.set("pareas",paper.getPareas()));	 // 区域
			}
			if(CommonUtil.isNotNull(paper.getPcategorys())){
				update.with(QueryBuilder.set("pcategorys",paper.getPcategorys()));	  // 一级分类
			}
			if(CommonUtil.isNotNull(paper.getPexamtype())){
				update.with(QueryBuilder.set("pexamtype",paper.getPexamtype()));	 // 考试类型
			}
			if(CommonUtil.isNotNull(paper.getPtimelimit())){
				update.with(QueryBuilder.set("ptimelimit",paper.getPtimelimit()));	 // 时限
			}
			if(CommonUtil.isNotNull(paper.getPorgs())){
				update.with(QueryBuilder.set("porgs",paper.getPorgs()));	 // 分校
			}
			if(CommonUtil.isNotNull(paper.getPqids())){
				update.with(QueryBuilder.set("pqids",paper.getPqids()));// 试题ID集合
			}
			if(CommonUtil.isNotNull(paper.getPattribute())){
				update.with(QueryBuilder.set("pattribute",paper.getPattribute()));// 试卷属性(0==>真题，1==>模拟题)
			}
			if(CommonUtil.isNotNull(paper.getPattrs())){
				update.with(QueryBuilder.set("pattrs",paper.getPattrs()));// 试卷扩展信息(JSON Map)
			}
			if(CommonUtil.isNotNull(paper.getPstatus())){
				update.with(QueryBuilder.set("pstatus",paper.getPstatus()));// 试卷审核状态(0->编辑中；1->待审核；2->审核中；3->发布) 0
			}
			if(CommonUtil.isNotNull(paper.getTombstone())){
				update.with(QueryBuilder.set("tombstone",paper.getTombstone()));// 假删除标识(0->有效；1->删除)
			}
			if(CommonUtil.isNotNull(paper.getUpdatetime())){
				update.with(QueryBuilder.set("updatetime",paper.getUpdatetime()));// 试卷数据最近更新时间
			}
			if(CommonUtil.isNotNull(paper.getUpdateuser())){
				update.with(QueryBuilder.set("updateuser",paper.getUpdateuser()));// 修改人账号
			}
			update.where(QueryBuilder.eq("pid",paper.getPid()));// 试卷主键
			noSqlBaseDao.executeSql(update);
			boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_UPDATE, this.getClass() + "插入试卷对象时发生异常", e);
		}
		return boo;
	}
	@Override
	public boolean updateQuesList(Paper paper) throws HttbException {
		boolean boo = false;
		try{
			Update update = QueryBuilder.update("httb", "HTPAPER");

			if(CommonUtil.isNotNull(paper.getPqids())){
				update.with(QueryBuilder.set("pqids",paper.getPqids()));// 试题ID集合
			}

			if(CommonUtil.isNotNull(paper.getPattribute())){
				update.with(QueryBuilder.set("pattribute",paper.getPattribute()));// 试卷属性(0==>真题，1==>模拟题)
			}
			if(CommonUtil.isNotNull(paper.getPattrs())){
				update.with(QueryBuilder.set("pattrs",paper.getPattrs()));// 试卷扩展信息(JSON Map)
			}
			if(CommonUtil.isNotNull(paper.getPstatus())){
				update.with(QueryBuilder.set("pstatus",paper.getPstatus()));// 试卷审核状态(0->编辑中；1->待审核；2->审核中；3->发布) 0
			}
			if(CommonUtil.isNotNull(paper.getTombstone())){
				update.with(QueryBuilder.set("tombstone",paper.getTombstone()));// 假删除标识(0->有效；1->删除)
			}
			if(CommonUtil.isNotNull(paper.getUpdatetime())){
				update.with(QueryBuilder.set("updatetime",paper.getUpdatetime()));// 试卷数据最近更新时间
			}
			if(CommonUtil.isNotNull(paper.getUpdateuser())){
				update.with(QueryBuilder.set("updateuser",paper.getUpdateuser()));// 修改人账号
			}
			if(CommonUtil.isNotNull(paper.getPauditopinion())){
				update.with(QueryBuilder.set("pauditopinion",paper.getPauditopinion()));// 修改人账号
			}
			update.where(QueryBuilder.eq("pid",paper.getPid()));// 试卷主键
			noSqlBaseDao.executeSql(update);
			boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_UPDATE, this.getClass() + "插入试卷对象时发生异常", e);
		}
		return boo;
	}

	@Override
	public ResultSet getPaperResultSet(Map<String, Object> filter) throws HttbException {
		try{
			Select select = QueryBuilder.select().all().from("httb", "HTPAPER");
			if(CommonUtil.isNotNull(filter.get("pyear"))){// 年份
				select.where(QueryBuilder.eq("pyear",filter.get("pyear")));
			}
			if(CommonUtil.isNotNull(filter.get("parea"))){// 地域
				select.where(QueryBuilder.eq("parea",filter.get("parea")));
			}
			if(CommonUtil.isNotNull(filter.get("pstatus"))){// 试卷审核状态(0->编辑中；1->待审核；2->审核中；3->发布)
				select.where(QueryBuilder.eq("pstatus",filter.get("pstatus")));
			}
			if(CommonUtil.isNotNull(filter.get("pattribute"))){// 试卷 属性 0 真题 1 模拟题
				select.where(QueryBuilder.eq("pattribute",filter.get("pattribute")));
			}
			if(CommonUtil.isNotNull(filter.get("psubtitle"))){// 试卷简标题
				select.where(QueryBuilder.eq("psubtitle",filter.get("psubtitle")));
			}
			select.allowFiltering();
			return noSqlBaseDao.executeQuery(select);
		}catch(Exception e){

			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询试卷对象时发生异常", e);
		}
	}

	@Override
	public ResultSet getPaperResultSet(Page page, Map<String, Object> filter) throws HttbException {
		return null;
	}

}
