/**
 * 项目名：				httb
 * 包名：				com.huatu.tb.question.dao.impl
 * 文件名：				QuestionDaoRestImpl.java
 * 日期：				2015年5月25日-下午4:56:45
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.tb.question.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huatu.analysis.model.Htexamanalysis;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.tb.category.enums.DeleteFlag;
import com.huatu.tb.question.model.ChildQuestion;
import com.huatu.tb.question.model.Corrects;
import com.huatu.tb.question.model.Question;
import com.huatu.tb.question.util.QuestionUtil;

/**
 * 类名称：				QuestionDaoRestImpl
 * 类描述：  			试题Dao层rest实现类
 * 创建人：				LiXin
 * 创建时间：			2015年5月25日 下午4:56:45
 * @version 		1.0
 */
@Repository
public class RQuestionDaoImpl{
	@Autowired
	private NoSqlBaseDao noSqlBaseDao;

	/**
	 * 获取试题集合返回ResultSet对象
	 * getQuestionResultSet
	 * @exception
	 * @param qids --试题ID
	 * @return
	 * @throws HttbException
	 */
	public List<Question> getQuestionResultSet(List<String> qids) throws HttbException {
		try{
			List<Question> Alllist = new ArrayList<Question>();
			// 分页总长
			int pageSize = 1000;
			int total = (qids.size()%pageSize) ==0 ?(qids.size()/pageSize) : (qids.size()/pageSize+1) ; // 总页数
			for(int pape=0;pape<total;pape++){//pape 当前页
				int length = pape+1==total ? (qids.size()%pageSize) : pageSize;
				Object[] ids = new Object[length];
				for(int i=0;i<length;i++){
					ids[i] = qids.get(pageSize*pape+i);
				}
				//批量in查询
				Select select = QueryBuilder.select().all().from("httb", "HTQUESTION");
				select.where(QueryBuilder.in("qid",ids));
				select.allowFiltering();
				ResultSet result = noSqlBaseDao.executeQuery(select);
				for (Row row : result) {
					Question  question = new Question();
					question.setQid(row.getString("qid") );
					question.setQyear(row.getString("qyear") );
					question.setQarea(row.getString("qarea") );
					question.setQtype(row.getString("qtype") );
					question.setQpoint(row.getList("qpoint",String.class));
					question.setQcontent(row.getString("qcontent") );
					question.setQdifficulty(row.getString("qdifficulty") );
					@SuppressWarnings("serial")
					List<Corrects>  rlist = new Gson().fromJson(row.getList("qcorrect",UDTValue.class).toString(), new TypeToken<List<Corrects>>() {}.getType());
					question.setQcorrect(rlist);
					question.setQattribute(row.getString("qattribute") );
					question.setQcategory(row.getList("qcategory",String.class));
					question.setQparaphrase(row.getString("qparaphrase") );
					question.setQfrom(row.getString("qfrom") );
					question.setQskill(row.getString("qskill") );
					question.setQauthor(row.getString("qauthor") );
					question.setQdiscussion(row.getString("qdiscussion") );
					question.setQvideourl(row.getString("qvideourl") );
					question.setTombstone(row.getString("tombstone") );
					question.setCreatetime(row.getDate("createtime") );
					question.setCreateuser(row.getString("createuser") );
					question.setUpdatetime(row.getDate("updatetime"));
					question.setUpdateuser(row.getString("updateuser") );
					String json = "";
					try{
						Gson gson = new GsonBuilder().serializeNulls().create();
						json = row.getList("qchild",UDTValue.class).toString();
						List<ChildQuestion>  clist = gson.fromJson(QuestionUtil.delHTMLTag(json), new TypeToken<List<ChildQuestion>>() {}.getType());
						//数据库中qcchoices字段有值  且 qcchoiceList无值   此处需转换赋值
						for(ChildQuestion cq : clist){
							String choicesStr = QuestionUtil.imgTransformStyle(cq.getQcchoices());
							cq.setQcchoiceList(Arrays.asList(choicesStr.split(Constants.SEPARATOR)));
						}
						question.setQchild(clist);
						if(CommonUtil.isNotNull(question.getQcontent())){
							question.setQcontent(QuestionUtil.imgTransformStyle(question.getQcontent()));
						}
						if(question.getQchild()!=null&&question.getQchild().size()>0){
							for(ChildQuestion cq : question.getQchild()){
								cq.setQccontent(QuestionUtil.imgTransformStyle(cq.getQccontent()));
								cq.setQcchoices(QuestionUtil.imgTransformStyle(cq.getQcchoices()));
								cq.setQccomment(QuestionUtil.imgTransformStyle(cq.getQccomment()));
								cq.setQcans(QuestionUtil.imgTransformStyle(cq.getQcans()));
								cq.setQcextension(QuestionUtil.imgTransformStyle(cq.getQcextension()));
								if(cq.getQcchoiceList()!=null&&cq.getQcchoiceList().size()>0){
									for(String s : cq.getQcchoiceList()){
										s = QuestionUtil.imgTransformStyle(s);
									}
								}
							}
						}
						Alllist.add(question);
					}catch(Exception e){
						System.out.println(QuestionUtil.delHTMLTag(json));
						e.printStackTrace();
						System.out.println("json转换对象出错");
					}
				}
			}
			return Alllist;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询试题集合时发生异常", e);
		}
	}
	/**
	 * 通过知识点ID获取小于1000条的试题数据
	 * getQuesByCid
	 * @exception
	 * @param cid
	 * @return
	 * @throws HttbException
	 */
	public List<Question> getQuesLimitByCid(String cid)throws HttbException{
		try{
			List<Question> qlist = new ArrayList<Question>();
			StringBuffer sql = new StringBuffer("SELECT *  FROM httb.HTQUESTION where tombstone = '" + DeleteFlag.NO.getDeleteFlag() + "'");
			if(CommonUtil.isNotNull(cid)){
				sql.append(" and qpoint contains'"+cid+"' ");
			}
			sql.append("  limit 100  allow filtering; ");
			ResultSet result = noSqlBaseDao.getResultSet(sql.toString());
			for (Row row : result) {
				Question  question = new Question();
				question.setQid(row.getString("qid") );
				question.setQyear(row.getString("qyear") );
				question.setQarea(row.getString("qarea") );
				question.setQtype(row.getString("qtype") );
				question.setQpoint(row.getList("qpoint",String.class));
				question.setQcontent(row.getString("qcontent") );
				question.setQdifficulty(row.getString("qdifficulty") );
				@SuppressWarnings("serial")
				List<ChildQuestion>  clist = new Gson().fromJson(QuestionUtil.delHTMLTag(row.getList("qchild",UDTValue.class).toString()), new TypeToken<List<ChildQuestion>>() {}.getType());

				//数据库中qcchoices字段有值  且 qcchoiceList无值   此处需转换赋值
				for(ChildQuestion cq : clist){
					String choicesStr = QuestionUtil.imgTransformStyle(cq.getQcchoices());
					cq.setQcchoiceList(Arrays.asList(choicesStr.split(Constants.SEPARATOR)));
				}
				question.setQchild(clist);
				@SuppressWarnings("serial")
				List<Corrects>  rlist = new Gson().fromJson(QuestionUtil.delHTMLTag(row.getList("qcorrect",UDTValue.class).toString()), new TypeToken<List<Corrects>>() {}.getType());
				question.setQcorrect(rlist);
				question.setQattribute(row.getString("qattribute") );
				question.setQcategory(row.getList("qcategory",String.class));
				question.setQparaphrase(row.getString("qparaphrase") );
				question.setQfrom(row.getString("qfrom") );
				question.setQskill(row.getString("qskill") );
				question.setQauthor(row.getString("qauthor") );
				question.setQdiscussion(row.getString("qdiscussion") );
				question.setQvideourl(row.getString("qvideourl") );
				question.setTombstone(row.getString("tombstone") );
				question.setCreatetime(row.getDate("createtime") );
				question.setCreateuser(row.getString("createuser") );
				question.setUpdatetime(row.getDate("updatetime"));
				question.setUpdateuser(row.getString("updateuser") );
				qlist.add(question);
			}
			return qlist;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询试题集合时发生异常", e);
		}
	}

}
