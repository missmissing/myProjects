/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.category.model  
 * 文件名：				CateQues.java    
 * 日期：				2015年6月8日-上午9:28:29  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.category.model;

import java.io.Serializable;
import java.util.List;

/**   
 * 类名称：				CateQues  
 * 类描述：                                          章节-试题关系
 * 创建人：				LiXin
 * 创建时间：			2015年6月8日 上午9:28:29  
 * @version 		1.0
 */
public class CateQues implements Serializable {
	private String cid;//章节id
	private String qid;//试题id
	private String attr;//属性   公务员下存地区编号  待续...
	private List<String> qcids ;//子试题ID集合
	
	public List<String> getQcids() {
		return qcids;
	}
	public void setQcids(List<String> qcids) {
		this.qcids = qcids;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}

}
