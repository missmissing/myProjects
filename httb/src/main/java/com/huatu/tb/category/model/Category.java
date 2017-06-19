/**
 *
 */
package com.huatu.tb.category.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Category
 * @Description: 分类(类型)   章节层级分类
 * @author LiXin
 * @date 2015年4月17日 下午4:23:58
 * @version 1.0
 *
 */
public class Category implements Serializable{
	private static final long serialVersionUID = 7744516881661695464L;
	private String cid;//主键(以三位数字区分父子节点100100)
	private String cname;//标签名
	private String cexplain;//说明
	private String clevels;//层级   1 2 3 4 5 。。
	private Integer cordernum;//排序字段
	private String cpid;//父节点ID

	private String tombstone = "0";//假删除标识(0->有效；1->删除)
	private Date createtime;//创建时间
	private String createuser;//创建人账号
	private Date updatetime;//修改时间
	private String updateuser;//

	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Integer getCordernum() {
		return cordernum;
	}
	public void setCordernum(Integer cordernum) {
		this.cordernum = cordernum;
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
	public String getCexplain() {
		return cexplain;
	}
	public void setCexplain(String cexplain) {
		this.cexplain = cexplain;
	}
	public String getCpid() {
		return cpid;
	}
	public void setCpid(String cpid) {
		this.cpid = cpid;
	}
	public String getClevels() {
		return clevels;
	}
	public void setClevels(String clevels) {
		this.clevels = clevels;
	}


}