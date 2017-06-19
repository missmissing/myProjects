package com.huatu.ou.roleMenu.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huatu.core.dao.impl.SqlBaseDaoImpl;
import com.huatu.core.exception.HttbException;
import com.huatu.ou.role.model.Role;
import com.huatu.ou.roleMenu.model.Role_Menu;
@Repository
public class RoleMenuDao {
	private static final String NameSpace = "com.huatu.ou.role.model.Role_Menu.";
	
	@Autowired
	private SqlBaseDaoImpl sqlBaseDaoImpl;
	
	public List<Role_Menu> selectRoleMenus(Map<String, Object> condition) throws HttbException {
		try {
			return sqlBaseDaoImpl.selectList(NameSpace + "selectList", condition);
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "根据参数查询角色时发生异常", e);
		}
	}
	
	public int insertRoleMenus(List<Role_Menu> list) throws HttbException {
		try {
			return sqlBaseDaoImpl.insert(NameSpace +"insert_role_menus", list);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"添加用户时发生异常", e);
		}
	}
	
	public int delete_role_menu(Role role) throws HttbException {
		try {
			return sqlBaseDaoImpl.delete(NameSpace+"delete_role_menus", role);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"删除角色和菜单关系时发生异常", e);
		}
	}
	
	
}
