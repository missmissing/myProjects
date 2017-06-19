/**
 * 项目名：				httb
 * 包名：				com.huatu.api.vo
 * 文件名：				SXRankVo.java
 * 日期：				2015年8月18日-下午7:40:05
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.api.vo;

/**
 * 类名称：				SXRankVo
 * 类描述：  				顺序答题排名vo
 * 创建人：				Aijunbo
 * 创建时间：				2015年8月18日 下午7:40:05
 * @version 			0.0.1
 */
public class SXRankVo {
	/**知识点id*/
	private String cid;
	/**排名*/
	private int rank;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
}
