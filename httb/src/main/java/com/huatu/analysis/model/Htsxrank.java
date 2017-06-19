/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.model
 * 文件名：				Htsxrank.java
 * 日期：				2015年6月23日-上午10:45:13
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.model;

import java.io.Serializable;

/**
 * 类名称：				Htsxrank
 * 类描述：  				顺序排名实体
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月23日 上午10:45:13
 * @version 			0.0.1
 */
public class Htsxrank implements Serializable{
	private static final long serialVersionUID = 9039450093847987793L;

	/**用户id*/
	private String sxuid;

	/**答题数量*/
	private int sxcount;

	/**知识点id*/
	private String sxpoint;

	/**时间戳*/
	private String qrecorddate;

	public String getSxuid() {
		return sxuid;
	}
	public void setSxuid(String sxuid) {
		this.sxuid = sxuid;
	}
	public int getSxcount() {
		return sxcount;
	}
	public void setSxcount(int sxcount) {
		this.sxcount = sxcount;
	}
	public String getQrecorddate() {
		return qrecorddate;
	}
	public void setQrecorddate(String qrecorddate) {
		this.qrecorddate = qrecorddate;
	}
	public String getSxpoint() {
		return sxpoint;
	}
	public void setSxpoint(String sxpoint) {
		this.sxpoint = sxpoint;
	}
}
