package com.text.extractdata.filterques;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.huatu.core.redis.service.IRedisService;



public class Test5
{
	@Autowired
	private IRedisService iRedisService;
	/*@Autowired
	private CategoryService categoryService;*/
	
	public static void main(String[] args)
	{
		Test5 t = new Test5();
	}
	
	public Test5()
	{
		 GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		 context.setValidating(false);
		 context.load("classpath*:*.xml"); 
		 context.refresh(); 
		 IRedisService iRedisService = context.getBean(IRedisService.class);
		 
		
		//ApplicationContext context = new ClassPathXmlApplicationContext("classpath:*.xml");
		//ApplicationContext context = new ClassPathXmlApplicationContext("/*.xml","/*/*.xml");
		//ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

		//ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:*springMVC.xml");
		//iRedisService = (IRedisService) context.getBean("iRedisService");

		System.out.println("iRedisService:"+iRedisService);
	}

}
