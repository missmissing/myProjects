package com.huatu.demo;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Row;

public class TestMain {
	public static void main(String[] args) {
		BaseDao bd = new BaseDao();
		bd.createSchema();

		//delete(bd);
		//insert(bd);
		search(bd);
	}

	private static void delete(BaseDao bd){
		String delSql = "delete from simplex.songs where id = 756716f7-2e54-4715-9f00-91dcbea6cf50";
		bd.delete(delSql);
	}

	private static  void insert(BaseDao bd){

		String insertSql = "INSERT INTO simplex.songs (id, title, album, artist, tags) "
				+ "VALUES ("
				+ UUID.randomUUID()+","
				+ "'作者',"
				+ "'再见',"
				+ "'Joséphine Baker'," + "{'jazz', '2013'})" + ";";
		bd.insert(insertSql);

		String insertSql2 = "INSERT INTO simplex.songs (id, title, album, artist, tags) "
				+ "VALUES (?,?,?,?,?);";
		Set<String> tags = new HashSet<String>();
		tags.add("bbbbb");
		tags.add("22222");

		Object[] params= new Object[5];
		params[0] = UUID.randomUUID();
		params[1] = "'艾俊波'";
		params[2] = "'测试'";
		params[3] = "'Joséphine Baker'";
		params[4] = tags;

		bd.insert(insertSql2, params);
	}

	private static void search(BaseDao bd){
		//bd.search();
		ResultSetFuture results = bd.getRows("simplex", "songs", UUID.fromString("46b41c2c-b340-4d32-a8e4-ab8b17fbf71c"));

		for (Row row : results.getUninterruptibly()) {
			System.out.printf( "%s: %s / %s\n",
			row.getString("artist"),
			row.getString("title"),
			row.getString("album") );
		}
	}
}
