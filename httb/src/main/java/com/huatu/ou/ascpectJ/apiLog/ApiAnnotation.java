package com.huatu.ou.ascpectJ.apiLog;

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
public @interface ApiAnnotation {
	//描述所做操作
	public String operateFuncName();
}
