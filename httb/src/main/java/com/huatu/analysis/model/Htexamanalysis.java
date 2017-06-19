/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.model
 * 文件名：				HTEXAMANALYSIS.java
 * 日期：				2015年6月21日-下午5:06:22
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.model;

import java.io.Serializable;

/**
 * 类名称：				HTEXAMANALYSIS
 * 类描述：				考试统计分析表
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月21日 下午5:06:22
 * @version 			0.0.1
 */
public class Htexamanalysis implements Serializable{
	private static final long serialVersionUID = 6994914363799307289L;

	/**试卷id*/
	private String eid;

	/**试卷平均分*/
	private float eaveragescore;

	/**试卷答题次数*/
	private int eansamount;

	/**试卷平均用时*/
	private int eaveragetime;

	/**时间戳*/
	private String qrecorddate;

	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public float getEaveragescore() {
		return eaveragescore;
	}
	public void setEaveragescore(float eaveragescore) {
		this.eaveragescore = eaveragescore;
	}
	public int getEansamount() {
		return eansamount;
	}
	public void setEansamount(int eansamount) {
		this.eansamount = eansamount;
	}
	public int getEaveragetime() {
		return eaveragetime;
	}
	public void setEaveragetime(int eaveragetime) {
		this.eaveragetime = eaveragetime;
	}
	public String getQrecorddate() {
		return qrecorddate;
	}
	public void setQrecorddate(String qrecorddate) {
		this.qrecorddate = qrecorddate;
	}
}
