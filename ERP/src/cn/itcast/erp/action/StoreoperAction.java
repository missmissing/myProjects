package cn.itcast.erp.action;

import cn.itcast.erp.biz.IStoreoperBiz;
import cn.itcast.erp.entity.Storeoper;
/**
 * 仓库库存操作记录 控制层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class StoreoperAction extends BaseAction<Storeoper> {
	
	private IStoreoperBiz storeoperBiz;

	public void setStoreoperBiz(IStoreoperBiz storeoperBiz) {
		this.storeoperBiz = storeoperBiz;
		setBaseBiz(storeoperBiz);
	}
	
	
	
}
