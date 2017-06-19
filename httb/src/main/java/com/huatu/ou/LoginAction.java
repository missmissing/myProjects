package com.huatu.ou;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatu.core.action.BaseAction;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Message;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.core.util.MD5Util;
import com.huatu.core.util.ServerContextUtil;
import com.huatu.ou.ascpectJ.operLog.LogAnnotation;
import com.huatu.ou.menu.model.Menu;
import com.huatu.ou.menu.service.MenuService;
import com.huatu.ou.user.model.User;
import com.huatu.ou.user.service.UserService;
import com.huatu.sys.session.HtSession;
import com.huatu.sys.session.HtSessionManager;
@Controller
@RequestMapping("login")
public class LoginAction extends BaseAction {
	public  Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;
	/*@Autowired
	private RoleService roleService;*/



	@RequestMapping(value = "/login.action", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "系统模块", operateModelName = "用户登录")
	public Message login(HttpServletRequest request,HttpServletResponse response,User user) throws HttbException {
		List<Menu> menus=null;
		Message message = new Message();
		String pass=user.getPassword();
		try {
			if (!CommonUtil.isNotNull(user.getUserno()) || !CommonUtil.isNotNull(user.getPassword())) {
				message.setSuccess(false);
				message.setMessage("用户名或密码为空！");
			}else{
				user = this.userService.getUserByName(user.getUserno().toLowerCase());
				//用户存在
				if (null!=user&&MD5Util.getMD5Code(pass).equals(user.getPassword().trim())) {
					if ("admin".equals(user.getUserno())){// 拿到所有菜单
						menus = menuService.getAllMenus();
						message.setSuccess(true);
						message.setMessage("admin登录");
					}else{
						// 查询用户对应角色拥有的菜单
						//List<Role> set = roleService.selectLoginRoles(user.getId());
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("user_id", user.getId());
						menus = menuService.getMenusByUser(params);
						message.setSuccess(true);
						message.setMessage(user.getUname()+"登录");
					}
					HtSession session = newSession(request,response);
					session.setAttribute(Constants.SESSION_CURRENT_USER, user);
					session.setAttribute("menus", menus);
					boolean b = flushSession(session);
					if(b){
						request.getSession().setAttribute(ServerContextUtil.SESSION_ID, session.getSessionId());
					}else{
						message.setSuccess(false);
						message.setMessage("save session error");
					}
					//request.getSession().setAttribute("correntUser", user);
					//request.getSession().setAttribute("menus", menus);
				}else{
					message.setSuccess(false);
					message.setMessage("用户不存在或账户密码不匹配");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			message.setSuccess(false);
			message.setMessage("发生系统异常，登录失败！");
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "用户登录异常", e);

		}
		return message;
	}

	@RequestMapping(value = "/logOut.action", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "系统模块", operateModelName = "用户注销")
	public Message logout(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Message message = new Message();
		User cuser =(User)request.getSession().getAttribute("correntUser");
		try {
			//集群环境下
			HtSession session=htSessionManager.getSession(request,response);
			if(null == session){
				message.setSuccess(true);
				return message;
			}
			User cuser2 =(User)session.getAttribute("correntUser");
            if(null == cuser2){
            	message.setSuccess(true);
				return message;
			}
			boolean b = invalidSession(session);
			if(b)
				log.info(cuser2.getUserno()+"_注销登录");
			else
				log.log(Priority.ERROR, cuser2.getUserno()+"_注销登录出错");
			//非集群环境下
			if(null != cuser){
				request.getSession().removeAttribute("correntUser");
				request.getSession().removeAttribute("menus");
				request.getSession().invalidate();
				log.error(cuser.getUserno()+"_注销登录");
				message.setSuccess(true);
				message.setMessage(cuser.getUserno()+"_注销登录");
			}			
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Priority.ERROR, "_注销登录出错"+"_"+e.getMessage());
			message.setSuccess(false);
			message.setMessage("发生系统异常，注销失败！");
			throw new HttbException(ModelException.JAVA_REDIS_DELETE, this.getClass() + "用户注销异常", e);

		}
		return message;
	}

}
