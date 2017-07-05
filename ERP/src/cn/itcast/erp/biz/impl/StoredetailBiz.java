package cn.itcast.erp.biz.impl;

import java.util.List;

import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.entity.Storedetail;
/**
 * 仓库库存 业务层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class StoredetailBiz extends BaseBiz<Storedetail> implements IStoredetailBiz {

	private IStoredetailDao storedetailDao;//数据访问层
	
	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
		setBaseDao(storedetailDao);
	}


}
