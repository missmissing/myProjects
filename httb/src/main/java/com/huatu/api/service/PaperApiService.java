/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.service  
 * 文件名：				PaperApiService.java    
 * 日期：				2015年6月12日-上午9:06:22  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.service;

import java.util.List;
import java.util.Map;

import com.huatu.api.vo.PaperVo;
import com.huatu.core.exception.HttbException;

/**   
 * 类名称：				PaperApiService  
 * 类描述：                                	试卷服务
 * 创建人：				LiXin
 * 创建时间：			2015年6月12日 上午9:06:22  
 * @version 		1.0
 */
public interface PaperApiService {
	/**
	 * 获取试卷列表
	 * @exception 
	 * @param filter
	 * @return
	 * @throws HttbException
	 */
	public List<PaperVo> getPaperList(Map<String,Object> filter)throws HttbException;
	
	/**
	 * 根据试卷ID获取试题--【压缩 加密】
	 * @exception 根据试卷ID获取试卷下所有试题 
	 * @param id --试卷ID
	 * @return
	 * @throws HttbException
	 */
	public String getQuestionsByPaperId(String id)throws HttbException;
	
}
