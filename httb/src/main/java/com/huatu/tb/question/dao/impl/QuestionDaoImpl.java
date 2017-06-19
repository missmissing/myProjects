/**
 *
 */
package com.huatu.tb.question.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.UserType;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Update;
import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Page;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.tb.category.enums.DeleteFlag;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.question.dao.QuestionDao;
import com.huatu.tb.question.model.ChildQuestion;
import com.huatu.tb.question.model.Corrects;
import com.huatu.tb.question.model.Question;
import com.huatu.tb.question.util.QuestionUtil;

/**
 * @ClassName: QuestionDao
 * @Description: 试题Dao
 * @author LiXin
 * @date 2015年4月23日 下午4:36:27
 * @version 1.0  @Component
 *
 */
@Repository
public class QuestionDaoImpl implements QuestionDao{
	@Autowired
	private NoSqlBaseDao noSqlBaseDao;

	@Override
	public boolean insert(Question question) throws HttbException {
		boolean boo  = false;
		try{

			Insert insert = QueryBuilder.insertInto("httb", "HTQUESTION");
			if(CommonUtil.isNotNull(question.getQid())){
				insert.value("qid",question.getQid());// 试题主键
			}else{
				insert.value("qid",CommonUtil.getUUID());// 试题主键
			}
			if(CommonUtil.isNotNull(question.getQyear())){
				insert.value("qyear", question.getQyear());// 年份
			}
			if(CommonUtil.isNotNull(question.getQarea())){
				insert.value("qarea",question.getQarea());// 区域
			}
			if(CommonUtil.isNotNull(question.getQtype())){
				insert.value("qtype",question.getQtype());//// 题目类型(0 -> 单选题；1 -> 多选题；2 -> 共用题干；3 -> 共用备选；)
			}
			if(CommonUtil.isNotNull(question.getQpoint())){
				insert.value("qpoint",question.getQpoint());// 知识点,考点(数据字典ID)
			}
			if(CommonUtil.isNotNull(question.getQcontent())){
				insert.value("qcontent",QuestionUtil.imgLabelTransform(question.getQcontent()));// 试题题干
			}
			if(CommonUtil.isNotNull(question.getQdifficulty())){
				insert.value("qdifficulty",question.getQdifficulty());// 难度
			}
			if(CommonUtil.isNotNull(question.getQchild())){
				List<UDTValue> udtList = new ArrayList<UDTValue>();
				UserType childsUDT = noSqlBaseDao.getNoSqlSession().getCluster().getMetadata().getKeyspace("httb").getUserType("type_queschild");
				for(ChildQuestion cq : question.getQchild()){
					UDTValue cqt = childsUDT.newValue();
					cqt.setString("qcid", cq.getQcid());//子题id
					cqt.setString("qctype", cq.getQctype());//题型
					cqt.setString("qccontent", QuestionUtil.imgLabelTransform(cq.getQccontent()));//子题干
					if(CommonUtil.isNotNull(cq.getQcchoiceList())){
						String xuanxiang = StringUtils.collectionToDelimitedString(cq.getQcchoiceList(), Constants.SEPARATOR);

						cqt.setString("qcchoices",QuestionUtil.imgLabelTransform(xuanxiang));//选项
					}
					cqt.setString("qcans", cq.getQcans()); ;//答案
					cqt.setString("qccomment", QuestionUtil.imgLabelTransform(cq.getQccomment()));//解析
					cqt.setString("qcextension",QuestionUtil.imgLabelTransform(cq.getQcextension()));//拓展
					cqt.setFloat("qcscore", cq.getQcscore());// 分值
					udtList.add(cqt);
				}
				insert.value("qchild",ImmutableList.copyOf(udtList));
			}
			if(CommonUtil.isNotNull(question.getQcorrect())){
				List<UDTValue> udtList = new ArrayList<UDTValue>();
				UserType correctUDT = noSqlBaseDao.getNoSqlSession().getCluster().getMetadata().getKeyspace("httb").getUserType("type_correct");
				for(Corrects cq : question.getQcorrect()){
					UDTValue cqt = correctUDT.newValue();
					cqt.setString("cdescription", cq.getCdescription());//错误信息
					cqt.setDate("ctime",cq.getCtime());// 评论时间
					cqt.setString("crejecter", cq.getCrejecter());// 驳回人
					cqt.setString("updateuser", cq.getUpdateuser());// 修改人
					cqt.setDate("updatetime",cq.getUpdatetime());//修改时间
					udtList.add(cqt);
				}
				insert.value("qcorrect",ImmutableList.copyOf(udtList));
			}
			if(CommonUtil.isNotNull(question.getQattribute())){
				insert.value("qattribute",question.getQattribute());// 属性(是否真题)
			}
			if(CommonUtil.isNotNull(question.getQcategory())){
				insert.value("qcategory",question.getQcategory());// 考试分类(护士考试，护师考试，主管考试)
			}
			if(CommonUtil.isNotNull(question.getQparaphrase())){
				insert.value("qparaphrase",QuestionUtil.imgLabelTransform(question.getQparaphrase()));// 释义
			}
			if(CommonUtil.isNotNull(question.getQfrom())){
				insert.value("qfrom",question.getQfrom());// 信息源
			}
			if(CommonUtil.isNotNull(question.getQskill())){
				insert.value("qskill",question.getQskill());// 答题技巧
			}
			if(CommonUtil.isNotNull(question.getQauthor())){
				insert.value("qauthor",question.getQauthor());//作者
			}
			if(CommonUtil.isNotNull(question.getQdiscussion())){
				insert.value("qdiscussion",question.getQdiscussion());// 讨论
			}
			if(CommonUtil.isNotNull(question.getQvideourl())){
				insert.value("qvideourl",question.getQvideourl());// 视频路径
			}
			if(CommonUtil.isNotNull(question.getQstatus())){
				insert.value("qstatus",question.getQstatus());// 假删除标识(0 -> 有效；1 -> 删除)
			}
			if(CommonUtil.isNotNull(question.getTombstone())){
				insert.value("tombstone",question.getTombstone());// 假删除标识(0 -> 有效；1 -> 删除)
			}
			if(CommonUtil.isNotNull(question.getCreatetime())){
				insert.value("createtime",question.getCreatetime());// 创建时间
			}
			if(CommonUtil.isNotNull(question.getCreateuser())){
				insert.value("createuser",question.getCreateuser());// 创建人账号
			}
			if(CommonUtil.isNotNull(question.getUpdatetime())){
				insert.value("updatetime",question.getUpdatetime());// 修改时间
			}
			if(CommonUtil.isNotNull(question.getUpdateuser())){
				insert.value("updateuser",question.getUpdateuser());// 修改人账号
			}
			if(CommonUtil.isNotNull(question.getQauditopinion())){
				insert.value("qauditopinion",question.getQauditopinion());// 审核意见
			}
			if(CommonUtil.isNotNull(question.getQbatchnum())){
				insert.value("qbatchnum",question.getQbatchnum());// 批次号
			}
			noSqlBaseDao.executeSql(insert);
			boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入试题对象时发生异常", e);
		}
		return boo;
	}
	@Override
	public boolean insertBatch(List<Question> list) throws HttbException {
		boolean boo  = false;
		try{
		  Insert[] inserts = new Insert[list.size()];
		  int i =0;
		  for(Question question : list ){
				Insert insert = QueryBuilder.insertInto("httb", "HTQUESTION");
				if(CommonUtil.isNotNull(question.getQid())){
					insert.value("qid",question.getQid());// 试题主键
				}else{
					insert.value("qid",CommonUtil.getUUID());// 试题主键
				}
				if(CommonUtil.isNotNull(question.getQyear())){
					insert.value("qyear", question.getQyear());// 年份
				}
				if(CommonUtil.isNotNull(question.getQarea())){
					insert.value("qarea",question.getQarea());// 区域
				}
				if(CommonUtil.isNotNull(question.getQtype())){
					insert.value("qtype",question.getQtype());//// 题目类型(0 -> 单选题；1 -> 多选题；2 -> 共用题干；3 -> 共用备选；)
				}
				if(CommonUtil.isNotNull(question.getQpoint())){
					insert.value("qpoint",question.getQpoint());// 知识点,考点(数据字典ID)
				}
				if(CommonUtil.isNotNull(question.getQcontent())){
					insert.value("qcontent",QuestionUtil.imgLabelTransform(question.getQcontent()));// 试题题干
				}
				if(CommonUtil.isNotNull(question.getQdifficulty())){
					insert.value("qdifficulty",question.getQdifficulty());// 难度
				}
				if(CommonUtil.isNotNull(question.getQchild())){
					List<UDTValue> udtList = new ArrayList<UDTValue>();
					UserType childsUDT = noSqlBaseDao.getNoSqlSession().getCluster().getMetadata().getKeyspace("httb").getUserType("type_queschild");
					for(ChildQuestion cq : question.getQchild()){
						UDTValue cqt = childsUDT.newValue();
						if(question.getQtype().equals("0")||question.getQtype().equals("1")){
							cqt.setString("qcid", null);//子题id
						}else{
							cqt.setString("qcid", cq.getQcid());//子题id
						}
						cqt.setString("qctype", cq.getQctype());//题型
						cqt.setString("qccontent", QuestionUtil.imgLabelTransform(cq.getQccontent()));//子题干
						if(CommonUtil.isNotNull(cq.getQcchoiceList())){
							String xuanxiang = StringUtils.collectionToDelimitedString(cq.getQcchoiceList(), Constants.SEPARATOR);

							cqt.setString("qcchoices",QuestionUtil.imgLabelTransform(xuanxiang));//选项
						}
						cqt.setString("qcans", cq.getQcans()); ;//答案
						cqt.setString("qccomment", QuestionUtil.imgLabelTransform(cq.getQccomment()));//解析
						cqt.setString("qcextension", QuestionUtil.imgLabelTransform(cq.getQcextension()));//拓展
						cqt.setFloat("qcscore", cq.getQcscore());// 分值
						udtList.add(cqt);
					}
					insert.value("qchild",ImmutableList.copyOf(udtList));
				}
				if(CommonUtil.isNotNull(question.getQcorrect())){
					List<UDTValue> udtList = new ArrayList<UDTValue>();
					UserType correctUDT = noSqlBaseDao.getNoSqlSession().getCluster().getMetadata().getKeyspace("httb").getUserType("type_correct");
					for(Corrects cq : question.getQcorrect()){
						UDTValue cqt = correctUDT.newValue();
						cqt.setString("cdescription", cq.getCdescription());//错误信息
						cqt.setDate("ctime",cq.getCtime());// 评论时间
						cqt.setString("crejecter", cq.getCrejecter());// 驳回人
						cqt.setString("updateuser", cq.getUpdateuser());// 修改人
						cqt.setDate("updatetime",cq.getUpdatetime());//修改时间
						udtList.add(cqt);
					}
					insert.value("qcorrect",ImmutableList.copyOf(udtList));
				}
				if(CommonUtil.isNotNull(question.getQattribute())){
					insert.value("qattribute",question.getQattribute());// 属性(是否真题)
				}
				if(CommonUtil.isNotNull(question.getQcategory())){
					insert.value("qcategory",question.getQcategory());// 考试分类(护士考试，护师考试，主管考试)
				}
				if(CommonUtil.isNotNull(question.getQparaphrase())){
					insert.value("qparaphrase",question.getQparaphrase());// 释义
				}
				if(CommonUtil.isNotNull(question.getQfrom())){
					insert.value("qfrom",question.getQfrom());// 信息源
				}
				if(CommonUtil.isNotNull(question.getQskill())){
					insert.value("qskill",question.getQskill());// 答题技巧
				}
				if(CommonUtil.isNotNull(question.getQauthor())){
					insert.value("qauthor",question.getQauthor());//作者
				}
				if(CommonUtil.isNotNull(question.getQdiscussion())){
					insert.value("qdiscussion",question.getQdiscussion());// 讨论
				}
				if(CommonUtil.isNotNull(question.getQvideourl())){
					insert.value("qvideourl",question.getQvideourl());// 视频路径
				}
				if(CommonUtil.isNotNull(question.getQstatus())){
					insert.value("qstatus",question.getQstatus());// 假删除标识(0 -> 有效；1 -> 删除)
				}
				if(CommonUtil.isNotNull(question.getQbatchnum())){
					insert.value("qbatchnum",question.getQbatchnum());// 批次号
				}
				if(CommonUtil.isNotNull(question.getTombstone())){
					insert.value("tombstone",question.getTombstone());// 假删除标识(0 -> 有效；1 -> 删除)
				}
				if(CommonUtil.isNotNull(question.getCreatetime())){
					insert.value("createtime",question.getCreatetime());// 创建时间
				}
				if(CommonUtil.isNotNull(question.getCreateuser())){
					insert.value("createuser",question.getCreateuser());// 创建人账号
				}
				if(CommonUtil.isNotNull(question.getUpdatetime())){
					insert.value("updatetime",question.getUpdatetime());// 修改时间
				}
				if(CommonUtil.isNotNull(question.getUpdateuser())){
					insert.value("updateuser",question.getUpdateuser());// 修改人账号
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
	public boolean update(Question question) throws HttbException {
		boolean boo = false;
		try{
			Update update = QueryBuilder.update("httb", "HTQUESTION");
			if(CommonUtil.isNotNull(question.getQyear())){
				update.with(QueryBuilder.set("qyear", question.getQyear()));// 年份
			}
			if(CommonUtil.isNotNull(question.getQarea())){
				update.with(QueryBuilder.set("qarea",question.getQarea()));// 区域
			}
			if(CommonUtil.isNotNull(question.getQtype())){
				update.with(QueryBuilder.set("qtype",question.getQtype()));//// 题目类型(0 -> 单选题；1 -> 多选题；2 -> 共用题干；3 -> 共用备选；)
			}
			if(CommonUtil.isNotNull(question.getQpoint())){
				update.with(QueryBuilder.set("qpoint",question.getQpoint()));// 知识点,考点(数据字典ID)
			}
			if(CommonUtil.isNotNull(question.getQcontent())){
				update.with(QueryBuilder.set("qcontent",QuestionUtil.imgLabelTransform(question.getQcontent())));// 试题题干
			}
			if(CommonUtil.isNotNull(question.getQdifficulty())){
				update.with(QueryBuilder.set("qdifficulty",question.getQdifficulty()));// 难度
			}
			if(CommonUtil.isNotNull(question.getQchild())){
				List<UDTValue> udtList = new ArrayList<UDTValue>();
				UserType childsUDT = noSqlBaseDao.getNoSqlSession().getCluster().getMetadata().getKeyspace("httb").getUserType("type_queschild");
				for(ChildQuestion cq : question.getQchild()){
					UDTValue cqt = childsUDT.newValue();
					cqt.setString("qcid", cq.getQcid());//子题id
					cqt.setString("qctype", cq.getQctype());//题型
					cqt.setString("qccontent", QuestionUtil.imgLabelTransform(cq.getQccontent()));//子题干
					if(CommonUtil.isNotNull(cq.getQcchoiceList())){
						String xuanxiang = StringUtils.collectionToDelimitedString(cq.getQcchoiceList(), Constants.SEPARATOR);
						cqt.setString("qcchoices",QuestionUtil.imgLabelTransform(xuanxiang));//选项
					}
					cqt.setString("qcans", cq.getQcans()); ;//答案
					cqt.setString("qccomment", QuestionUtil.imgLabelTransform(cq.getQccomment()));//解析
					cqt.setString("qcextension", QuestionUtil.imgLabelTransform(cq.getQcextension()));//拓展
					cqt.setFloat("qcscore", cq.getQcscore());// 分值
					udtList.add(cqt);
				}
				update.with(QueryBuilder.set("qchild",ImmutableList.copyOf(udtList)));// 具体子问题集合
			}
			if(CommonUtil.isNotNull(question.getQcorrect())){
				update.with(QueryBuilder.set("qcorrect",question.getQcorrect()));// 纠错
			}
			if(CommonUtil.isNotNull(question.getQattribute())){
				update.with(QueryBuilder.set("qattribute",question.getQattribute()));// 属性(是否真题)
			}
			if(CommonUtil.isNotNull(question.getQcategory())){
				update.with(QueryBuilder.set("qcategory",question.getQcategory()));// 考试分类(护士考试，护师考试，主管考试)
			}
			if(CommonUtil.isNotNull(question.getQbatchnum())){
				update.with(QueryBuilder.set("qbatchnum",question.getQbatchnum()));// 考试分类(护士考试，护师考试，主管考试)
			}
			if(CommonUtil.isNotNull(question.getQparaphrase())){
				update.with(QueryBuilder.set("qparaphrase",question.getQparaphrase()));// 释义
			}
			if(CommonUtil.isNotNull(question.getQfrom())){
				update.with(QueryBuilder.set("qfrom",question.getQfrom()));// 信息源
			}
			if(CommonUtil.isNotNull(question.getQskill())){
				update.with(QueryBuilder.set("qskill",question.getQskill()));// 答题技巧
			}
			if(CommonUtil.isNotNull(question.getQauthor())){
				update.with(QueryBuilder.set("qauthor",question.getQauthor()));//作者
			}
			if(CommonUtil.isNotNull(question.getQdiscussion())){
				update.with(QueryBuilder.set("qdiscussion",question.getQdiscussion()));// 讨论
			}
			if(CommonUtil.isNotNull(question.getQvideourl())){
				update.with(QueryBuilder.set("qvideourl",question.getQvideourl()));// 视频路径
			}
			if(CommonUtil.isNotNull(question.getUpdatetime())){
				update.with(QueryBuilder.set("updatetime",question.getUpdatetime()));// 修改时间
			}
			if(CommonUtil.isNotNull(question.getUpdateuser())){
				update.with(QueryBuilder.set("updateuser",question.getUpdateuser()));// 修改人账号
			}
			if(CommonUtil.isNotNull(question.getQstatus())){
				update.with(QueryBuilder.set("qstatus",question.getQstatus()));// 假删除标识(0 -> 有效；1 -> 删除)
			}
			update.where(QueryBuilder.eq("qid",question.getQid()));
			noSqlBaseDao.executeSql(update);
			boo = true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_UPDATE, this.getClass() + "修改试题对象时发生异常", e);
		}
		return boo;
	}

	@Override
	public boolean delete(String id) throws HttbException {
		boolean boo = false;
		try{
			Delete delete  = QueryBuilder.delete().from("httb", "HTQUESTION");
			delete.where(QueryBuilder.eq("qid",id));
			noSqlBaseDao.executeSql(delete);
			boo= true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "删除试题对象时发生异常", e);
		}
		return boo;
	}
	/**
	 * 目前批量删除 IN 不成功
	 */
	@Override
	public boolean deleteBatch(List<String> ids) throws HttbException {
		boolean boo = false;
		try{
			Object[] obj = new Object[ids.size()];
			for(int i=0;i<ids.size();i++){
				obj[i] = ids.get(i);
			}
			Delete delete  = QueryBuilder.delete().from("httb", "HTQUESTION");
			delete.where(QueryBuilder.in("qid",obj));
			noSqlBaseDao.executeSql(delete);
			boo= true;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "删除试题对象时发生异常", e);
		}
		return boo;
	}
	public Question get(String id) throws HttbException {


		Question  question = new Question();
		Select select = QueryBuilder.select().all().from("httb", "HTQUESTION");
		select.where(QueryBuilder.eq("qid",id));
		select.allowFiltering();
		ResultSet result = noSqlBaseDao.executeQuery(select);
		Row row = null;
		List<Row> rows = result.all();
		if(result!=null&&rows!=null&&rows.size()>0){
			row = rows.get(0);
		}
		if(CommonUtil.isNotNull(row)){
			question.setQid(row.getString("qid") );
			question.setQyear(row.getString("qyear") );
			question.setQarea(row.getString("qarea") );
			question.setQtype(row.getString("qtype") );
			question.setQpoint(row.getList("qpoint",String.class));
			question.setQcategory(row.getList("qcategory",String.class));
			question.setQbatchnum(row.getString("qbatchnum") );
			question.setQcontent(row.getString("qcontent") );
			question.setQdifficulty(row.getString("qdifficulty") );
			question.setQauditopinion(row.getString("qauditopinion") );
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
			if(CommonUtil.isNotNull(question.getQcontent())){
				question.setQcontent(QuestionUtil.imgTransformStyle(question.getQcontent()));
			}
			if(question.getQchild()!=null&&question.getQchild().size()>0){
				for(ChildQuestion cq : question.getQchild()){
					cq.setQccontent(QuestionUtil.imgTransformStyle(cq.getQccontent()));
					cq.setQcchoices(QuestionUtil.imgTransformStyle(cq.getQcchoices()));
					cq.setQccomment(QuestionUtil.imgTransformStyle(cq.getQccomment()));
					cq.setQcextension(QuestionUtil.imgTransformStyle(cq.getQcextension()));
					cq.setQcans(QuestionUtil.imgTransformStyle(cq.getQcans()));
					if(cq.getQcchoiceList()!=null&&cq.getQcchoiceList().size()>0){
						for(String s : cq.getQcchoiceList()){
							s = QuestionUtil.imgTransformStyle(s);
						}
					}
				}
			}

		}
		return question;
	}
	@Override
	public List<Question> getQuestionResultSet(Map<String,Object> filter) throws HttbException {
		StringBuffer pageSql = new StringBuffer("SELECT qid ,qpoint,qauditopinion,qcontent,qyear,qtype,qattribute,qstatus,createtime,createuser   FROM httb.HTQUESTION where tombstone = '" + DeleteFlag.NO.getDeleteFlag() + "'");
		if(CommonUtil.isNotNull(filter.get("year"))){
			String year = filter.get("year").toString();//年份
			pageSql.append(" and qyear = '"+year+"' ");
		}
		if(CommonUtil.isNotNull(filter.get("qbatchnum"))){
			String qbatchnum = filter.get("qbatchnum").toString();//批次
			pageSql.append(" and qbatchnum = '"+qbatchnum+"' ");
		}
		if(CommonUtil.isNotNull(filter.get("area"))){
			String area = filter.get("area").toString();//地区
			pageSql.append(" and qarea = '"+area+"' ");
		}
		if(CommonUtil.isNotNull(filter.get("type"))){
			String area = filter.get("type").toString();//题型
			pageSql.append(" and qtype = '"+area+"' ");
		}
		if(CommonUtil.isNotNull(filter.get("attribute"))){
			String area = filter.get("attribute").toString();//属性
			pageSql.append(" and qattribute = '"+area+"' ");
		}
		if(CommonUtil.isNotNull(filter.get("status"))){
			String area = filter.get("status").toString();//状态
			pageSql.append(" and qstatus = '"+area+"' ");
		}
		if(CommonUtil.isNotNull(filter.get("point"))){
			String[] points = filter.get("point").toString().split(",");//知识点
			for(String str : points){
				pageSql.append(" and qpoint contains'"+str+"' ");
			}
		}
		pageSql.append(" limit 100 allow filtering;");

		List<Question> lists = new ArrayList<Question>();
		ResultSet resultSet  = noSqlBaseDao.getResultSet(pageSql.toString());
		for (Row row : resultSet) {
//			获取row里字段 名称和类型
//			ColumnDefinitions  d = row.getColumnDefinitions();
//			for(int i=0;i<d.size();i++){
//				String name = d.getName(i);
//				DataType t = d.getType(i);
//				System.out.println(name+"==="+t);
//			}
			Question question = new Question();
			question.setQid(row.getString("qid"));	// 试题主键ID
			question.setQcontent(row.getString("qcontent"));	// 题干
			question.setQyear(row.getString("qyear")); 	// 年份
			question.setQtype(row.getString("qtype"));	//类型
			question.setQattribute(row.getString("qattribute"));	//属性
			question.setQstatus(row.getString("qstatus"));	//状态
			question.setCreatetime(row.getDate("createtime"));	//创建时间
			question.setCreateuser(row.getString("createuser"));	//创建人
			question.setQauditopinion(row.getString("qauditopinion"));	//创建人
			if(CommonUtil.isNotNull(question.getQcontent())){
				question.setQcontent(QuestionUtil.imgTransformStyle(question.getQcontent()));
			}
			lists.add(question);
		}
		return lists;
	}

	@Override
	public ResultSet getQuestionResultSet(Page page,Map<String,Object> filter) throws HttbException {
		return null;
	}
	@Override
	public List<Question> gets(List<String> qids) throws HttbException {
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
	 * {@inheritDoc}
	 */
	@Override
	public long queryCategoryUsedCount(Category category) throws HttbException
	{
		StringBuffer countSql = new StringBuffer("SELECT count(*) FROM httb.HTQUESTION where qpoint contains  '" +category.getCid() + "'");
		return noSqlBaseDao.getPagingCount(countSql.toString());
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long queryHthueshisCatUsedCount(Category category) throws HttbException
	{
		StringBuffer countSql = new StringBuffer("SELECT count(*) FROM httb.HTQUESHIS where qhpoint contains '" +category.getCid() + "'");
		return noSqlBaseDao.getPagingCount(countSql.toString());
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long queryHterrorCatUsedCount(Category category) throws HttbException
	{
		StringBuffer countSql = new StringBuffer("SELECT count(*) FROM httb.HTERROR where qpoint contains '" +category.getCid() + "'");
		return noSqlBaseDao.getPagingCount(countSql.toString());
	}


}
