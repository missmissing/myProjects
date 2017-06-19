/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.restint.vo  
 * 文件名：				PaperVo.java    
 * 日期：				2015年5月22日-上午8:58:51  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.vo;

import java.io.Serializable;

/**   
 * 类名称：				PaperVo  
 * 类描述：  			试卷VO对象
 * 创建人：				LiXin
 * 创建时间：			2015年5月22日 上午8:58:51  
 * @version 		1.0
 */
public class PaperVo implements Serializable{
	private String pid;			//试卷ID
	private String name;		//试卷名称
	private String year;		//年份 
	private String versions;    //当前版本
	private String attribute;	//属性( 真题 、 模拟题) 【预留】
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getVersions() {
		return versions;
	}
	public void setVersions(String versions) {
		this.versions = versions;
	}

}
