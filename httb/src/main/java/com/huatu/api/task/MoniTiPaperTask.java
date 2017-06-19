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

import com.huatu.api.task.service.RMoniTiPaperService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.util.Constants;

/**
 * 类名称： MoniTiPaperTask 
 * 类描述： APP端章节缓存定时更新 
 * 创建人： ydp 
 * 创建时间： 2015年6月17日
 * @version 1.0
 */
@Component
public class MoniTiPaperTask {
	public Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private RMoniTiPaperService rmoniTiPaperService;

	public void execute() throws HttbException {
		// 定时任务生效
		if (Constants.ISTIMEDTASK) {
			try {
				log.info("开始扫描要更新的模拟题列表及对应的试题缓存");
				rmoniTiPaperService.refreshMoniTiList();
				log.info("结束扫描要更新的模拟题列表及对应的试题缓存");
			} catch (Exception e) {
				log.error("更新模拟题列表及对应的试题缓存时失败：" + e.getMessage(), e);
			}
		}
	}
}
