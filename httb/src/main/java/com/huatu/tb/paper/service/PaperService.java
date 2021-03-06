/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.paper.service  
 * 文件名：				PaperService.java    
 * 日期：				2015年5月11日-下午4:48:38  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.paper.service;

import java.util.List;
import java.util.Map;

import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Page;
import com.huatu.tb.paper.model.Paper;

/**   
 * 类名称：				PaperService  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年5月11日 下午4:48:38  
 * @version 		1.0
 */
public interface PaperService {
	/**
	 * 添加
	 * @param 对象
	 * @return
	 * @throws HttbException
	 */
	public boolean save(Paper paper)throws HttbException;
	
	/**
	 * 删除 通过ID
	 * @param 对象id
	 * @return
	 * @throws HttbException
	 */
	public boolean delete(String id)throws HttbException;
	/**
	 * 通过Ids批量删除
	 * @param list
	 * @return
	 * @throws HttbException
	 */
	public boolean deleteToIds(List<String> list)throws HttbException;
	/**
	 * 修改 
	 * @param condition
	 * @return
	 * @throws HttbException 
	 */
	public boolean update(Paper paper) throws HttbException;
	/**
	 * 修改试卷中试题列表
	 * @param executeSQL
	 * @return
	 * @throws HttbException
	 */
	public boolean updateQuesList(Paper paper) throws HttbException;
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * @throws HttbException 
	 */
	public Paper get(String id) throws HttbException;
	 
	 /**
	  * 获取全都数据
	  * @return  List 
	  * @exception    
	  * @since  1.0.0
	  */
	public List<Paper> findAll()throws HttbException ; 
	
	/**
	 * 查询集合
	 * @param condition 条件过滤Map
	 * @return
	 * @throws HttbException
	 */
	public List<Paper> findList(Map<String, Object> condition) throws HttbException ;
	/**
	 * 分页查询
	 * @param condition 条件过滤Map
	 * @param startpage 开始第几行
	 * @param pageSize 每页多少行
	 * @param orderBy 排序字段
	 * @return
	 * @throws HttbException
	 */
	public Page<Paper> findPage(Map<String, Object> condition) throws HttbException ;
}
