package com.huatu.ou.organization.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.huatu.core.exception.HttbException;
import com.huatu.core.model.Tree;
import com.huatu.ou.organization.dao.OrganizationDao;
import com.huatu.ou.organization.model.Organization;
import com.huatu.tb.category.model.Category;

@Service
public class OrganizationService {
	@Autowired
	private OrganizationDao organizationDao;
	
	public List<Organization> queryOrganizations(Map<String, Object> condition) throws HttbException {
		return organizationDao.selectOrganizations(condition);
	}
	
	/**
	 * 添加组织机构
	 * @param organization
	 * @return
	 * @throws HttbException
	 */
	public int insertOrganizations(Organization organization) throws HttbException {
		return organizationDao.insert(organization);
	}
	
	/**
	 * 修改组织机构
	 * @param organization
	 * @return
	 * @throws HttbException
	 */
	public int updateOrganizations(Organization organization) throws HttbException {
		return organizationDao.update(organization);
	}
	
	/**
	 * 修改组织机构
	 * @param organization
	 * @return
	 * @throws HttbException
	 */
	public int deleteOrganizations(Organization organization) throws HttbException {
		return organizationDao.delete(organization);
	}
	/**
	 * 修改组织机构
	 * @param organization
	 * @return
	 * @throws HttbException
	 */
	public Organization getOrganization(Map<String, Object> condition) throws HttbException {
		List<Organization> l  =organizationDao.selectOrganizations(condition);
		if(l!=null&&l.size()>0){
			return (Organization)l.get(0);
		}else
			return null;
	}
		
	
	/**
	 * 组装 章节 知识点  树
	 * @param list
	 * @return
	 */
	public List<Tree> getOrganizationTree(List<Organization> list){
		List<Tree> treeList = new ArrayList<Tree>();
		for(Organization o : list){
			Tree tree = new Tree();
			tree.setId(o.getId());
			tree.setName(o.getName());
			String pid;
			if(o.getParentOrg() !=null){
				pid = o.getParentOrg().getId();
			}else{
				pid = "0" ;//根节点
				tree.setOpen("true");
			}
			tree.setpId(pid);
			treeList.add(tree);
		}
		return treeList;
	}
	
	public int selectCount() throws HttbException {
		return organizationDao.selectCount();
	}
	
}
