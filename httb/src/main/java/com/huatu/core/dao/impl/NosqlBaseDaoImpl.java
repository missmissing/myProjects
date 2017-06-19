package com.huatu.core.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.CassandraConnectFactory;
import com.huatu.core.util.Constants;

@Repository
@Scope("prototype")
public class NosqlBaseDaoImpl implements NoSqlBaseDao{
	public Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private CassandraConnectFactory cassandraConnectFactory;

	@Override
	public Session getNoSqlSession() {
		return cassandraConnectFactory.getSession();
	}

	@Override
	public ResultSet executeSql(Statement statement) throws HttbException {
		statement.setConsistencyLevel(Constants.CASSANDRA_CONSISTENCY_WRITELEVEL);
		Session session = cassandraConnectFactory.getSession();
		ResultSet results = null;

		try {
			results = session.execute(statement);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "执行sql时发生异常", e);
		}finally{
			cassandraConnectFactory.closeSession(session);
		}
		return results;
	}

	@Override
	public ResultSet executeQuery(Statement statement) throws HttbException {
		statement.setConsistencyLevel(Constants.CASSANDRA_CONSISTENCY_READLEVEL);
		Session session = cassandraConnectFactory.getSession();
		ResultSet results = null;

		try {
			results = session.execute(statement);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "执行sql时发生异常", e);
		}finally{
			cassandraConnectFactory.closeSession(session);
		}
		return results;
	}

	@Override
	public List<Row> executeSearchSql(Statement statement) throws HttbException {
		statement.setConsistencyLevel(Constants.CASSANDRA_CONSISTENCY_WRITELEVEL);
		Session session = cassandraConnectFactory.getSession();
		List<Row> listRow = null;
		try {
			listRow = session.execute(statement).all();
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "执行sql时发生异常", e);
		}finally{
			cassandraConnectFactory.closeSession(session);
		}
		return listRow;
	}


	@Override
	public Object[] delete(String executeSQL) throws HttbException {

		Object[] obj = new Object[2];
		try {
			obj[0] = executeSql(executeSQL);
		} catch (Exception e) {
			obj[0] = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "删除数据时发生异常", e);
		}
		return obj;
	}

	@Override
	public Object[] delete(String executeSQL, Object... params) throws HttbException {
		Object[] obj = new Object[2];
		try {
			obj[0] = executeSqlParams(executeSQL,params);
		} catch (Exception e) {
			obj[0] = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "删除数据时发生异常", e);
		}
		return obj;
	}

	@Override
	public Object[] insert(String executeSQL) throws HttbException {
		Object[] obj = new Object[2];
		try {
			obj[0] = executeSql(executeSQL);
		} catch (Exception e) {
			obj[0] = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入数据时发生异常", e);
		}
		return obj;
	}

	@Override
	public Object[] insert(String executeSQL, Object... params) throws HttbException {
		Object[] obj = new Object[2];
		try {
			obj[0] = executeSqlParams(executeSQL, params);
		} catch (Exception e) {
			obj[0] = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入数据时发生异常", e);
		}
		return obj;
	}

	@Override
	public Object[] update(String executeSQL) throws HttbException {
		Object[] obj = new Object[2];
		try {
			obj[0] = executeSql(executeSQL);
		} catch (Exception e) {
			obj[0] = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_UPDATE, this.getClass() + "修改数据时发生异常", e);
		}
		return obj;
	}

	@Override
	public Object[] update(String executeSQL, Object... params) throws HttbException {
		Object[] obj = new Object[2];
		try {
			obj[0] = executeSqlParams(executeSQL, params);
		} catch (Exception e) {
			obj[0] = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_UPDATE, this.getClass() + "修改数据时发生异常", e);
		}
		return obj;
	}

	@Override
	public ResultSet getResultSet(String executeSQL) throws HttbException {
		// 获得session链接
		Session session = cassandraConnectFactory.getSession();
		// 查询结果集
		ResultSet resultSet;
		try {
			PreparedStatement statement = session.prepare(executeSQL);
			statement.setConsistencyLevel(Constants.CASSANDRA_CONSISTENCY_READLEVEL);
			BoundStatement boundStatement = new BoundStatement(statement);

			// 绑定参数
			boundStatement.setFetchSize(10000);
			resultSet = session.execute(boundStatement);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询数据resultSet对象时发生异常", e);
		} finally{
			// 关闭session连接
			cassandraConnectFactory.closeSession(session);
		}
		return resultSet;
	}

	@Override
	public List<Row> getAllRows(String executeSQL) throws HttbException {
		// 获得session链接
		Session session = cassandraConnectFactory.getSession();
		// 查询结果集
		List<Row> rows;
		try {
			PreparedStatement statement = session.prepare(executeSQL);
			statement.setConsistencyLevel(Constants.CASSANDRA_CONSISTENCY_READLEVEL);
			BoundStatement boundStatement = new BoundStatement(statement);
			rows = session.execute(boundStatement).all();
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询数据resultSet对象时发生异常", e);
		} finally{
			// 关闭session连接
			cassandraConnectFactory.closeSession(session);
		}
		return rows;
	}

	@Override
	public ResultSet getResultSet(String executeSQL, Object... params) throws HttbException {
		// 获得session链接
		Session session = cassandraConnectFactory.getSession();
		// 查询结果集
		ResultSet resultSet;
		try {
			PreparedStatement statement = session.prepare(executeSQL);
			statement.setConsistencyLevel(Constants.CASSANDRA_CONSISTENCY_READLEVEL);
			BoundStatement boundStatement = new BoundStatement(statement);
			// 绑定参数
			resultSet = session.execute(boundStatement.bind(params));
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "查询数据resultSet对象时发生异常", e);
		} finally{
			// 关闭session连接
			cassandraConnectFactory.closeSession(session);
		}
		return resultSet;
	}

	@Override
	public long getPagingCount(String countCql) throws HttbException {
		// 总条数
		long count = 0;
		Session session = null;
		try {
			session = cassandraConnectFactory.getSession();

			// 查询结果集
			ResultSet resultSet = null;
			PreparedStatement statement = session.prepare(countCql);
			statement.setConsistencyLevel(Constants.CASSANDRA_CONSISTENCY_READLEVEL);
			BoundStatement boundStatement = new BoundStatement(statement);
			// 绑定参数
			resultSet = session.execute(boundStatement);
			// 如果结果集不为空
			if (resultSet != null) {
				for (Row row : resultSet) {
					count = row.getLong(0);
				}
			}
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "查询分页数据条数时发生异常", e);
		} finally{
			// 关闭session连接
			cassandraConnectFactory.closeSession(session);
		}

		return count;
	}

	@Override
	public long getPagingCount(String countCql, Object... params) throws HttbException {
		Session session = cassandraConnectFactory.getSession();
		// 总条数
		long count = 0;
		// 查询结果集
		ResultSet resultSet = null;
		PreparedStatement statement = session.prepare(countCql);
		statement.setConsistencyLevel(Constants.CASSANDRA_CONSISTENCY_READLEVEL);
		BoundStatement boundStatement = new BoundStatement(statement);
		// 绑定参数
		resultSet = session.execute(boundStatement.bind(params));
		cassandraConnectFactory.closeSession(session);
		// 如果结果集不为空
		if (resultSet != null) {
			for (Row row : resultSet) {
				count = row.getLong(0);
			}
		}
		return count;
	}

	@Override
	public Object[] batchDispose(String executeSQL,List<Object[]> params) throws HttbException {
		Object[] obj = new Object[2];
		Session session = null;
		try {
			// 获取链接

			session = cassandraConnectFactory.getSession();
			BatchStatement batch = new BatchStatement();
			// 预编译SQL
			PreparedStatement statement = session.prepare(executeSQL);
			statement.setConsistencyLevel(Constants.CASSANDRA_CONSISTENCY_WRITELEVEL);
			// 遍历传入的参数
			for (int i = 0; i < params.size(); i++) {
				batch.add(statement.bind(params.get(i)));
			}
			// 执行批处理
			session.execute(batch);
			obj[0] = true;
		} catch (Exception e) {
			obj[0] = false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_DELETE, this.getClass() + "批量删除数据时发生异常", e);
		} finally{
			// 关闭session连接
			cassandraConnectFactory.closeSession(session);
		}
		return obj;
	}
	/**
	 *
	 * executeSQL (用于 增、删、改) 传入完整SQL语句，直接执行(增、删、改)
	 *
	 * @param executeSQL
	 *            执行SQL
	 * @return boolean 返回true/false
	 * @throws HttbException
	 *             封装的异常
	 */
	public boolean executeSql(String executeSQL) {
		boolean flag = true;
		Session session = null;
		try{
			PreparedStatement statement = session.prepare(executeSQL);
			statement.setConsistencyLevel(Constants.CASSANDRA_CONSISTENCY_WRITELEVEL);
			BoundStatement boundStatement = new BoundStatement(statement);
			session.execute(boundStatement);
		}catch(Exception e){
			e.printStackTrace();
			flag =false;
		} finally{
			// 关闭session连接
			cassandraConnectFactory.closeSession(session);
		}
		// 如果未出异常，标识删除成功
		return flag;
	}

	private boolean executeSqlParams(String executeSQL, Object... params)throws HttbException {
		boolean flag = true;
		Session session = null;
		try{
			// 获得session链接
			session = cassandraConnectFactory.getSession();
			PreparedStatement statement = session.prepare(executeSQL);
			statement.setConsistencyLevel(Constants.CASSANDRA_CONSISTENCY_WRITELEVEL);
			BoundStatement boundStatement = new BoundStatement(statement);
			// 绑定参数
			session.execute(boundStatement.bind(params));
		}catch(Exception e){
			flag =false;
		} finally{
			// 关闭session连接
			cassandraConnectFactory.closeSession(session);
		}
		// 如果未出异常，标识删除成功
		return flag;
	}

	/**
	 *
	 * batchExecuteSqlParams		(批处理方法)
	 * 								(批处理是调用)
	 * @param 		executeSQL		执行sql
	 * @param 		params			参数集合
	 * @return
	 * @throws HttbException
	 */
	public boolean batchExecuteSqlParams(String executeSQL, List<Object[]> params) throws HttbException{
		boolean flag = true;
		Session session = null;
		try{
			// 获得session链接
			session = cassandraConnectFactory.getSession();
			PreparedStatement statement = session.prepare(executeSQL);
			statement.setConsistencyLevel(Constants.CASSANDRA_CONSISTENCY_WRITELEVEL);
			BatchStatement batch = new BatchStatement();
			for (int i = 0; i < params.size(); i++) {
				batch.add(statement.bind(params.get(i)));
			}
			// 绑定参数
			session.execute(batch);
		}catch(Exception e){
			flag =false;
			throw new HttbException(ModelException.JAVA_CASSANDRA_BATCH, this.getClass() + "批量处理数据时发生异常", e);
		} finally{
			// 关闭session连接
			cassandraConnectFactory.closeSession(session);
		}
		// 如果未出异常，标识删除成功
		return flag;
	}
}
