package cn.itcast.erp.biz.impl;

import java.util.List;

import cn.itcast.erp.dao.IBaseDao;


public class BaseBiz<T> {

	private IBaseDao<T> baseDao;//数据访问层	
	
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}
	

	/**
	 * 返回部门列表(按条件查询)
	 */
	public List<T> getList(T t1,T t2,Object param) {
		// TODO Auto-generated method stub
		return baseDao.getList(t1,t2,param);
	}


	public List<T> getListByPage(T t1,T t2,Object param, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return baseDao.getListByPage(t1,t2,param,firstResult,maxResults);
	}


	public long getCount(T t1,T t2,Object param) {
		// TODO Auto-generated method stub
		return baseDao.getCount(t1,t2,param);
	}


	public void add(T t) {		
		baseDao.add(t);		
	}

	
	public void delete(Long id) {
		baseDao.delete(id);		
	}


	public T get(Long id) {		
		return baseDao.get(id);
	}
	
	/**
	 * 查询实体（方法重载）
	 * @param id
	 * @return
	 */
	public T get(String id) {		
		return baseDao.get(id);
	}


	public void update(T t) {		
		baseDao.update(t);		
	}
	
}
