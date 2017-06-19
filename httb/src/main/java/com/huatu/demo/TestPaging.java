package com.huatu.demo;

import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class TestPaging {
	static Session session;
	static Long count;
	public static void main(String[] args) {
		boolean flag = true;
		String pagingSQL = "select * from httb.users limit 5;";
		UUID cid = paging(pagingSQL);

		String pagingSQL2 = "select * from httb.users where token(id)>token( ?) and age = 13 limit 5 allow filtering;";
		String pageCount = "select count(*) from httb.users where token(id)>token( ?) and age = 13  limit 5 allow filtering;";
		//while(flag){
			Object[] params = new Object[1];
			params[0] = cid;
			UUID cid2 = paging(pageCount,pagingSQL2,params);
			if(cid == cid2){
				flag = false;
			}else{
				cid = cid2;
			}
			System.out.println(count);
		//}

	}

	private static UUID paging(String executeSQL) {
		BaseDao bd = new BaseDao();
		session = bd.getSession();
		ResultSet results = session.execute(executeSQL);
		session.close();
		UUID cid = null;
		System.out
				.println(String
						.format("%-40s\t%-20s\t%-20s\t%-20s\n%s",
								"id",
								"name",
								"first_name",
								"regdate",
								"---------------------------------------------+-----------------------+--------------------+--------------------"));
		for (Row row : results) {
			cid = row.getUUID("id");
			System.out.println(String.format("%-40s\t%-20s\t%-20s\t%-20s", cid,
					row.getInt("age"), row.getString("first_name"),
					row.getDate("regdate")));
		}
		return cid;
	}

	private static UUID paging(String countSQL, String executeSQL, Object... params) {
		BaseDao bd = new BaseDao();
		session = bd.getSession();

		PreparedStatement statement1 = session.prepare(countSQL);
		BoundStatement boundStatement1 = new BoundStatement(statement1);
		ResultSet results1 = session.execute(boundStatement1.bind(params));
		for (Row row : results1) {
			 count = row.getLong(0);
		}

		UUID cid = null;
		PreparedStatement statement = session.prepare(executeSQL);
		BoundStatement boundStatement = new BoundStatement(statement);
		ResultSet results = session.execute(boundStatement.bind(params));
		session.close();
		System.out
				.println(String
						.format("%-40s\t%-20s\t%-20s\t%-20s\n%s",
								"id",
								"name",
								"first_name",
								"regdate",
								"---------------------------------------------+-----------------------+--------------------+--------------------"));
		for (Row row : results) {
			cid = row.getUUID("id");
			System.out.println(String.format("%-40s\t%-20s\t%-20s\t%-20s", cid,
					row.getInt("age"), row.getString("first_name"),
					row.getDate("regdate")));
		}

		return cid;
	}
}
