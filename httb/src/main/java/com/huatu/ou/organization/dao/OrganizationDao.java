package com.huatu.ou.organization.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.huatu.core.dao.impl.SqlBaseDaoImpl;
import com.huatu.core.exception.HttbException;
import com.huatu.ou.organization.model.Organization;

@Repository
public class OrganizationDao{
	
	@Autowired
	private SqlBaseDaoImpl sqlBaseDaoImpl;
	
	private static final String NameSpace = "com.huatu.ou.organization.model.Organization.";
	public List<Organization> selectOrganizations(Map<String, Object> condition) throws HttbException {
		try {
			return sqlBaseDaoImpl.selectList(NameSpace + "selectList", condition);
		} catch (Exception e) {
			throw new HttbException(this.getClass() + "根据参数查询用户时发生异常", e);
		}
	}
	
	
	/**
	 * 添加组织机构
	 * 
	 * @param organization
	 * @return
	 * @throws HttbException
	 */
	public int insert(Organization organization) throws HttbException {
		try {
			return sqlBaseDaoImpl.insert(NameSpace +"insert", organization);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"添加组织机构时发生异常", e);
		}
	}
	
	/**
	 * 修改组织机构
	 * 
	 * @param organization
	 * @return
	 * @throws HttbException
	 */
	public int update(Organization organization) throws HttbException {
		try {
			return sqlBaseDaoImpl.update(NameSpace+"update", organization);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"修改组织机构时发生异常", e);
		}
	}
	/**
	 * 修改组织机构
	 * 
	 * @param organization
	 * @return
	 * @throws HttbException
	 */
	public int delete(Organization organization) throws HttbException {
		try {
			String[] organizationIds = organization.getId().split(",");
			HashMap map = new HashMap();
			map.put("ids", organizationIds);
			return sqlBaseDaoImpl.delete(NameSpace+"deleteByPrimaryKey", map);
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"修改组织机构时发生异常", e);
		}
	}
	
	public int selectCount() throws HttbException {
		try {
			int count = sqlBaseDaoImpl.get(NameSpace +"selectCount");
			return count;
		} catch (Exception e) {
			throw new HttbException(this.getClass()+"查询组织机构总数量时发生异常", e);
		}
	}
	
	
}
