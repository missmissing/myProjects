package cn.itcast.erp.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class SheetDao extends HibernateDaoSupport implements ISheetDao{
	
	/**
	 * 得到销售统计报表
	 * @param date1   //按日期进行查询：条件一
	 * @param date2	  //按日期进行查询：条件二
	 * @return
	 */
	public List getOrderSheet(Date date1,Date date2){
		String hql = "SELECT new cn.itcast.erp.entity.Sheet(gs.name,sum(od.money)) FROM Goodstype gs,Goods g,Orders o,Orderdetail od " +
				" WHERE g.goodstype=gs AND od.orders=o AND od.goodsUuid=g.uuid " +
				" AND o.orderType=2 AND o.state=3 AND o.endTime>=? AND o.endTime<=?  GROUP BY gs.name";
		return getHibernateTemplate().find(hql,date1,date2);
	}
}
