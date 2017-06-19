/**
 *
 */
package com.huatu.tb.question.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Review
 * @Description: 错误描述
 * @author LiXin
 * @date 2015年4月20日 上午9:58:37
 * @version 1.0
 *
 */
public class Corrects implements Serializable{
	private static final long serialVersionUID = -2560124591626370716L;
	private String cdescription;// 错误信息
	private Date ctime;// 评论时间
	private String crejecter;// 驳回人
	private String updateuser;// 修改人
	private Date updatetime;// 修改时间
	public String getCdescription() {
		return cdescription;
	}
	public void setCdescription(String cdescription) {
		this.cdescription = cdescription;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public String getCrejecter() {
		return crejecter;
	}
	public void setCrejecter(String crejecter) {
		this.crejecter = crejecter;
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

}
