package cn.itcast.erp.biz.impl;

import java.util.List;

import cn.itcast.erp.biz.IStoreBiz;
import cn.itcast.erp.dao.IStoreDao;
import cn.itcast.erp.entity.Store;
/**
 * 仓库 业务层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class StoreBiz extends BaseBiz<Store> implements IStoreBiz {

	private IStoreDao storeDao;//数据访问层
	
	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
		setBaseDao(storeDao);
	}


}
