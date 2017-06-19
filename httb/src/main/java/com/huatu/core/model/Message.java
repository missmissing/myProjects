/**
 * 
 */
package com.huatu.core.model;

/** 
 * @ClassName: Message 
 * @Description: 向前端返回信息对象
 * @author LiXin 
 * @date 2015年4月20日 下午7:06:37 
 * @version 1.0
 *  
 */
public class Message {
	
	/**
	 * 操作成功与否的标识
	 */
	private boolean success;
	/**
	 * 返回提示信息
	 */
	private String message;
	/**
	 * 返回数据
	 */
	private Object data;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}
