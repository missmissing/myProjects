/**   
 * 项目名：				httb
 * 包名：				com.huatu.extractdata.util  
 * 文件名：				JdbcUtil.java    
 * 日期：				2015年6月3日-上午9:14:37  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text.extractdata.gwy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**   
 * 类名称：				JdbcUtil  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年6月3日 上午9:14:37  
 * @version 		1.0
 */
public class GwyJdbcUtil {
	public static final String url = "jdbc:mysql://123.103.79.113:3306/vhuatu";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "tikubackup";
	public static final String password = "tiku_#@!sub";
	
//	public static final String url = "jdbc:mysql://192.168.200.12:3306/httb";
//	public static final String name = "com.mysql.jdbc.Driver";
//	public static final String user = "root";
//	public static final String password = "htrbase";


	public Connection conn = null;
	public PreparedStatement pst = null;

	public GwyJdbcUtil(String sql) {
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
}
