package com.huatu.ou.organization.model;


import  com.huatu.core.model.BaseEntity;

public class Organization extends BaseEntity{
  
	private String name;
	
    private String organizationNo;
    
    private Organization parentOrg;
    
    
    public Organization getParentOrg() {
		return parentOrg;
	}
	public void setParentOrg(Organization parentOrg) {
		this.parentOrg = parentOrg;
	}
	private Integer sort;
    

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrganizationNo() {
		return organizationNo;
	}
	public void setOrganizationNo(String organizationNo) {
		this.organizationNo = organizationNo;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

    
    
    
}