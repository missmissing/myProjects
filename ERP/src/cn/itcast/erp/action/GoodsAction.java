package cn.itcast.erp.action;

import cn.itcast.erp.biz.IGoodsBiz;
import cn.itcast.erp.entity.Goods;
import com.alibaba.fastjson.JSON;

import java.util.List;
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
