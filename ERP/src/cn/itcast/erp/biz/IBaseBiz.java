package cn.itcast.erp.biz;

import java.util.List;



public interface IBaseBiz<T> {
		
	/**
	 * 返回部门列表(按条件查询)
	 */
	public List<T> getList(T t1,T t2,Object param);
	
	/**
	 * 返回部门列表（分页查询）
	 * @param t1
	 * @param firstResult  开始记录索引
	 * @param maxResults   每页记录数
	 * @return
	 */
	public List<T> getListByPage(T t1,T t2,Object param,int firstResult,int maxResults);

	
	/**
	 * 返回记录个数
	 */
	public long getCount(T t1,T t2,Object param);
	
	/**
	 * 增加部门
	 * @param t
	 */
	public void add(T t);
		
	/**
	 * 删除部门
	 * @param id
	 */
	public void delete(Long id);
	
	/**
	 * 查询实体
	 * @param id
	 * @return
	 */
	public T get(Long id);	
	
	/**
	 * 查询实体（方法重载）
	 * @param id
	 * @return
	 */
	public T get(String id);	
	
	/**
	 * 修改部门
	 * @param t
	 */
	public void update(T t);
}
