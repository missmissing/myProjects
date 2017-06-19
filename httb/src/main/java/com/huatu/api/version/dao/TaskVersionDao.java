/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.version.dao  
 * 文件名：				TaskVersionDao.java    
 * 日期：				2015年6月21日-下午2:54:33  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.version.dao;

import java.util.Map;

import com.datastax.driver.core.ResultSet;
import com.huatu.api.version.model.TaskVersion;
import com.huatu.core.exception.HttbException;

/**   
 * 类名称：				TaskVersionDao  
 * 类描述：  			版本标记 dao
 * 创建人：				LiXin
 * 创建时间：			2015年6月21日 下午2:54:33  
 * @version 		1.0
 */
public interface TaskVersionDao {
	
    public TaskVersion getVersion(String id) throws HttbException;
	
	public ResultSet getVersionResultSet(Map<String, Object> condition) throws HttbException;
	
	public boolean deleteVersion(String id) throws HttbException;
	
	public boolean addVersion(TaskVersion taskVersion) throws HttbException;

}
