package cn.itcast.erp.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class MyFilter extends AuthorizationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object arg2) throws Exception {
		
		//获得主题
		Subject subject = getSubject(request, response);
		
		//获得页面设置的权限列表
		String[] perms = (String[]) arg2;
		if(perms==null || perms.length==0){
			return true;
		}
		for(String p:perms){
			if(subject.isPermitted(p)){  //如果有一个权限符合，也认为符合登陆
				return true;
			}
		}
		
		return false;
	}

}
