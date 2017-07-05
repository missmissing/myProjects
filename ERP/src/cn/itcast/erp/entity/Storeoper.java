package cn.itcast.erp.entity;
/**
 * 仓库库存操作记录 实体类
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class Storeoper {
//实体类属性
	private Long uuid;//ID
	private Long empUuid;//员工ID
	private java.util.Date operTime;//操作事件
	private Long storeUuid;//仓库ID
	private Long goodsUuid;//商品ID
	private Integer num;//数量
	private Integer type;//出入库标记
	
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public Long getEmpUuid() {
		return empUuid;
	}
	public void setEmpUuid(Long empUuid) {
		this.empUuid = empUuid;
	}
	public java.util.Date getOperTime() {
		return operTime;
	}
	public void setOperTime(java.util.Date operTime) {
		this.operTime = operTime;
	}
	public Long getStoreUuid() {
		return storeUuid;
	}
	public void setStoreUuid(Long storeUuid) {
		this.storeUuid = storeUuid;
	}
	public Long getGoodsUuid() {
		return goodsUuid;
	}
	public void setGoodsUuid(Long goodsUuid) {
		this.goodsUuid = goodsUuid;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

}
