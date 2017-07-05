package cn.itcast.erp.entity;
/**
 * 订单明细 实体类
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class Orderdetail {
	//实体类属性
	private Long uuid;//ID
	private Integer num;//数量
	private Double price;//价格
	private Double money;//金额
	private Long goodsUuid;//商品ID
	private String goodsName;//商品ID
	private java.util.Date endTime;//出入库时间
	private Long ender;//库管员
	private Long storeUuid;//仓库编号
	private String state;//状态
	private Orders orders;   //订单主表
	
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Long getGoodsUuid() {
		return goodsUuid;
	}
	public void setGoodsUuid(Long goodsUuid) {
		this.goodsUuid = goodsUuid;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public java.util.Date getEndTime() {
		return endTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	public Long getEnder() {
		return ender;
	}
	public void setEnder(Long ender) {
		this.ender = ender;
	}
	public Long getStoreUuid() {
		return storeUuid;
	}
	public void setStoreUuid(Long storeUuid) {
		this.storeUuid = storeUuid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}

}
