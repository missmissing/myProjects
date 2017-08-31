package cn.itcast.erp.action;

import cn.itcast.erp.biz.IStoreBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Store;
import com.opensymphony.xwork2.ActionContext;
/**
 * 仓库 控制层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class StoreAction extends BaseAction<Store> {
	
	private IStoreBiz storeBiz;

	public void setStoreBiz(IStoreBiz storeBiz) {
		this.storeBiz = storeBiz;
		setBaseBiz(storeBiz);
	}
	
	/**
	 * 显示当前登录人的仓库
	 */
	public void myList(){
		//获得登陆用户的信息
		Emp user = (Emp) ActionContext.getContext().getSession().get("user");
		Store store = new Store();
		store.setEmpUuid(user.getUuid());
		
		setT1(store);   //把store作为条件赋值给T1
		
		list();  //调用父类中的List方法
	}
	
}
