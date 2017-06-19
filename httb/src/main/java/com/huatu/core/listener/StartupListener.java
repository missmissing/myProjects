package com.huatu.core.listener;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContextEvent;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.huatu.core.util.ProcessPropertiesConfigUtil;
import com.huatu.core.util.SpringContextHolder;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;



public class StartupListener extends ContextLoaderListener{
	 private static Logger logger = Logger.getLogger(StartupListener.class);

	  public void contextInitialized(ServletContextEvent event)
	  {
	    super.contextInitialized(event);
	    try {
	    	Set<HostAndPort> jedisClusterNodes = null;	    	
			ProcessPropertiesConfigUtil.initProperties(event.getServletContext());
			ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
			ConfigurableApplicationContext context = (ConfigurableApplicationContext) ac;
			// Bean的实例工厂
			DefaultListableBeanFactory dbf = (DefaultListableBeanFactory) context.getBeanFactory();
			
			BeanDefinitionBuilder dataSourceBuider4 = null; //Cassandra需要加载的Bean
			BeanDefinitionBuilder dataSourceBuider = null;  //mysql需要加载的Bean
			
			//获得配置文件，并读取内容						
			Properties typeTable = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("httbContext");
			String type = typeTable.getProperty("app.deploy.type");
			switch (type) {

			case "100": {// 医疗		
				//初始化log4j
				Properties log4jTable = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("log4j_yl");
		    	PropertyConfigurator.configure(log4jTable);
				/*cassandra配置文件*/
			    dataSourceBuider4 = BeanDefinitionBuilder.genericBeanDefinition(com.huatu.core.util.CassandraConnectFactory.class);
				dataSourceBuider4.addPropertyValue("node", typeTable.getProperty("cassandra_yl"));			
				/*cassandra配置文件*/
				
				/*redis配置文件*/
				jedisClusterNodes = new HashSet<HostAndPort>();
				for(int i=1;i<7;i++){
					String ipAndPort = typeTable.getProperty("redis_yl"+i);
					String[] parts = ipAndPort.split(":");
					jedisClusterNodes.add(new HostAndPort(parts[0], Integer.valueOf(parts[1]).intValue()));
				}
				/*redis配置文件*/
				
				/*mysql配置文件*/
				dataSourceBuider = BeanDefinitionBuilder.genericBeanDefinition(com.mchange.v2.c3p0.ComboPooledDataSource.class);
				dataSourceBuider.addPropertyValue("driverClass", "com.mysql.jdbc.Driver");
				dataSourceBuider.addPropertyValue("jdbcUrl", typeTable.getProperty("mysql_yl"));				
				/*mysql配置文件*/

				break;
			}
			case "200": {// 金融
				//初始化log4j
				Properties log4jTable = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("log4j_jr");
		    	PropertyConfigurator.configure(log4jTable);
				/*cassandra配置文件*/
				dataSourceBuider4 = BeanDefinitionBuilder.genericBeanDefinition(com.huatu.core.util.CassandraConnectFactory.class);
				dataSourceBuider4.addPropertyValue("node", typeTable.getProperty("cassandra_jr"));
				/*cassandra配置文件*/
				
				/*redis配置文件*/
				jedisClusterNodes = new HashSet<HostAndPort>();
				for(int i=1;i<7;i++){
					String ipAndPort = typeTable.getProperty("redis_jr"+i);
					String[] parts = ipAndPort.split(":");
					jedisClusterNodes.add(new HostAndPort(parts[0], Integer.valueOf(parts[1]).intValue()));
				}
				/*redis配置文件*/
				
				/*mysql配置文件*/
				dataSourceBuider = BeanDefinitionBuilder.genericBeanDefinition(com.mchange.v2.c3p0.ComboPooledDataSource.class);
				dataSourceBuider.addPropertyValue("driverClass", "com.mysql.jdbc.Driver");
				dataSourceBuider.addPropertyValue("jdbcUrl", typeTable.getProperty("mysql_jr"));
				/*mysql配置文件*/
				break;
			}
			case "300": {// 公务员
				//初始化log4j
				Properties log4jTable = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("log4j_gwy");
		    	PropertyConfigurator.configure(log4jTable);
				/*cassandra配置文件*/
				dataSourceBuider4 = BeanDefinitionBuilder.genericBeanDefinition(com.huatu.core.util.CassandraConnectFactory.class);
				dataSourceBuider4.addPropertyValue("node", typeTable.getProperty("cassandra_gwy"));
				/*cassandra配置文件*/
				
				/*redis配置文件*/
				jedisClusterNodes = new HashSet<HostAndPort>();
				for(int i=1;i<7;i++){
					String ipAndPort = typeTable.getProperty("redis_gwy"+i);
					String[] parts = ipAndPort.split(":");
					jedisClusterNodes.add(new HostAndPort(parts[0], Integer.valueOf(parts[1]).intValue()));
				}
				/*redis配置文件*/
				
				/*mysql配置文件*/
				dataSourceBuider = BeanDefinitionBuilder.genericBeanDefinition(com.mchange.v2.c3p0.ComboPooledDataSource.class);
				dataSourceBuider.addPropertyValue("driverClass", "com.mysql.jdbc.Driver");
				dataSourceBuider.addPropertyValue("jdbcUrl", typeTable.getProperty("mysql_gwy"));
				/*mysql配置文件*/
				break;
			}
			case "400": {// 教师
				//初始化log4j
				Properties log4jTable = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("log4j_js");
		    	PropertyConfigurator.configure(log4jTable);
				/*cassandra配置文件*/
				dataSourceBuider4 = BeanDefinitionBuilder.genericBeanDefinition(com.huatu.core.util.CassandraConnectFactory.class);
				dataSourceBuider4.addPropertyValue("node", typeTable.getProperty("cassandra_js"));
				/*cassandra配置文件*/
				
				/*redis配置文件*/
				jedisClusterNodes = new HashSet<HostAndPort>();
				for(int i=1;i<7;i++){
					String ipAndPort = typeTable.getProperty("redis_js"+i);
					String[] parts = ipAndPort.split(":");
					jedisClusterNodes.add(new HostAndPort(parts[0], Integer.valueOf(parts[1]).intValue()));
				}
				/*redis配置文件*/
				
				/*mysql配置文件*/
				dataSourceBuider = BeanDefinitionBuilder.genericBeanDefinition(com.mchange.v2.c3p0.ComboPooledDataSource.class);
				dataSourceBuider.addPropertyValue("driverClass", "com.mysql.jdbc.Driver");
				dataSourceBuider.addPropertyValue("jdbcUrl", typeTable.getProperty("mysql_js"));
				/*mysql配置文件*/
				break;
			}
			case "500": {// 事业单位
				//初始化log4j
				Properties log4jTable = (Properties) ProcessPropertiesConfigUtil.propertiesMap.get("log4j_sydw");
		    	PropertyConfigurator.configure(log4jTable);
				/*cassandra配置文件*/
				dataSourceBuider4 = BeanDefinitionBuilder.genericBeanDefinition(com.huatu.core.util.CassandraConnectFactory.class);
				dataSourceBuider4.addPropertyValue("node", typeTable.getProperty("cassandra_sydw"));
				/*cassandra配置文件*/
				
				/*redis配置文件*/
				jedisClusterNodes = new HashSet<HostAndPort>();
				for(int i=1;i<7;i++){
					String ipAndPort = typeTable.getProperty("redis_sydw"+i);
					String[] parts = ipAndPort.split(":");
					jedisClusterNodes.add(new HostAndPort(parts[0], Integer.valueOf(parts[1]).intValue()));
				}
				/*redis配置文件*/
				
				/*mysql配置文件*/
				dataSourceBuider = BeanDefinitionBuilder.genericBeanDefinition(com.mchange.v2.c3p0.ComboPooledDataSource.class);
				dataSourceBuider.addPropertyValue("driverClass", "com.mysql.jdbc.Driver");
				dataSourceBuider.addPropertyValue("jdbcUrl", typeTable.getProperty("mysql_sydw"));
				/*mysql配置文件*/
				break;
			}
			default: {
				break;
			}

			}
			
			//构建redis连接池配置
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			poolConfig.setMaxTotal(65535);
			poolConfig.setMaxIdle(1000);
			poolConfig.setMaxWaitMillis(10000);
			poolConfig.setTimeBetweenEvictionRunsMillis(60000);
			poolConfig.setMinEvictableIdleTimeMillis(60000);
			poolConfig.setTestOnBorrow(true);
            //构建JedisCluster的BeanDefinitionBuilder
			BeanDefinitionBuilder dataSourceBuiderJc = BeanDefinitionBuilder.genericBeanDefinition(redis.clients.jedis.JedisCluster.class);
			dataSourceBuiderJc.addConstructorArgValue(jedisClusterNodes);
			dataSourceBuiderJc.addConstructorArgValue(poolConfig);
			//注入JedisCluster
			dbf.registerBeanDefinition("jc", dataSourceBuiderJc.getBeanDefinition());	
			
			//构建Cassandra通用配置
			dataSourceBuider4.addPropertyValue("maxSimultaneousRequests", "128");
			dataSourceBuider4.addPropertyValue("coreConnectionsPerHost", "2");
			dataSourceBuider4.addPropertyValue("maxConnectionsPerHost", "65534");
			dataSourceBuider4.addPropertyValue("name", "");
			dataSourceBuider4.addPropertyValue("pwd", "");
			//注入Cassandra
			dbf.registerBeanDefinition("cassandraConnectFactory", dataSourceBuider4.getBeanDefinition());
			
			//构建mysql通用配置
			dataSourceBuider.addPropertyValue("user", typeTable.getProperty("mysql_username"));
			dataSourceBuider.addPropertyValue("password", typeTable.getProperty("mysql_password"));		
			dataSourceBuider.addPropertyValue("maxPoolSize", "30");
			dataSourceBuider.addPropertyValue("minPoolSize", "5");		
			dataSourceBuider.addPropertyValue("initialPoolSize", "5");
			dataSourceBuider.addPropertyValue("maxIdleTime", "6");
			dataSourceBuider.addPropertyValue("checkoutTimeout", "3000");	
			dataSourceBuider.addPropertyValue("acquireIncrement", "2");
			dataSourceBuider.addPropertyValue("acquireRetryAttempts", "0");
			dataSourceBuider.addPropertyValue("acquireRetryDelay", "1000");		
			dataSourceBuider.addPropertyValue("autoCommitOnClose", "false");
			dataSourceBuider.addPropertyValue("idleConnectionTestPeriod", "60");
			dataSourceBuider.addPropertyValue("maxStatements", "100");		
			//注入mysql
			dbf.registerBeanDefinition("dataSource", dataSourceBuider.getBeanDefinition());
			
		} catch (Exception e) {
			logger.error("初始化错误！！！");
			e.printStackTrace();
		}
	  }
}
