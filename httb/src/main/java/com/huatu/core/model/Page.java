/**
 *
 */
package com.huatu.core.model;

import java.util.Collection;

/**
 * @ClassName: Page
 * @Description: 分页信息
 * @author LiXin
 * @date 2015年4月16日 上午9:51:12
 * @version 1.0
 * @param <T>
 *
 */
public class Page<T> {
	private int total; // 总页数

	private int pageSize; // 分页步长

	private int page; // 当前页

	private Collection<T> rows; // 数据

	private String responseText;

	/**边界值(存储最后一条记录主键)
	 * 这个属性每次分页时必须赋值，不然无法分页
	 * */
	private String border;

	/**总记录数*/
	private Long records;

	private String sortField;//排序字段
	private String sortOrder;//升序或降序
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public Long getRecords() {
		return records;
	}
	public void setRecords(Long records) {
		this.records = records;
	}
	public Collection<T> getRows() {
		return rows;
	}
	public void setRows(Collection<T> rows) {
		this.rows = rows;
	}
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getBorder() {
		return border;
	}
	public void setBorder(String border) {
		this.border = border;
	}
}
