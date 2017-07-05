package cn.itcast.erp.dao.impl;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IGoodsDao;
import cn.itcast.erp.entity.Goods;
/**
 * 商品数据层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class GoodsDao extends BaseDao<Goods> implements IGoodsDao {

	

	public DetachedCriteria getDetachedCriteria(Goods goods1,Goods goods2,Object param)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(Goods.class);
		
		if(goods1!=null)
		{
			if(goods1.getName()!=null &&  goods1.getName().trim().length()>0)
			{
				dc.add(Restrictions.like("name", goods1.getName(), MatchMode.ANYWHERE));			
			}
			if(goods1.getOrigin()!=null &&  goods1.getOrigin().trim().length()>0)
			{
				dc.add(Restrictions.like("origin", goods1.getOrigin(), MatchMode.ANYWHERE));			
			}
			if(goods1.getProducer()!=null &&  goods1.getProducer().trim().length()>0)
			{
				dc.add(Restrictions.like("producer", goods1.getProducer(), MatchMode.ANYWHERE));			
			}
			if(goods1.getUnit()!=null &&  goods1.getUnit().trim().length()>0)
			{
				dc.add(Restrictions.like("unit", goods1.getUnit(), MatchMode.ANYWHERE));			
			}
			if(goods1.getInPrice()!=null){
				dc.add(Restrictions.ge("inPrice", goods1.getInPrice()));
			}
		}
		
		if(goods2!=null){
			if(goods2.getOutPrice()!=null){
				dc.add(Restrictions.le("outPrice", goods2.getOutPrice()));
			}
		}
		return dc;
	}
	
	/**
	 * 获得所以厂家
	 * @return
	 */
	public List<String> getProducerList(String q){
		List<String> list = getHibernateTemplate()
				.find("select distinct new Goods(producer) from Goods where producer like ?","%"+q+"%");
		return list;
	}
	

}
