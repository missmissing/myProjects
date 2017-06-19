/**
 * 项目名：								httb
 * 包名：									com.huatu.upload.model
 * 文件名：								upload.java
 * 日期：									2015年4月23日-下午3:58:50
 * Copyright							(c) 2015华图教育-版权所有
 */
package com.huatu.upload.model;

import java.util.Date;

/**
 * 类名称：								upload
 * 类描述：								图片实体类(文件上传)
 * 创建人：								Aijunbo
 * 创建时间：								2015年4月23日 下午3:58:50
 * @version 							0.0.1
 */
public class Image {
	/**图片id作主键 */
	private String aid;

	/**图片路劲 */
	private String imgurl;

	/**图片名称(图片原名称) */
	private String imgname;

	/**图片名称(存放到服务器的名称) */
	private String newimgname;

	/**知识点id */
	private String cid;

	/**图片年份 */
	private String fromyear;

	/**假删除标识(0->有效；1->删除) */
	private String tombstone;

	/**创建时间 */
	private Date createtime;

	/**创建人账号 */
	private String createuser;

	/**修改人账号 */
	private String updateuser;

	/**修改时间 */
	private Date updatetime;

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getFromyear() {
		return fromyear;
	}

	public void setFromyear(String fromyear) {
		this.fromyear = fromyear;
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

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getImgname() {
		return imgname;
	}

	public void setImgname(String imgname) {
		this.imgname = imgname;
	}

	public String getNewimgname() {
		return newimgname;
	}

	public void setNewimgname(String newimgname) {
		this.newimgname = newimgname;
	}
}
