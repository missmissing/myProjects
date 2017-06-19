package com.huatu.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

public class TestBatch {
	public static void main(String[] args) {
		//batch();
		//batchInsert();
		//batchDel();
		List<Object[]> list = new ArrayList<Object[]>();
		Object[] params = new Object[1];
		params[0] = UUID.fromString("5e271e98-d46b-4bdf-8f27-0064c248b9c8");

		Object[] params1 = new Object[1];
		params1[0] = UUID.fromString("5b0ba6a6-f6ac-46d4-8a1a-5d5346b9266d");

		Object[] params2 = new Object[1];
		params2[0] = UUID.fromString("a4500822-c21a-40e9-af49-abf4ac202e1a");

		Object[] params3 = new Object[1];
		params3[0] = UUID.fromString("9fc3a61d-6af5-4ec7-8221-546ed283c4a0");

		Object[] params4 = new Object[1];
		params4[0] = UUID.fromString("2d25e4f4-f6ad-4038-9461-0de5551fb69a");

		Object[] params5 = new Object[1];
		params5[0] = UUID.fromString("b757d8ec-de62-4540-a9e3-9da5600a374e");


		/*
		list.add(params);
		list.add(params1);
		list.add(params2);
		list.add(params3);
		list.add(params4);
		list.add(params5);*/

		batchDel(list);
	}

	private static void batchInsert() {
		BaseDao bd = new BaseDao();
		Session session = bd.getSession();
		BatchStatement batch = new BatchStatement();
		PreparedStatement insertSongPreparedStatement = session
				.prepare("INSERT INTO simplex.songs (id, title, album, artist) VALUES(?, ?, ?, ?);");

		batch.add(insertSongPreparedStatement.bind(UUID.randomUUID(),
				"Die Mösch", "In Gold", "WilliOstermann"));
		batch.add(insertSongPreparedStatement.bind(UUID.randomUUID(),
				"Memo From Turner", "Performance", "MickJagger"));
		batch.add(insertSongPreparedStatement.bind(UUID.randomUUID(),
				"La Petite Tonkinoise", "Bye ByeBlackbird", "Joséphine Baker"));
		session.execute(batch);
	}

	private static void batch(){
		BaseDao bd = new BaseDao();
		Session session = bd.getSession();
		PreparedStatement insertPreparedStatement = session.prepare(
				"BEGIN BATCH "+
				" INSERT INTO simplex.songs (id, title, album, artist) " +
				"VALUES (?, ?, ?, ?); " +
				" INSERT INTO simplex.songs (id, title, album, artist) " +
				"VALUES (?, ?, ?, ?); " +
				" INSERT INTO simplex.songs (id, title, album, artist) " +
				"VALUES (?, ?, ?, ?); " +
				"APPLY BATCH"
				);
		session.execute(
				insertPreparedStatement.bind(
				UUID.randomUUID(), "Seaside Rendezvous1", "A Night at the Opera", "Queen",
				UUID.randomUUID(), "Entre Nous1", "Permanent Waves",
				"Rush",
				UUID.randomUUID(), "Frank Sinatra1", "Fashion Nugget", "Cake"
				));
		session.close();
	}


	private static void batchDel(){
		BaseDao bd = new BaseDao();
		Session session = bd.getSession();
		BatchStatement batch = new BatchStatement();
		PreparedStatement delPreparedStatement = session.prepare("delete from simplex.songs where id = ?");

		batch.add(delPreparedStatement.bind(UUID.fromString("dab78d59-d43b-4090-9cef-948ee27bbff8")));
		batch.add(delPreparedStatement.bind(UUID.fromString("3d7dcd07-bbd9-4dcd-bed1-e0d5c3581009")));
		batch.add(delPreparedStatement.bind(UUID.fromString("17397657-d6c5-44f3-a724-4ba783db55b9")));
		batch.add(delPreparedStatement.bind(UUID.fromString("4b7bb611-3e8f-4252-a57f-2d5fc68a1476")));
		batch.add(delPreparedStatement.bind(UUID.fromString("76661f20-5722-42a3-9b9a-ee28d0bf875c")));
		batch.add(delPreparedStatement.bind(UUID.fromString("a97612a5-40c3-405f-83d2-d8f9477e491e")));
		batch.add(delPreparedStatement.bind(UUID.fromString("eb9e48b2-6e39-461b-a1cd-07fad8fce14c")));
		batch.add(delPreparedStatement.bind(UUID.fromString("aca59573-6385-405f-aa5a-a5f7234f9c18")));
		batch.add(delPreparedStatement.bind(UUID.fromString("9ff18f94-6fcb-42d3-9a16-7d7153120b5f")));
		session.execute(batch);
	}

	private static void batchDel(List<Object[]> paramslist){
		BaseDao bd = new BaseDao();
		Session session = bd.getSession();
		BatchStatement batch = new BatchStatement();
		PreparedStatement delPreparedStatement = session.prepare("delete from simplex.songs where id = ?");

		for (int i = 0; i < paramslist.size(); i++) {
			batch.add(delPreparedStatement.bind(paramslist.get(i)));
		}
		session.execute(batch);
	}
}
