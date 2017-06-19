/**
 * 项目名：				httb
 * 包名：				com.huatu.api.po
 * 文件名：				PaperPo.java
 * 日期：				2015年6月13日-下午5:06:32
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.api.po;

import java.io.Serializable;
import java.util.List;

/**
 * 类名称：				PaperPo
 * 类描述：  			试卷写入PO对象【来自app对象】
 * 创建人：				LiXin
 * 创建时间：			2015年6月13日 下午5:06:32
 * @version 		1.0
 */
public class PaperPo implements Serializable{
	private static final long serialVersionUID = -1201435175999535671L;
	private String userno;//答题人id
	private String type;//类型  0 随机联系  1 顺序练习  2 模拟题  3 真题
	private String pid;  //试卷Id
	private String point;//知识点
	private String second;//答题时间
	private List<QuestionPo> queslist; //所做试题集合
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public List<QuestionPo> getQueslist() {
		return queslist;
	}
	public void setQueslist(List<QuestionPo> queslist) {
		this.queslist = queslist;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}

}
