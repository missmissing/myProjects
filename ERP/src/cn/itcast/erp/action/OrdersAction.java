package cn.itcast.erp.action;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;

import cn.itcast.erp.biz.IActivitiBiz;
import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
/**
 * 订单 控制层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class OrdersAction extends BaseAction<Orders>{
	//注入业务层
	private IOrdersBiz ordersBiz;
	public void setOrdersBiz(IOrdersBiz ordersBiz) {
		this.ordersBiz = ordersBiz;
		setBaseBiz(ordersBiz);
	}
	
	//注入工作流的业务逻辑层
	private IActivitiBiz activitiBiz;
	public void setActivitiBiz(IActivitiBiz activitiBiz) {
		this.activitiBiz = activitiBiz;
	}

	//属性驱动，存储浏览器输入的表格数据
	private String json;
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	
	/**
	 * 增加订单（重写父类BaseAction中的add方法）
	 */
	public void add(){
		//获得登陆用户信息
		Emp emp = (Emp) ActionContext.getContext().getSession().get("user");
		if(emp==null){  //说明没有登陆
			write("请先登录系统!!");
			return;
		}
		try {
			//获得订单实体
			Orders orders = getT();
			
			orders.setCreater(emp.getUuid()); //设置订单录入者
			//转换对象，将json转换为集合
			List<Orderdetail> orderdetails = JSON.parseArray(json, Orderdetail.class);
			//把得到的orderdetails集合设置到订单主表的属性中
			orders.setOrderdetails(orderdetails);
			ordersBiz.add(orders);
			write("ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			write("发生异常!");
		}
	}
	
	/**
	 * 订单审核
	 */
	public void doCheck(){
		//从Session对象中获得用户的登陆信息
		Emp user = (Emp)ActionContext.getContext().getSession().get("user");
		//调用业务逻辑方法实现订单审核
		try{
			ordersBiz.doCheck(getId(),user.getUuid());
			write("ok");
		}catch(Exception e){
			e.printStackTrace();
			write("审核发生异常！");
		}
	}
	
	/**
	 * 订单确认
	 */
	public void doConfirm(){
		//从Session对象中获得用户的登陆信息
		Emp user = (Emp)ActionContext.getContext() .getSession().get("user");
		//调用业务逻辑方法实现订单审核
		try{
			ordersBiz.doConfirm(getId(),user.getUuid());
			write("ok");
		}catch(Exception e){
			e.printStackTrace();
			write("确认发生异常！");
		}
	}
	
	
	private String pdkey;  //用来存储流程定义key
	public String getPdkey() {
		return pdkey;
	}
	public void setPdkey(String pdkey) {
		this.pdkey = pdkey;
	}
	
	public void listByPage(){
		//得到用户的Id
		Emp user = (Emp) ActionContext.getContext().getSession().get("user");
		if(pdkey!=null){
			//根据流程定义key和任务节点key查询得到订单id集合
			List<Long> ids = activitiBiz.getBusinessIdListByUserId(pdkey,String.valueOf(user.getUuid()));
			//把订单集合设置到父类中的param变量中
			setParam(ids);
		}
		//调用父类的 方法
		super.listByPage();
	}
	
}
