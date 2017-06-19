package com.itcast.common.exception;


import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理类
 * @author lx
 *
 */
public class CustomExceptionResolver implements HandlerExceptionResolver  {

	public ModelAndView resolveException(HttpServletRequest request,
										 HttpServletResponse response, Object obj, Exception ex) {
		// TODO Auto-generated method stub
		//处理
		ModelAndView modelAndView = new ModelAndView();
		
		if(ex instanceof CustomException){
			CustomException ce = (CustomException)ex;
			
			modelAndView.addObject("error", ce.getMessage());
		}
		
		modelAndView.setViewName("error/error");
		
		return modelAndView;
	}

}
