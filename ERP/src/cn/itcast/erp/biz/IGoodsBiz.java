package cn.itcast.erp.biz;

import java.util.List;

import cn.itcast.erp.entity.Goods;
/**
 * 商品业务层接口
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public interface IGoodsBiz extends IBaseBiz<Goods>{

	/**
	 * 获得所以厂家
	 * @param q 
	 * @return
	 */
	public List<String> getProducerList(String q);

}
