package com.huatu.ou.menu.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huatu.core.dao.impl.SqlBaseDaoImpl;
import com.huatu.core.exception.HttbException;
import com.huatu.core.util.CommonUtil;
import com.huatu.ou.menu.model.Menu;

@Repository
public class MenuDao{
	@Autowired
	private SqlBaseDaoImpl sqlBaseDaoImpl;
	private static final String NameSpace = "com.huatu.ou.menu.model.Menu.";
	
	/**
	 * 根据角色id查询菜单idlist
	 * 
	 * @param condition
	 * @return
	 * @throws HttbException
	 */
	public List<Menu> selectMenuIdByRoleId(Map<String, Object> condition) throws HttbException {
		try {
			return sqlBaseDaoImpl.selectList("selectMenuIdByRoleId", condition);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"根据角色ID查询菜单集合时发生异常", e);
		}
	}
	/**
	 * 根据角色id 集合 查询菜单id集合
	 * 
	 * @param condition
	 * @return
	 * @throws HttbException
	 */
	public List<Menu> selectMenuIdByRoleIds(Map<String, Object> params) throws HttbException {
		try {
			return this.reloadChild(sqlBaseDaoImpl.selectList(NameSpace
					+ "selectMenuIdByRoleIds", params));
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "根据角色id集合查询菜单id集合时发生异常", e);
		}
	}
	
	public List<Menu> selecMenus(Map<String, Object> params) throws HttbException {
		try {
			return this.reloadChild(sqlBaseDaoImpl.selectList(NameSpace
					+ "selectAllMenus", params));
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "获取所有菜单时发生异常", e);
		}
	}
	
	//根据菜单id查询单个菜单
	public Menu selectMenuById(String id) throws HttbException {
		try {
			return (Menu) sqlBaseDaoImpl.get(NameSpace + "selectMenuById", id);
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "根据菜单id查询单个菜单时发生异常", e);
		}
	}
	
	
	private List<Menu> reloadChild(List<Object> menus) throws HttbException {
		/*String tmp="100,101,102,103"
				+ "|用户管理,菜单管理,角色管理,权限管理"
				+ "|/pages/user/list.html,/pages/menu/list.html,/pages/role/list.html,/pages/permission/list.html";*/
		List<Menu> tmp = new ArrayList<Menu>();
		
		//菜单排序
		MenuDao.ComparatorMenu comparatorMenu = new MenuDao.ComparatorMenu();
		Collections.sort(menus, comparatorMenu);
		
		for(Object obj:menus){
			Menu menu=(Menu)obj;
			String childContents = menu.getChildContents();
			if(!CommonUtil.isNotNull(childContents)) break;
			String[] child=childContents.split("#"); 
			if(!CommonUtil.isNotNull(child[0])||!CommonUtil.isNotNull(child[1])) break; 
			String[] ids=child[0].split(",");
			String[] names=child[1].split(",");
			String[] urls=child[2].split(",");
			String[] levels=child[3].split(",");
			String[] ordernums=child[4].split(",");
			List<Menu> children = new  ArrayList<Menu>();
			
			for(int i=0;i<ids.length;i++){
				Menu tmpmenu=new Menu();
				tmpmenu.setId(ids[i]);
				tmpmenu.setName(names[i]);
				tmpmenu.setUrl(urls[i]);
				tmpmenu.setLevel(Integer.valueOf(levels[i])); 
				tmpmenu.setOrderNum(Integer.valueOf(ordernums[i])); 
				children.add(tmpmenu);
			}
			
			//菜单排序
			Collections.sort(children, comparatorMenu);
			menu.setChildren(children); 
			
			tmp.add(menu);
		}
		return tmp;
	}
	
	//菜单排序内部类
	public class ComparatorMenu implements Comparator<Object>
	{
		 public int compare(Object obj0, Object obj1) 
		 {
			 Menu menu0=(Menu)obj0;
			 Menu menu1=(Menu)obj1;
			 
			 Integer order0 = 0;
			 Integer order1 = 0;
			 
			 if(null!=menu0.getOrderNum())
			 {
				 order0 = menu0.getOrderNum();
			 }
			 
			 if(null!=menu1.getOrderNum())
			 {
				 order1 = menu1.getOrderNum();
			 }
			 
			 int result = order0.compareTo(order1);
			 if(result==0)
			 {
				 return menu0.getName().compareTo(menu1.getName());
			 }
			 else
			 {
				 return result;
			 }  
		 }
	} 

	public List<Menu> selectMenusOriginal(Map<String, Object> condition) throws HttbException {
		try {
			return sqlBaseDaoImpl.selectList(NameSpace+"selectListoriginal", condition);
			//return sqlBaseDaoImpl.selectList(NameSpace + "selectList", condition);
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "根据参数查询角色时发生异常", e);
		}
	}
	
	
	public List<Menu> selectMenus(Map<String, Object> condition) throws HttbException {
		try {
			return sqlBaseDaoImpl.selectList(NameSpace+"selectList", condition);
			//return sqlBaseDaoImpl.selectList(NameSpace + "selectList", condition);
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "根据参数查询角色时发生异常", e);
		}
	}
	
	
	/**
	 * 添加菜单
	 * 
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int insert(Menu menu) throws HttbException {
		try {
			return sqlBaseDaoImpl.insert(NameSpace +"insert", menu);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"添加菜单时发生异常", e);
		}
	}
	
	/**
	 * 修改菜单
	 * 
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int update(Menu menu) throws HttbException {
		try {
			return sqlBaseDaoImpl.update(NameSpace+"update", menu);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"修改菜单时发生异常", e);
		}
	}
	/**
	 * 删除菜单
	 * 
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int delete(Menu menu) throws HttbException {
		try {
			String[] menuIds = menu.getId().split(",");
			HashMap map = new HashMap();
			map.put("ids", menuIds);
			return sqlBaseDaoImpl.delete(NameSpace+"deleteByPrimaryKey", map);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"删除菜单时发生异常", e);
		}
	}
	
	public List<Menu> selecMenusByUserId(Map<String, Object> params) throws HttbException {
		try {
			return this.reloadChild(sqlBaseDaoImpl.selectList(NameSpace
					+ "selectAllMenusByUserId", params));
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"根据用户id查询菜单时发生异常", e);
		}
	}
	
	/**
	 * 判断角色是否和菜单绑定
	 * 
	 * @param role
	 * @return
	 * @throws HttbException
	 */
	public int menuIsUesd(String menu_id) throws HttbException {
		try {
			int count = sqlBaseDaoImpl.get(NameSpace +"menuIsUesd",menu_id);
			return count;
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"判断角色是否和菜单绑定时发生异常", e);
		}
	}
	
	public int selectCount() throws HttbException {
		try {
			int count = sqlBaseDaoImpl.get(NameSpace +"selectCount");
			return count;
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"查询菜单总数量时发生异常", e);
		}
	}
	
	
	
}
