/**   
 * 项目名：				httb
 * 包名：				com.huatu.tb.userfavor.service.impl  
 * 文件名：				UserFavorServiceImpl.java    
 * 日期：				2015年5月13日-下午2:04:01  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.tb.userfavor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Page;
import com.huatu.tb.userfavor.dao.UserFavorDao;
import com.huatu.tb.userfavor.model.UserFavor;
import com.huatu.tb.userfavor.service.UserFavorService;

/**   
 * 类名称：				UserFavorServiceImpl  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年5月13日 下午2:04:01  
 * @version 		1.0
 */
@Service
public class UserFavorServiceImpl implements UserFavorService {
	
	@Autowired
	private UserFavorDao userFavorDao;
	@Override
	public boolean save(UserFavor favor) throws HttbException {
		return userFavorDao.insert(favor);
	}

	@Override
	public boolean delete(String id) throws HttbException {
		return userFavorDao.delete(id);
	}

	@Override
	public boolean deleteToIds(List<String> list) throws HttbException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(UserFavor favor) throws HttbException {
		return userFavorDao.update(favor);
	}

	@Override
	public UserFavor get(String id) throws HttbException {
		return userFavorDao.get(id);
	}

	@Override
	public List<UserFavor> findAll() throws HttbException {
		return null;
	}

	@Override
	public List<UserFavor> findList(Map<String, Object> condition) throws HttbException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<UserFavor> findPage(Map<String, Object> condition) throws HttbException {
		// TODO Auto-generated method stub
		return null;
	}

}
