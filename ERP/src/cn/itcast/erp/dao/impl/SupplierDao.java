package cn.itcast.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Supplier;
/**
 * 供应商数据层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class SupplierDao extends BaseDao<Supplier> implements ISupplierDao {

	

	public DetachedCriteria getDetachedCriteria(Supplier supplier1,Supplier supplier2,Object param)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(Supplier.class);
		
		if(supplier1!=null)
		{
			if(supplier1.getName()!=null &&  supplier1.getName().trim().length()>0)
			{
				dc.add(Restrictions.like("name", supplier1.getName(), MatchMode.ANYWHERE));			
			}
			if(supplier1.getAddress()!=null &&  supplier1.getAddress().trim().length()>0)
			{
				dc.add(Restrictions.like("address", supplier1.getAddress(), MatchMode.ANYWHERE));			
			}
			if(supplier1.getContact()!=null &&  supplier1.getContact().trim().length()>0)
			{
				dc.add(Restrictions.like("contact", supplier1.getContact(), MatchMode.ANYWHERE));			
			}
			if(supplier1.getTele()!=null &&  supplier1.getTele().trim().length()>0)
			{
				dc.add(Restrictions.like("tele", supplier1.getTele(), MatchMode.ANYWHERE));			
			}
			if(supplier1.getType()!=null)
			{
				dc.add(Restrictions.eq("type", supplier1.getType()));
			}
		}
		return dc;
	}

	

}
