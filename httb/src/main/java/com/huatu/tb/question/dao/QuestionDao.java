/**
 * 
 */
package com.huatu.tb.question.dao;

import java.util.List;
import java.util.Map;

import com.datastax.driver.core.ResultSet;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Page;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.question.model.Question;

/** 
 * @ClassName: QuestionDao 
 * @Description: 试题Dao接口
 * @author LiXin 
 * @date 2015年4月24日 上午10:44:21 
 * @version 1.0
 *  
 */
public interface QuestionDao {
	/**
	 * 获取对象 
	 * @param id
	 * @return
	 * @throws HttbException
	 */
	public Question get(String id)throws HttbException;
	/**
	 * 根据ids查询试题集合
	 * @param ids --试题Id集合
	 * @return
	 * @throws HttbException 
	 */
	public List<Question> gets(List<String> ids) throws HttbException;
	/**
	 * 删除
	 * @return
	 * @throws HttbException
	 */
	public boolean delete(String id) throws HttbException;
	/**
	 * 批量删除
	 * @return
	 * @throws HttbException
	 */
	public boolean deleteBatch(List<String> ids) throws HttbException;
	/**
	 * 插入
	 * @param executeSQL
	 * @return
	 * @throws HttbException
	 */
	public boolean insert(Question question) throws HttbException;
	/**
	 * 批量插入
	 * insertBatch  
	 * @exception 
	 * @param list
	 * @return
	 * @throws HttbException
	 */
	public boolean insertBatch(List<Question> list)throws HttbException;
	/**
	 * 修改
	 * @param executeSQL
	 * @return
	 * @throws HttbException
	 */
	public boolean update(Question question) throws HttbException;
	/**
	 * 获取ResultSet对象
	 * @param params
	 * @return
	 * @throws HttbException
	 */
	public List<Question> getQuestionResultSet(Map<String,Object> filter) throws HttbException;
	
	/**
	 * 获取ResultSet对象分页
	 * @param params
	 * @return
	 * @throws HttbException
	 */
	public ResultSet getQuestionResultSet(Page page,Map<String,Object> filter) throws HttbException;
	
	/**
	 * 查询试题表里 某知识点被引用的次数
	 * @return
	 * @throws HttbException
	 */
	public long queryCategoryUsedCount(Category category) throws HttbException;
	
	/**
	 * 查询 答题记录表里 某知识点被引用的次数
	 * @return
	 * @throws HttbException
	 */
	public long queryHthueshisCatUsedCount(Category category) throws HttbException;
	
	/**
	 * 查询 错题记录表里 某知识点被引用的次数
	 * @return
	 * @throws HttbException
	 */
	public long queryHterrorCatUsedCount(Category category) throws HttbException;
}
