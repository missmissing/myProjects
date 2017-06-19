package com.huatu.demo;

import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Select.Where;

public class BaseDao {
	private Cluster cluster;
	private Session session;

	public void initPool(String node) {
		PoolingOptions poolingOptions = new PoolingOptions();
		poolingOptions.setMaxSimultaneousRequestsPerConnectionThreshold(
				HostDistance.LOCAL, 32);
		poolingOptions.setCoreConnectionsPerHost(HostDistance.LOCAL, 2);
		poolingOptions.setMaxConnectionsPerHost(HostDistance.LOCAL, 4);

		cluster = Cluster.builder().addContactPoints(node)
				.withPoolingOptions(poolingOptions).build();
	}

	public Session getSession() {
		if (cluster == null) {
			initPool("192.168.2.108");
		}
		session = cluster.connect();
		return session;
	}

	public void delete(String sql) {
		getSession().execute(sql);
		getSession().close();
	}

	public void delete(String sql, Object... params) {
		PreparedStatement statement = getSession().prepare(sql);
		BoundStatement boundStatement = new BoundStatement(statement);
		getSession().execute(boundStatement.bind(params));
	}

	public void insert(String sql) {
		getSession().execute(sql);
		getSession().close();
	}

	public void insert(String sql, Object... params) {
		PreparedStatement statement = getSession().prepare(sql);
		BoundStatement boundStatement = new BoundStatement(statement);
		getSession().execute(boundStatement.bind(params));

		getSession().close();
	}

	public void createSchema() {
		getSession()
				.execute(
						"CREATE KEYSPACE if not exists simplex WITH replication "
								+ "= {'class':'SimpleStrategy', 'replication_factor':3};");

		getSession().execute(
				"CREATE TABLE IF NOT EXISTS simplex.songs (" + "id uuid PRIMARY KEY,"
						+ "title text," + "album text," + "artist text,"
						+ "tags set<text>," + "data int" + ");");
		getSession().execute(
				"CREATE TABLE IF NOT EXISTS simplex.playlists (" + "id uuid," + "title text,"
						+ "album text, " + "artist text," + "song_id uuid,"
						+ "PRIMARY KEY (id, title, album, artist)" + ");");
	}

	public void search() {
		ResultSet results = getSession()
				.execute("SELECT * FROM simplex.songs;");
		System.out
				.println(String
						.format("%-30s\t%-20s\t%-20s\t%-20s\n%s",
								"album",
								"artist",
								"title",
								"tags",
								"-------------------------------+-----------------------+--------------------+--------------------"));
		for (Row row : results) {
			System.out.println(String.format("%-30s\t%-20s\t%-20s\t%-20s",
					row.getString("album"), row.getString("artist"),
					row.getString("title"), row.getSet("tags", String.class)));
		}
	}

	public ResultSetFuture getRows(String keyspace,String table) {
		Select query = QueryBuilder.select().all().from(keyspace, table);
		return getSession().executeAsync(query);
	}

	public ResultSetFuture getRows(String keyspace,String table,UUID uuid) {
		Where where = QueryBuilder.select().all().from(keyspace, table).where(QueryBuilder.eq("id", uuid));
		return getSession().executeAsync(where);
	}
}
