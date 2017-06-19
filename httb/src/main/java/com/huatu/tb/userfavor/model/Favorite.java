/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.userfavor.model  
 * 文件名：				Favorite.java    
 * 日期：				2015年5月13日-上午11:34:39  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.userfavor.model;

import java.util.Date;

/**   
 * 类名称：				Favorite  
 * 类描述：  			试题收藏明细对象
 * 创建人：				LiXin
 * 创建时间：			2015年5月13日 上午11:34:39  
 * @version 		1.0
 */
public class Favorite {
	/**试题ID*/
	private String qid;
	/**收藏时间*/
	private Date afavortime;
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public Date getAfavortime() {
		return afavortime;
	}
	public void setAfavortime(Date afavortime) {
		this.afavortime = afavortime;
	}
	

}
