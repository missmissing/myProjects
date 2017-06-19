/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.model
 * 文件名：				Htcateanalysis.java
 * 日期：					2015年6月21日-下午6:09:53
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.model;

/**
 * 类名称：				Htcateanalysis
 * 类描述：				知识分类统计分析
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月21日 下午6:09:53
 * @version 			0.0.1
 */
public class Htcateanalysis {
	/**知识分类id*/
	private String cid;
	/**知识点对应题数*/
	private int cquesamount;
	/**用户id*/
	private String uid;
	/**时间戳*/
	private String qrecorddate;

	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public int getCquesamount() {
		return cquesamount;
	}
	public void setCquesamount(int cquesamount) {
		this.cquesamount = cquesamount;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getQrecorddate() {
		return qrecorddate;
	}
	public void setQrecorddate(String qrecorddate) {
		this.qrecorddate = qrecorddate;
	}

}
