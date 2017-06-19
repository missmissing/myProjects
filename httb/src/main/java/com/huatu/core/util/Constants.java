package com.huatu.core.util;

import com.datastax.driver.core.ConsistencyLevel;

/**
 * @Description 					系统全局常量
 * @author 							Aijunbo
 * @Creatertime 					2015年4月23日
 */
public class Constants {
		/** 系统超级管理员角色ID：1 */
		public final static String SYSTEM_SUPER_ADMIN = "admin";
		/**rest接口 许可证标识   app端认证使用*/
		public final static String LICENSE = "license";

		/**系统启动是否初始化缓存数据*/
		public static boolean ISINIT = true;//默认启动
		/**定时任务是否生效**/
		public static boolean ISTIMEDTASK = true;//默认生效

		/**httbContext.properties配置文件中属性*/
		public static final String P_START_TIMED_TASK ="start.timed.task";
		public static final String P_APP_DEPLOY_TYPE ="app.deploy.type";
		public static final String P_INIT_REFRESH = "init.refresh";

		/**章节 试题变动 nosql中版本标识   当触发章节增删改 和 试题 发布或下线时  刷新版本号    */
		public static final String CATEGORY = "category";

	//redis缓存key标识  begin
		/**设置redis数据有效期 (单位秒)*/
		public static final int REDIS_EXPIRE = 30*60;

		/**知识缓存是否需要刷新*/
		public static boolean REDIS_CATEGORY_REFRESHFLAG = false;

	//cassandra============================================================start
		/**系统当前登录用户*/
		public final static String SESSION_CURRENT_USER = "correntUser";
		/**系统当前登录用户人数*/
		public final static String SESSION_CURRENT_USERCOUNT = "SESSION_CURRENT_USERCOUNT";

		/**Oracle数据库类型*/
		public final static String ORACLE = "oracle";

		/**SqlServer数据库类型*/
		public final static String SQLSERVER = "sqlserver";

		/**MySql数据库类型*/
		public final static String MYSQL = "mysql";

		/**列表GRID每页默认显示记录数*/
		public static final int PAGE_SIZE = 30;

		/**列表Grid每页默认显示可选择页数*/
		public static final int PAGE_INGRP = 5;

		/**默认最大显示行数*/
		public static final int SHOWMAXNUM = 500;

		/**cassandra写一致性级别*/
		public static final ConsistencyLevel CASSANDRA_CONSISTENCY_WRITELEVEL = ConsistencyLevel.ONE;
		/**cassandra读一致性级别*/
		public static final ConsistencyLevel CASSANDRA_CONSISTENCY_READLEVEL = ConsistencyLevel.ONE;
	//cassandra==============================================================end


	//文件上传===============================================================start
		/**上传图片文件类型*/
		public static final String UPLOAD_IMG_TYPE = "jpg,jpeg,png,gif,tif,tiff,bmp.ico,JPG,JPEG,PNG,GIF,TIF,TIFF,BMP.ICO";
		/**下载压缩文件后缀*/
		public static final String DOWNLOAD_FILE_TYPE = ".rar";
		/**分类异常记录导出路径*/
		public static final String CATEGORY_OUTPUT = "D:\\io\\log\\";
		/** 文件上传临时保存目录 */
		public final static String FILE_UPLOAD_MKDIRS = "common/upload/";
	//文件上传=================================================================end

	//全局变量===============================================================start
	/**数据逻辑删除状态:0---》未删除*/
		public static final String TOMBSTONE_DeleteFlag_NO = "0";
		/**数据逻辑删除状态:1---》已删除*/
		public static final String TOMBSTONE_DeleteFlag_YES = "1";
	//全局变量=================================================================end


	//cassandra============================================================start
		/**cassandra-- keyspace*/
		public static final String NOSQL_KEYSPACE = "httb";
	//cassandra==============================================================end


	//session==============================================================start
		/**session--session失效时间*/
		public static final int REDIS_SESSIONIDS_TIMEOUT = 30 * 60 * 1000;

		/**sessionId前缀*/
		public static final String REDIS_SESSION_ID = "REDIS_SESSION_ID";
	//session================================================================end

	//category=============================================================start
		/**分类树节点ID间隔长度*/
		public static final int CATEGORY_ID_LEN = 3;
		/**分隔符*/
		public static final String SEPARATOR ="H#TT#B";
		/**医疗根节点id*/
		public static final String YL_ROOTID = "m100";
		/**金融根节点*/
		public static final String JR_ROOTID = "m200";
	//category===============================================================end

	//analysis=============================================================start
		/**知识分类树*/
		public static final String ANALYSIS_CATEGORY = "ANALYSIS_CATEGORY";
		/**知识分类树根节点(大分类)*/
		public static final String ANALYSIS_CATEGORY_ROOTNODE = "ANALYSIS_CATEGORY_ROOTNODE";
		/**顺序排名集合缓存*/
		public static final String ANALYSIS_RANK_SX = "ANALYSIS_RANK_SX";
		/**试题分析缓存*/
		public static final String ANALYSIS_QUES_DATA = "ANALYSIS_QUES_DATA";
		/**试卷分析缓存*/
		public static final String ANALYSIS_PAPER_DATA = "ANALYSIS_PAPER_DATA";

		/**顺序排名集合排他锁*/
		public static final String ANALYSIS_RANK_LOCK = "ANALYSIS_RANK_LOCK";
		/**试题分析排他锁*/
		public static final String ANALYSIS_QUES_LOCK = "ANALYSIS_QUES_LOCK";
		/**试卷分析排他锁*/
		public static final String ANALYSIS_PAPER_LOCK = "ANALYSIS_PAPER_LOCK";
		/**用户分析排他锁*/
		public static final String ANALYSIS_USER_LOCK = "ANALYSIS_USER_LOCK";

		/**顺序排名集合排他锁有效期(秒)*/
		public static final int ANALYSIS_LOCK_EXPIRE = 60;

	//版本管理变量start
		/**试卷分析版本*/
		public static final String ANALYSIS_VERSION_EXAM = "ANALYSIS_VERSION_EXAM";

		/**试题分析版本*/
		public static final String ANALYSIS_VERSION_QUESTION = "ANALYSIS_VERSION_QUESTION";

	//版本管理变量end
	//analysis===============================================================end

	//jms==================================================================start
		/**消息答题结果保存*/
		public static final String JMS_RESULT_SAVE = "JMS_RESULT_SAVE";
		/**顺序答题排名*/
		public static final String JMS_ANALYZE_RANK = "JMS_ANALYZE_RANK";
		/**试题分析*/
		public static final String JMS_ANALYZE_QUES = "JMS_ANALYZE_QUES";
		/**试卷分析*/
		public static final String JMS_ANALYZE_PAPER = "JMS_ANALYZE_PAPER";
		/**用户分析*/
		public static final String JMS_ANALYZE_USER = "JMS_ANALYZE_USER";

	//jms====================================================================end
}
