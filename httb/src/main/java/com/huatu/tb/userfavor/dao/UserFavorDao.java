/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.userfavor.dao  
 * 文件名：				UserFavorDao.java    
 * 日期：				2015年5月13日-上午11:59:43  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.userfavor.dao;

import java.util.Map;

import com.datastax.driver.core.ResultSet;
import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Page;
import com.huatu.tb.userfavor.model.UserFavor;

/**   
 * 类名称：				UserFavorDao  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年5月13日 上午11:59:43  
 * @version 		1.0
 */
public interface UserFavorDao {
	/**
	 * 获取对象 
	 * @param id
	 * @return
	 * @throws HttbException
	 */
	public UserFavor get(String id)throws HttbException;
	/**
	 * 删除
	 * @return
	 * @throws HttbException
	 */
	public boolean delete(String id) throws HttbException;
	/**
	 * 插入
	 * @param executeSQL
	 * @return
	 * @throws HttbException
	 */
	public boolean insert(UserFavor favor) throws HttbException;
	/**
	 * 修改
	 * @param executeSQL
	 * @return
	 * @throws HttbException
	 */
	public boolean update(UserFavor favor) throws HttbException;
	/**
	 * 获取ResultSet对象
	 * @param params
	 * @return
	 * @throws HttbException
	 */
	public ResultSet getPagerResultSet(Map<String,Object> filter) throws HttbException;
	
	/**
	 * 获取ResultSet对象分页
	 * @param params
	 * @return
	 * @throws HttbException
	 */
	public ResultSet getPagerResultSet(Page page,Map<String,Object> filter) throws HttbException;
}
