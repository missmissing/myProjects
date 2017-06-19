package com.itcast.core.service;

import java.util.List;

import com.itcast.core.bean.Product;
import com.itcast.core.dao.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 商品测试事务
 * @author lx
 *
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	
	@Autowired
	private ProductMapper productMapper;
	
	//查询商品结果集
	public List<Product> selectProductList(){
		return productMapper.selectByExample(null);
	}
	
	//根据Id查询商品信息
	public Product selectProductList(Integer id){
		return  productMapper.selectByPrimaryKey(id);
	}
	
	//修改商品
	public void updateProduct(Product product){
		 productMapper.updateByPrimaryKeySelective(product);
	}
	
	//删除商品
	public void deleteLProduct(Integer[] ids){
		for(Integer id:ids){
			productMapper.deleteByPrimaryKey(id);
		}
	}
}
