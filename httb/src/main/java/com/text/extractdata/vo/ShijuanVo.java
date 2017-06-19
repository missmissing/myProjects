/**   
 * 项目名：				httb
 * 包名：				com.huatu.extractdata.gongwuyuan.vo  
 * 文件名：				ShijuanVo.java    
 * 日期：				2015年6月5日-上午11:03:35  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text.extractdata.vo;

import java.util.Date;

/**   
 * 类名称：				ShijuanVo  
 * 类描述：  			试卷提取对象
 * 创建人：				LiXin
 * 创建时间：			2015年6月5日 上午11:03:35  
 * @version 		1.0
 */
public class ShijuanVo {

	private String type;//题型   真题 1  模拟题0
	private String PUKEY;//	记录ID
	private String pastpaper_name;//	真题卷名称
	private String past_year;//	真题年份
	private String past_area;//	真题地区
	private String pastpaper_mark;//	试卷其他标示
	private String shenhe ;//	审核标示
	private String youxia;//	有效标识
	private Date updatetime; //修改时间
	private Date createtime;//创建时间
	private String category;//类型 如 美术  语文
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPUKEY() {
		return PUKEY;
	}
	public void setPUKEY(String pUKEY) {
		PUKEY = pUKEY;
	}
	public String getPastpaper_name() {
		return pastpaper_name;
	}
	public void setPastpaper_name(String pastpaper_name) {
		this.pastpaper_name = pastpaper_name;
	}
	public String getPast_year() {
		return past_year;
	}
	public void setPast_year(String past_year) {
		this.past_year = past_year;
	}
	public String getPast_area() {
		return past_area;
	}
	public void setPast_area(String past_area) {
		this.past_area = past_area;
	}
	public String getPastpaper_mark() {
		return pastpaper_mark;
	}
	public void setPastpaper_mark(String pastpaper_mark) {
		this.pastpaper_mark = pastpaper_mark;
	}
	public String getShenhe() {
		return shenhe;
	}
	public void setShenhe(String shenhe) {
		this.shenhe = shenhe;
	}
	public String getYouxia() {
		return youxia;
	}
	public void setYouxia(String youxia) {
		this.youxia = youxia;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	

}
