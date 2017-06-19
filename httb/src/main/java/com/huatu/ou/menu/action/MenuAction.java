package com.huatu.ou.menu.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Message;
import com.huatu.core.model.Page;
import com.huatu.core.model.Tree;
import com.huatu.core.util.CommonUtil;
import com.huatu.ou.ascpectJ.operLog.LogAnnotation;
import com.huatu.ou.menu.model.Menu;
import com.huatu.ou.menu.service.MenuService;
import com.huatu.tb.category.model.Category;
import com.huatu.tb.category.util.CategoryUtil;
@Controller
@RequestMapping("menu")
public class MenuAction {
	@Autowired
	private MenuService menuService;
	/**
	 * 进入角色管理界面
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) throws HttbException{
		return "menu/list";
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public Page<Menu> query(Model model, Menu menu,String pageNum) throws HttbException {
		HashMap<String, Object> filter = new HashMap<String, Object>();
		try{
			int pageSize = 10;
			Page<Menu> page = new Page<Menu>();
			page.setPage(Integer.valueOf(pageNum));
			page.setPageSize(pageSize);
			filter.put("pageNum", (Integer.valueOf(pageNum)-1)*pageSize);
			filter.put("pageSize", pageSize);
			//条件集合
			List<Menu> menus;
			if (CommonUtil.isNotNull(menu)) {
				if (CommonUtil.isNotNull(menu.getName())) {
					String name = menu.getName();
					filter.put("name", name);
				}
			}
			try {
				menus = menuService.queryMenus(filter);
				for(Menu m : menus){
					if(null == m.getUrl()||"".equals(m.getUrl()))
						m.setUrl("无");
					if(null == m.getParentId()||"".equals(m.getParentId()))
						m.setParentId("无");
				}
			
			} catch (Exception e) {
				throw new HttbException(this.getClass() + "查询菜单信息发生异常", e);
			}
			try {
				int length = menuService.selectCount();
				page.setTotal(length / pageSize);
				if (length % pageSize != 0) {
					page.setTotal(page.getTotal() + 1);
				}
			} catch (Exception e) {
				throw new HttbException(this.getClass() + "查询菜单信息总数发生异常", e);
			}
			page.setRows(menus);
			return page;
		}catch(Exception e){
			throw new HttbException(this.getClass() + "查询菜单信息发生异常", e);
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
		return "menu/add";
	}
	
	/**
	 * add							进入试题管理添加页面
	 * @param		model			参数集合
	 * @return 		String			返回页面路径
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/save.action", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "菜单模块", operateModelName = "菜单修改或添加")
	public Message save(HttpServletRequest request,Menu menu) throws HttbException {
		Message message = new Message();
		try {
			//如果ID为空==>添加图片
			if(CommonUtil.isNull(menu.getId())){
				//添加
				menu.setId(CommonUtil.getUUID());
				menu.setCreateTime((new Date()));
				menu.setCreateUser("");
				menuService.insertMenus(menu);
				message.setSuccess(true);
				message.setMessage("菜单添加成功！");
			}else{
				//修改
				menu.setCreateTime((new Date()));
				menuService.updateMenus(menu);
				message.setSuccess(true);
				message.setMessage("菜单修改成功！");
			}
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("菜单编辑失败！");
			throw new HttbException(this.getClass() + "查询菜单信息发生异常", e);
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
	@LogAnnotation(operateFuncName = "菜单模块", operateModelName = "菜单删除")
	public Message delete(HttpServletRequest request,String id) throws HttbException{
		Message message = new Message();
		Menu menu=new Menu();
		menu.setId(id);
		try {
			int menuIsUesd = menuService.menuIsUesd(id);
			if(menuIsUesd > 0){
				message.setSuccess(false);
				message.setMessage("菜单已绑定，不能删除！");
				return message;
			}
			menuService.deleteMenus(menu);
			message.setSuccess(true);
			message.setMessage("菜单删除成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("菜单删除失败！");
			throw new HttbException(this.getClass() + "菜单删除发生异常", e);
		}
		return message;
	}
	
	@RequestMapping(value = "/handle")
	public String handle(Model model,String id) throws HttbException {
		HashMap<String, Object> filter = new HashMap<String, Object>();
		if(CommonUtil.isNotEmpty(id)){
			try {
				filter.put("id", id);
				Menu menu = menuService.selectMenuById(id);
				Menu fatherMenu = menuService.selectMenuById(menu.getParentId());
				if(null == fatherMenu){
					menu.setParentName("无");
				}else{
					menu.setParentName(fatherMenu.getName());
				}
				
				if(null == menu.getOrderNum())
				{
					menu.setOrderNum(0);
				}
				model.addAttribute("menu", menu);
			} catch (HttbException e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "根据ID查询菜单时发生异常", e);
			}
		}
		return "menu/add";
	}
	
	@RequestMapping(value = "/authorizeMenu",method = RequestMethod.GET)
	public String authorizeMenu(HttpServletRequest request,String id) throws HttbException {
		request.setAttribute("role_id",id);
		return "menu/authorizeMenu";
	}
	
	@RequestMapping(value = "/getAllMenu",method = RequestMethod.POST)
	@ResponseBody
	public Message getAllMenu(HttpServletRequest request) throws HttbException {
		String roleId=request.getParameter("roleId");
		List<Tree> menuTree = menuService.getMenusOfTree(roleId);
		JSONArray jsonArray = JSONArray.fromObject(menuTree);
		Message message = new Message();
		message.setSuccess(true);
		message.setData(jsonArray);
		//model.addAttribute("treeJson",jsonArray);
		//return "category/list";
		return message;
	}	
	
	/**
	 * 实时发布
	 * @param		model			参数集合
	 * @return 		String			返回页面路径
	 * @throws HttbException
	 */
	@RequestMapping(value = "/directpublish", method = RequestMethod.GET)
	public String directpublish(HttpServletRequest request) throws HttbException{
		return "common/directpublish";
	}
	
}
