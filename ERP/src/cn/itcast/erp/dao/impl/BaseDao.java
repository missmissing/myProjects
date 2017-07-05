package cn.itcast.erp.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



public abstract class BaseDao<T> extends HibernateDaoSupport {

	private Class<T> entityClass;
	
	//构造方法
	public BaseDao()
	{		
		Type type=getClass().getGenericSuperclass();//得到子类的class的父一级Class
		ParameterizedType ptype=(ParameterizedType)type;//可以得到泛型类型的TYPE
		Type[] actualTypeArguments = ptype.getActualTypeArguments();//得到所有泛型的类型
		entityClass =(Class<T>) actualTypeArguments[0];	//取第一个泛型的类型	
	}
	
	/**
	 * 返回部门列表(按条件查询)
	 */
	public List<T> getList(T t1,T t2,Object param) {
		DetachedCriteria dc = getDetachedCriteria(t1,t2,param);
		return getHibernateTemplate().findByCriteria(dc);		
	}
	
	/**
	 * 返回部门列表（分页查询）
	 * @param t1
	 * @param firstResult  开始记录索引
	 * @param maxResults   每页记录数
	 * @return
	 */
	public List<T> getListByPage(T t1,T t2,Object param,int firstResult,int maxResults) {
		// 离线动态查询
		DetachedCriteria dc=getDetachedCriteria(t1,t2,param);			
		return getHibernateTemplate().findByCriteria(dc, firstResult, maxResults);
	}

	/**
	 * 返回记录个数
	 */
	public long getCount(T t1,T t2,Object param) {
		
		DetachedCriteria dc = getDetachedCriteria(t1,t2,param);	
		
		dc.setProjection(Projections.rowCount());//投影查询，相当于  select count(*) 
		List<Long> list= getHibernateTemplate().findByCriteria(dc);	
		return list.get(0);
	}
	
	/**
	 * 构建查询条件
	 * @param t1
	 * @return
	 */
	protected abstract DetachedCriteria getDetachedCriteria(T t1,T t2,Object param);
	
	/**
	 * 增加部门
	 */
	public void add(T t) {
		
		getHibernateTemplate().save(t);
	}

	/**
	 * 删除部门
	 */
	public void delete(Long id) {
		
		getHibernateTemplate().delete(getHibernateTemplate().get(entityClass, id));
	}
	
	/**
	 * 查询实体
	 * @param id
	 * @return
	 */
	public T get(Long id)
	{
		return (T) getHibernateTemplate().get(entityClass, id);		
	}
	
	/**
	 * 查询实体(方法重载，因为在menu.java中的menuid为String类型，不是Long类型)
	 * @param id
	 * @return
	 */
	public T get(String id)
	{
		return (T) getHibernateTemplate().get(entityClass, id);		
	}

	/**
	 * 修改部门
	 */
	public void update(T t) {
		getHibernateTemplate().update(t);
	}
	
	
}
