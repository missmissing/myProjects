/**
 *
 */
package com.huatu.tb.question.model;

import java.io.Serializable;
import java.util.List;
/**
 * @ClassName: ChildrenQuestion
 * @Description: 子试题(试题的子集合对象)
 * @author LiXin
 * @date 2015年4月20日 上午9:31:16
 * @version 1.0
 *
 */
public class ChildQuestion implements Serializable{

	private static final long serialVersionUID = -1961747002189960919L;
	private String qcid;//子题id
	private String qctype;//题型
	private String qccontent;//子题干
	private List<String> qcchoiceList;//选项 列表
	private String qcchoices;//选项---该字段仅用户数据库存储
	private String qcans;//答案
	private String qccomment;//解析
	private String qcextension;//拓展
	private float qcscore;// 分值
	public String getQcid() {
		return qcid;
	}
	public void setQcid(String qcid) {
		this.qcid = qcid;
	}
	public String getQctype() {
		return qctype;
	}
	public void setQctype(String qctype) {
		this.qctype = qctype;
	}
	public String getQccontent() {
		return qccontent;
	}
	public void setQccontent(String qccontent) {
		this.qccontent = qccontent;
	}

	public String getQcchoices() {
		return qcchoices;
	}
	public void setQcchoices(String qcchoices) {
		this.qcchoices = qcchoices;
	}
	public String getQcans() {
		return qcans;
	}
	public void setQcans(String qcans) {
		this.qcans = qcans;
	}
	public String getQccomment() {
		return qccomment;
	}
	public void setQccomment(String qccomment) {
		this.qccomment = qccomment;
	}
	public String getQcextension() {
		return qcextension;
	}
	public void setQcextension(String qcextension) {
		this.qcextension = qcextension;
	}
	public float getQcscore() {
		return qcscore;
	}
	public void setQcscore(float qcscore) {
		this.qcscore = qcscore;
	}
	public List<String> getQcchoiceList() {
		return qcchoiceList;
	}
	public void setQcchoiceList(List<String> qcchoiceList) {
		this.qcchoiceList = qcchoiceList;
	}

}
