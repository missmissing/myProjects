package cn.itcast.erp.dao;

import java.util.List;

import cn.itcast.erp.entity.Orders;
/**
 * 订单数据访问层接口 
 * @author liujunling
 * 
 */
public interface IOrdersDao extends IBaseDao<Orders>{

	/**
	 * 获得最大订单号
	 * @param date
	 */
	public String getMaxOrderNum(String date);
	
	
}
