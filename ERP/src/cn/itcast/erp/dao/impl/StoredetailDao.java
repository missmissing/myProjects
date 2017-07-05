package cn.itcast.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.entity.Storedetail;
/**
 * 仓库库存数据层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class StoredetailDao extends BaseDao<Storedetail> implements IStoredetailDao {


	public DetachedCriteria getDetachedCriteria(Storedetail storedetail1,Storedetail storedetail2,Object param)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(Storedetail.class);
		
		if(storedetail1!=null)
		{
			if(storedetail1.getGoods()!=null && storedetail1.getGoods().getUuid()!=null ){
				dc.add(Restrictions.eq("goods", storedetail1.getGoods()));
			}
			if(storedetail1.getStore()!=null && storedetail1.getStore().getUuid()!=null){
				dc.add(Restrictions.eq("store",storedetail1.getStore()));
			}
		}
		
		return dc;
	}

	

}
