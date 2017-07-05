package cn.itcast.erp.entity;

import java.util.Date;

/**
 * 用来存储工作流程相关--->历史任务实例查询 菜单中的组合条件查询的条件
 * @author Administrator
 *
 */
public class ActivitiQuery {
	private String userId;  //存储查询执行人的Id
	private Date date1;    //存储操作开始日期
	private Date date2;		//存储操作结束日期
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}
	public Date getDate2() {
		return date2;
	}
	public void setDate2(Date date2) {
		this.date2 = date2;
	}
	
}
