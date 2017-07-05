package cn.itcast.erp.biz.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
/**
 * 订单 业务层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class OrdersBiz extends ActivitiBiz<Orders> implements IOrdersBiz {

	private IOrdersDao ordersDao;//数据访问层
	
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
		setBaseDao(ordersDao);
	}
	

	/**
	 * 重写业务层逻辑添加方法
	 */
	public void add(Orders orders){
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new Date());  //格式化
		//获得最大订单号(订单号是根据日期产生的)
		String maxNum = ordersDao.getMaxOrderNum(date);
		//设置新增的订单号
		String orderNum;
		if(maxNum==null){
			orderNum = date+"00001";
		}else{
			orderNum = String.valueOf(Long.parseLong(maxNum)+1);
		}
		//把新产生的订单号设置进orders对象中
		orders.setOrderNum(orderNum);
		//===============================================
		//设置订单的产生日期
		orders.setCreateTime(new Date());
//		//设置订单的类型，1为采购订单，2为销售订单
//		orders.setOrderType(1);   //因为已在前端js页面设置orderType=Request['type']
		//设置订单的状态（0为未审核，1为已审核）
		orders.setState("0");
		//===============================================
		//设置totalNum和totalPrice
		int num = 0;
		double money = 0;
		for(Orderdetail od:orders.getOrderdetails()){
			num += od.getNum();
			money += od.getMoney();
			od.setState("0");
		}
		orders.setTotalNum(num);  //合计数量
		orders.setTotalPrice(money);  //合计金额
		
		ordersDao.add(orders);
		
		/////////启动工作流
		startBizProcess("Buy", String.valueOf(orders.getUuid()),String.valueOf(orders.getCreater()));  //调用父类的方法
	
	}

	/**
	 * 审核订单
	 */
	public void doCheck(Long ordersUuid, Long empUuid) {
		//根据订单uuid得到订单信息
		Orders orders = ordersDao.get(ordersUuid);
		//设置审核人
		orders.setChecker(empUuid);
		//设置审核时间 
		orders.setCheckTime(new Date());
		//设置订单的状态(1代表已审核）
		orders.setState("1");
		
		/////完成业务逻辑流程
		//设置审批的条件
		Map map = new HashMap();
		map.put("money", orders.getTotalPrice());
		//根据业务ID(订单id)--->任务ID
		completeBizProcess(String.valueOf(ordersUuid),String.valueOf(empUuid),map);
	}

	/**
	 * 确认订单
	 */
	
	public void doConfirm(Long ordersUuid, Long empUuid) {
		//根据订单uuid得到订单信息
		Orders orders = ordersDao.get(ordersUuid);
		//设置确人
		orders.setStarter(empUuid);
		//设置确认时间
		orders.setStartTime(new Date());
		//设置订单的状态(2代表已确认）
		orders.setState("2");
		
		/////完成业务逻辑流程
		//根据业务ID(订单id)--->任务ID
		completeBizProcess(String.valueOf(ordersUuid),String.valueOf(empUuid));
	}

}
