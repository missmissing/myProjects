/**   
 * 项目名：				httb
 * 包名：				com.huatu.extractdata.gongwuyuan.vo  
 * 文件名：				FuhetiVo.java    
 * 日期：				2015年6月4日-下午3:36:34  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text.extractdata.vo;

import java.util.List;

/**   
 * 类名称：				FuhetiVo  
 * 类描述：  			复合题提取对象
 * 创建人：				LiXin
 * 创建时间：			2015年6月4日 下午3:36:34  
 * @version 		1.0
 */
public class FuhetiVo {
	private String PUKEY;//	试题ID
	private String stem;//		题干
	private String info_from;//		信息来源
	private String item_1;//		关联题项-1
	private String item_2;//		关联题项-2
	private String item_3;//		关联题项-3
	private String item_4;//		关联题项-4
	private String item_5;//		关联题项-5
	private String item_6;//		关联题项-6
	private String item_7;//		关联题项-7
	private String item_8;//		关联题项-8
	private String item_9;//		关联题项-9
	private String item_10;//		关联题项-10
	private String is_ture_answer;//		是否为真题
	private String source;//		试题来源
	private String source_year;//		试题年份(来源)
	private String source_area;//		试题来源地区
	private Double point;//		试题分数
	private Double difficult_grade;//		难度系数
	private String shenhe;
	private List<String> childs;
	public String getPUKEY() {
		return PUKEY;
	}
	public void setPUKEY(String pUKEY) {
		PUKEY = pUKEY;
	}
	public String getStem() {
		return stem;
	}
	public void setStem(String stem) {
		this.stem = stem;
	}
	public String getInfo_from() {
		return info_from;
	}
	public void setInfo_from(String info_from) {
		this.info_from = info_from;
	}
	public String getItem_1() {
		return item_1;
	}
	public void setItem_1(String item_1) {
		this.item_1 = item_1;
	}
	public String getItem_2() {
		return item_2;
	}
	public void setItem_2(String item_2) {
		this.item_2 = item_2;
	}
	public String getItem_3() {
		return item_3;
	}
	public void setItem_3(String item_3) {
		this.item_3 = item_3;
	}
	public String getItem_4() {
		return item_4;
	}
	public void setItem_4(String item_4) {
		this.item_4 = item_4;
	}
	public String getItem_5() {
		return item_5;
	}
	public void setItem_5(String item_5) {
		this.item_5 = item_5;
	}
	public String getItem_6() {
		return item_6;
	}
	public void setItem_6(String item_6) {
		this.item_6 = item_6;
	}
	public String getItem_7() {
		return item_7;
	}
	public void setItem_7(String item_7) {
		this.item_7 = item_7;
	}
	public String getItem_8() {
		return item_8;
	}
	public void setItem_8(String item_8) {
		this.item_8 = item_8;
	}
	public String getItem_9() {
		return item_9;
	}
	public void setItem_9(String item_9) {
		this.item_9 = item_9;
	}
	public String getItem_10() {
		return item_10;
	}
	public void setItem_10(String item_10) {
		this.item_10 = item_10;
	}
	public String getIs_ture_answer() {
		return is_ture_answer;
	}
	public void setIs_ture_answer(String is_ture_answer) {
		this.is_ture_answer = is_ture_answer;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSource_year() {
		return source_year;
	}
	public void setSource_year(String source_year) {
		this.source_year = source_year;
	}
	public String getSource_area() {
		return source_area;
	}
	public void setSource_area(String source_area) {
		this.source_area = source_area;
	}
	public Double getPoint() {
		return point;
	}
	public void setPoint(Double point) {
		this.point = point;
	}
	public Double getDifficult_grade() {
		return difficult_grade;
	}
	public void setDifficult_grade(Double difficult_grade) {
		this.difficult_grade = difficult_grade;
	}
	public String getShenhe() {
		return shenhe;
	}
	public void setShenhe(String shenhe) {
		this.shenhe = shenhe;
	}
	public List<String> getChilds() {
		return childs;
	}
	public void setChilds(List<String> childs) {
		this.childs = childs;
	}
	
}
