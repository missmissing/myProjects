/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.task  
 * 文件名：				QuesToCateTask.java    
 * 日期：				2015年6月17日-下午8:50:24  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huatu.api.task.service.RQuesToCateService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.util.Constants;

/**
 * 类名称： QuesToCateTask 
 * 类描述： 章节（知识点） 中试题缓存 任务 
 * 创建人： LiXin 
 * 创建时间： 2015年6月17日
 * 下午8:50:24
 * @version 1.0
 */
@Component
public class QuesToCateTask {
	public Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private RQuesToCateService rquesToCateService;

	public void execute() throws HttbException {
		// 定时任务生效
		if (Constants.ISTIMEDTASK) {
			try {
				log.info("开始扫描章节（知识点） 中试题缓存");
				rquesToCateService.refreshQuesToCate();
				log.info("结束扫章节（知识点） 中试题缓存");
			} catch (Exception e) {
				log.error("（知识点） 中试题更新缓存 异常：" + e.getMessage(), e);
			}
		}
	}
}
