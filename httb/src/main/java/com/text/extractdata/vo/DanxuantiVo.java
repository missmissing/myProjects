/**   
 * 项目名：				httb
 * 包名：				com.huatu.extractdata.gongwuyuan.vo  
 * 文件名：				DanxuantiVo.java    
 * 日期：				2015年6月4日-下午3:53:02  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text.extractdata.vo;

/**   
 * 类名称：				DanxuantiVo  
 * 类描述：  			单选题提取对象
 * 创建人：				LiXin
 * 创建时间：			2015年6月4日 下午3:53:02  
 * @version 		1.0
 */
public class DanxuantiVo {

	private String id ; // 试题ID
	private String tigan; // 题干
	private String tixing; // 题型
	private String isfuhe; // 是否复合题项    1 复合题   -1 单选题
	private String fuheID; // 复合题ID
	private String xuan1; // 选项-1
	private String xuan2 ; // 选项-2
	private String xuan3 ; // 选项-3
	private String xuan4 ; // 选项-4
	private String xuan5 ; // 选项-5
	private String xuan6 ; // 选项-6
	private String xuan7 ; // 选项-7
	private String xuan8 ; // 选项-8
	private String xuan9; // 选项-9
	private String xuan10; // 选项-10
	private String daan; // 参考答案
	private String jiexi; // 参考解析
	private String shiyi; // 参考释义
	private String jiqiao; // 解题技巧
	private String kuozhan; // 解题拓展
	private String zuozhe; // 解析作者
	private String laiyuan; // 信息来源
	private String iszhenti ; // 是否为真题-----
	private String laiyuan2 ; // 试题来源
	private String nianfen ; // 试题年份(来源)
	private String diqu; 		// 试题来源地区
	private String fenshu; // 试题分数
	private String shenhe; // 审核标示    1-已经校对    -1为未校对
	private String biaoshi; // 有效标示 
	private Double nandu;	//难度
	private String leixing;//考试类型
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTigan() {
		return tigan;
	}
	public void setTigan(String tigan) {
		this.tigan = tigan;
	}
	public String getTixing() {
		return tixing;
	}
	public void setTixing(String tixing) {
		this.tixing = tixing;
	}
	public String getIsfuhe() {
		return isfuhe;
	}
	public void setIsfuhe(String isfuhe) {
		this.isfuhe = isfuhe;
	}
	public String getFuheID() {
		return fuheID;
	}
	public void setFuheID(String fuheID) {
		this.fuheID = fuheID;
	}
	public String getXuan1() {
		return xuan1;
	}
	public void setXuan1(String xuan1) {
		this.xuan1 = xuan1;
	}
	public String getXuan2() {
		return xuan2;
	}
	public void setXuan2(String xuan2) {
		this.xuan2 = xuan2;
	}
	public String getXuan3() {
		return xuan3;
	}
	public void setXuan3(String xuan3) {
		this.xuan3 = xuan3;
	}
	public String getXuan4() {
		return xuan4;
	}
	public void setXuan4(String xuan4) {
		this.xuan4 = xuan4;
	}
	public String getXuan5() {
		return xuan5;
	}
	public void setXuan5(String xuan5) {
		this.xuan5 = xuan5;
	}
	public String getXuan6() {
		return xuan6;
	}
	public void setXuan6(String xuan6) {
		this.xuan6 = xuan6;
	}
	public String getXuan7() {
		return xuan7;
	}
	public void setXuan7(String xuan7) {
		this.xuan7 = xuan7;
	}
	public String getXuan8() {
		return xuan8;
	}
	public void setXuan8(String xuan8) {
		this.xuan8 = xuan8;
	}
	public String getXuan9() {
		return xuan9;
	}
	public void setXuan9(String xuan9) {
		this.xuan9 = xuan9;
	}
	public String getXuan10() {
		return xuan10;
	}
	public void setXuan10(String xuan10) {
		this.xuan10 = xuan10;
	}
	public String getDaan() {
		return daan;
	}
	public void setDaan(String daan) {
		this.daan = daan;
	}
	public String getJiexi() {
		return jiexi;
	}
	public void setJiexi(String jiexi) {
		this.jiexi = jiexi;
	}
	public String getShiyi() {
		return shiyi;
	}
	public void setShiyi(String shiyi) {
		this.shiyi = shiyi;
	}
	public String getJiqiao() {
		return jiqiao;
	}
	public void setJiqiao(String jiqiao) {
		this.jiqiao = jiqiao;
	}
	public String getKuozhan() {
		return kuozhan;
	}
	public void setKuozhan(String kuozhan) {
		this.kuozhan = kuozhan;
	}
	public String getZuozhe() {
		return zuozhe;
	}
	public void setZuozhe(String zuozhe) {
		this.zuozhe = zuozhe;
	}
	public String getLaiyuan() {
		return laiyuan;
	}
	public void setLaiyuan(String laiyuan) {
		this.laiyuan = laiyuan;
	}
	public String getIszhenti() {
		return iszhenti;
	}
	public void setIszhenti(String iszhenti) {
		this.iszhenti = iszhenti;
	}
	public String getLaiyuan2() {
		return laiyuan2;
	}
	public void setLaiyuan2(String laiyuan2) {
		this.laiyuan2 = laiyuan2;
	}
	public String getNianfen() {
		return nianfen;
	}
	public void setNianfen(String nianfen) {
		this.nianfen = nianfen;
	}
	public String getDiqu() {
		return diqu;
	}
	public void setDiqu(String diqu) {
		this.diqu = diqu;
	}
	public String getFenshu() {
		return fenshu;
	}
	public void setFenshu(String fenshu) {
		this.fenshu = fenshu;
	}
	public String getShenhe() {
		return shenhe;
	}
	public void setShenhe(String shenhe) {
		this.shenhe = shenhe;
	}
	public String getBiaoshi() {
		return biaoshi;
	}
	public void setBiaoshi(String biaoshi) {
		this.biaoshi = biaoshi;
	}
	public Double getNandu() {
		return nandu;
	}
	public void setNandu(Double nandu) {
		this.nandu = nandu;
	}
	public String getLeixing() {
		return leixing;
	}
	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}
	
}
