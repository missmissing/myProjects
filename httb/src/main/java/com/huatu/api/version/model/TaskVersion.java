/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.version.model  
 * 文件名：				TaskVersion.java    
 * 日期：				2015年6月21日-下午2:46:53  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.version.model;

import java.util.Date;

/**   
 * 类名称：				TaskVersion  
 * 类描述：  			标记或版本 信息对象
 * 					对应表 -HTTASKVERSION
 * 创建人：				
 * 创建时间：			2015年6月21日 下午2:46:53  
 * @version 		1.0
 */
public class TaskVersion {
	private String tkey;//标记key
	private String tvalue;//标记value
	private String tpapertype;//试卷类型(是否真题，模拟题) (0==>真题，1==>模拟题) 　
	private String tdesc;//描述
	private Date createtime;//创建时间
	public TaskVersion() {
		
	}
	public TaskVersion(String tkey, String tvalue, String tpapertype,
			String tdesc, Date createtime) {
		super();
		this.tkey = tkey;
		this.tvalue = tvalue;
		this.tpapertype = tpapertype;
		this.tdesc = tdesc;
		this.createtime = createtime;
	}
	public String getTkey() {
		return tkey;
	}
	public void setTkey(String tkey) {
		this.tkey = tkey;
	}
	public String getTvalue() {
		return tvalue;
	}
	public void setTvalue(String tvalue) {
		this.tvalue = tvalue;
	}
	public String getTpapertype() {
		return tpapertype;
	}
	public void setTpapertype(String tpapertype) {
		this.tpapertype = tpapertype;
	}
	public String getTdesc() {
		return tdesc;
	}
	public void setTdesc(String tdesc) {
		this.tdesc = tdesc;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	


}
