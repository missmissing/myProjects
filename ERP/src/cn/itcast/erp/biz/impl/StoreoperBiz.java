package cn.itcast.erp.biz.impl;

import java.util.List;

import cn.itcast.erp.biz.IStoreoperBiz;
import cn.itcast.erp.dao.IStoreoperDao;
import cn.itcast.erp.entity.Storeoper;
/**
 * 仓库库存操作记录 业务层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class StoreoperBiz extends BaseBiz<Storeoper> implements IStoreoperBiz {

	private IStoreoperDao storeoperDao;//数据访问层
	
	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
		setBaseDao(storeoperDao);
	}


}
