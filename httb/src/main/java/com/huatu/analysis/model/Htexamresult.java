/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.model
 * 文件名：				Htexamresult.java
 * 日期：				2015年6月22日-下午4:45:35
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 类名称：				Htexamresult
 * 类描述：				考试结果
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月22日 下午4:45:35
 * @version 			0.0.1
 */
public class Htexamresult implements Serializable{
	private static final long serialVersionUID = 4751060997803791674L;
	/**用户测试主键*/
	private String rid;
	/**用户ID*/
	private String ruid;
	/**关联的考试主键*/
	private String reid;
	/**关联的考试名*/
	private String rname;
	/**考试类型 (0=>真题,1=>模拟)*/
	private String rexamtype;
	/**考试结果*/
	private float rexamresult;
	/**考试耗时(单位/秒)*/
	private int rexamconsume;
	/**用户提交的答案记录*/
	private List<Answerrecord> rresults;
	/**用户答题状态(0->答题中；1->答题完成)*/
	private String rstatus;
	/**时间戳*/
	private String qrecorddate;
	/**创建时间*/
	private Date createtime;

	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getRuid() {
		return ruid;
	}
	public void setRuid(String ruid) {
		this.ruid = ruid;
	}
	public String getReid() {
		return reid;
	}
	public void setReid(String reid) {
		this.reid = reid;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getRexamtype() {
		return rexamtype;
	}
	public void setRexamtype(String rexamtype) {
		this.rexamtype = rexamtype;
	}
	public float getRexamresult() {
		return rexamresult;
	}
	public void setRexamresult(float rexamresult) {
		this.rexamresult = rexamresult;
	}
	public int getRexamconsume() {
		return rexamconsume;
	}
	public void setRexamconsume(int rexamconsume) {
		this.rexamconsume = rexamconsume;
	}
	public List<Answerrecord> getRresults() {
		return rresults;
	}
	public void setRresults(List<Answerrecord> rresults) {
		this.rresults = rresults;
	}
	public String getRstatus() {
		return rstatus;
	}
	public void setRstatus(String rstatus) {
		this.rstatus = rstatus;
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
