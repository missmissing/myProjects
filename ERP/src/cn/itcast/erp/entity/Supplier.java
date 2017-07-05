package cn.itcast.erp.entity;
/**
 * 供应商 实体类
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class Supplier {
//实体类属性
	private Long uuid;//ID
	private String name;//名称
	private String address;//地址
	private String contact;//联系人
	private String tele;//电话
	private Integer type;//类型
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

}
