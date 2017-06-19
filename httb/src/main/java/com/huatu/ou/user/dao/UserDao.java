package com.huatu.ou.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huatu.core.dao.impl.SqlBaseDaoImpl;
import com.huatu.core.exception.HttbException;
import com.huatu.ou.User_role;
import com.huatu.ou.user.model.User;

@Repository
public class UserDao  {
	private static final String NameSpace = "com.huatu.ou.user.model.User.";
	
	@Autowired
	private SqlBaseDaoImpl sqlBaseDaoImpl;

	public SqlBaseDaoImpl getSqlBaseDaoImpl() {
		return sqlBaseDaoImpl;
	}

	public void setSqlBaseDaoImpl(SqlBaseDaoImpl sqlBaseDaoImpl) {
		this.sqlBaseDaoImpl = sqlBaseDaoImpl;
	}
	/**
	 * 根据用户账号查询用户
	 *
	 * @param userNo
	 * @return
	 * @throws HttbException
	 */
	public User selectUser(String userno) throws Exception {
		try {
			return sqlBaseDaoImpl.get(NameSpace+"selectLogin", userno);
		} catch (Exception e) {
			throw new Exception(this.getClass() + "查询用户时发生异常", e);
		}
	}

	/**
	 * 根据用户账号查询用户
	 *
	 * @param userNo
	 * @return
	 * @throws HttbException
	 */
	public List<User> selectAllUser() throws HttbException {
		try {
			return sqlBaseDaoImpl.get(NameSpace+"selectLogin");
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "查询用户时发生异常", e);
		}
	}

	public List<User> selectUsers(Map<String, Object> condition) throws HttbException {
		try {
			return sqlBaseDaoImpl.selectList(NameSpace + "selectList", condition);
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "根据参数查询用户时发生异常", e);
		}
	}
	/**
	 * 添加用户
	 * 
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int insert(User user) throws HttbException {
		try {
			return sqlBaseDaoImpl.insert(NameSpace +"insert", user);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"添加用户时发生异常", e);
		}
	}
	/**
	 * 修改用户
	 * 
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int update(User user) throws HttbException {
		try {
			return sqlBaseDaoImpl.update(NameSpace+"update", user);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"修改用户时发生异常", e);
		}
	}
	/**
	 * 修改用户
	 * 
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int delete(User user) throws HttbException {
		try {
			String[] userIds = user.getId().split(",");
			HashMap map = new HashMap();
			map.put("ids", userIds);
//			map.put("1", 1);
			return sqlBaseDaoImpl.delete(NameSpace+"deleteByPrimaryKey", map);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"修改用户时发生异常", e);
		}
	}
	
	public int delete_user_role(User user) throws HttbException {
		try {
			return sqlBaseDaoImpl.delete(NameSpace+"delete_user_roles", user);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"修改用户时发生异常", e);
		}
	}
	
	
	public int insertUserRoles(List<User_role> list) throws HttbException {
		try {
			return sqlBaseDaoImpl.insert(NameSpace +"insert_user_roles", list);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"添加用户时发生异常", e);
		}
	}
	
	/**
	 * 查询用户总数量
	 * 
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int selectCount() throws HttbException {
		try {
			int count = sqlBaseDaoImpl.get(NameSpace +"selectCount");
			return count;
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"查询用户总数量时发生异常", e);
		}
	}
	
	/**
	 * 判断用户名是否存在
	 * 
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int usernoIsExist(String userno) throws HttbException {
		try {
			int count = sqlBaseDaoImpl.get(NameSpace +"usernoIsExist",userno);
			return count;
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"判断用户名是否存在时发生异常", e);
		}
	}
	
}
