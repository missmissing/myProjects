/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.service  
 * 文件名：				QuestionApiService.java    
 * 日期：				2015年6月12日-上午9:06:44  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.service;

import java.util.List;
import java.util.Map;

import com.huatu.api.vo.CategoryVo;
import com.huatu.api.vo.ErrorQuestionVo;
import com.huatu.core.exception.HttbException;


/**   
 * 类名称：				QuestionApiService  
 * 类描述：  			试题服务
 * 创建人：				LiXin
 * 创建时间：			2015年6月12日 上午9:06:44  
 * @version 		1.0
 */
public interface QuestionApiService {

	/**收藏相关*/
	public boolean collectQuestion(String userid,List<String> questionids)throws HttbException;
	
	public boolean deleteCollectQuestion(String userid,List<String> qids)throws HttbException;
	
	public List<String> getCollectQuestionListByUserId(String userid)throws HttbException;
	/**end*/
	
	/**错题相关*/
	public List<String> getWrongQuestionListByUserId(String userid)throws HttbException;
	
	public boolean addWrongQuestion(Map<String, Object> filter)throws HttbException;
	
	public boolean deleteWrongQuestion(String userid,List<String> qids)throws HttbException;
	
	/**end*/
	
	public CategoryVo getWrongQuestionCategory(String id,String userno,String area) throws HttbException;
	//获取用户详细信息
	public List<ErrorQuestionVo> getErrorQuestions(List<String> qids, String userno)throws HttbException;
}
