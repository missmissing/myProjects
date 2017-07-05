package cn.itcast.erp.biz;

import java.util.List;

import cn.itcast.erp.entity.Menu;
/**
 * 菜单业务层接口
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public interface IMenuBiz extends IBaseBiz<Menu>{

	/**
	 * 利用关联查询，根据登陆用户id查询该用户所拥有的所有角色菜单
	 */
	public List<Menu> getMenus(Long empUuid);
	
	/**
	 * 根据用户的登陆id获得该用户拥有的菜单
	 * @param uuid
	 * @return
	 */
	public Menu getEmpMenus(Long empUuid);

	

}
