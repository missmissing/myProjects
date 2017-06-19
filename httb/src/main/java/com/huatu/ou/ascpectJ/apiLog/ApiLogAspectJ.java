package com.huatu.ou.ascpectJ.apiLog;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目名：名道恒通智能开发平台V0.5
 * 日期：     2012-8-9-上午10:42:50
 * Copyright (c) 2012名道恒通-版权所有
 */

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.huatu.core.util.CommonUtil;



/**
 * 接口操作记录
 * @author aijunbo
 * @param
 *
 */
@Aspect
@Component
public class ApiLogAspectJ{
	public  Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 拦截Action层所有带有注解的方法
	 * @param joinPoint
	 * @param log
	 * @throws Exception
	 */
	@AfterReturning(value = "execution(* com..*ApiAction.*(..)) && @annotation(log)")
	public void operLog(ApiAnnotation log) throws Exception{
		 //功能名称
		 String funcName = log.operateFuncName();
		//获取request
	     HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	     //记录日志
	     logger.info("ip地址为:"+CommonUtil.getRemortIP(request)+"所做操作:"+funcName);
	}
}