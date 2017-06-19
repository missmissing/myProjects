package com.huatu.ou.menu.model;

import java.util.List;

import com.huatu.core.model.BaseEntity;

public class Menu extends BaseEntity {
	
	private String code;     //标识
	private String name;     //名称
	private Integer type;    //类型   
	private Integer level;   //等级  1,2,3,...
	private String url;      //跳转地址  
	private String parentId; //父节点ID
    private String iconUrl;  //图标URL 选填属性
    private Integer orderNum;//排序值
    private List<Menu> children; //子菜单集合
    private String childContents;
    private String parentName;
    
    
	public String getChildContents() {
		return childContents;
	}
	public void setChildContents(String childContents) {
		this.childContents = childContents;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public List<Menu> getChildren() {
		return children;
	}
	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	
    
}