package com.huatu.ou.organization.action;

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
import com.huatu.core.util.Constants;
import com.huatu.ou.ascpectJ.operLog.LogAnnotation;
import com.huatu.ou.organization.model.Organization;
import com.huatu.ou.organization.service.OrganizationService;
import com.huatu.upload.model.Image;
@Controller
@RequestMapping("organization")
public class OrganizationAction extends BaseAction {
	
	@Autowired
	private OrganizationService organizationService;
	/**
	 * 进入组织机构管理界面
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) throws HttbException{
		return "organization/list";
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public Page<Organization> query(Model model, Organization organization,String pageNum) throws HttbException {
		int pageSize = 10;
		HashMap<String, Object> filter = new HashMap<String, Object>();
		try{
			Page<Organization> page = new Page<Organization>();
			page.setPage(Integer.valueOf(pageNum));
			page.setPageSize(pageSize);
			//条件集合
			List<Organization> organizations;
			filter.put("pageNum", (Integer.valueOf(pageNum)-1)*pageSize);
			filter.put("pageSize", pageSize);
			if (CommonUtil.isNotNull(organization)) {
				if (CommonUtil.isNotNull(organization.getName())) {
					String name = organization.getName();
					filter.put("name", name);
				}
			}
			try {
				organizations = organizationService.queryOrganizations(filter);
			} catch (Exception e) {
				throw new HttbException(this.getClass() + "查询组织机构信息发生异常", e);
			}
			try {
				int length = organizationService.selectCount();
				page.setTotal(length / pageSize);
				if (length % pageSize != 0) {
					page.setTotal(page.getTotal() + 1);
				}
			} catch (Exception e) {
				throw new HttbException(this.getClass() + "查询组织机构信息总数发生异常", e);
			}
			page.setRows(organizations);
			return page;
		}catch(Exception e){
			throw new HttbException(this.getClass() + "查询组织机构信息发生异常", e);
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
		return "organization/add";
	}
	
	/**
	 * add							进入试题管理添加页面
	 * @param		model			参数集合
	 * @return 		String			返回页面路径
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/save.action", method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "组织机构模块", operateModelName = "组织机构修改或添加")
	public Message save(HttpServletRequest request,Organization organization) throws HttbException {
		Message message = new Message();
		try {
			//如果ID为空==>添加图片
			if(CommonUtil.isNull(organization.getId())){
				//添加
				organization.setId(CommonUtil.getUUID());
				organization.setCreateTime((new Date()));
				organization.setCreateUser("");
				organizationService.insertOrganizations(organization);
				message.setSuccess(true);
				message.setMessage("组织机构添加成功！");
			}else{
				//修改
				organization.setCreateTime((new Date()));
				organizationService.updateOrganizations(organization);
				message.setSuccess(true);
				message.setMessage("组织机构修改成功！");
			}
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("组织机构编辑失败！");
			throw new HttbException(this.getClass() + "组织机构编辑发生异常", e);
		}
		return message;
	}

	/**
	 *
	 * delete						(组织机构删除)
	 * @param 		model
	 * @param 		image
	 * @return
	 * @throws HttbException 
	 */
	@RequestMapping(value = "/delete.action",method = RequestMethod.POST)
	@ResponseBody
	@LogAnnotation(operateFuncName = "组织机构模块", operateModelName = "组织机构删除")
	public Message delete(HttpServletRequest request,String id) throws HttbException{
		Message message = new Message();
		Organization organization=new Organization();
		organization.setId(id);
		try {
			organizationService.deleteOrganizations(organization);
			message.setSuccess(true);
			message.setMessage("组织机构删除成功！");
		} catch (Exception e) {
			message.setSuccess(false);
			message.setMessage("组织机构删除失败！");
			throw new HttbException(this.getClass() + "组织机构删除发生异常", e);
		}
		return message;
	}
	
	@RequestMapping(value = "/handle")
	public String handle(Model model,String id) throws HttbException {
		HashMap<String, Object> filter = new HashMap<String, Object>();
		if(CommonUtil.isNotEmpty(id)){
			try {
				filter.put("id", id);
				Organization organization = organizationService.getOrganization(filter);
				model.addAttribute("organization", organization);
			} catch (HttbException e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass() + "根据ID查询组织机构时发生异常", e);
			}
		}
		return "organization/add";
	}
	
}
