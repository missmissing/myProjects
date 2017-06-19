package com.huatu.ou.user.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatu.core.action.BaseAction;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Message;
import com.huatu.core.model.Page;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.core.util.MD5Util;
import com.huatu.core.util.SpringContextHolder;
import com.huatu.ou.ascpectJ.operLog.LogAnnotation;
import com.huatu.ou.menu.service.MenuService;
import com.huatu.ou.organization.model.Organization;
import com.huatu.ou.organization.service.OrganizationService;
import com.huatu.ou.role.service.RoleService;
import com.huatu.ou.user.model.User;
import com.huatu.ou.user.service.UserService;
import com.huatu.sys.session.HtSession;
import com.huatu.sys.session.HtSessionManager;
import com.huatu.tb.category.util.CategoryUtil;

@Controller
@RequestMapping("user")
public class UserAction extends BaseAction {
	@Autowired
	private UserService userService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OrganizationService organizationService;
	/**
	 * 进入用户管理界面
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		return "user/list";
	}
	
	/**
	 *
	 * query						(分页查询)
	 * 								(Ajax-分页查询)
	 * @param 		model
	 * @param 		border			边界值
	 * @return
	 * @throws 		HttbException
	 */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public Page<User> query(Model model, User user,String pageNum) throws HttbException {
		int pageSize = 10;
		Page<User> page = new Page<User>();
		page.setPage(Integer.valueOf(pageNum));
		page.setPageSize(pageSize);
		//条件集合
		Map<String, Object> condition = new HashMap<String, Object>();		
		condition.put("pageNum", (Integer.valueOf(pageNum)-1)*pageSize);
		condition.put("pageSize", pageSize);
		List<User> ilist;
		try {
			//判断
			if(CommonUtil.isNotEmpty(user.getUname())){
				condition.put("uname", user.getUname());
			}
			if(CommonUtil.isNotEmpty(user.getOrganization().getId())){
				condition.put("orgid", user.getOrganization().getId());
			}
			if(CommonUtil.isNotEmpty(user.getPhone())){
				condition.put("phone", user.getPhone());
			}
			/*//判断图片名称
			if(CommonUtil.isNotEmpty(user.getOrganization().getName())){
				condition.put("imgname", user.getOrganization().getName());
			}*/
			ilist = userService.getUsers(condition);
			for(User u : ilist){
				if(null == u.getViewText())
					u.setViewText("无");
			}
		} catch (HttbException e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询用户时发生异常", e);
		}
		try {
			int length = userService.selectCount();
			page.setTotal(length / pageSize);
			if (length % pageSize != 0) {
				page.setTotal(page.getTotal() + 1);
			}
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "查询用户总数时发生异常", e);
		}
		page.setRows(ilist);
		return page;
	}

	/**
	 * haddle							进入试题管理添加页面
	 * @param		model			参数集合
	 * @return 		String			返回页面路径
	 * @throws HttbException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(HttpServletRequest request) throws HttbException{
		return "user/add";
	}
	
	/**
	 * add							进入试题管理添加页面
	 * @param		model			参数集合
	 * @return 		String			返回页面路径
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/save.action", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "用户模块", operateModelName = "用户修改或添加")
	public Message save(HttpServletRequest request,HttpServletResponse response,User user) throws HttbException {
		Message message = new Message();		
		try {
			HtSessionManager htSessionManager =SpringContextHolder.getBean("htSessionManager");
			User correntUser=null;
			HtSession htsession = null;
			try {
				htsession = htSessionManager.getSession(request,response);
			} catch (HttbException e1) {
				log.error("查询htsession异常！", e1);
			}
			if(htsession!=null){
				correntUser = (User)htsession.getAttribute(Constants.SESSION_CURRENT_USER);
			}
			String password= user.getPassword()==null?"123456":user.getPassword().toString();
			//如果ID为空==>添加
			if(CommonUtil.isNull(user.getId())){
				user.setId(CommonUtil.getUUID());
				user.setDelFlag(false);
				user.setCreateTime((new Date()));
				user.setCreateUser(correntUser.getUserno());
				user.setPassword(MD5Util.getMD5Code(password));
				user.setRegdate(new Date());
				user.setLastLoginTime(new Date());
				userService.insertUsers(user);
				message.setMessage("用户添加成功！");
			}else{
				user.setPassword(MD5Util.getMD5Code(password));
				user.setLastLoginTime(new Date());
				userService.updateUsers(user);
				message.setMessage("用户修改成功！");
			}
			message.setSuccess(true);
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("用户编辑失败！");
			throw new HttbException(this.getClass() + "用户编辑发生异常", e);
		}
		return message;
	}
	
	/**
	 * usernoIsExist							判断用户名是否存在
	 * @param		model			参数集合
	 * @return 		String			返回页面路径
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/usernoIsExist.action", method = RequestMethod.POST)
	@ResponseBody
	public Message usernoIsExist(HttpServletRequest request,String userno) throws HttbException {
		Message message = new Message();
		try {
			int result = userService.usernoIsExist(userno);
			if(1 == result){
				message.setData(1);
			}
			else{
				message.setData(-1);
			}
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("判断用户名是否存在发生异常！");
			throw new HttbException(this.getClass() + "判断用户名是否存在异常", e);
		}
	    message.setMessage("判断用户名是否存在成功！");
	    message.setSuccess(true);

		return message;
	}

	/**
	 *
	 * delete						(用户删除)
	 * @param 		model
	 * @param 		image
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/delete.action",method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "用户模块", operateModelName = "用户删除")
	public Message delete(HttpServletRequest request,String id) throws HttbException{
		Message message = new Message();
		User user=new User();
		user.setId(id);
		try {
			userService.deleteUsers(user);
			message.setSuccess(true);
			message.setMessage("用户删除成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("用户删除失败！");
			throw new HttbException(this.getClass() + "用户删除异常", e);
		}
		return message;
	}
	
	@RequestMapping(value = "/handle")
	public String handle(Model model,String id) throws HttbException {
		HashMap<String, Object> filter = new HashMap<String, Object>();
		if(CommonUtil.isNotEmpty(id)){
			try {
				filter.put("id", id);
				User user = userService.getUser(filter);
				model.addAttribute("user", user);
			} catch (HttbException e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "根据ID查询用户时发生异常", e);
			}
		}
		return "user/add";
	}
	
	/**
	 * 获取章节树
	 * 
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/getOrgnizationTree", method = RequestMethod.POST)
	@ResponseBody
	public Message getOrgnizationTree(Model model) throws HttbException {
		Message message = new Message();
		try{
			List<Organization> list = organizationService.queryOrganizations(null);
			JSONArray jsonArray = JSONArray.fromObject(organizationService.getOrganizationTree(list));
			message.setSuccess(true);
			message.setMessage("获取章节树成功");
			message.setData(jsonArray);
		}catch(Exception e){
			message.setSuccess(false);
			message.setMessage("获取章节树失败");
			throw new HttbException(this.getClass() + "用户删除异常", e);
		}
		return message;
	}
	
	@RequestMapping(value = "/allocate", method = RequestMethod.GET)
	public String allocate(Model model,HttpServletRequest request) throws HttbException{
		User u = new User();
		String userid= request.getParameter("id") ;
		String viewText= request.getParameter("viewText") ;
		u.setId(userid);
		u.setViewText(viewText);
		model.addAttribute("user", u);
		
		return "user/user_role";
	}
	
	@RequestMapping(value = "/save_use_role.action", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "用户模块", operateModelName = "用户角色配置")
	public Message save_use_role(HttpServletRequest request,String userId,String roleIds) throws HttbException {
		userId=request.getParameter("userId");
		roleIds=request.getParameter("roleIds");
		Message message = new Message();
		
		try {	
				if(CommonUtil.isNotEmpty(userId)&&CommonUtil.isNotEmpty(roleIds)){
					User user=new User();
					user.setId(userId);
					userService.deleteUser_role(user);
					userService.insertUserRoles(userId, roleIds);
					message.setSuccess(true);
					message.setMessage("用户角色配置成功");	
				}else{
					message.setSuccess(false);
					message.setMessage("userId\roleIds is null");	
				}
				
			} catch (HttbException e) {
				message.setSuccess(false);
				message.setMessage("用户角色配置失败");
				throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "保存用户角色配置时发生异常", e);
			}
		return message;
	}
	
}
