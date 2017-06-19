package com.huatu.tb.category.model;

import java.util.List;

public class CategoryInputMessage {
	/**知识点集合*/
	private List<Category> cateList;
	/**异常记录文件名*/
	private String errorName;
	public List<Category> getCateList() {
		return cateList;
	}
	public void setCateList(List<Category> cateList) {
		this.cateList = cateList;
	}
	public String getErrorName() {
		return errorName;
	}
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
}
