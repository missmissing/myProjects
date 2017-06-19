package com.itcast.core.service;

import com.itcast.core.bean.Product;

import java.util.List;


public interface ProductService {

	
	
	//查询商品结果集
	public List<Product> selectProductList();
	
	//根据Id查询商品信息
	public Product selectProductList(Integer id);
	
	//修改商品
	public void updateProduct(Product product);
	
	//删除商品
	public void deleteLProduct(Integer[] ids);
}
