package cn.itcast.erp.entity;

public class Sheet {
	private String name;   //统计报表中字段的名称
	private Double money;   //统计报表中的金额
	
	//创建构造方法
	public Sheet(){
		
	}
	public Sheet(String name,Double money){
		this.name=name;
		this.money=money;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	
	
}
