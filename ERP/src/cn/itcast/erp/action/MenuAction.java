package cn.itcast.erp.action;

import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Menu;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
/**
 * 菜单 控制层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class MenuAction extends BaseAction<Menu> {
	
	private IMenuBiz menuBiz;

	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
		setBaseBiz(menuBiz);
	}
	
	/**
	 * 读取菜单
	 */
	public void getMenuTree(){
		//得到用户的登陆信息
		Emp user = (Emp) ActionContext.getContext().getSession().get("user");		
		Menu menu = menuBiz.getEmpMenus(user.getUuid());
		String jsonString = JSON.toJSONString(menu);
		write(jsonString);
	}
	
}
