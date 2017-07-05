package cn.itcast.erp.entity;

import java.util.List;

/**
 * 订单 实体类
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class Orders {
//实体类属性
	private Long uuid;//ID
	private String orderNum;//订单编号
	private java.util.Date createTime;//生成日期
	private java.util.Date checkTime;//检查日期
	private java.util.Date startTime;//开始日期
	private java.util.Date endTime;//结束日期
	private Integer orderType;//订单类型
	private Long creater;//下单员
	private Long checker;//审查员
	private Long starter;//采购员
	private Long ender;//库管员
	private Long supplierUuid;//供应商ID
	private Integer totalNum;//总数量
	private Double totalPrice;//总价格
	private String state;//订单状态
	private List<Orderdetail> orderdetails;  //订单详细信息表
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.util.Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(java.util.Date checkTime) {
		this.checkTime = checkTime;
	}
	public java.util.Date getStartTime() {
		return startTime;
	}
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	public java.util.Date getEndTime() {
		return endTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Long getCreater() {
		return creater;
	}
	public void setCreater(Long creater) {
		this.creater = creater;
	}
	public Long getChecker() {
		return checker;
	}
	public void setChecker(Long checker) {
		this.checker = checker;
	}
	public Long getStarter() {
		return starter;
	}
	public void setStarter(Long starter) {
		this.starter = starter;
	}
	public Long getEnder() {
		return ender;
	}
	public void setEnder(Long ender) {
		this.ender = ender;
	}
	public Long getSupplierUuid() {
		return supplierUuid;
	}
	public void setSupplierUuid(Long supplierUuid) {
		this.supplierUuid = supplierUuid;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<Orderdetail> getOrderdetails() {
		return orderdetails;
	}
	public void setOrderdetails(List<Orderdetail> orderdetails) {
		this.orderdetails = orderdetails;
	}
}
