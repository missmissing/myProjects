/**
 * 项目名：				httb
 * 包名：				com.huatu.api.po
 * 文件名：				QuestionPo.java
 * 日期：				2015年6月13日-下午5:07:21
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.api.po;

import java.io.Serializable;
import java.util.List;

/**
 * 类名称：				QuestionPo
 * 类描述：  			试题写入PO对象【来自app对象】
 * 创建人：				LiXin
 * 创建时间：			2015年6月13日 下午5:07:21
 * @version 		1.0
 */
public class QuestionPo implements Serializable{
	private static final long serialVersionUID = 8329639129977976489L;
	private String userno;//答题人no
	private String qid;//试题id
	private List<String> qusanswer;//试题元答案
	private List<String> useranswers;//用户答案
	private String iserror;//是否错误 0- 错误  1 正确 -1 为空
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public List<String> getQusanswer() {
		return qusanswer;
	}
	public void setQusanswer(List<String> qusanswer) {
		this.qusanswer = qusanswer;
	}
	public List<String> getUseranswers() {
		return useranswers;
	}
	public void setUseranswers(List<String> useranswers) {
		this.useranswers = useranswers;
	}
	public String getIserror() {
		return iserror;
	}
	public void setIserror(String iserror) {
		this.iserror = iserror;
	}


}
