package com.text.extractdata.filterques;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.huatu.core.redis.service.IRedisService;



public class Test4
{
	@Autowired
	private IRedisService iRedisService;
	
	public static void main(String[] args)
	{
		Test4 t = new Test4();
	}
	
	public Test4()
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:*.xml");
		//ApplicationContext context = new ClassPathXmlApplicationContext("/*.xml","/*/*.xml");
		//ApplicationContext context = new ClassPathXmlApplicationContext("classpath:*applicationContext.xml");
		//ApplicationContext context = new ClassPathXmlApplicationContext("classpath:*springMVC.xml");
		
		//ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:*springMVC.xml");
		iRedisService = (IRedisService) context.getBean("iRedisService");

		System.out.println("iRedisService:"+iRedisService);
	}

}
