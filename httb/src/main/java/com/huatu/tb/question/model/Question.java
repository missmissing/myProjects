/**
 *
 */
package com.huatu.tb.question.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: Question
 * @Description: 试题
 * @author LiXin
 * @date 2015年4月20日 上午9:29:31
 * @version 1.0
 *
 */
public class Question implements Serializable{
	/**
	 *Question.java:TODO
	 */

	private static final long serialVersionUID = 1L;
	private String qid;// 试题主键
	private String qyear;// 年份
	private String qarea;// 区域
	private String qtype;// 题目类型(0 -> 单选题；1 -> 多选题；2 -> 共用题干；3 -> 共用备选；)
	private List<String> qpoint;// 知识点,考点(数据字典ID)
	private String qcontent;// 试题题干
	private String qdifficulty;// 难度
	private List<ChildQuestion> qchild;// 具体子问题集合
	private List<Corrects> qcorrect;// 纠错
	//所属分校(组织机构)
	private String qorg;
	//导题批次号
	private String qbatchnum;


	private String qattribute;// 属性(0 真题  1 模拟题)
	private List<String> qcategory;// 考试分类(护士资格考试，护师资格考试，主管资格考试,事业单位考试)
								   //事业单位   1	资格证        2	招教              3	特岗
								   //教师 1	中学
									//	2	小学
									//	3	幼儿园
									//	4	初中
									//	5	中职
									//	6	高中
									//	7	中小学
	
	private String qparaphrase;// 释义
	private String qfrom;// 信息源
	private String qskill;// 答题技巧
	private String qauthor;// 作者
	private String qdiscussion;// 讨论
	private String qvideourl;// 视频路径
	private String qstatus;//试题审核状态(0->编辑中；1->待审核；2->审核中；3->发布；4->退回；5->下线)

	private String tombstone;// 假删除标识(0 -> 有效；1 -> 删除)
	private Date createtime;// 创建时间
	private String createuser;// 创建人账号
	private Date updatetime;// 修改时间
	private String updateuser;// 修改人账号

	private String qauditopinion;//审核意见
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getQyear() {
		return qyear;
	}
	public void setQyear(String qyear) {
		this.qyear = qyear;
	}
	public String getQarea() {
		return qarea;
	}
	public void setQarea(String qarea) {
		this.qarea = qarea;
	}
	public String getQtype() {
		return qtype;
	}
	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	public List<String> getQpoint() {
		return qpoint;
	}
	public void setQpoint(List<String> qpoint) {
		this.qpoint = qpoint;
	}
	public String getQcontent() {
		return qcontent;
	}
	public void setQcontent(String qcontent) {
		this.qcontent = qcontent;
	}
	public String getQdifficulty() {
		return qdifficulty;
	}
	public void setQdifficulty(String qdifficulty) {
		this.qdifficulty = qdifficulty;
	}

	public List<ChildQuestion> getQchild() {
		return qchild;
	}
	public void setQchild(List<ChildQuestion> qchild) {
		this.qchild = qchild;
	}
	public String getQattribute() {
		return qattribute;
	}
	public void setQattribute(String qattribute) {
		this.qattribute = qattribute;
	}
	public List<String> getQcategory() {
		return qcategory;
	}
	public void setQcategory(List<String> qcategory) {
		this.qcategory = qcategory;
	}
	public List<Corrects> getQcorrect() {
		return qcorrect;
	}
	public void setQcorrect(List<Corrects> qcorrect) {
		this.qcorrect = qcorrect;
	}
	public String getQparaphrase() {
		return qparaphrase;
	}
	public void setQparaphrase(String qparaphrase) {
		this.qparaphrase = qparaphrase;
	}
	public String getQfrom() {
		return qfrom;
	}
	public void setQfrom(String qfrom) {
		this.qfrom = qfrom;
	}
	public String getQskill() {
		return qskill;
	}
	public void setQskill(String qskill) {
		this.qskill = qskill;
	}
	public String getQauthor() {
		return qauthor;
	}
	public void setQauthor(String qauthor) {
		this.qauthor = qauthor;
	}
	public String getQdiscussion() {
		return qdiscussion;
	}
	public void setQdiscussion(String qdiscussion) {
		this.qdiscussion = qdiscussion;
	}
	public String getQvideourl() {
		return qvideourl;
	}
	public void setQvideourl(String qvideourl) {
		this.qvideourl = qvideourl;
	}
	public String getTombstone() {
		return tombstone;
	}
	public void setTombstone(String tombstone) {
		this.tombstone = tombstone;
	}
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
	public String getQstatus() {
		return qstatus;
	}
	public void setQstatus(String qstatus) {
		this.qstatus = qstatus;
	}



	public String getQauditopinion() {
		return qauditopinion;
	}
	public void setQauditopinion(String qauditopinion) {
		this.qauditopinion = qauditopinion;
	}
	public String getQorg() {
		return qorg;
	}
	public void setQorg(String qorg) {
		this.qorg = qorg;
	}
	public String getQbatchnum() {
		return qbatchnum;
	}
	public void setQbatchnum(String qbatchnum) {
		this.qbatchnum = qbatchnum;
	}

}
