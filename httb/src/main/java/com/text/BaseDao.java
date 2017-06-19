package com.text;

import java.util.Date;
import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

public class BaseDao {
	private Cluster cluster;
	private Session session;

	public void initPool(String node) {
		PoolingOptions poolingOptions = new PoolingOptions();
		poolingOptions.setMaxSimultaneousRequestsPerConnectionThreshold(
				HostDistance.LOCAL, 128);
		poolingOptions.setCoreConnectionsPerHost(HostDistance.LOCAL, 1);
		poolingOptions.setMaxConnectionsPerHost(HostDistance.LOCAL, 100000);

		cluster = Cluster.builder().addContactPoints(node)
				.withPoolingOptions(poolingOptions).build();
	}

	public Session getSession() {
		if (cluster == null || cluster.isClosed()) {
			initPool("192.168.200.5");
		}
		session = cluster.connect();
		return session;
	}

	public void closeSession(Session session) {
		//session.close();
		session.getCluster().close();
	}

//	public void insert(List<result> list){
//		 Insert[] inserts = new Insert[list.size()];
//		 for (int j = 0; j < list.size(); j++) {
//
//			Insert insert = QueryBuilder.insertInto("testai", "result");
//			insert.value("rid",list.get(j).getRid());// 试题主键
//			insert.value("qid",list.get(j).getQid());
//			insert.value("uid",list.get(j).getUid());
//			insert.value("qans",list.get(j).getQans());
//			insert.value("uans",list.get(j).getUans());
//			insert.value("isright",list.get(j).getIsright());
//			insert.value("rtype",list.get(j).getRtype());
//			insert.value("createtime",new Date());
//			inserts[j] = insert;
//
//		}
//		Batch batch = QueryBuilder.batch(inserts);
//		Session session = getSession();
//		session.execute(batch);
//		closeSession(session);
//	}

	public void searchCount(){
		Select select = QueryBuilder.select().column("rid").from("testai", "result");
		Session session = getSession();
		ResultSet re = session.execute(select);
		System.out.println("======================当前数据库中数量："+re.all().size());

		closeSession(session);
	}


	public void search(){
		Select select = QueryBuilder.select()
		.all()
		.from("httb", "htcate_ques");


		select.limit(1000000);
		select.allowFiltering();
		Session session = getSession();
		ResultSet re = session.execute(select);
	/*	for (Row row : re) {
			System.out.println(row.getString(0));
			System.out.println(row.getString(1));
		}*/
		System.out.println("======================当前数据库中数量："+re.all().size());
		closeSession(session);
	}

}
