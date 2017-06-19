package com.huatu.core.util;

import org.apache.log4j.Logger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import com.datastax.driver.core.policies.ReconnectionPolicy;

public class CassandraConnectFactory {
	private static Logger logger = Logger.getLogger(CassandraConnectFactory.class);
	private String node;

	//登录名
	private String name;

	//密码
	private String pwd;

	//每个连接允许请求并发数量
	private Integer maxSimultaneousRequests;

	//集群里的机器至少有几个连接
	private Integer coreConnectionsPerHost;

	//集群里的机器最多有几个连接
	private Integer maxConnectionsPerHost;

	public String getNode() {
		return node;
	}

	//设置节点
	public void setNode(String node) {
		this.node = node;
	}

	public String getName() {
		return name;
	}

	//设置登录名
	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	//设置密码
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getMaxSimultaneousRequests() {
		//如果没在配置文件中进行配置,则在这里设置默认值
		if(maxSimultaneousRequests == null){
			maxSimultaneousRequests = 32;
		}
		return maxSimultaneousRequests;
	}

	public void setMaxSimultaneousRequests(Integer maxSimultaneousRequests) {
		this.maxSimultaneousRequests = maxSimultaneousRequests;
	}

	public Integer getCoreConnectionsPerHost() {
		//如果没在配置文件中进行配置,则在这里设置默认值
		if(coreConnectionsPerHost == null){
			coreConnectionsPerHost = 1;
		}
		return coreConnectionsPerHost;
	}

	public void setCoreConnectionsPerHost(Integer coreConnectionsPerHost) {
		this.coreConnectionsPerHost = coreConnectionsPerHost;
	}

	public Integer getMaxConnectionsPerHost() {
		//如果没在配置文件中进行配置,则在这里设置默认值
		if(maxConnectionsPerHost == null){
			maxConnectionsPerHost = 4;
		}
		return maxConnectionsPerHost;
	}

	public void setMaxConnectionsPerHost(Integer maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	//集群对象
	private Cluster cluster;

	public void initPool() {
		PoolingOptions poolingOptions = new PoolingOptions();
		/**设置集群里的机器至少有几个连接*/
		poolingOptions.setCoreConnectionsPerHost(HostDistance.LOCAL, getCoreConnectionsPerHost());
		/**最多有N个连接*/
		poolingOptions.setMaxConnectionsPerHost(HostDistance.LOCAL, getMaxConnectionsPerHost());
		/**每个连接允许N请求并发*/
		poolingOptions.setMaxSimultaneousRequestsPerConnectionThreshold(HostDistance.LOCAL, getMaxSimultaneousRequests());
		/**如果有多个数据中心。要设置REMOTE连接数*/
		//poolingOptions.setCoreConnectionsPerHost(HostDistance.REMOTE, 2);
		ReconnectionPolicy reconnectionPolicy = new ConstantReconnectionPolicy(2000);

		//创建集群
		cluster = Cluster.builder().addContactPoints(getNode())
				.withCredentials(name,pwd)
				.withPoolingOptions(poolingOptions)
				.withReconnectionPolicy(reconnectionPolicy)
				.build();

	}

	/**
	 *
	 * getSession					(获取链接session)
	 * @return  	Session   		返回链接session
	 * @exception
	 * @since  0.0.1
	 */
	public Session getSession() {
		//如果集群为空，则初始化
		if(cluster == null || cluster.isClosed()){
			initPool();
			logger.info("=============初始化nosql线程池=======================");
		}
		//链接session
		 Session session = cluster.newSession();
		return session;
	}

	/**
	 *
	 * closeSession					(关闭连接)
	 */
	public void closeSession(Session session){
		//Cluster cluster = session.getCluster();
		session.closeAsync();
		//cluster.close();
	}
}
