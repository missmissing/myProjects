package com.itcast.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itcast.core.bean.Product;
import com.itcast.core.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 商品管理
 * 商品列表
 * 去商品修改页面
 * 修改商品
 * @author lx
 *
 */
@Controller
@RequestMapping(value="/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 查询商品列表
	 * @param name
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list.do")
	public String list(String name,Integer type,Model model){
		Map<Integer,String> map = new HashMap<Integer,String>();
		map.put(1, "台式机");
		map.put(2, "笔记本");
		model.addAttribute("maps",map);
		
		List<Product> productList = productService.selectProductList();
		model.addAttribute("itemsList",productList);
		return "product/list";
	}
	
	//去修改页面
	@RequestMapping(value="/toEdit.do")
	public String toEdit(Integer id,Model model){
		//根据id查询商品信息
		Product productList = productService.selectProductList(id);
		model.addAttribute("item", productList);
		return "product/edit";
	}
	
	//修改商品
	@RequestMapping(value="/edit.do")
	public String updateproduct(Product product){
		
		productService.updateProduct(product);
		
		return "redirect:/product/list.do";		
	}
	
	//删除商品
	@RequestMapping(value="/delete.do")
	public String updateProduct(Integer[] ids){
		productService.deleteLProduct(ids);
		
		return "redirect:/product/list.do";	
	}
}
