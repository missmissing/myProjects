/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.vo  
 * 文件名：				QuesAttrVo.java    
 * 日期：				2015年6月13日-下午4:09:31  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.vo;

/**   
 * 类名称：				QuesAttrVo  
 * 类描述：  			试题分析属性VO
 * 创建人：				LiXin
 * 创建时间：			2015年6月13日 下午4:09:31  
 * @version 		1.0
 */
public class QuesAttrVo {

	private String qid;//试题Id
	private String answercount;//答题个数
	private String precision;//准确率
	private String fallibility;//易错项
	
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getAnswercount() {
		return answercount;
	}
	public void setAnswercount(String answercount) {
		this.answercount = answercount;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getFallibility() {
		return fallibility;
	}
	public void setFallibility(String fallibility) {
		this.fallibility = fallibility;
	}
	

}
