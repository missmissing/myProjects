package cn.itcast.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IOrderdetailDao;
import cn.itcast.erp.entity.Orderdetail;
/**
 * 订单明细数据层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class OrderdetailDao extends BaseDao<Orderdetail> implements IOrderdetailDao {

	

	public DetachedCriteria getDetachedCriteria(Orderdetail orderdetail1,Orderdetail orderdetail2,Object param)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(Orderdetail.class);
		
		if(orderdetail1!=null)
		{
			if(orderdetail1.getGoodsName()!=null &&  orderdetail1.getGoodsName().trim().length()>0)
			{
				dc.add(Restrictions.like("goodsName", orderdetail1.getGoodsName(), MatchMode.ANYWHERE));			
			}
			if(orderdetail1.getState()!=null &&  orderdetail1.getState().trim().length()>0)
			{
				dc.add(Restrictions.like("state", orderdetail1.getState(), MatchMode.ANYWHERE));			
			}
			if(orderdetail1.getOrders()!=null && orderdetail1.getOrders().getUuid()!=null)
			{
				dc.add(Restrictions.eq("orders", orderdetail1.getOrders()));
			}
				
		}
		return dc;
	}

	

}
