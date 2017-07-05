package cn.itcast.erp.dao;

import java.util.List;

import cn.itcast.erp.entity.Goods;
/**
 * 商品数据访问层接口 
 * @author liujunling
 * 
 */
public interface IGoodsDao extends IBaseDao<Goods>{
	/**
	 * 获得所以厂家
	 * @param q 
	 * @return
	 */
	public List<String> getProducerList(String q);
	
}
