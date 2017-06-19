/**
 * 
 */
package com.text.extractdata.filterques.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author liyulong
 *
 */
public class FilterMysqlJdbcUtil
{
	// 公务员 mysql 连接mysql参数
	public static final String url0 = "jdbc:mysql://123.103.79.113:3306/vhuatu";
	public static final String name0 = "com.mysql.jdbc.Driver";
	public static final String user0 = "tikubackup";
	public static final String password0 = "tiku_#@!sub";

	// 事业单位 教师 连接mysql参数
	public static final String url1 = "jdbc:mysql://123.103.79.113:3306/vhuatu";
	public static final String name1 = "com.mysql.jdbc.Driver";
	public static final String user1 = "tikubackup";
	public static final String password1 = "tiku_#@!sub";

	public Connection conn = null;
	public PreparedStatement pst = null;

	/**
	 * 查询
	 * @param sql
	 * @param filterType
	 *            300：获取公务员题库的mysql 查询连接  
	 *            400：获得教师 题库mysql 查询连接
	 *            500：获得事业单位 题库mysql 查询连接
	 */
	public FilterMysqlJdbcUtil(String sql, int filterType)
	{

		try
		{

			if (ConstantCompareUtil.GET_GWY_MYSQL_JDBC == filterType)
			{
				Class.forName(name0);// 指定连接类型
				conn = DriverManager.getConnection(url0, user0, password0);// 获取连接
			} 
			else if (ConstantCompareUtil.GET_JS_MYSQL_JDBC == filterType)
			{
				Class.forName(name1);// 指定连接类型
				conn = DriverManager.getConnection(url1, user1, password1);// 获取连接
			} 
			else if (ConstantCompareUtil.GET_SYDW_MYSQL_JDBC == filterType)
			{
				Class.forName(name1);// 指定连接类型
				conn = DriverManager.getConnection(url1, user1, password1);// 获取连接
			} 
			else
			{
				System.out.println("mysql 连接库选择不正确");
				return;
			}

			pst = conn.prepareStatement(sql);// 准备执行语句
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void close()
	{
		try
		{
			this.conn.close();
			this.pst.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
