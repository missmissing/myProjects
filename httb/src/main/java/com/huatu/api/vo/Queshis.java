package com.huatu.api.vo;

import java.util.Date;

public class Queshis {
	/**试题历史记录主键*/
	private String qhid;
	/**试题ID*/
	private String qhuid;
	/**用户ID*/
	private String qhqid;
	/**试题答案*/
	private String qhqans;
	/**用户答案*/
	private String qhuans;
	/**是否正确(true,false)*/
	private String qhisright;
	/**试题类型(mn=>模拟,zd=>真题,sx=>顺序,mk=>模块)*/
	private String qhtype;
	/**创建时间*/
	private Date createtime;
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
	public String getQhisright() {
		return qhisright;
	}
	public void setQhisright(String qhisright) {
		this.qhisright = qhisright;
	}
	public String getQhtype() {
		return qhtype;
	}
	public void setQhtype(String qhtype) {
		this.qhtype = qhtype;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
