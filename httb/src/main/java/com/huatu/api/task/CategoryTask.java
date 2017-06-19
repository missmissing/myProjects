/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.task  
 * 文件名：				CategoryTask.java    
 * 日期：				2015年6月15日-下午4:52:43  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huatu.api.task.service.RCategoryService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.util.Constants;

/**
 * 类名称： CategoryTask 
 * 类描述： APP端章节缓存定时更新 
 * 创建人： LiXin 
 * 创建时间： 2015年6月15日 下午4:52:43
 * @version 1.0
 */
@Component
public class CategoryTask {
	public Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private RCategoryService rcategoryService;
	
	public void execute() throws HttbException {
		// 定时任务生效
		if (Constants.ISTIMEDTASK) {
			try {
				log.info("开始扫描要更新知识分类缓存");
				rcategoryService.refreshCategroy();
				log.info("结束扫描需要更新知识分类缓存");
			} catch (Exception e) {
				log.error("更新知识分类缓存失败：" + e.getMessage(), e);
			}
		}
	}
}
