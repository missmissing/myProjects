/**
 * 
 */
package com.huatu.tb.question.service;

import java.util.List;
import java.util.Map;

import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Page;
import com.huatu.tb.question.model.Question;

/** 
 * @ClassName: QuestionService 
 * @Description: 试题Service
 * @author LiXin 
 * @date 2015年4月20日 上午10:42:35 
 * @version 1.0
 *  
 */
public interface QuestionService {
	
	/**
	 * 添加
	 * @param 对象
	 * @return
	 * @throws HttbException
	 */
	public boolean save(Question question)throws HttbException;
	
	/**
	 * 批量保存
	 * @return
	 */
	public boolean saveBatch(List<Question> list)throws HttbException; 

	/**
	 * 删除 
	 * @param 对象
	 * @return
	 * @throws HttbException
	 */
	public boolean delete(Question question)throws HttbException;
	/**
	 * 删除 通过ID
	 * @param 对象id
	 * @return
	 * @throws HttbException
	 */
	public boolean delete(String id)throws HttbException;
	/**
	 * 通过Ids批量删除
	 * @param list
	 * @return
	 * @throws HttbException
	 */
	public boolean deleteToIds(List<String> list)throws HttbException;
	/**
	 * 修改 
	 * @param condition
	 * @return
	 * @throws HttbException 
	 */
	public boolean update(Question question) throws HttbException;
	
	/**
	 * 批量修改
	 * @param list
	 * @return
	 * @throws HttbException 
	 */
	public boolean updateBatch(List<Question> list) throws HttbException;
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @throws HttbException 
	 */
	public Question get(String id) throws HttbException;
	/**
	 * 根据ids查询试题集合
	 * @param ids --试题Id集合
	 * @return
	 * @throws HttbException 
	 */
	public List<Question> gets(List<String> ids) throws HttbException;
	 
	 /**
	  * 获取全都数据
	  * @return  List 
	  * @exception    
	  * @since  1.0.0
	  */
	public List<Question> findAll()throws HttbException ; 
	
	/**
	 * 查询集合
	 * @param condition 条件过滤Map
	 * @return
	 * @throws HttbException
	 */
	public List<Question> findList(Map<String, Object> condition) throws HttbException ;
	/**
	 * 分页查询
	 * @param condition 条件过滤Map
	 * @param startpage 开始第几行
	 * @param pageSize 每页多少行
	 * @param orderBy 排序字段
	 * @return
	 * @throws HttbException
	 */
	public Page<Question> findPage(Map<String, Object> condition) throws HttbException ; 
}
