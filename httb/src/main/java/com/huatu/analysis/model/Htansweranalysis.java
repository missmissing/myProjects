/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.model
 * 文件名：				Htansweranalysis.java
 * 日期：					2015年6月21日-下午5:12:52
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.model;

import java.io.Serializable;

/**
 * 类名称：				Htansweranalysis
 * 类描述：				用户答题统计分析
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月21日 下午5:12:52
 * @version 			0.0.1
 */
public class Htansweranalysis implements Serializable{
	private static final long serialVersionUID = -7498937617664393144L;

	/**用户ID*/
	private String auid;
	/**答题数量*/
	private int aquesamount;
	/**做对数量*/
	private int qcorrectamount;
	/**时间戳*/
	private String qrecorddate;


	public String getAuid() {
		return auid;
	}
	public void setAuid(String auid) {
		this.auid = auid;
	}
	public int getAquesamount() {
		return aquesamount;
	}
	public void setAquesamount(int aquesamount) {
		this.aquesamount = aquesamount;
	}
	public int getQcorrectamount() {
		return qcorrectamount;
	}
	public void setQcorrectamount(int qcorrectamount) {
		this.qcorrectamount = qcorrectamount;
	}
	public String getQrecorddate() {
		return qrecorddate;
	}
	public void setQrecorddate(String qrecorddate) {
		this.qrecorddate = qrecorddate;
	}
}
