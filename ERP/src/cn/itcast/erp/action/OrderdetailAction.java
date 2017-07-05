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


import cn.itcast.erp.biz.IOrderdetailBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.exception.ERPException;
/**
 * 订单明细 控制层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class OrderdetailAction extends BaseAction<Orderdetail> {
	
	private IOrderdetailBiz orderdetailBiz;

	public void setOrderdetailBiz(IOrderdetailBiz orderdetailBiz) {
		this.orderdetailBiz = orderdetailBiz;
		setBaseBiz(orderdetailBiz);
	}
	
	private Long sotreUuid;  //仓库id
	
	public Long getSotreUuid() {
		return sotreUuid;
	}
	public void setSotreUuid(Long sotreUuid) {
		this.sotreUuid = sotreUuid;
	}



	/**
	 * 订单入库
	 * 
	 */
	public void doInStore(){
		//从Session对象中获得用户登录信息
		Emp user = (Emp) ActionContext.getContext().getSession().get("user");
		if(user==null){
			write("请先登陆");
			return ;
		}
		
		try{
			orderdetailBiz.doInStore(getId(), sotreUuid, user.getUuid());
			write("ok");
		}catch(Exception e){
			e.printStackTrace();
			write("发生异常");
		}
	}
	
	/**
	 * 订单出库
	 * 
	 */
	public void doOutStore(){
		//从Session对象中获得用户登录信息
		Emp user = (Emp) ActionContext.getContext().getSession().get("user");
		if(user==null){
			write("请先登陆");
			return ;
		}
		
		try{
			orderdetailBiz.doOutStore(getId(), sotreUuid, user.getUuid());
			write("ok");
		}catch(ERPException e){
			write(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			write("发生异常");
		}
	}
	
	
}
