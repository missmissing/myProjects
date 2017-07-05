package cn.itcast.erp.biz;

import java.util.List;

import cn.itcast.erp.entity.Orders;
/**
 * 订单业务层接口
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public interface IOrdersBiz extends IBaseBiz<Orders>{

	/**
	 * 审核订单
	 * @param ordersUuid  订单的uuid
	 * @param empUuid    审核人的uuid
	 */
	public void doCheck(Long ordersUuid, Long empUuid);

	/**
	 * 确认订单
	 * @param ordersUuid  订单的uuid
	 * @param empUuid    审核人的uuid
	 */
	public void doConfirm(Long ordersUuid, Long empUuid);

}
