package cn.itcast.erp.biz.impl;

import java.util.List;

import cn.itcast.erp.biz.IGoodstypeBiz;
import cn.itcast.erp.dao.IGoodstypeDao;
import cn.itcast.erp.entity.Goodstype;
/**
 * 商品类型 业务层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class GoodstypeBiz extends BaseBiz<Goodstype> implements IGoodstypeBiz {

	private IGoodstypeDao goodstypeDao;//数据访问层
	
	public void setGoodstypeDao(IGoodstypeDao goodstypeDao) {
		this.goodstypeDao = goodstypeDao;
		setBaseDao(goodstypeDao);
	}


}
