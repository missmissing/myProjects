/**
 * 
 */
package com.huatu.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.huatu.core.exception.HttbException;

/** 
 * @ClassName: 		SqlBaseDao 
 * @Description: 	传统sqlBaseDao 
 * @author 			 
 * @date 			2015年4月23日 下午5:28:24 
 * @version 		1.0
 *  
 */
public interface SqlBaseDao {

	public SqlSession getSqlSession();
	
	/**
	 * 清除缓存 
	 */
	public void clearCache() ;

	/**
	 * 
	 * @param force
	 */
	public void commit(boolean force);

	/**
	 * 调用删除命令
	 * 
	 * @param commandId
	 * @return 删除数量
	 */
	public int delete(String commandId) throws HttbException;

	/**
	 * 调用删除命令进行条件删除
	 * 
	 * @param commandId
	 * @param params
	 * @return 删除数量
	 */
	public int delete(String commandId, Object params) throws HttbException;

	/**
	 * 调用插入命令
	 * 
	 * @param commandId
	 * @return 插入数量
	 */
	public int insert(String commandId) throws HttbException;

	/**
	 * 调用插入命令插入对象
	 * 
	 * @param commandId 
	 * @param params 参数对象/Map
	 * @return 插入数量
	 */
	public int insert(String commandId, Object params) throws HttbException;
	/**
	 * 查询列表
	 * 
	 * @param <T>
	 * @param commandId
	 * @return 
	 */
	public <T> List<T> selectList(String commandId) throws HttbException;
	/**
	 * 
	 * @param <T>
	 * @param commandId
	 * @param params
	 * @return
	 */
	public <T> List<T> selectList(String commandId, Object params) throws HttbException;

	/**
	 * 
	 * @param <T>
	 * @param commandId
	 * @param params
	 * @param page
	 * @param size
	 * @return
	 */
	public <T> List<T> selectList(String commandId, Map<String, Object> params, int page, int size) throws HttbException;

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param commandId
	 * @param mapKey
	 * @return
	 */
	public <K, V> Map<K, V> selectMap(String commandId, String mapKey);

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param commandId
	 * @param params
	 * @param mapKey
	 * @return
	 */
	public <K, V> Map<K, V> selectMap(String commandId, Object params, String mapKey);

	/**
	 * 
	 * @param commandId
	 * @return
	 * @throws HttbException 
	 */
	public <T> T get(String commandId) throws HttbException;

	/**
	 * 
	 * @param <T>
	 * @param commandId
	 * @param params
	 * @return
	 */
	public <T> T get(String commandId, Object params) throws HttbException;
	/**
	 * 
	 * @param commandId
	 * @return
	 */
	public int update(String commandId) throws HttbException;

	/**
	 * 更新
	 * 
	 * @param commandId
	 * @param params
	 * @return
	 * @throws HttbException 
	 */
	public int update(String commandId, Object params) throws HttbException;
}
