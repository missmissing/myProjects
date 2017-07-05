package cn.itcast.erp.biz;

import java.util.List;

import cn.itcast.erp.entity.Orderdetail;
/**
 * 订单明细业务层接口
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public interface IOrderdetailBiz extends IBaseBiz<Orderdetail>{

/**
 * 订单入库
 * @param orderdetailuuid   订单明细表id
 * @param storeUuid     库存表id
 * @param empUuid     员工表id
 */
public void doInStore(Long orderdetailuuid,Long storeUuid,Long empUuid);
	
/**
 * 订单出库
 * @param orderdetailuuid   订单明细表id
 * @param storeUuid     库存表id
 * @param empUuid     员工表id
 */
public void doOutStore(Long orderdetailuuid,Long storeUuid,Long empUuid);


}
