package com.huatu.ou.menu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Tree;
import com.huatu.core.util.CommonUtil;
import com.huatu.ou.menu.dao.MenuDao;
import com.huatu.ou.menu.model.Menu;
import com.huatu.ou.roleMenu.dao.RoleMenuDao;
import com.huatu.ou.roleMenu.model.Role_Menu;
import com.huatu.ou.user.model.User;
import com.huatu.tb.category.util.ComparatorTree;

@Service
public class MenuService {
	
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	public List<Menu> getMenusByUser(Map<String, Object> condition) throws HttbException {
		return menuDao.selecMenusByUserId(condition);
	}
	
	
	public List<Menu> getMenusByMenu(Long menuId) throws Exception {
		// 查询菜单权限
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("menuId", menuId);
		return menuDao.selectMenus(filter);
	}
	
	public List<Menu> queryMenus(Map<String, Object> condition) throws HttbException {
		return menuDao.selectMenus(condition);
	}
	
	
	
	
	public List<Menu> getMenusByMenus(List<Menu> menus) throws Exception {
		if (null != menus && menus.size() > 0) {
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("list", menus);
			filter.put("delFlag", 0);
			//List<Menu> mlist = menuDao.selectMenuIdByMenuIds(filter);
			return null;
		} else {
			return new ArrayList<Menu>();
		}

	}
	
	public List<Menu> getAllMenus() throws Exception {
		List<Menu> mlist = menuDao.selecMenus(null);
		return mlist;
	}
	
	public List<Tree>  getMenusOfTree(String roleId) throws HttbException {
		//StringBuffer sb = new StringBuffer();
		List<Menu> mList = menuDao.selectMenusOriginal(null);
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("role_id", roleId);
		List<Role_Menu> rmList = roleMenuDao.selectRoleMenus(condition);
		List<String> checkedIds = new ArrayList<>();
		for(Role_Menu rm:rmList){
			checkedIds.add(rm.getMenu_id());
		}
		
		return getMenuTree(mList,checkedIds);
		/*int i=1;
		for(Menu menu:mlist){
			if(i!=mlist.size())
				sb.append("{ id:\""+menu.getId()+"\", pId:\""+menu.getParentId()+"\", name:\""+menu.getName()+"\", open:true},");
			else
				sb.append("{ id:\""+menu.getId()+"\", pId:\""+menu.getParentId()+"\", name:\""+menu.getName()+"\", open:true}");
			i++;
		}
		//System.out.println("["+sb.toString().replaceAll("pId:null", "pId:0")+"]");
		return "["+sb.toString().replaceAll("pId:null", "pId:0")+"]";*/
	}
	

	public static List<Tree> getMenuTree(List<Menu> list,List<String> checkedIds){
		List<Tree> treeList = new ArrayList<Tree>();
		for(Menu m : list){
			Tree tree = new Tree();
			tree.setId(m.getId());
			tree.setName(m.getName());
			tree.setExplain(m.getDescription());
			tree.setOrderNum(m.getOrderNum() == null ? 0 : m.getOrderNum());
			String pid = m.getParentId();
			if(pid !=null && pid.length()>0){

			}else{
				pid = "0" ;//根节点
				tree.setOpen("true");
			}
			if(CommonUtil.isNotNull(checkedIds) && checkedIds.contains(m.getId())){
				tree.setChecked("true");
			}
			tree.setpId(pid);
			treeList.add(tree);
		}
		ComparatorTree comparator = new ComparatorTree();
		Collections.sort(treeList, comparator);
		return treeList;
	}
	
	
	/**
	 * 添加组织机构
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int insertMenus(Menu menu) throws HttbException {
		return menuDao.insert(menu);
	}
	
	/**
	 * 修改组织机构
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int updateMenus(Menu menu) throws HttbException {
		return menuDao.update(menu);
	}
	
	/**
	 * 修改组织机构
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int deleteMenus(Menu menu) throws HttbException {
		return menuDao.delete(menu);
	}
	/**
	 * 修改组织机构
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public Menu getMenu(Map<String, Object> condition) throws HttbException {
		List<Menu> l  =menuDao.selecMenus(condition);
		if(l!=null&&l.size()>0){
			return (Menu)l.get(0);
		}else
			return null;
	}
	
	public Menu selectMenuById(String id) throws HttbException {
		return menuDao.selectMenuById(id);
	}
	
	public int menuIsUesd(String menu_id) throws HttbException {
		return menuDao.menuIsUesd(menu_id);
	}
	
	public int selectCount() throws HttbException {
		return menuDao.selectCount();
	}
}
