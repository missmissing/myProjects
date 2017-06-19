package com.text.extractdata.filterques;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huatu.tb.category.service.CategoryService;
import com.huatu.tb.category.service.impl.CategoryServiceImpl;


public class Test2
{
	
	
	public static void main(String[] args)
	{
		//ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		//ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:resouce/sys/applicationContext*.xml");
		
		//ApplicationContext context = new ClassPathXmlApplicationContext("/*.xml","/*/*.xml");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:*.xml");
		
		//CategoryService categoryService = (CategoryService) ac.getBean("CategoryService");
		
		
		
		
		Object categoryService2 =  context.getBean("categoryService");
		System.out.println(categoryService2);
	}
}
