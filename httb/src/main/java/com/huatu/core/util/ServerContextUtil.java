package com.huatu.core.util;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huatu.ou.menu.model.Menu;
import com.huatu.sys.session.HtSession;
import com.huatu.sys.session.HtSessionManager;

public class ServerContextUtil {

	public static final String SESSION_ID="current_session_id";
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (CommonUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (CommonUtil.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	
	public static  Cookie getCookieByName(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if(null==cookies) return null;
		for(Cookie c:cookies){
			if(c!=null && SESSION_ID.equalsIgnoreCase(c.getName().trim())){
				return c;
			}
		}
		return null; 
	}
	
	public static String getSessionId(HttpServletRequest request){
		//return   ServerContextUtil.getCookieByName(request).getValue(); 
		if(null!=ServerContextUtil.getCookieByName(request)){
			return   ServerContextUtil.getCookieByName(request).getValue();  
		}//else if(null!=request.getSession().getAttribute(ServerContextUtil.SESSION_ID))
			//return request.getSession().getAttribute(ServerContextUtil.SESSION_ID).toString();
		else
			return null;
	}
	
	
	public static void test(HttpServletRequest request,HttpServletResponse response){
		try{
			HtSessionManager htSessionManager =com.huatu.core.util.SpringContextHolder.getBean("htSessionManager");
			HtSession htsession = htSessionManager.getSession(request,response);
			List<Menu> menus = (List<Menu>)htsession.getAttribute("menus");
			request.getSession().setAttribute("menus", menus);
			System.out.println(menus.size());
		}catch(Exception e){
			e.getStackTrace();
		}
		;
	}
}
