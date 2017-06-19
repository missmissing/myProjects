package com.huatu.ou.ascpectJ.operLog;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
/**
 * 自定义注解用于记录日志时获取方法的详细定义
 * @author caojunhao
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogAnnotation {
	//记录日志的模块名称
	public String operateModelName();
	//记录日志的方法名称
	public String operateFuncName();
}
