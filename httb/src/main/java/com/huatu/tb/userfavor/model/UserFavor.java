/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.userfavor.model  
 * 文件名：				UserFavor.java    
 * 日期：				2015年5月13日-上午11:31:40  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.userfavor.model;

import java.util.List;

/**   
 * 类名称：				UserFavor  
 * 类描述：  			用户收藏对象
 * 创建人：				LiXin
 * 创建时间：			2015年5月13日 上午11:31:40  
 * @version 		1.0
 */
public class UserFavor {
	/**用户ID*/
	private String uid;
	/**试题收藏夹*/
	private List<Favorite> fhisfavors;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public List<Favorite> getFhisfavors() {
		return fhisfavors;
	}
	public void setFhisfavors(List<Favorite> fhisfavors) {
		this.fhisfavors = fhisfavors;
	}
	
	
}
