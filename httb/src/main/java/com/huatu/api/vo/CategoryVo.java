/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.restint.vo  
 * 文件名：				CategoryVo.java    
 * 日期：				2015年5月21日-下午3:50:34  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.vo;

import java.io.Serializable;
import java.util.List;

/**   
 * 类名称：				CategoryVo  
 * 类描述：  			章节 知识点  VO对象
 * 创建人：				LiXin
 * 创建时间：			2015年5月21日 下午3:50:34  
 * @version 		1.0
 */
public class CategoryVo implements Serializable{
	private String cid;//章节ID
	private String name;//章节名称
	private String pid;//父节点ID
	private String explain;//说明（公务员叶子节点）        ****预留
	private String count;//试题个数
	private Integer ordernum;//排序字段
	private String isdownload;//是否有下载包     0 存在  1 不存在
	private String versions;//版本[有下载包的前提下使用]
	private List<String> qids;//试题ID
	private List<CategoryVo> children; //子节点
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<CategoryVo> getChildren() {
		return children;
	}
	public void setChildren(List<CategoryVo> children) {
		this.children = children;
	}
	public Integer getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}
	public List<String> getQids() {
		return qids;
	}
	public void setQids(List<String> qids) {
		this.qids = qids;
	}
	public String getIsdownload() {
		return isdownload;
	}
	public void setIsdownload(String isdownload) {
		this.isdownload = isdownload;
	}
	public String getVersions() {
		return versions;
	}
	public void setVersions(String versions) {
		this.versions = versions;
	}
}
