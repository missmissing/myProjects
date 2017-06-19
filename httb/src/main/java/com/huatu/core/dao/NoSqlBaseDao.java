/**
 *
 */
package com.huatu.core.dao;

import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.huatu.core.exception.HttbException;

/**
 * @ClassName: 		NoSqlBaseDao
 * @Description:    NoSql数据库BaseDao
 * @author          LiXin
 * @date            2015年4月23日 下午5:28:52
 * @version         1.0
 *
 */
public interface NoSqlBaseDao{
	/**
	 * 获取Nosql Session
	 */
	public Session getNoSqlSession();

	/**
	 * 执行Statement对象--用于 Cassandra中的  增删改查
	 * executeSql 执行sql
	 * create 2015.05.07 lixin
	 * @param arg0
	 * @return
	 * @throws HttbException
	 */
	public ResultSet executeSql(Statement arg0) throws HttbException;

	/**
	 *
	 * executeQuery					(执行查询sql)
	 * 								(执行查询时调用)
	 * @param 		arg0			查询statement
	 * @return
	 * @throws 		HttbException
	 */
	public ResultSet executeQuery(Statement arg0) throws HttbException;

	/**
	 * 获取ResultSet对象[临时]
	 * @param executeSQL-执行sql
	 * @return ResultSet
	 * @throws HttbException
	 */
	public ResultSet getResultSet(String executeSQL)throws HttbException;
	/**
	 * 获取ResultSet对象[临时]
	 * @param executeSQL-执行sql
	 * @param 参数对象
	 * @return ResultSet
	 * @throws HttbException
	 */
	public ResultSet getResultSet(String executeSQL, Object... params)throws HttbException;
	/**
	 * 获取查询条数
	 * @param countCql -- 执行sql
	 * @return
	 * @throws HttbException
	 */
	public long getPagingCount(String countCql)throws HttbException;
	/**
	 * 获取查询条数
	 * @param countCql--执行sql
	 * @param params --参数对象
	 * @return
	 * @throws HttbException
	 */
	public long getPagingCount(String countCql, Object... params)throws HttbException;
	/**
	 * 调用删除SQL
	 * @param executeSQL 删除SQL语句或命令ID
	 * @return 删除成功与否【true/成功  false/失败】和删除数量的Object数组
	 * @throws HttbException
	 */
	public Object[] delete(String executeSQL) throws HttbException;

	/**
	 * 调用删除SQL进行条件删除
	 *
	 * @param executeSQL 删除SQL语句或命令ID
	 * @param params 删除参数集合(map或数组)
	 * @return 删除成功与否【true/成功  false/失败】和删除数量的Object数组
	 * @throws HttbException
	 */
	public Object[] delete(String executeSQL, Object... params) throws HttbException;

	/**
	 * 调用插入SQL
	 *
	 * @param executeSQL SQL语句或命令ID
	 * @return 插入成功与否【true/成功  false/失败】和插入数量的Object数组
	 * @throws HttbException
	 */
	public Object[] insert(String executeSQL) throws HttbException;

	/**
	 * 调用插入SQL插入对象
	 *
	 * @param executeSQL SQL语句或命令ID
	 * @param params 参数对象/Map
	 * @return 插入成功与否【true/成功  false/失败】和插入数量的Object数组
	 * @throws HttbException
	 */
	public Object[] insert(String executeSQL, Object... params) throws HttbException;
	/**
	 * 更新
	 * @param executeSQL SQL语句或命令ID
	 * @return 更新成功与否【true/成功  false/失败】和更新数量的Object数组
	 * @throws HttbException
	 */
	public Object[] update(String executeSQL) throws HttbException;

	/**
	 * 更新
	 *
	 * @param executeSQL SQL语句或命令ID
	 * @param params
	 * @return 更新成功与否【true/成功  false/失败】和更新数量的Object数组
	 * @throws HttbException
	 */
	public Object[] update(String executeSQL, Object... params) throws HttbException;

	/**
	 * 批处理【服务批量 修改 删除  插入 】
	 * @param sql
	 * @param params
	 * @return
	 * @throws HttbException
	 */
	public Object[] batchDispose(String executeSQL,List<Object[]> params) throws HttbException ;

	/**
	 *
	 * batchExecuteSqlParams		(批处理方法)
	 * 								(批处理是调用)
	 * @param 		executeSQL		执行sql
	 * @param 		params			参数集合
	 * @return
	 * @throws HttbException
	 */
	public boolean batchExecuteSqlParams(String executeSQL, List<Object[]> params) throws HttbException;

	public boolean executeSql(String executeSQL);

	public List<Row> executeSearchSql(Statement statement) throws HttbException;


	/**
	 * getAllRows					(查询所有行数据)
	 * 								(需要查询数据量太大时调用--需要注意如果数据量特别大，最好做好分页查询)
	 * @param 		executeSQL		查询SQL
	 * @return
	 * @throws 		HttbException
	 */
	public List<Row> getAllRows(String executeSQL) throws HttbException;
}
