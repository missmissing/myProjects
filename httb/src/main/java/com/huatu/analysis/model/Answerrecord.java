/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.model
 * 文件名：				Answerrecord.java
 * 日期：				2015年6月22日-下午4:52:29
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 类名称：				Answerrecord
 * 类描述：  				答题记录
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月22日 下午4:52:29
 * @version 			0.0.1
 */
public class Answerrecord implements Serializable{
	private static final long serialVersionUID = 6868497812770704149L;
	/**试题id*/
	private String qid;
	/**试题答案*/
	private String qans;
	/**用户答案*/
	private String uans;
	/**是否正确(0=>正确，1=>不正确)*/
	private String isright;
	/**答题时间*/
	private Date atime;

	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getQans() {
		return qans;
	}
	public void setQans(String qans) {
		this.qans = qans;
	}
	public String getUans() {
		return uans;
	}
	public void setUans(String uans) {
		this.uans = uans;
	}
	public String getIsright() {
		return isright;
	}
	public void setIsright(String isright) {
		this.isright = isright;
	}
	public Date getAtime() {
		return atime;
	}
	public void setAtime(Date atime) {
		this.atime = atime;
	}
}
