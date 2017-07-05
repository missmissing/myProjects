package cn.itcast.erp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ModelDriven;

import sun.org.mozilla.javascript.internal.json.JsonParser;


import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Tree;
/**
 * 员工 控制层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class EmpAction extends BaseAction<Emp> {
	
	private IEmpBiz empBiz;

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
		setBaseBiz(empBiz);
	}
	
	/**
	 * ====不建立关联在表格中显示操作员姓名====
	 * 更新显示员工信息的脚本文件（在orders.html中要用到显示员工信息的emp.js文件）
	 */
	/**
	 * 
	 */
	public void updatejs(){
		//创建stringbuffer，用来拼接修改的字符串。（也可以定义为string类型，只不过StringBuffer效率比较高）
		StringBuffer sb = new StringBuffer("var emp = new Array();");
		//查询全部的员工数据
		List<Emp> list = empBiz.getList(null,null,null);
		for(Emp e:list){
			sb.append("emp["+e.getUuid()+"]='"+e.getName()+"';");
		}
		//获得js文件的目录和文件名
		String fileName = ServletActionContext.getServletContext().getRealPath("/js/data/emp.js");
		//调用封装到util包中的方法，把sb数据写入到js文件中
		util.FileUtil.write(fileName, sb.toString());
	}
	
	//重写、增、删改方法，在执行后更新脚本文件
	public void add(){
		try{
			empBiz.add(getT());
			updatejs();   //更新脚本文件
			write("ok");
		} catch (Exception e) {			
			write("发生异常");
			e.printStackTrace();
		}
	}
	public void update(){
		try{
			empBiz.update(getT());
		    updatejs();   //更新脚本文件
		    write("ok");
		   } catch (Exception e) {			
			write("发生异常");
			e.printStackTrace();
		   }
	}
	public void delete(){
		try {
			empBiz.delete(getId());
			updatejs();   //更新脚本文件
			write("ok");
		} catch (Exception e) {			
			write("发生异常");
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到用户角色
	 */
	public void getEmpRole(){
		try {
			List<Tree> empRole = empBiz.getEmpRole(getId());
			String jsonString = JSON.toJSONString(empRole);
			write(jsonString);
		} catch (Exception e) {			
			write("发生异常");
			e.printStackTrace();
		}
	}
	
	
	private String roleIds; //属性驱动，封装角色id
	
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public void updateEmpRole(){
		try {
			empBiz.updateEmpRole(getId(),roleIds);
			write("ok");
		} catch (Exception e) {			
			write("发生异常");
			e.printStackTrace();
		}
	}
}
