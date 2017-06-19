package com.huatu.core.exception;


/**
 * 项目名：华图教育题库项目
 * 包名：    com.java.exception.model
 * 文件名：modelException.java
 * 日期：     2012-8-8-下午05:15:10
 * Copyright (c) 2012名道恒通-版权所有
 */


/**
 * 类名称：modelException
 * 类描述：
 * 创建人：
 * 创建时间：2012-8-8 下午05:15:10
 * 修改人：
 * 修改时间：2012-8-8 下午05:15:10
 * 修改备注：
 * @version 0.5
 */
public class ModelException {
	   public static final String FILE_NOTEXIST 		= "FILE_NOTEXIST:文件不存在";
	   public static final String FILE_OPEN_ERROR 		= "FILE_OPEN_ERROR:打开文件时错误";
	   public static final String FILE_READERROR 		= "FILE_READERROR:读取文件错误";
	   public static final String FILE_WRITEERROR 		= "FILE_WRITEERROR:写文件错误";
	   public static final String FILE_ISNULL 			= "FILE_ISNULL:文件名称是空";
	   public static final String FILE_DOWNLOAD_ERROR 	= "FILE_DOWNLOAD_ERROR:下载文件错误";
	   public static final String FILE_DELETE_ERROR 	= "FILE_DELETE_ERROR:删除文件时错误";
	   public static final String FILE_CREATE_ERROR 	= "FILE_CREATE_ERROR:创建文件时错误";
	   public static final String FILE_CLOSE_ERROR 		= "FILE_CLOSE_ERROR:关闭文件错误";
	   public static final String FILE_ALREADY_EXIST 	= "FILE_ALREADY_EXIST:文件已经存在";
	   public static final String FILE_CREATEPATH_ERROR = "FILE_CREATEPATH_ERROR:创建目录时错误";
	   public static final String FILE_PATH_NOTFOUND	= "FILE_PATH_NOTFOUND:目录不存在";
	   public static final String FILE_RENAME_ERROR 	= "FILE_RENAME_ERROR:修改文件名称时错误";
	   public static final String FILE_CREATE_EXCEL_ERROR = "FILE_CREATE_EXCEL_ERROR:生成EXCEL文档时错误";
	   public static final String FILE_TYPE_ERROR 	    = "FILE_TYPE_ERROR:文件类型错误";
	   public static final String FILE_INPUT_ERROR      = "FILE_INPUT_ERROR:文件导入错误";

	   public static final String JAVA_STRING_DECODE 	="JAVA_STRING_DECODE:字符串解码出错";
	   /**类型转换错误*/
	   public static final String TYPE_TRANSFORM  ="类型转换错误";

	   public static final String JAVA_RUNTIME_EXCEPTION ="JAVA_RUNTIME_EXCEPTION:运行时异常";

	   public static final String JAVA_THREAD_EXCEPTION ="JAVA_THREAD_EXCEPTION:运行线程时异常";

	   public static final String JAVA_CASSANDRA_INSERT ="JAVA_CASSANDRA_INSERT:cassandra插入数据出错";
	   public static final String JAVA_CASSANDRA_DELETE ="JAVA_CASSANDRA_DELETE:cassandra删除数据出错";
	   public static final String JAVA_CASSANDRA_UPDATE ="JAVA_CASSANDRA_UPDATE:cassandra修改数据出错";
	   public static final String JAVA_CASSANDRA_SEARCH ="JAVA_CASSANDRA_SEARCH:cassandra查询数据出错";
	   public static final String JAVA_CASSANDRA_BATCH ="JAVA_CASSANDRA_BATCH:cassandra批处理数据出错";

	   public static final String JAVA_REDIS_ADD ="JAVA_REDIS_ADD:REDIS插入数据出错";
	   public static final String JAVA_REDIS_DELETE ="JAVA_REDIS_DELETE:REDIS删除数据出错";
	   public static final String JAVA_REDIS_UPDATE ="JAVA_REDIS_UPDATE:REDIS修改数据出错";
	   public static final String JAVA_REDIS_SEARCH ="JAVA_REDIS_SEARCH:REDIS查询数据出错";
	   public static final String JAVA_REDIS_REFRESH ="JAVA_REDIS_REFRESH:REDIS刷新数据出错";

	   public static final String JAVA_ANALYSIS ="JAVA_ANALYSIS:分析数据出错";
	   public static final String JAVA_ANALYSIS_SAVE ="JAVA_ANALYSIS_SAVE:保存分析数据出错";
}
