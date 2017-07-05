package cn.itcast.erp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mysql.jdbc.Util;
import com.opensymphony.xwork2.ModelDriven;

import sun.org.mozilla.javascript.internal.json.JsonParser;


import cn.itcast.erp.biz.ISupplierBiz;
import cn.itcast.erp.entity.Supplier;
/**
 * 供应商 控制层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class SupplierAction extends BaseAction<Supplier> {
	
	private ISupplierBiz supplierBiz;

	public void setSupplierBiz(ISupplierBiz supplierBiz) {
		this.supplierBiz = supplierBiz;
		setBaseBiz(supplierBiz);
	}
	
	//不建立关联关系在订单表中显示供应商名称
	public void updatejs(){
		StringBuffer sb = new StringBuffer("var supplier = new Array();");
		//得到的所有的供应商
		List<Supplier> list = supplierBiz.getList(null, null, null);
		for(Supplier s:list){
			sb.append("supplier["+s.getUuid()+"]='"+s.getName()+"';");
		}
		String fileName = ServletActionContext.getServletContext().getRealPath("/js/data/supplier.js");
		//调用方法把得到的供应商写入到指定的文件中
		util.FileUtil.write(fileName, sb.toString());
	}
	
	//重写父类中的增删改方法，并在执行后，更新js文件中的供应商信息
	
	public void add(){
		try{
			supplierBiz.add(getT());
			updatejs();   //更新脚本文件
			write("ok");
		} catch (Exception e) {			
			write("发生异常");
			e.printStackTrace();
		}
	}
	public void update(){
		try{
			supplierBiz.update(getT());
			updatejs();   //更新脚本文件
			write("ok");
		} catch (Exception e) {			
			write("发生异常");
			e.printStackTrace();
		}
	}public void delete(){
		try{
			supplierBiz.delete(getId());
		updatejs();   //更新脚本文件
		write("ok");
	} catch (Exception e) {			
		write("发生异常");
		e.printStackTrace();
	}
}
	
	
}
