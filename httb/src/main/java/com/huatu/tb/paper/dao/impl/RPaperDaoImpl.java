/**
 * 项目名：				httb
 * 包名：				com.huatu.tb.paper.dao.impl
 * 文件名：				RPaperDaoImpl.java
 * 日期：				2015年5月25日-下午5:13:41
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.tb.paper.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.CommonUtil;
import com.huatu.tb.paper.model.Paper;

/**
 * 类名称：				RPaperDaoImpl
 * 类描述：
 * 创建人：				LiXin
 * 创建时间：			2015年5月25日 下午5:13:41
 * @version 		1.0
 */
@Repository
public class RPaperDaoImpl {
	@Autowired
	private NoSqlBaseDao noSqlBaseDao;

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
				paper.setPcategorys(row.getList("pexamtype", String.class));// 考试类型
				paper.setPcategorys(row.getList("pcategorys", String.class));// 一级分类　
				paper.setPqids(row.getList("pqids", String.class));// 试题ID集合
				paper.setPattribute(row.getString("pattribute")); // 试卷属性(0==>真题，1==>模拟题)
				paper.setPattrs(row.getMap("pattrs", String.class, Object.class));// 试卷扩展信息(JSON Map)
				paper.setPstatus(row.getString("pstatus"));// 试卷审核状态(0->编辑中；1->待审核；2->审核中；3->发布) 0
				paper.setTombstone(row.getString("tombstone"));// 假删除标识(0->有效；1->删除)
				paper.setUpdatetime(row.getDate("updatetime"));// 试卷数据最近更新时间
				paper.setUpdateuser(row.getString("updateuser"));// 修改人账号
				paper.setCreatetime(row.getDate("createtime"));// 试卷数据创建时间
				paper.setCreateuser(row.getString("createuser"));// 创建人ID

			}
		}catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "获取试卷对象时发生异常", e);
		}

		return paper;
	}
	/**
	 * 通过试卷Ids获取试卷列表
	 * getPaperListbyids
	 * @exception
	 * @param id
	 * @return
	 * @throws HttbException
	 */
	public List<Paper> getPaperListbyids(List<String> ids) throws HttbException {
		List<Paper>  list = new ArrayList<Paper>();
		try{
			int pageSize = 1000; // 分页步长
			int total = (ids.size()%pageSize) ==0 ?(ids.size()/pageSize) : (ids.size()/pageSize+1) ; // 总页数
			for(int pape=0;pape<total;pape++){//pape 当前页
				int length = pape+1==total ? ids.size() : pageSize;
				Object[] obj = new Object[length];
				for(int i=0;i<length;i++){
					obj[i] = ids.get(pageSize*pape+i);
				}
				Select select = QueryBuilder.select().all().from("httb", "HTPAPER");
				select.where(QueryBuilder.in("pid",obj));
				select.allowFiltering();
				ResultSet result = noSqlBaseDao.executeQuery(select);
				for(Row row : result){
					Paper paper = new Paper();
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
					list.add(paper);
				}
			}

		}catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "获取试卷对象时发生异常", e);
		}

		return list;
	}
}
