package com.text.extractdata.filterques;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.huatu.core.redis.service.IRedisService;



public class Test3
{
	@Autowired
	private IRedisService redisService;
	
	public static void main(String[] args)
	{
		Test3 t = new Test3();
	}
	
	public Test3()
	{
		@SuppressWarnings("resource")

		ApplicationContext context = new FileSystemXmlApplicationContext("/*.xml","/*/*.xml");
		redisService = (IRedisService) context.getBean("redisService");

		System.out.println("redisService:"+redisService);
	}

}
