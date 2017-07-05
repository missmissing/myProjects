package cn.itcast.erp.dao;
import java.util.List;
public interface IBaseDao<T> {
	/**
	 * 返回列表(按条件查询)
	 */
	public List<T> getList(T t1,T t2,Object param);
	
	/**
	 * 返回列表（分页查询）
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
	 * 增加
	 * @param t
	 */
	public void add(T t);
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(Long id);
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public T get(Long id);
	
	/**
	 * 查询实体(方法重载，因为在menu.java中的menuid为String类型，不是Long类型)
	 * @param id
	 * @return
	 */
	public T get(String id);
	
	/**
	 * 修改
	 * @param t
	 */
	public void update(T t);

}
