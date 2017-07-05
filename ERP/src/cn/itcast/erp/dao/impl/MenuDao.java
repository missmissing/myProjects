package cn.itcast.erp.dao.impl;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.entity.Menu;
/**
 * 菜单数据层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class MenuDao extends BaseDao<Menu> implements IMenuDao {

	

	public DetachedCriteria getDetachedCriteria(Menu menu1,Menu menu2,Object param)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(Menu.class);
		
		if(menu1!=null)
		{
			if(menu1.getMenuid()!=null &&  menu1.getMenuid().trim().length()>0)
			{
				dc.add(Restrictions.like("menuid", menu1.getMenuid(), MatchMode.ANYWHERE));			
			}
			if(menu1.getMenuname()!=null &&  menu1.getMenuname().trim().length()>0)
			{
				dc.add(Restrictions.like("menuname", menu1.getMenuname(), MatchMode.ANYWHERE));			
			}
			if(menu1.getIcon()!=null &&  menu1.getIcon().trim().length()>0)
			{
				dc.add(Restrictions.like("icon", menu1.getIcon(), MatchMode.ANYWHERE));			
			}
			if(menu1.getUrl()!=null &&  menu1.getUrl().trim().length()>0)
			{
				dc.add(Restrictions.like("url", menu1.getUrl(), MatchMode.ANYWHERE));			
			}
				
		}
		return dc;
	}

	/**
	 * 利用关联查询，根据登陆用户id查询该用户所拥有的所有角色菜单
	 */
	public List<Menu> getMenus(Long empUuid) {
		String hql="select m from Emp e join e.roles r join r.menus m where e.uuid=?";
		return getHibernateTemplate().find(hql,empUuid);
	}

	

}
