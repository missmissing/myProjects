/**   
 * 项目名：				httb
 * 包名：				com.huatu.core.dao.impl  
 * 文件名：				SqlBaseDaoImpl.java    
 * 日期：				2015年5月7日-下午8:13:27  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.core.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huatu.core.dao.SqlBaseDao;
import com.huatu.core.exception.HttbException;

/**   
 * 类名称：				SqlBaseDaoImpl  
 * 类描述：  			传统sqlBaseDao实现类
 * 创建人：				Root
 * 创建时间：			2015年4月8日 下午8:13:27  
 * @version 		1.0
 */
@Repository
public class SqlBaseDaoImpl implements SqlBaseDao {
	@Autowired
	protected SqlSession session;

	public SqlSession getSqlSession() {

		return session;
	}

	/**
	 * 清除缓存
	 */
	public void clearCache() {
		session.clearCache();
	}

	/**
	 * 
	 * @param force
	 */
	public void commit(boolean force) {
		session.commit(force);
	}

	/**
	 * 调用删除命令
	 * 
	 * @param commandId
	 * @return 删除数量
	 */
	public int delete(String commandId) throws HttbException{

		return session.delete(commandId);
	}

	/**
	 * 调用删除命令进行条件删除
	 * 
	 * @param commandId
	 * @param params
	 * @return 删除数量
	 */

	public int delete(String commandId, Object params) throws HttbException{

		return session.delete(commandId, params);
	}

	/**
	 * 调用插入命令
	 * 
	 * @param commandId
	 * @return 插入数量
	 */
	public int insert(String commandId) throws HttbException{
		return session.insert(commandId);
	}

	/**
	 * 调用插入命令插入对象
	 * 
	 * @param commandId
	 * @param params
	 *            参数对象/Map
	 * @return 插入数量
	 */
	public int insert(String commandId, Object params) throws HttbException{
		return session.insert(commandId, params);
	}

	/**
	 * 查询列表
	 * 
	 * @param <T>
	 * @param commandId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> selectList(String commandId) throws HttbException{
		return (List<T>) session.selectList(commandId);
	}

	/**
	 * 
	 * @param <T>
	 * @param commandId
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> selectList(String commandId, Object params) throws HttbException{
		return (List<T>) session.selectList(commandId, params);
	}

	/**
	 * 
	 * @param <T>
	 * @param commandId
	 * @param params
	 * @param page
	 * @param size
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> selectList(String commandId, Map<String, Object> params,
			int page, int size) throws HttbException{
		return (List<T>) session.selectList(commandId, params);
	}

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param commandId
	 * @param mapKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <K, V> Map<K, V> selectMap(String commandId, String mapKey) {
		return (Map<K, V>) session.selectMap(commandId, mapKey);
	}

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param commandId
	 * @param params
	 * @param mapKey
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <K, V> Map<K, V> selectMap(String commandId, Object params,
			String mapKey) {
		return (Map<K, V>) session.selectMap(commandId, params, mapKey);
	}

	/**
	 * 
	 * @param commandId
	 * @return
	 * @throws HttbException 
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String commandId) throws HttbException {
		return (T) session.selectOne(commandId);
	}

	/**
	 * 
	 * @param <T>
	 * @param commandId
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String commandId, Object params) throws HttbException{
		return (T) session.selectOne(commandId, params);
	}

	/**
	 * 
	 * @param commandId
	 * @return
	 */
	public int update(String commandId) throws HttbException{
		return session.update(commandId);
	}

	/**
	 * 更新
	 * 
	 * @param commandId
	 * @param params
	 * @return
	 */
	public int update(String commandId, Object params) throws HttbException{
		return session.update(commandId, params);
	}

}
