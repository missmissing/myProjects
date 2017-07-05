package cn.itcast.erp.dao.impl;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.entity.Emp;
/**
 * 员工数据层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class EmpDao extends BaseDao<Emp> implements IEmpDao {

	

	public DetachedCriteria getDetachedCriteria(Emp emp1,Emp emp2,Object param)
	{
		DetachedCriteria dc=DetachedCriteria.forClass(Emp.class);
		
		if(emp1!=null)
		{
			if(emp1.getUserName()!=null &&  emp1.getUserName().trim().length()>0)
			{
				dc.add(Restrictions.like("userName", emp1.getUserName(), MatchMode.ANYWHERE));			
			}
			if(emp1.getName()!=null &&  emp1.getName().trim().length()>0)
			{
				dc.add(Restrictions.like("name", emp1.getName(), MatchMode.ANYWHERE));			
			}
			if(emp1.getEmail()!=null &&  emp1.getEmail().trim().length()>0)
			{
				dc.add(Restrictions.like("email", emp1.getEmail(), MatchMode.ANYWHERE));			
			}
			if(emp1.getTele()!=null &&  emp1.getTele().trim().length()>0)
			{
				dc.add(Restrictions.like("tele", emp1.getTele(), MatchMode.ANYWHERE));			
			}
			if(emp1.getAddress()!=null &&  emp1.getAddress().trim().length()>0)
			{
				dc.add(Restrictions.like("address", emp1.getAddress(), MatchMode.ANYWHERE));			
			}
			if(emp1.getGender()!=null && emp1.getGender()!=2){
				dc.add(Restrictions.eq("gender",emp1.getGender()));
			}
			if(emp1.getDep()!=null && emp1.getDep().getUuid()!=null){
				dc.add(Restrictions.eq("dep", emp1.getDep()));
			}
			if(emp1.getBirthday()!=null){
				dc.add(Restrictions.ge("birthday", emp1.getBirthday()));
			}
		}
		
		if(emp2!=null){
			if(emp2.getBirthday()!=null){
				dc.add(Restrictions.le("birthday",emp2.getBirthday()));
			}
		}
		return dc;
	}

	/**
	 * 登陆验证
	 */
	public Emp findEmpByLoginNamePassword(String loginName, String password) {
		List<Emp> list = getHibernateTemplate().find("from Emp where userName=? and pwd=?",loginName,password);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	

}
