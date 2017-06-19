package com.huatu.ou.user.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.core.exception.HttbException;
import com.huatu.ou.User_role;
import com.huatu.ou.user.dao.UserDao;
import com.huatu.ou.user.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	/**
	 * 根据账号查询用户
	 * 
	 * @param id
	 * @return
	 * @throws 
	 */
	public User getUserByName(String username)  throws Exception{
		return userDao.selectUser(username);
	}
	
	/**
	 * 根据查询所有用户
	 * 
	 * @param id
	 * @return
	 * @throws 
	 */
	public List<User> getAllUser() throws HttbException{
		return userDao.selectAllUser();
	}
	
	public List<User> getUsers(Map<String, Object> condition) throws HttbException {
		return userDao.selectUsers(condition);
	}
	
	
	public List<User> queryUsers(Map<String, Object> condition) throws HttbException {
		return userDao.selectUsers(condition);
	}
	
	/**
	 * 添加组织机构
	 * @param user
	 * @return
	 * @throws HttbException
	 */
	public int insertUsers(User user) throws HttbException {
		return userDao.insert(user);
	}
	
	public int selectCount() throws HttbException {
		return userDao.selectCount();
	}
	
	public int insertUserRoles(String userId,String roleIds) throws HttbException {
		List<User_role> list = new java.util.ArrayList<>(); 
		String[] array=roleIds.split(",");
		for(String roleid:array){
			User_role u = new User_role();
			u.setUser_id(userId); 
			u.setRole_id(roleid);
			list.add(u);
		}
		return userDao.insertUserRoles(list);
	}
	
	
	/**
	 * 修改组织机构
	 * @param user
	 * @return
	 * @throws HttbException
	 */
	public int updateUsers(User user) throws HttbException {
		return userDao.update(user);
	}
	
	/**
	 * 修改组织机构
	 * @param user
	 * @return
	 * @throws HttbException
	 */
	public int deleteUsers(User user) throws HttbException {
		return userDao.delete(user);
	}
	
	/**
	 * 删除用户角色关系
	 * @param user
	 * @return
	 * @throws HttbException
	 */
	public int deleteUser_role(User user) throws HttbException {
		return userDao.delete_user_role(user);
	}
	
	/**
	 * 查询用户信息
	 * @param user
	 * @return
	 * @throws HttbException
	 */
	public User getUser(Map<String, Object> condition) throws HttbException {
		List<User> l  =userDao.selectUsers(condition);
		if(l!=null&&l.size()>0){
			return (User)l.get(0);
		}else
			return null;
	}
	
	public int usernoIsExist(String userno) throws HttbException {
		return userDao.usernoIsExist(userno);
	}
	
}





