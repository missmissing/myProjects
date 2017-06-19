package com.huatu.ou.ascpectJ.operLog;

/**
 * 项目名：名道恒通智能开发平台V0.5
 * 日期：     2012-8-9-上午10:42:50
 * Copyright (c) 2012名道恒通-版权所有
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.ou.user.model.User;
import com.huatu.sys.session.HtSession;
import com.huatu.sys.session.HtSessionManager;



/**
 * 日志操作
 * @author aijunbo
 * @param
 *
 */
@Aspect
@Component
public class OperLogAspectJ{
	public  Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	protected HtSessionManager htSessionManager;
	/**
	 * 拦截Action层所有带有注解的方法
	 * @param joinPoint
	 * @param log
	 * @throws Exception
	 */
	@AfterReturning(value = "execution(* com..*Action.*(..)) && @annotation(log)")
	public void operLog(LogAnnotation log) throws Exception{
		 //模块名称
		 String modleName = log.operateModelName();
		 //功能名称
		 String funcName = log.operateFuncName();
		 //获取request
	     HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	     //获取当前用户
	     HtSession session=htSessionManager.getSession(request,null);
	     if(null == session){
		     logger.info("----操作模块:"+modleName+"----所做操作:"+funcName+"----ip地址为:"+CommonUtil.getRemortIP(request));
	     }
	     else{
	    	 User user =(User)session.getAttribute("correntUser");
		     if(user!=null){
			     //记录日志操作
			     logger.info("操作用户:"+user.getUname()+"----操作模块:"+modleName+"----所做操作:"+funcName+"----ip地址为:"+CommonUtil.getIpAddr(request));
		     }
	     }
	    // User user = (User) request.getSession().getAttribute(Constants.SESSION_CURRENT_USER);	     
	}
}