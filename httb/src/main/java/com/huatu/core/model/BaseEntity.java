package com.huatu.core.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huatu.core.util.CommonUtil;

/**
 *
 * 类名称：				BaseEntity
 * 类描述：  				基础实例类
 * 创建人：				Aijunbo
 * 创建时间：				2015年4月1日 下午2:20:05
 * @version 			0.0.1
 */
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	/**实例id*/
	public String id;
	/**删除标识*/
	public boolean delFlag=false;
	/**创建时间*/
	private Date createTime;
	/**创建人*/
	private String createUser;
	/**描述*/
	private String description;

	public String getId() {

		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDelFlag() {
		return delFlag;
	}

	public void setDelFlag(boolean delFlag) {
		this.delFlag = delFlag;
	}

	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getDescription() {
		if(CommonUtil.isNotNull(description)){
			return description;
		}else{
			return "";
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
     * 获得一个UUID
     * @return String UUID
     */
    public String getUUID(){
    	return UUID.randomUUID().toString();
    }

}
