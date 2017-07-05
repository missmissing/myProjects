package cn.itcast.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Role;
/**
 * 角色数据层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class RoleDao extends BaseDao<Role> implements IRoleDao {

	

	public DetachedCriteria getDetachedCriteria(Role role1,Role role2,Object param)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(Role.class);
		
		if(role1!=null)
		{
			if(role1.getName()!=null &&  role1.getName().trim().length()>0)
			{
				dc.add(Restrictions.like("name", role1.getName(), MatchMode.ANYWHERE));			
			}
				
		}
		return dc;
	}

	

}
