package cn.itcast.erp.entity;
/**
 * 商品类型 实体类
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class Goodstype {
//实体类属性
	private Long uuid;//商品类别编号
	private String name;//商品类别名称
	
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

}
