/**
 * 
 */
package com.huatu.core.model;

import java.io.Serializable;
import java.util.Date;

/** 
 * @ClassName: Tree 
 * @Description: 树结构对象
 * @author LiXin 
 * @date 2015年4月16日 下午3:56:11 
 * @version 1.0
 *  
 */
public class Tree implements Serializable  {
	private static final long serialVersionUID = 1730201232017584124L;
	private String id;   //主键
	private String name;   //显示名称
	private String pId;   //父节点ID
	private String level;//层级
	private String url;   //跳转路径
	private String checked;   //是否选中  true/false
	private String open;   //是否展开  true/false
	private String nocheck;   //在多选的模块下 该节点是否显示选择框
	private String click;   //是否允许发生单击事件
	private Integer orderNum;//排序值
	private String explain;//说明
	
	/**
	 * 构建树json
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	public String toString() {
		if (null == this){
			return null;
		}
		StringBuilder sb = new StringBuilder("{");
		sb.append(" id:\"").append(id).append("\",");
		sb.append(" name:\"").append(name).append("\",");
		sb.append(" pId:\"").append(pId).append("\",");
		sb.append(" url:\"").append(url).append("\"");
		if(checked !=null)
			sb.append(" ,checked:\"").append(checked).append("\"");
		if(open !=null)
			sb.append(" ,open:\"").append(open).append("\"");
		if(nocheck !=null)
			sb.append(" ,nocheck:\"").append(nocheck).append("\",");
		if(click !=null)
			sb.append(" ,click:\"").append(click).append("\",");
		if(explain !=null)
			sb.append(" ,explain:\"").append(explain).append("\",");
		return sb.toString();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getNocheck() {
		return nocheck;
	}
	public void setNocheck(String nocheck) {
		this.nocheck = nocheck;
	}
	public String getClick() {
		return click;
	}
	public void setClick(String click) {
		this.click = click;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	
}
