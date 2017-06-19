/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.paper.dao  
 * 文件名：				PaperDao.java    
 * 日期：				2015年5月11日-下午5:09:59  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.paper.dao;

import java.util.List;
import java.util.Map;

import com.datastax.driver.core.ResultSet;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Page;
import com.huatu.tb.paper.model.Paper;
import com.huatu.tb.question.model.Question;

/**   
 * 类名称：				PaperDao  
 * 类描述：  			试题Dao接口
 * 创建人：				LiXin
 * 创建时间：			2015年5月11日 下午5:09:59  
 * @version 		1.0
 */
public interface PaperDao {
	/**
	 * 获取对象 
	 * @param id
	 * @return
	 * @throws HttbException
	 */
	public Paper get(String id)throws HttbException;
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
	public boolean insert(Paper paper) throws HttbException;
	/**
	 * 修改
	 * @param executeSQL
	 * @return
	 * @throws HttbException
	 */
	public boolean update(Paper paper) throws HttbException;
	/**
	 * 修改试卷中试题列表
	 * @param executeSQL
	 * @return
	 * @throws HttbException
	 */
	public boolean updateQuesList(Paper paper) throws HttbException;
	
	/**
	 * 获取ResultSet对象
	 * @param params
	 * @return
	 * @throws HttbException
	 */
	public ResultSet getPaperResultSet(Map<String,Object> filter) throws HttbException;
	
	/**
	 * 获取ResultSet对象分页
	 * @param params
	 * @return
	 * @throws HttbException
	 */
	public ResultSet getPaperResultSet(Page page,Map<String,Object> filter) throws HttbException;
}
