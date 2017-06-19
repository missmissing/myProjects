package com.huatu.ou.role.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.core.exception.HttbException;
import com.huatu.ou.role.dao.RoleDao;
import com.huatu.ou.role.model.Role;
import com.huatu.ou.roleMenu.dao.RoleMenuDao;
import com.huatu.ou.roleMenu.model.Role_Menu;
import com.huatu.ou.user.model.User;

@Service
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RoleMenuDao roleMenuDao;
	public List<Role> queryRoles(Map<String, Object> condition) throws HttbException {
		return roleDao.selectRoles(condition);
	}
	
	/**
	 * 添加组织机构
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int insertRoles(Role role) throws HttbException {
		return roleDao.insert(role);
	}
	
	/**
	 * 修改组织机构
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int updateRoles(Role role) throws HttbException {
		return roleDao.update(role);
	}
	
	/**
	 * 修改组织机构
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int deleteRoles(Role role) throws HttbException {
		return roleDao.delete(role);
	}
	/**
	 * 修改组织机构
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public Role getRole(Map<String, Object> condition) throws HttbException {
		List<Role> l  =roleDao.selectRoles(condition);
		if(l!=null&&l.size()>0){
			return (Role)l.get(0);
		}else
			return null;
	}
	
	
	public String getRolesOption(Map<String, Object> condition,User u) throws HttbException {
		List<Role> l  =roleDao.selectRoles(condition);
		String opts="";
		//u.getViewText().trim().equalsIgnoreCase(r.getName().trim())
		if(l!=null&&l.size()>0){
			for(Role r:l){
				if(hasExist(u.getViewText(),r.getName()))
					opts+="<option selected='true' value='"+r.getId()+"'>"+r.getName()+"</option>";
				else
					opts+="<option value='"+r.getId()+"'>"+r.getName()+"</option>";	
			}
			return opts;
		}else
			return null;
	}
	
	private boolean hasExist(String rolesStr,String role){
		String[] roles=rolesStr.split(",");
		for(String tmp:roles){
			if(tmp.trim().equalsIgnoreCase(role.trim())){
				return true;
			}
		}
		return false;
	}
	
	public int deleteUser_role(Role role) throws HttbException {
		return roleMenuDao.delete_role_menu(role);
	}
	
	public int insertRoleMenus(String roleid,String menuIds) throws HttbException {
		List<Role_Menu> list = new java.util.ArrayList<Role_Menu>(); 
		String[] array=menuIds.split(",");
		for(String menuid:array){
			Role_Menu rm = new Role_Menu();
			rm.setMenu_id(menuid); 
			rm.setRole_id(roleid);
			list.add(rm);
		}
		return roleMenuDao.insertRoleMenus(list);
	}
	
	public int roleIsUesd(String role_id) throws HttbException {
		return roleDao.roleIsUesd(role_id);
	}

	public int selectCount() throws HttbException {
		return roleDao.selectCount();
	}
	
}
