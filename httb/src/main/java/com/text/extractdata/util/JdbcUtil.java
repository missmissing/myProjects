/**   
 * 项目名：				httb
 * 包名：				com.text.extractdata.util  
 * 文件名：				JdbcUtil.java    
 * 日期：				2015年6月26日-下午1:49:29  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text.extractdata.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**   
 * 类名称：				JdbcUtil  
 * 类描述：  			DB工具类
 * 创建人：				LiXin
 * 创建时间：			2015年6月26日 下午1:49:29  
 * @version 		1.0
 */
public class JdbcUtil {
	
	public static final String url = "jdbc:mysql://123.103.79.113:3306/vhuatuitems";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "huatuedu";
	public static final String password = "huatuedu_2015";

	public Connection conn = null;
	public PreparedStatement pst = null;

	public JdbcUtil(String sql) {
		try {
			Class.forName(name);//指定连接类型
			conn = DriverManager.getConnection(url, user, password);//获取连接
			pst = conn.prepareStatement(sql);//准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
		JdbcUtil db1 = null;
		ResultSet ret = null;
		String quessql = "select t1.PUKEY,t1.is_multi_part , multi_id from v_obj_question t1, v_pastpaper_question_r t2 where t1.PUKEY=t2.question_id and  t2.pastpaper_id=1683";// SQL语句
		db1 = new JdbcUtil(quessql);
		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集
			while (ret.next()) {
				System.out.println(ret.getString(1)+"-"+ret.getString(2)+"-"+ret.getString(3)); 
			}
			ret.close();
			db1.close();// 关闭连接
		}catch(Exception e){
		}
		
	}

}
