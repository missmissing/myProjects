/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.vo  
 * 文件名：				PaperAttrVo.java    
 * 日期：				2015年6月24日-下午8:18:50  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.vo;

import java.util.List;

/**   
 * 类名称：				PaperAttrVo  
 * 类描述：  			试卷属性对象
 * 创建人：				LiXin
 * 创建时间：			2015年6月24日 下午8:18:50  
 * @version 		1.0
 */
public class PaperAttrVo {
	private String pid;//试卷
	private String theranking;//本次排名
	private String headcount;//答题总人数
	private String averagetime;//平均用时
	private String averagescore;//平均得分
	private List<String> utendlist;//个人得分趋势
	private List<String> meantendlist;//平均得分趋势
	private List<QuesAttrVo> qavList;//试题分析
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getTheranking() {
		return theranking;
	}
	public void setTheranking(String theranking) {
		this.theranking = theranking;
	}
	public String getHeadcount() {
		return headcount;
	}
	public void setHeadcount(String headcount) {
		this.headcount = headcount;
	}
	public String getAveragetime() {
		return averagetime;
	}
	public void setAveragetime(String averagetime) {
		this.averagetime = averagetime;
	}
	public String getAveragescore() {
		return averagescore;
	}
	public void setAveragescore(String averagescore) {
		this.averagescore = averagescore;
	}
	public List<QuesAttrVo> getQavList() {
		return qavList;
	}
	public void setQavList(List<QuesAttrVo> qavList) {
		this.qavList = qavList;
	}
	public List<String> getUtendlist() {
		return utendlist;
	}
	public void setUtendlist(List<String> utendlist) {
		this.utendlist = utendlist;
	}
	public List<String> getMeantendlist() {
		return meantendlist;
	}
	public void setMeantendlist(List<String> meantendlist) {
		this.meantendlist = meantendlist;
	}
	
	
}
