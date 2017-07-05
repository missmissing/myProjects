package cn.itcast.erp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 员工 实体类
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class Emp{
//实体类属性
	private Long uuid;//员工编号
	private String userName;//登陆名称
	private String pwd;//登陆密码
	private String name;//姓名
	private String email;//E-mail
	private String tele;//电话
	private Integer gender;//性别
	private String address;//地址
	private java.util.Date birthday;//出生年月日
	private Dep dep;
	
	private List<Role> roles;   //角色
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public java.util.Date getBirthday() {
		return birthday;
	}
	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}
	public Dep getDep() {
		return dep;
	}
	public void setDep(Dep dep) {
		this.dep = dep;
	}

}
