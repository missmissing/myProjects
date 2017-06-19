/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.model
 * 文件名：				Httest.java
 * 日期：				2015年6月22日-下午4:59:43
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 类名称：				Httest
 * 类描述：				测试结果
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午4:59:43
 * @version 			0.0.1
 */
public class Httest implements Serializable{
	private static final long serialVersionUID = -2325374014189598685L;
	/**测试主键*/
	private String tid;
	/**测试名*/
	private String tname;
	/**答题人ID*/
	private String tuid;
	/**测试结果记录*/
	private List<Answerrecord> tresults;
	/**测试类别(2=>顺序,3=>模块)*/
	private int ttype;
	/**时间戳*/
	private String qrecorddate;
	/**创建时间*/
	private Date createtime;


	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getTuid() {
		return tuid;
	}
	public void setTuid(String tuid) {
		this.tuid = tuid;
	}
	public List<Answerrecord> getTresults() {
		return tresults;
	}
	public void setTresults(List<Answerrecord> tresults) {
		this.tresults = tresults;
	}
	public int getTtype() {
		return ttype;
	}
	public void setTtype(int ttype) {
		this.ttype = ttype;
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
