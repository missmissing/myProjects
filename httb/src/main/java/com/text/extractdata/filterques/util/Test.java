package com.text.extractdata.filterques.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Test
{
	private GenericXmlApplicationContext context;
	
	public void init()
	{
		context = new GenericXmlApplicationContext();
	    context.setValidating(false);
	    context.load("classpath*:resouce/sys/applicationContext*.xml");
	    context.refresh();
	}
	
	public GenericXmlApplicationContext getContext()
	{
		if(null == context || !context.isActive())
		{
			init();
		}
		return context;
	}
	
	
}
