package cn.itcast.erp.biz.impl;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.entity.Menu;
/**
 * 菜单 业务层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class MenuBiz extends BaseBiz<Menu> implements IMenuBiz {

	private IMenuDao menuDao;//数据访问层
	
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
		setBaseDao(menuDao);
	}
	
	/**
	 * 利用关联查询，根据登陆用户id查询该用户所拥有的所有角色菜单
	 */
	public List<Menu> getMenus(Long empUuid){
		return menuDao.getMenus(empUuid);
	}
	
	/**
	 * 得到登陆用户所拥有的角色的菜单
	 */
	public Menu getEmpMenus(Long empUuid) {
		//利用关联查询，根据登陆用户id查询该用户所拥有的所有角色菜单
		List<Menu> empMenus = menuDao.getMenus(empUuid);
		
		//获得系统菜单树（跟菜单，里面包含子菜单或者子子菜单）
		Menu menu0 = menuDao.get("0");
		//调用工具方法，创建要返回的菜单，并把得到的菜单树中的信息添加到要返回的菜单中
		Menu menu = createMenu(menu0); 
		
		//循环遍历一级菜单
		for(Menu m1:menu0.getMenus()){
			//调用方法，创建一级菜单并把得到的菜单树中的信息添加到要返回的菜单中
			Menu menu1 = createMenu(m1);
			//循环遍历二级菜单
			for(Menu m2:m1.getMenus()){
				//判断该用户拥有的才菜单中是否包含该二级菜单
				if(empMenus.contains(m2)){
					//调用方法，创建二级菜单，并把得到的菜单树中的信息添加到要返回的菜单中
					Menu menu2 = createMenu(m2);
					menu1.getMenus().add(menu2);
				}
			}
			//把拥有二级菜单的一级菜单添加到要返回的菜单中
			if(menu1.getMenus().size()>0){
				menu.getMenus().add(menu1);
			}
		}
		return menu;
	}

	/**
	 * 创建菜单实体，并把该用户应该拥有的菜单信息设置进去
	 * @param m
	 * @return
	 */
	private Menu createMenu(Menu m){
		Menu menu = new Menu();    
		menu.setIcon(m.getIcon());
		menu.setMenuid(m.getMenuid());
		menu.setMenuname(m.getMenuname());
		menu.setUrl(m.getUrl());
		menu.setMenus(new ArrayList());
		return menu;
	}

}
