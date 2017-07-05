package cn.itcast.erp.entity;
/**
 * 商品 实体类
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class Goods {
	//实体类属性
	private Long uuid;//商品编号
	private String name;//商品名称
	private String origin;//产地
	private String producer;//厂家
	private String unit;//计量单位
	private Double inPrice;//进货价格
	private Double outPrice;//出货价格
	private Goodstype goodstype;//商品类型
	
	//Goods构造方法
	public Goods(String producer){  //有参构造方法，是为了把查询厂家的结果构造成key-value格式的json数据
		this.producer=producer;
	}
	public Goods(){ //无参构造方法
		
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
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getInPrice() {
		return inPrice;
	}
	public void setInPrice(Double inPrice) {
		this.inPrice = inPrice;
	}
	public Double getOutPrice() {
		return outPrice;
	}
	public void setOutPrice(Double outPrice) {
		this.outPrice = outPrice;
	}
	public Goodstype getGoodstype() {
		return goodstype;
	}
	public void setGoodstype(Goodstype goodstype) {
		this.goodstype = goodstype;
	}

}
