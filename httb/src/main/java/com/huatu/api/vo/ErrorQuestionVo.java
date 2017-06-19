/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.vo  
 * 文件名：				ErrorQuestionVo.java    
 * 日期：				2015年6月25日-上午10:34:56  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.vo;

/**   
 * 类名称：				ErrorQuestionVo  
 * 类描述：  			错题Vo对象
 * 创建人：				LiXin
 * 创建时间：			2015年6月25日 上午10:34:56  
 * @version 		1.0
 */
public class ErrorQuestionVo extends QuestionVo {

	private static final long serialVersionUID = -120207005077222376L;
	private String erroroption;//错误项

	public String getErroroption() {
		return erroroption;
	}

	public void setErroroption(String erroroption) {
		this.erroroption = erroroption;
	}
	
}
