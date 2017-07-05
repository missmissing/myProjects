package cn.itcast.erp.entity;

import java.util.List;

/**
 * 角色 实体类
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class Role {
//实体类属性
	private Long uuid;//角色ID
	private String name;//角色名称
	
	private List<Menu> menus;  //每个角色拥有的菜单的集合
	
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
