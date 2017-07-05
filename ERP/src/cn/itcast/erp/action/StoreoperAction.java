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
