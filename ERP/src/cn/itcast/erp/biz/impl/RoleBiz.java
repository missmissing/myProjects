package cn.itcast.erp.biz.impl;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.erp.biz.IRoleBiz;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Menu;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
/**
 * 角色 业务层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class RoleBiz extends BaseBiz<Role> implements IRoleBiz {

	private IRoleDao roleDao;//数据访问层
	
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
		setBaseDao(roleDao);
	}
	
	private IMenuDao menuDao;  //菜单的数据访问层

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}
	
	
	/**
	 * 得到角色菜单
	 * @return
	 */
	public List<Tree> getRoleMenu(Long roleUuid){
		List<Tree>  trees = new ArrayList<Tree>();  //要返回的角色菜单树
		List<Menu> menus = roleDao.get(roleUuid).getMenus();  //得到角色对应的菜单
		
		Menu rootMenu = menuDao.get("0");   //得到根菜单
		//循环遍历一级菜单
		for(Menu menu1:rootMenu.getMenus()){
			Tree tree1 = new Tree();  //保存一级菜单
			tree1.setId(menu1.getMenuid());
			tree1.setText(menu1.getMenuname());
			if(menus.contains(menu1)){  //如果当前菜单项在该角色对应的菜单中
				tree1.setChecked(true);  //表明此选项是选中的
			}
			
			//循环遍历二级菜单
			for(Menu menu2:menu1.getMenus()){
				Tree tree2 = new Tree();   //保存二级菜单
				tree2.setId(menu2.getMenuid());
				tree2.setText(menu2.getMenuname());
				
				if(menus.contains(menu2)){  //如果当前菜单项在该角色对应的菜单中
					tree2.setChecked(true);  //表明此选项是选中的
				}
				
				//因为是第一次循环时，tree1.getChildren为空，所以要声明
				if(tree1.getChildren()==null){
					tree1.setChildren(new ArrayList());
				}
				tree1.getChildren().add(tree2);
			}
			
			//把以及菜单加入角色菜单树
			trees.add(tree1);
		}
		return trees;
	}
	
	/**
	 * 跟新角色菜单
	 * @param roleUuid   角色ID
	 * @param menuIds   菜单ID
	 */
	public void updateRoleMenu(Long roleUuid,String menuIds){
		//得到更新的菜单ID
		String[] ids = menuIds.split(",");
		//得到角色对象
		Role role = roleDao.get(roleUuid);
		//利用快照清空原来所有的权限
		role.setMenus(new ArrayList());
		for(String id:ids){
			//利用菜单id得到菜单实体
			Menu menu = menuDao.get(id);
			role.getMenus().add(menu);   //添加角色到权限列表中
		}
		
	}
}
