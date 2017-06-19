/**
 * 项目名：				httb
 * 包名：				com.huatu.tb.paper.model
 * 文件名：				Paper.java
 * 日期：				2015年5月11日-下午4:31:34
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.tb.paper.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huatu.core.util.Constants;
/**
 * 类名称：				Paper
 * 类描述：  			试卷对象
 * 创建人：				LiXin
 * 创建时间：			2015年5月11日 下午4:31:34
 * @version 		1.0
 */
public class Paper implements Serializable{
	private String pid;// 试卷主键
	private String psubtitle;// 副标题
	private String ptitle;// 试卷标题
	private String pyear;// 年份 　
	private String parea;// 地域 　
	private List<String> pqids;// 试题ID集合
	private String pattribute;// 试卷属性(0==>真题，1==>模拟题) 　
	private Map<String, Object> pattrs;// 试卷扩展信息(JSON Map)
	private String pstatus;// 试卷审核状态(0->编辑中；1->待审核；2->审核中；3->发布;4->下线) 0
	private String tombstone = Constants.TOMBSTONE_DeleteFlag_NO;// 假删除标识(0->有效；1->删除)
	private Date updatetime;// 试卷数据最近更新时间
	private String updateuser;// 修改人账号
	private Date createtime;// 试卷数据最近更新时间
	private String createuser;// 创建人ID
	private String pauditopinion;//审核意见
	private List<String> pareas;//试卷地域(公务员)
	private List<String> pexamtype;//试卷分类(医疗)
	private List<String> pcategorys;//一级章节分类（如 金融的 金融学  会计学   经济学）
	private int ptimelimit;//试卷时限(单位分钟)
	private List<String> porgs;//所属分校

	public static void main(String[] args) {
		System.currentTimeMillis();
	}

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPsubtitle() {
		return psubtitle;
	}
	public void setPsubtitle(String psubtitle) {
		this.psubtitle = psubtitle;
	}
	public String getPtitle() {
		return ptitle;
	}
	public void setPtitle(String ptitle) {
		this.ptitle = ptitle;
	}
	public String getPyear() {
		return pyear;
	}
	public void setPyear(String pyear) {
		this.pyear = pyear;
	}
	public String getParea() {
		return parea;
	}
	public void setParea(String parea) {
		this.parea = parea;
	}
	public List<String> getPqids() {
		return pqids;
	}
	public void setPqids(List<String> pqids) {
		this.pqids = pqids;
	}
	public String getPattribute() {
		return pattribute;
	}
	public void setPattribute(String pattribute) {
		this.pattribute = pattribute;
	}
	public Map<String, Object> getPattrs() {
		return pattrs;
	}
	public void setPattrs(Map<String, Object> pattrs) {
		this.pattrs = pattrs;
	}
	public String getPstatus() {
		return pstatus;
	}
	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
	}
	public String getTombstone() {
		return tombstone;
	}
	public void setTombstone(String tombstone) {
		this.tombstone = tombstone;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getUpdateuser() {
		return updateuser;
	}
	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getCreateuser() {
		return createuser;
	}
	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}
	public String getPauditopinion() {
		return pauditopinion;
	}
	public void setPauditopinion(String pauditopinion) {
		this.pauditopinion = pauditopinion;
	}
	public List<String> getPareas() {
		return pareas;
	}
	public void setPareas(List<String> pareas) {
		this.pareas = pareas;
	}
	public List<String> getPcategorys() {
		return pcategorys;
	}
	public void setPcategorys(List<String> pcategorys) {
		this.pcategorys = pcategorys;
	}
	public int getPtimelimit() {
		return ptimelimit;
	}
	public void setPtimelimit(int ptimelimit) {
		this.ptimelimit = ptimelimit;
	}
	public List<String> getPorgs() {
		return porgs;
	}
	public void setPorgs(List<String> porgs) {
		this.porgs = porgs;
	}
	public List<String> getPexamtype() {
		return pexamtype;
	}
	public void setPexamtype(List<String> pexamtype) {
		this.pexamtype = pexamtype;
	}

}
