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


import cn.itcast.erp.biz.IDepBiz;
import cn.itcast.erp.entity.Dep;
/**
 * 部门 控制层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class DepAction extends BaseAction<Dep> {
	
	private IDepBiz depBiz;

	public void setDepBiz(IDepBiz depBiz) {
		this.depBiz = depBiz;
		setBaseBiz(depBiz);
	}
	
	
	
}
