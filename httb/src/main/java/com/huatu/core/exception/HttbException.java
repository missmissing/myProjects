/**
 * 项目名：华图教育题库项目
 * 包名：    com.java.exception
 * 文件名：HttbException.java
 * 日期：     2012-8-8-下午05:04:41
 * Copyright (c) 2012名道恒通-版权所有
 */
package com.huatu.core.exception;


/**
 * 类名称：				HttbException
 * 类描述：				异常类
 * 创建人：  				艾俊波
 * 创建时间：				2012-8-8 下午05:04:41
 * 修改备注：  				所有错误类的父类，实现了Exception的toString()方法，能更加详细的对错误进行描述和记录。
 * @version 0.5
 */
public class HttbException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = 7620866554977483667L;

	private static final String CRLT_DELIM = "\r\n";

	/**
	 * 异常级别：警告
	 */
	public static final int WARN = 2;

	/**
	 * 异常级别：错误
	 */
	public static final int ERROR = 1;

	/**
	 * 错误名称，根据这个名称可以中错误代码表中获取错误代码和错误描述
	 */
	protected String errName;

	/**
	 * 错误级别，WARN和ERROR两个级别；WARN说明业务可以继续执行，ERROR说明业务错误已经失败，需要回滚
	 */
	protected int level;

	/**
	 * 错误描述，根据errName从文件error.properties中获取
	 */
	protected String errDesc;

	/**
	 * 初始化函数 如果 errName 不为空，调用setErrName函数设置属性errName, errCode, errDesc 如果
	 * errName 为空，并且cause是TxnException类派生出来的，则从cause中获取errName, errCode, errDesc
	 * 设置属性 level = ERROR detailMessage = null 调用父类的初始化函数，初始化message和cause
	 *
	 * @param errName
	 * @param message
	 * @param cause
	 */
	public HttbException(String errorName, String message, Throwable cause) {
		// 调用父类的初始化函数，初始化message和cause
		super(message, cause);

		// 设置属性
		level = ERROR;

		// 如果 errorName 不为空，调用setErrName函数设置属性errName, errCode, errDesc
		if (errorName != null && errorName.length() != 0) {
			this.setErrName(errorName);
		}
		// 如果 errorName 为空，并且cause是TxnException类派生出来的，则从cause中获取errName,errCode,
		else if (cause instanceof HttbException) {
			this.errName = ((HttbException) cause).getErrName();
			this.errDesc = ((HttbException) cause).getErrDesc();
		} else {
			this.setErrName(null);
		}
	}

	/**
	 * 初始化函数 如果 errName 不为空，调用setErrName函数设置属性errName, errCode, errDesc 如果
	 * errName 为空，并且cause是TxnException类派生出来的，则从cause中获取errName, errCode, errDesc
	 * 设置属性 level = ERROR 调用父类的初始化函数，初始化cause
	 *
	 * @param errName
	 * @param cause
	 */
	public HttbException(String errorName, Throwable cause) {
		// 调用父类的初始化函数，初始化cause
		super(cause);

		// 设置属性
		level = ERROR;

		// 如果 errorName 不为空，调用setErrName函数设置属性errName, errCode, errDesc
		if (errorName != null && errorName.length() != 0) {
			this.setErrName(errorName);
		}
		// 如果 errorName 为空，并且cause是TxnException类派生出来的，则从cause中获取errName,errCode,
		else if (cause instanceof HttbException) {
			this.errName = ((HttbException) cause).getErrName();
			this.errDesc = ((HttbException) cause).getErrDesc();
		} else {
			this.setErrName(null);
		}
	}

	/**
	 * 初始化函数 调用setErrName函数设置属性errName, errCode, errDesc 设置属性 level = ERROR
	 * 调用父类的初始化函数，初始化message
	 *
	 * @param errorName
	 * @param message
	 */
	public HttbException(String errorName, String message) {
		// 调用父类的初始化函数
		super(message);

		// 初始化属性
		level = ERROR;

		// 调用setErrName函数设置属性errName, errCode, errDesc
		this.setErrName(errorName);
	}

	/**
	 * 初始化函数 调用setErrName函数设置属性errName, errCode, errDesc 设置属性 level = ERROR
	 * 调用父类的初始化函数
	 *
	 * @param errorName
	 */
	public HttbException(String errorName) {
		// 调用父类的初始化函数
		super();

		// 初始化属性
		level = ERROR;

		// 调用setErrName函数设置属性errName, errCode, errDesc
		this.setErrName(errorName);
	}

	/**
	 * 初始化函数 设置属性 errorName = "" level = ERROR errCode = "" errDesc = ""
	 * 调用父类的初始化函数
	 */
	public HttbException() {
		// 调用父类的初始化函数
		super();

		// 初始化属性
		level = ERROR;

		// 调用setErrName函数设置属性errName, errCode, errDesc
		this.setErrName(null);
	}

	/**
	 * 设置属性 errorName 从文件error.properties中获取errName对应的errCode和errDesc的内容
	 * 如果errName的格式是 XXXXX:XXXXXXX，前后两部分使用[:]分割，则把前一部分保存到errCode中，后一部分保存?
	 * errDesc中，errorName = "NA"
	 *
	 * @param errorName
	 */
	public void setErrName(String errorName) {
		if (errorName == null || errorName.length() == 0) {
			this.errName = "Error";
			this.errDesc = "发生未知异常";
		} else {
			int ptr = errorName.indexOf(":");
			if (ptr > 0) {
				this.errName = errorName.split(":")[0];
				this.errDesc = errorName.split(":")[1];
			}
		}
	}

	/**
	 * 返回errName的内容
	 *
	 * @return java.lang.String
	 */
	public String getErrName() {
		return this.errName;
	}

	/**
	 * 设置属性level
	 *
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * 获取属性level
	 *
	 * @return int
	 */
	public int getLevel() {
		return this.level;
	}

	/**
	 * 设置属性errDesc
	 *
	 * @param errDesc
	 */
	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	/**
	 * 获取属性errDesc
	 *
	 * @return java.lang.String
	 */
	public String getErrDesc() {
		return this.errDesc;
	}

	/**
	 * 原始的错误信息
	 *
	 * @return
	 */
	public String getExceptionMessage() {
		return super.getMessage();
	}

	/**
	 * 按以下格式生成错误信息 错误级别：WARN/ERROR； 错误名称：errName； 错误代码：errCode； 错误描述：errDesc；
	 * 错误原因：getMessage()；
	 *
	 * @return java.lang.String
	 */
	@Override
	public String toString() {
		// 错误信息
		StringBuilder errLog = new StringBuilder();

		// 显示错误级别
		if (this.level == ERROR) {
			errLog.append(CRLT_DELIM+"【错误级别：ERROR】" );
		} else {
			errLog.append(CRLT_DELIM+"【错误级别：WARN】");
		}
		// 显示错误名称
		if (this.errName != null && this.errName.length() != 0) {
			errLog.append("【错误名称：").append(this.errName).append("】");
		}

		// 显示错误描述
		errLog.append("【错误描述：").append(this.errDesc).append("】");

		// 显示起始错误
		errLog.append("【错误原因：").append(super.getMessage()).append("】"+CRLT_DELIM);

		// 如果cause不为空，在后面添加
		errLog.append("【错误详细:");
		Throwable c = getCause();
		if (c != null) {
			errLog.append(c.toString()+"】   ");
		}
		StackTraceElement [] messages=this.getStackTrace();
		errLog.append("【"+messages[0].toString()+"】");

		return errLog.toString();
	}

	//当前类接收到的Exception是由其引用类抛出的，并且不会有新的异常抛出
	//则调用次方法返回传递异常的ErrName字符串
	public String getErrorName(){
		return this.errName+":"+this.getErrDesc();
	}

}
