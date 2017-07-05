package cn.itcast.erp.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Menu;

public class ErpRealm extends AuthorizingRealm {
	
	private IEmpBiz empBiz;  //注入员工业务逻辑层

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}
	
	private IMenuBiz menuBiz;   //注入菜单业务逻辑层

	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
	}

	/**
	 * 授权的方法
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		//创建SimpleAuthorizationInfo类对象，SimpleAuthorizationInfo为AuthorizationInfo的实现类
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获得emp实体
		Emp emp = (Emp) arg0.getPrimaryPrincipal();
		//获得该员工所拥有的菜单
		List<Menu> menus = menuBiz.getMenus(emp.getUuid());
		for(Menu menu:menus){
			info.addStringPermission(menu.getMenuname());  //添加权限
			
		}
		
		return info;
	}

	/**
	 * 认证的方法
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken arg0) throws AuthenticationException {
		//1、得到令牌（UsernamePasswordToken为AuthenticationToken的子接口的实现类）
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		//2、执行认证
		Emp emp = empBiz.findEmpByLoginNamePassword(token.getUsername(), 
												String.valueOf(token.getPassword()));
		if(emp==null){ //如果为空，表明用户名或密码错误，返回null
			return null;
		}
		//当emp不为空时，表明校验成功，返回SimpleAuthenticationInfo的实例
		//第三个参数getName()得到的是当前的realm名称
		return new SimpleAuthenticationInfo(emp,emp.getPwd(),getName());
	}

}
