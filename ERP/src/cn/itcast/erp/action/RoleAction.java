package cn.itcast.erp.action;

import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IRoleBiz;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
/**
 * 角色 控制层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class RoleAction extends BaseAction<Role> {
	
	private IRoleBiz roleBiz;

	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
		setBaseBiz(roleBiz);
	}
	
	private String menuIds; //属性驱动，存储菜单Id
	
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	/**
	 * 得到角色菜单树
	 */
	public void getRoleMenu(){
		System.out.println(getId());
		List<Tree> trees = roleBiz.getRoleMenu(getId());
		String jsonString = JSON.toJSONString(trees);
		write(jsonString);	
	}
	
	public void updateRoleMenu(){
		try{
			roleBiz.updateRoleMenu(getId(),menuIds);
			write("ok");
		}catch(Exception e){
			e.printStackTrace();
			write("保存失败");
		}
	}
}
