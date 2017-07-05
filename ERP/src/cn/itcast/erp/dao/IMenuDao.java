package cn.itcast.erp.dao;

import java.util.List;

import cn.itcast.erp.entity.Menu;
/**
 * 菜单数据访问层接口 
 * @author liujunling
 * 
 */
public interface IMenuDao extends IBaseDao<Menu>{

	/**
	 * 利用关联查询，根据登陆用户id查询该用户所拥有的所有角色菜单
	 * @param empUuid
	 * @return
	 */
	public List<Menu> getMenus(Long empUuid);
	
	
}
