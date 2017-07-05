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


import cn.itcast.erp.biz.IGoodsBiz;
import cn.itcast.erp.entity.Goods;
/**
 * 商品 控制层
 * @author liujunling
 * 本代码通过《传智代码神器》生成
 */
public class GoodsAction extends BaseAction<Goods> {
	
	private IGoodsBiz goodsBiz;

	public void setGoodsBiz(IGoodsBiz goodsBiz) {
		this.goodsBiz = goodsBiz;
		setBaseBiz(goodsBiz);
	}
	

	/**
	 * 获得所有厂家
	 * @return
	 */
	public void getProducerList(){
		List<String> list = goodsBiz.getProducerList(getQ());
		String jsonString = JSON.toJSONString(list);
		write(jsonString);
		
	}
	
	
}
