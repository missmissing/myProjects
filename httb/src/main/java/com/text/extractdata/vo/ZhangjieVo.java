/**   
 * 项目名：				httb
 * 包名：				com.huatu.extractdata.gongwuyuan.vo  
 * 文件名：				ZhangjieVo.java    
 * 日期：				2015年6月4日-下午3:59:19  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.text.extractdata.vo;

import java.util.Date;

/**   
 * 类名称：				ZhangjieVo  
 * 类描述：  			章节提取对象
 * 创建人：				LiXin
 * 创建时间：			2015年6月4日 下午3:59:19  
 * @version 		1.0
 */
public class ZhangjieVo {

	private String id ;//章节ID
	private String name ;//章节名称
	private String descrp;//章节描述
	private String pid;//父节点ID
	private String levels;//层级   1 2 3 4 5 。。
	private String createUser;//
	private Date createTime;//
	private String updateUser;//
	private Date updateTime;//
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescrp() {
		return descrp;
	}
	public void setDescrp(String descrp) {
		this.descrp = descrp;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getLevels() {
		return levels;
	}
	public void setLevels(String levels) {
		this.levels = levels;
	}
	
	
}
