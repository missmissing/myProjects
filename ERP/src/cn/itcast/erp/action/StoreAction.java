package cn.itcast.erp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import sun.org.mozilla.javascript.internal.json.JsonParser;


import cn.itcast.erp.biz.IStoreBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Store;
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
