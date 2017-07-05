package cn.itcast.erp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 部门 实体类
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class Dep implements Serializable{
//实体类属性
	private Long uuid;//部门编号
	private String name;//部门名称
	private String tele;//电话
	
	private List<Emp> emps;
	
	public List<Emp> getEmps() {
		return emps;
	}
	public void setEmps(List<Emp> emps) {
		this.emps = emps;
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
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}

}
