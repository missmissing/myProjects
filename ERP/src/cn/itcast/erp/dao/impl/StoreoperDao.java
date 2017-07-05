package cn.itcast.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IStoreoperDao;
import cn.itcast.erp.entity.Storeoper;
/**
 * 仓库库存操作记录数据层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class StoreoperDao extends BaseDao<Storeoper> implements IStoreoperDao {

	

	public DetachedCriteria getDetachedCriteria(Storeoper storeoper1,Storeoper storeoper2,Object param)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(Storeoper.class);
		
		if(storeoper1!=null)
		{
				
		}
		return dc;
	}

	

}
