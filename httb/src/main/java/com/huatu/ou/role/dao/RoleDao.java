package com.huatu.ou.role.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huatu.core.dao.impl.SqlBaseDaoImpl;
import com.huatu.core.exception.HttbException;
import com.huatu.ou.role.model.Role;
import com.huatu.ou.roleMenu.model.Role_Menu;

@Repository
public class RoleDao{
	
	@Autowired
	private SqlBaseDaoImpl sqlBaseDaoImpl;
	
	private static final String NameSpace = "com.huatu.ou.role.model.Role.";
	public List<Role> selectRoles(Map<String, Object> condition) throws HttbException {
		try {
			return sqlBaseDaoImpl.selectList(NameSpace + "selectList", condition);
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "根据参数查询角色时发生异常", e);
		}
	}
	
	
	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int insert(Role role) throws HttbException {
		try {
			return sqlBaseDaoImpl.insert(NameSpace +"insert", role);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"添加角色时发生异常", e);
		}
	}
	
	/**
	 * 修改角色
	 * 
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int update(Role role) throws HttbException {
		try {
			return sqlBaseDaoImpl.update(NameSpace+"update", role);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"修改角色时发生异常", e);
		}
	}
	/**
	 * 修改角色
	 * 
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int delete(Role role) throws HttbException {
		try {
			String[] roleIds = role.getId().split(",");
			HashMap map = new HashMap();
			map.put("ids", roleIds);
			return sqlBaseDaoImpl.delete(NameSpace+"deleteByPrimaryKey", map);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"修改角色时发生异常", e);
		}
	}
	
	/**
	 * 判断角色是否和用户绑定
	 * 
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int roleIsUesd(String role_id) throws HttbException {
		try {
			int count = sqlBaseDaoImpl.get(NameSpace +"roleIsUesd",role_id);
			return count;
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"判断角色是否和用户绑定时发生异常", e);
		}
	}
	
	
	public int selectCount() throws HttbException {
		try {
			int count = sqlBaseDaoImpl.get(NameSpace +"selectCount");
			return count;
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"查询角色总数量时发生异常", e);
		}
	}
	
}
