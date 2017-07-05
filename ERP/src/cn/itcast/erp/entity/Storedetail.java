package cn.itcast.erp.entity;

import java.util.List;

/**
 * 仓库库存 实体类
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class Storedetail {
	//实体类属性
	private Long uuid;//ID
	private Store store;//仓库ID
	private Goods goods;//商品ID
	private Integer num;//库存数量
	
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}

}
