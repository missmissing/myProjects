package cn.itcast.erp.biz.impl;

import java.util.List;

import cn.itcast.erp.biz.IGoodsBiz;
import cn.itcast.erp.dao.IGoodsDao;
import cn.itcast.erp.entity.Goods;
/**
 * 商品 业务层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class GoodsBiz extends BaseBiz<Goods> implements IGoodsBiz {

	private IGoodsDao goodsDao;//数据访问层
	
	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
		setBaseDao(goodsDao);
	}
	
	/**
	 * 获得所以厂家
	 * @return
	 */
	public List<String> getProducerList(String q){
		return goodsDao.getProducerList(q);
	}

}
