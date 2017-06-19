/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.service  
 * 文件名：				CategoryApiService.java    
 * 日期：				2015年6月12日-上午9:05:31  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.service;

import net.sf.json.JSONArray;

import com.huatu.api.vo.CategoryVo;
import com.huatu.core.exception.HttbException;

/**   
 * 类名称：				CategoryApiService  
 * 类描述：  			章节服务
 * 创建人：				LiXin
 * 创建时间：			2015年6月12日 上午9:05:31  
 * @version 		1.0
 */
public interface CategoryApiService {
	/**
	 * 获取题库分类列表（大类及一级分析 如公务员及其下地区公务员）
	 * getTbClassList  
	 * @exception 
	 * @return
	 * @throws HttbException
	 */
	public JSONArray getTbClassList() throws HttbException;
	/**
	 * 获取该节点下所有章节信息(子集)
	 * @exception  根据知识分类ID，获取该分类下所有知识分类
	 * @param  id --章节 知识点ID
	 * @param  userno -- 用户no
	 * @param  area --地区
	 * @return
	 * @throws HttbException
	 */
	public CategoryVo getSubsetById(String id,String userno,String area)throws HttbException;
	/**
	 * 通过章节id和地区标识 获取其下所有试题【已经压缩加密的字符串】
	 * getQuestions  
	 * @exception 
	 * @param cateid --章节ID
	 * @param area -- 地区id 选填
	 * @return
	 * @throws HttbException
	 */
	public String  getQuestions(String cateid,String area)throws HttbException;
}
