package com.huatu.ou.role.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.huatu.ou.User_role;
import com.huatu.ou.ascpectJ.operLog.LogAnnotation;
import com.huatu.ou.role.model.Role;
import com.huatu.ou.role.service.RoleService;
import com.huatu.ou.roleMenu.model.Role_Menu;
import com.huatu.ou.user.model.User;

@Controller
@RequestMapping("role")
public class RoleAction extends BaseAction {
	@Autowired
	private RoleService roleService;
	/**
	 * 进入角色管理界面
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) throws HttbException{
		return "role/list";
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public Page<Role> query(Model model, Role role,String pageNum) throws HttbException {
		HashMap<String, Object> filter = new HashMap<String, Object>();
		try{
			int pageSize = 10;
			Page<Role> page = new Page<Role>();
			page.setPage(Integer.valueOf(pageNum));
			page.setPageSize(pageSize);
			//条件集合
			filter.put("pageNum", (Integer.valueOf(pageNum)-1)*pageSize);
			filter.put("pageSize", pageSize);
			List<Role> roles;
			if (CommonUtil.isNotNull(role)) {
				if (CommonUtil.isNotNull(role.getName())) {
					String name = role.getName();
					filter.put("name", name);
				}
			}
			try {
				roles = roleService.queryRoles(filter);
			} catch (Exception e) {
				throw new HttbException(this.getClass() + "查询角色信息发生异常", e);
			}
			try {
				int length = roleService.selectCount();
				page.setTotal(length / pageSize);
				if (length % pageSize != 0) {
					page.setTotal(page.getTotal() + 1);
				}
			} catch (Exception e) {
				throw new HttbException(this.getClass() + "查询角色信息总数发生异常", e);
			}
			page.setRows(roles);
			return page;
		}catch(Exception e){
			throw new HttbException(this.getClass() + "查询角色信息发生异常", e);
		}
	}

	/**
	 * haddle							进入试题管理添加页面
	 * @param		model			参数集合
	 * @return 		String			返回页面路径
	 * @throws HttbException
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(HttpServletRequest request) throws HttbException{
		return "role/add";
	}
	

	@RequestMapping(value = "/save.action", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "角色模块", operateModelName = "角色修改或添加")
	public Message save(HttpServletRequest request,Role role) throws HttbException {
		Message message = new Message();
		try {
			//如果ID为空==>添加图片
			if(CommonUtil.isNull(role.getId())){
				//添加
				role.setId(CommonUtil.getUUID());
				role.setCreateTime((new Date()));
				role.setCreateUser("");
				roleService.insertRoles(role);
				message.setSuccess(true);
				message.setMessage("角色添加成功！");
			}else{
				//修改
				role.setCreateTime((new Date()));
				roleService.updateRoles(role);
				message.setSuccess(true);
				message.setMessage("角色修改成功！");
			}
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("角色编辑失败！");
			throw new HttbException(this.getClass() + "角色编辑发生异常", e);
		}
		return message;
	}

	/**
	 *
	 * delete						(删除图片)
	 * @param 		model
	 * @param 		image
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/delete.action",method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "角色模块", operateModelName = "角色删除")
	public Message delete(HttpServletRequest request,String id) throws HttbException{
		Message message = new Message();
		Role role=new Role();
		role.setId(id);
		try {
			int roleIsUsed = roleService.roleIsUesd(id);
			if(roleIsUsed > 0){
				message.setSuccess(false);
				message.setMessage("角色已绑定，不能删除！");
				return message;
			}
			roleService.deleteRoles(role);
			message.setSuccess(true);
			message.setMessage("角色删除成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("角色删除失败！");
			throw new HttbException(this.getClass() + "角色删除发生异常", e);
		}
		return message;
	}
	
	@RequestMapping(value = "/handle")
	public String handle(Model model,String id) throws HttbException {
		HashMap<String, Object> filter = new HashMap<String, Object>();
		if(CommonUtil.isNotEmpty(id)){
			try {
				filter.put("id", id);
				Role role = roleService.getRole(filter);
				model.addAttribute("role", role);
			} catch (HttbException e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "根据ID查询角色时发生异常", e);
			}
		}
		return "role/add";
	}
	
	@RequestMapping(value = "/getroleOpts.action",method = RequestMethod.POST)
	@ResponseBody
	public Message getroleOpts(HttpServletRequest request,User user) throws HttbException {
		HashMap<String, Object> filter = new HashMap<String, Object>();
		Message message = new Message();
			try {
				String opts = roleService.getRolesOption(filter,user);
				message.setSuccess(true);
				message.setData(opts);
			} catch (HttbException e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "根据ID查询角色时发生异常", e);
			}
		return message;
	}
	
	@RequestMapping(value = "/save_role_menu", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "角色模块", operateModelName = "角色菜单配置")
	public Message save_role_menu(HttpServletRequest request,String roleId,String menuIds) throws HttbException {
		roleId=request.getParameter("roleId");
		menuIds=request.getParameter("menuIds");
		Message message = new Message();
		try {	
				if(CommonUtil.isNotEmpty(roleId)&&CommonUtil.isNotEmpty(menuIds)){
					Role role=new Role();
					role.setId(roleId);
					roleService.deleteUser_role(role);
					roleService.insertRoleMenus(roleId, menuIds);
					message.setSuccess(true);
					message.setMessage("角色菜单关系配置成功");	
				}else{
					message.setSuccess(false);
					message.setMessage("userId\roleIds is null");	
				}
				
			} catch (HttbException e) {
				message.setSuccess(false);
				message.setMessage("角色菜单关系配置失败");
				throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "保存角色菜单关系时发生异常", e);
			}
		return message;
	}
	
	
}
