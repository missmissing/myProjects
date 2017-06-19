/**
 * 项目名：				httb
 * 包名：					com.huatu.analysis.model
 * 文件名：				Htquesanalysis.java
 * 日期：					2015年6月21日-下午5:18:59
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.model;

import java.io.Serializable;

/**
 * 类名称：				Htquesanalysis
 * 类描述：				试题信息统计分析
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月21日 下午5:18:59
 * @version 			0.0.1
 */
public class Htquesanalysis implements Serializable{
	private static final long serialVersionUID = -2896779353186846507L;

	/**试题id*/
	private String qid;

	/**试题答案*/
	private String qqans;

	/**试题答题次数*/
	private int qansamount = 0;

	/**正确次数*/
	private int qcorrectamount = 0;

	/**选A的次数*/
	private int qchoicea = 0;

	/**选B的次数*/
	private int qchoiceb = 0;

	/**选C的次数*/
	private int qchoicec = 0;

	/**选D的次数*/
	private int qchoiced = 0;

	/**选E的次数*/
	private int qchoicee = 0;

	/**选F的次数*/
	private int qchoicef = 0;

	/**选G的次数*/
	private int qchoiceg = 0;

	/**选H的次数*/
	private int qchoiceh = 0;

	/**时间戳*/
	private String qrecorddate;

	/**易错项*/
	private String qmostwrong;

	/**第二易错项*/
	private String qsecendwrong;

	//get、set方法
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getQqans() {
		return qqans;
	}
	public void setQqans(String qqans) {
		this.qqans = qqans;
	}
	public int getQansamount() {
		return qansamount;
	}
	public void setQansamount(int qansamount) {
		this.qansamount = qansamount;
	}
	public int getQcorrectamount() {
		return qcorrectamount;
	}
	public void setQcorrectamount(int qcorrectamount) {
		this.qcorrectamount = qcorrectamount;
	}
	public int getQchoicea() {
		return qchoicea;
	}
	public void setQchoicea(int qchoicea) {
		this.qchoicea = qchoicea;
	}
	public int getQchoiceb() {
		return qchoiceb;
	}
	public void setQchoiceb(int qchoiceb) {
		this.qchoiceb = qchoiceb;
	}
	public int getQchoicec() {
		return qchoicec;
	}
	public void setQchoicec(int qchoicec) {
		this.qchoicec = qchoicec;
	}
	public int getQchoiced() {
		return qchoiced;
	}
	public void setQchoiced(int qchoiced) {
		this.qchoiced = qchoiced;
	}
	public int getQchoicee() {
		return qchoicee;
	}
	public void setQchoicee(int qchoicee) {
		this.qchoicee = qchoicee;
	}
	public int getQchoicef() {
		return qchoicef;
	}
	public void setQchoicef(int qchoicef) {
		this.qchoicef = qchoicef;
	}
	public int getQchoiceg() {
		return qchoiceg;
	}
	public void setQchoiceg(int qchoiceg) {
		this.qchoiceg = qchoiceg;
	}
	public int getQchoiceh() {
		return qchoiceh;
	}
	public void setQchoiceh(int qchoiceh) {
		this.qchoiceh = qchoiceh;
	}
	public String getQrecorddate() {
		return qrecorddate;
	}
	public void setQrecorddate(String qrecorddate) {
		this.qrecorddate = qrecorddate;
	}
	public String getQmostwrong() {
		return qmostwrong;
	}
	public void setQmostwrong(String qmostwrong) {
		this.qmostwrong = qmostwrong;
	}
	public String getQsecendwrong() {
		return qsecendwrong;
	}
	public void setQsecendwrong(String qsecendwrong) {
		this.qsecendwrong = qsecendwrong;
	}
}
