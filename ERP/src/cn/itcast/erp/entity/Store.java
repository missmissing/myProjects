package cn.itcast.erp.entity;
/**
 * 仓库 实体类
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class Store {
	//实体类属性
	private Long uuid;//ID
	private String name;//仓库
	private String address;//地址
	private Long empUuid;//管理员ID
	
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
	public Long getEmpUuid() {
		return empUuid;
	}
	public void setEmpUuid(Long empUuid) {
		this.empUuid = empUuid;
	}

}
