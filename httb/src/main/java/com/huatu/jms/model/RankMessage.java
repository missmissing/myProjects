/**
 * 项目名：				httb
 * 包名：					com.huatu.jms.model
 * 文件名：				JmsMessage.java
 * 日期：					2015年7月8日-下午6:09:32
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.jms.model;

import java.io.Serializable;

/**
 * 类名称：				RankMessage
 * 类描述：				顺序答题排名对象
 * 创建人：				Aijunbo
 * 创建时间：				2015年7月8日 下午6:09:32
 * @version 			0.0.1
 */
public class RankMessage implements Serializable{
	private static final long serialVersionUID = -6821959890317517287L;

	//消息类型(1、保存答题结果)
	private String jmsType;

	private Object object;

	public String getJmsType() {
		return jmsType;
	}

	public void setJmsType(String jmsType) {
		this.jmsType = jmsType;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
