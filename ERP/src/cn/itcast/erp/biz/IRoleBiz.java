package cn.itcast.erp.biz;

import java.util.List;

import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
/**
 * 角色业务层接口
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public interface IRoleBiz extends IBaseBiz<Role>{
	/**
	 * 得到角色菜单
	 * @return
	 */
	public List<Tree> getRoleMenu(Long roleUuid);
	
	/**
	 * 跟新角色菜单
	 * @param roleUuid   角色ID
	 * @param menuIds   菜单ID
	 */
	public void updateRoleMenu(Long roleUuid,String menuIds);
}
