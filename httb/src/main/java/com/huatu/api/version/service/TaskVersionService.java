/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.version.service  
 * 文件名：				TaskVersionService.java    
 * 日期：				2015年6月21日-下午2:52:47  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.version.service;

import java.util.List;
import java.util.Map;

import com.huatu.api.version.model.TaskVersion;
import com.huatu.core.exception.HttbException;

/**   
 * 类名称：				TaskVersionService  
 * 类描述：  			标记或版本 服务
 * 创建人：				LiXin
 * 创建时间：			2015年6月21日 下午2:52:47  
 * @version 		1.0
 */
public interface TaskVersionService {
	/**
	 * 通过唯一标识key获取版本信息
	 * getVersion  
	 * @exception 
	 * @param id
	 * @return
	 * @throws HttbException
	 */
	public TaskVersion getVersion(String key) throws HttbException;
	/**
	 * 获取版本信息集合
	 * getVersionResultSet  
	 * @exception 
	 * @param condition
	 * @return
	 * @throws HttbException
	 */
	public List<TaskVersion> getVersionResultSet(Map<String, Object> condition) throws HttbException;
	/**
	 *  通过唯一标识key删除标记信息
	 * deleteVersion  
	 * @exception 
	 * @param id
	 * @return
	 * @throws HttbException
	 */
	public boolean deleteVersion(String key) throws HttbException;
	/**
	 * 添加版本信息
	 * addVersion  
	 * @exception 
	 * @param taskVersion
	 * @return
	 * @throws HttbException
	 */
	public boolean addVersion(TaskVersion taskVersion) throws HttbException;
}
