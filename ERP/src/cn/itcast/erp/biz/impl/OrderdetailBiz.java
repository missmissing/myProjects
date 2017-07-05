package cn.itcast.erp.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import cn.itcast.erp.biz.IOrderdetailBiz;
import cn.itcast.erp.dao.IOrderdetailDao;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.dao.IStoreoperDao;
import cn.itcast.erp.entity.Goods;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.entity.Store;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.entity.Storeoper;
import cn.itcast.erp.exception.ERPException;
/**
 * 订单明细 业务层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class OrderdetailBiz extends ActivitiBiz<Orderdetail> implements IOrderdetailBiz {

	private IOrderdetailDao orderdetailDao;//数据访问层
	public void setOrderdetailDao(IOrderdetailDao orderdetailDao) {
		this.orderdetailDao = orderdetailDao;
		setBaseDao(orderdetailDao);
	}
	
	private IStoredetailDao storedetailDao;  //注入仓库明细表
	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}

	private IStoreoperDao storeoperDao;   //注入仓库操作记录表
	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
	}

	
	
	/**
	 * 订单入库
	 * @param orderdetailuuid   订单明细表id
	 * @param storeUuid     库存表id
	 * @param empUuid     员工表id
	 */
	public void doInStore(Long orderdetailuuid,Long storeUuid,Long empUuid){
		//1、订单明细表修改endTime、ender、sotreId、state
		  //根据订单明细表uuid得到订单明细信息
		  Orderdetail orderdetail = orderdetailDao.get(orderdetailuuid);
		  //利用快照修改订单明细表中的字段值
		  orderdetail.setEnder(empUuid);
		  orderdetail.setEndTime(new Date());
		  orderdetail.setStoreUuid(storeUuid);
		  orderdetail.setState("1");
		  
		//2、修改仓库中该商品的数量
		  //根据商品ID和仓库ID进行查询详细的库存信息
		  Storedetail sd = new Storedetail();
		  Goods goods = new Goods();
		  goods.setUuid(orderdetail.getGoodsUuid());
		  sd.setGoods(goods);
		  
		  Store store = new Store();
		  store.setUuid(orderdetail.getStoreUuid());
		  sd.setStore(store);
		  
		  //查询得到库存信息
		  List<Storedetail> list = storedetailDao.getList(sd, null, null);
		  if(list.size()==0){   //说明库存表中没该商品的订单信息
			  sd.setNum(orderdetail.getNum());  
			  storedetailDao.add(sd);  //把该商品的库存信息存入到库存表中
		  }else{
			  sd=list.get(0);
			  sd.setNum(sd.getNum()+orderdetail.getNum());  //在原库存的基础上增加数量
		  }
		  
		//3、修改仓库变动记录表中的数据
		  //创建库存变动表对象
		  Storeoper storeoper = new Storeoper();
		  storeoper.setEmpUuid(empUuid);
		  storeoper.setGoodsUuid(orderdetail.getGoodsUuid());
		  storeoper.setNum(orderdetail.getNum());
		  storeoper.setOperTime(orderdetail.getEndTime());
		  storeoper.setStoreUuid(storeUuid);
		  storeoper.setType(1);  //1代表采购订单表
		
		  storeoperDao.add(storeoper);   
		  
		//4、修改订单表中的ender、endTime、state
		  //查询明细表中state为0的记录数，如果得到的记录数为0，说明订单明细表中的商品均已入库
		  Orderdetail orderdetail0 = new Orderdetail();
		  orderdetail0.setState("0");
		  orderdetail0.setOrders(orderdetail.getOrders());
		  //查询state为0的记录
		  Long count = orderdetailDao.getCount(orderdetail0, null, null);
		  if(count==0){
			  //修改订单主表中的入库人、入库时间、状态值
			  Orders orders = orderdetail.getOrders();
			  orders.setEnder(empUuid);
			  orders.setEndTime(new Date());
			  orders.setState("3");  //3表示已结束
			  
			/////完成业务逻辑流程
			//根据业务ID(订单id)--->流程实例ID--->任务ID
			completeBizProcess(String.valueOf(orders.getUuid()),String.valueOf(empUuid));
			  
		  }
	}

	public void doOutStore(Long orderdetailuuid,Long storeUuid,Long empUuid){
		//1、订单明细表修改endTime、ender、sotreId、state
		  //根据订单明细表uuid得到订单明细信息
		  Orderdetail orderdetail = orderdetailDao.get(orderdetailuuid);
		  //利用快照修改订单明细表中的字段值
		  orderdetail.setEnder(empUuid);
		  orderdetail.setEndTime(new Date());
		  orderdetail.setStoreUuid(storeUuid);
		  orderdetail.setState("1");
		  
		//2、修改仓库中该商品的数量
		  //根据商品ID和仓库ID进行查询详细的库存信息
		  Storedetail sd = new Storedetail();
		  
		  Goods goods = new Goods();
		  goods.setUuid(orderdetail.getGoodsUuid());
		  sd.setGoods(goods);
		  
		  Store store = new Store();
		  store.setUuid(orderdetail.getStoreUuid());
		  sd.setStore(store);
		  
		  //查询得到库存信息
		  List<Storedetail> list = storedetailDao.getList(sd, null, null);
		  if(list.size()==0){   //说明库存表中没该商品的订单信息
			  throw new ERPException("库存不足");
		  }else{
			  sd=list.get(0);
			  sd.setNum(sd.getNum()-orderdetail.getNum());  //在原库存的基础上扣减库存数量
			  if(sd.getNum()<0){   //说明库存不足
				  throw new ERPException("库存不足");
			  }
		  }
		  
		//3、修改仓库变动记录表中的数据
		  //创建库存变动表对象
		  Storeoper storeoper = new Storeoper();
		  storeoper.setEmpUuid(empUuid);
		  storeoper.setGoodsUuid(orderdetail.getGoodsUuid());
		  storeoper.setNum(orderdetail.getNum());
		  storeoper.setOperTime(orderdetail.getEndTime());
		  storeoper.setStoreUuid(storeUuid);
		  storeoper.setType(2);  //2代表销售订单表
		
		  storeoperDao.add(storeoper);   
		  
		//4、修改订单表中的ender、endTime、state
		  //查询明细表中state为0的记录数，如果得到的记录数为0，说明订单明细表中的商品均已入库
		  Orderdetail orderdetail0 = new Orderdetail();
		  orderdetail0.setState("0");
		  orderdetail0.setOrders(orderdetail.getOrders());
		  //查询state为0的记录
		  Long count = orderdetailDao.getCount(orderdetail0, null, null);
		  if(count==0){
			  //修改订单主表中的入库人、入库时间、状态值
			  Orders orders = orderdetail.getOrders();
			  orders.setEnder(empUuid);
			  orders.setEndTime(new Date());
			  orders.setState("3");  //3表示已结束
		  }
	}

}
