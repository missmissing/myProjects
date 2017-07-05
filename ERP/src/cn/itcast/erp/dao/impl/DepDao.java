package cn.itcast.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IDepDao;
import cn.itcast.erp.entity.Dep;
/**
 * 部门数据层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class DepDao extends BaseDao<Dep> implements IDepDao {

	

	public DetachedCriteria getDetachedCriteria(Dep dep1,Dep dep2,Object param)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(Dep.class);
		
		if(dep1!=null)
		{
			if(dep1.getName()!=null &&  dep1.getName().trim().length()>0)
			{
				dc.add(Restrictions.like("name", dep1.getName(), MatchMode.ANYWHERE));			
			}
			if(dep1.getTele()!=null &&  dep1.getTele().trim().length()>0)
			{
				dc.add(Restrictions.like("tele", dep1.getTele(), MatchMode.ANYWHERE));			
			}
		}
		if(dep2!=null)
		{
			//设置不等于自身（即主键）的条件
			if(dep2.getUuid()!=null){
				dc.add(Restrictions.ne("uuid", dep2.getUuid()));
			}
		}
		return dc;
	}

	

}
