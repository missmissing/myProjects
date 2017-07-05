package cn.itcast.erp.dao.impl;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.entity.Orders;
/**
 * 订单数据层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class OrdersDao extends BaseDao<Orders> implements IOrdersDao {

	

	public DetachedCriteria getDetachedCriteria(Orders orders1,Orders orders2,Object param)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(Orders.class);
		
		if(orders1!=null)
		{
			if(orders1.getOrderNum()!=null &&  orders1.getOrderNum().trim().length()>0)
			{
				dc.add(Restrictions.like("orderNum", orders1.getOrderNum(), MatchMode.ANYWHERE));			
			}
			if(orders1.getState()!=null &&  orders1.getState().trim().length()>0)
			{
				dc.add(Restrictions.like("state", orders1.getState(), MatchMode.ANYWHERE));			
			}
			if(orders1.getOrderType()!=null){
				dc.add(Restrictions.eq("orderType", orders1.getOrderType()));
			}
				
		}
		
		if(param!=null){
			List<Long> ids =(List<Long>)param;   //得到参数
			if(ids.size()>0){
				dc.add(Restrictions.in("uuid", ids));
			}else{
				//如果id为空，设置一个不存在的条件(注意：如果不加此条件，会查出所有的结果)
				dc.add(Restrictions.eq("uuid", 0L));
			}
			
		}
		return dc;
	}

	/**
	 * 获得最大订单编号
	 */
	public String getMaxOrderNum(String date) {
		List<String> list = getHibernateTemplate()
				.find("select max(orderNum) from Orders where orderNum like ?",date+"%");
		return list.get(0);
	}

	

}
