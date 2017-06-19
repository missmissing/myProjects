package com.huatu.ou.role.model;

import java.util.List;

import com.huatu.core.model.BaseEntity;
import com.huatu.ou.menu.model.Menu;

public class Role extends BaseEntity {
	
	private String name;
	
    private int sort;
    
    public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	private List<Menu> menus;
    
    
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
   
    
}