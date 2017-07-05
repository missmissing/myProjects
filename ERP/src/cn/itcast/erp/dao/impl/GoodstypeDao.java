package cn.itcast.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IGoodstypeDao;
import cn.itcast.erp.entity.Goodstype;
/**
 * 商品类型数据层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class GoodstypeDao extends BaseDao<Goodstype> implements IGoodstypeDao {

	

	public DetachedCriteria getDetachedCriteria(Goodstype goodstype1,Goodstype goodstype2,Object param)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(Goodstype.class);
		
		if(goodstype1!=null)
		{
			if(goodstype1.getName()!=null &&  goodstype1.getName().trim().length()>0)
			{
				dc.add(Restrictions.like("name", goodstype1.getName(), MatchMode.ANYWHERE));			
			}
				
		}
		return dc;
	}

	

}
