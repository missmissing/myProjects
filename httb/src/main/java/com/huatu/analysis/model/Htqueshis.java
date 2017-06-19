/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.model
 * 文件名：				Htqueshis.java
 * 日期：					2015年6月21日-上午11:53:26
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 类名称：				Htqueshis
 * 类描述：  				试题历史记录表
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月21日 上午11:53:26
 * @version 			0.0.1
 */
public class Htqueshis implements Serializable{
	/**
	 *Htqueshis.java:TODO
	 */
	private static final long serialVersionUID = -2708488038851326256L;

	/**试题历史记录主键*/
	private String qhid;

	/**用户ID*/
	private String qhuid;

	/**试题ID*/
	private String qhqid;

	/**试题答案*/
	private String qhqans;

	/**用户答案*/
	private String qhuans;

	/**是否正确(0=>不正确,1=>正确)*/
	private int qhisright;

	/**试题类型(0=>真题,1=>模拟,2=>顺序,3=>模块)*/
	private int qhtype;

	/**知识点id*/
	private List<String> qhpoint;

	/**时间戳*/
	private String qrecorddate;

	/**创建时间*/
	private Date createtime;

	//get、set方法
	public String getQhid() {
		return qhid;
	}
	public void setQhid(String qhid) {
		this.qhid = qhid;
	}
	public String getQhuid() {
		return qhuid;
	}
	public void setQhuid(String qhuid) {
		this.qhuid = qhuid;
	}
	public String getQhqid() {
		return qhqid;
	}
	public void setQhqid(String qhqid) {
		this.qhqid = qhqid;
	}
	public String getQhqans() {
		return qhqans;
	}
	public void setQhqans(String qhqans) {
		this.qhqans = qhqans;
	}
	public String getQhuans() {
		return qhuans;
	}
	public void setQhuans(String qhuans) {
		this.qhuans = qhuans;
	}
	public int getQhisright() {
		return qhisright;
	}
	public void setQhisright(int qhisright) {
		this.qhisright = qhisright;
	}
	public int getQhtype() {
		return qhtype;
	}
	public void setQhtype(int qhtype) {
		this.qhtype = qhtype;
	}
	public List<String> getQhpoint() {
		return qhpoint;
	}
	public void setQhpoint(List<String> qhpoint) {
		this.qhpoint = qhpoint;
	}
	public String getQrecorddate() {
		return qrecorddate;
	}
	public void setQrecorddate(String qrecorddate) {
		this.qrecorddate = qrecorddate;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
