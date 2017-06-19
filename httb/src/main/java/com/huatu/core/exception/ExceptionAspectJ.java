package com.huatu.core.exception;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.ou.ascpectJ.operLog.LogAnnotation;
import com.huatu.ou.user.model.User;
import com.huatu.sys.session.HtSession;
import com.huatu.sys.session.HtSessionManager;


/**
 *
 * 类名称：				ExceptionAspectJ
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月1日 下午8:07:14
 * @version 			0.0.1
 */
@Aspect
@Component
public class ExceptionAspectJ {
	public Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	protected HtSessionManager htSessionManager;

    //匹配com下所有以Action结尾的类的所有方法
    @Pointcut(value = "execution(* com..*Action.*(..)) ")
    private void ActionPointCut() {}


    /**
     *
     * doAfterThrowing				(记录异常信息)
     * 								(方法体执行完后调用)
     * @param 		ex				异常信息
     * @throws 		HttbException
     */
    @AfterThrowing(value = " ActionPointCut()", throwing = "ex")
    public void doAfterThrowing(JoinPoint jp, Throwable ex){
    	try {
    		//获取切点方法
        	MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        	Method method = methodSignature.getMethod();
        	//遍历是否有日志注解
        	if(method.getAnnotations() != null){
    	    	for (int i = 0; i < method.getAnnotations().length; i++) {
    				if(method.getAnnotations()[i] instanceof LogAnnotation){
    					//操作日志记录
    					operLog((LogAnnotation)method.getAnnotations()[i]);
    				}
    			}
        	}

        	//如果抛出的异常属于HttbException
        	if(ex instanceof HttbException){
        	   //将异常信息记录到日志中
        		logger.error(((HttbException)ex).toString()+"\r\n",ex);
               	//控制台输出异常
        	  // ((HttbException)ex).printStackTrace();
           }else{
        	   logger.error(ex.toString()+"\r\n",ex);
           }

		} catch (Exception e) {
			logger.error(ex.toString()+"\r\n",ex);
			//e.printStackTrace();
		}

    }

    /**
     *
     * operLog						(操作日志记录)
     * 								(程序发生异常时，记录什么操作引发的异常)
     * @param 		log				日志注解对象
     * @throws 		Exception
     */
    private void operLog(LogAnnotation log) throws Exception{
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
	}
}