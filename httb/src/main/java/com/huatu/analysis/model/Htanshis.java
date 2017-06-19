/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.model
 * 文件名：				Htanshis.java
 * 日期：					2015年6月21日-下午3:53:50
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.model;

import java.io.Serializable;

/**
 * 类名称：				Htanshis
 * 类描述：				答题用户记录
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月21日 下午3:53:50
 * @version 			0.0.1
 */
public class Htanshis implements Serializable{
	private static final long serialVersionUID = -9165633906226894907L;

	/**用户ID*/
	private String auid;

	/**时间戳*/
	private String qrecorddate;

	public String getAuid() {
		return auid;
	}
	public void setAuid(String auid) {
		this.auid = auid;
	}
	public String getQrecorddate() {
		return qrecorddate;
	}
	public void setQrecorddate(String qrecorddate) {
		this.qrecorddate = qrecorddate;
	}
}
