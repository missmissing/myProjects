package cn.itcast.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.sun.org.apache.regexp.internal.recompile;

import cn.itcast.erp.dao.IStoreDao;
import cn.itcast.erp.entity.Store;
/**
 * 仓库数据层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class StoreDao extends BaseDao<Store> implements IStoreDao {

	

	public DetachedCriteria getDetachedCriteria(Store store1,Store store2,Object param)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(Store.class);
		
		if(store1!=null)
		{
			if(store1.getName()!=null &&  store1.getName().trim().length()>0)
			{
				dc.add(Restrictions.like("name", store1.getName(), MatchMode.ANYWHERE));			
			}
			if(store1.getAddress()!=null &&  store1.getAddress().trim().length()>0)
			{
				dc.add(Restrictions.like("address", store1.getAddress(), MatchMode.ANYWHERE));			
			}
			if(store1.getEmpUuid()!=null)
			{
				dc.add(Restrictions.eq("empUuid", store1.getEmpUuid()));
			}
				
		}
		return dc;
	}

	

}
