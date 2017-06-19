package com.huatu.api.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.service.HtquesanalysisService;
import com.huatu.analysis.service.HtqueshisService;
import com.huatu.analysis.util.AnaCommonUtil;
import com.huatu.api.version.model.TaskVersion;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;

@Component
public class QuestionAnalysisTask {

	public Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private TaskVersionService taskVersionService;

	@Autowired
	private HtqueshisService htqueshisService;

	@Autowired
	private HtquesanalysisService htquesanalysisService;

	public void execute() throws HttbException {
		String version = "";
		//获取当前系统时间戳
		String systemdate = AnaCommonUtil.getRecordDate();
		if (Constants.ISTIMEDTASK) {
			try {
				log.info("开始比较试题分析的版本号");
				TaskVersion taskVersion = taskVersionService.getVersion(Constants.ANALYSIS_VERSION_QUESTION);
				if(taskVersion.getTvalue() != null){
					version = taskVersion.getTvalue();
				}else{//如果taskVersion为空，说明nosql中还没有版本号，则新增一个taskVersion
					updateVersionAndRefresh(systemdate,null);
					return;
				}
				//如果当前系统时间和nosql中的时间不一样，则更新nosql中的时间
				if(!systemdate.substring(0, 8).equals(version.substring(0, 8))){
					updateVersionAndRefresh(systemdate,version);
				}
				log.info("结束比较试题分析的版本号");
			} catch (Exception e) {
				log.error("比较试题分析版本号和刷新分析结果时出错！：" + e.getMessage(), e);
			}

		}

	}

	private void updateVersionAndRefresh(String systemdate,String version) throws HttbException{
		boolean flag = false;

      //查询条件
		Map<String,Object> filter = new HashMap<String, Object>();
		if(CommonUtil.isNotEmpty(version)){
			filter.put("qrecorddate", version);
		}
		//答题记录集合
		List<Htqueshis> htqueshis = htqueshisService.getlist(filter);

		flag = htquesanalysisService.refreshQuesAnalysis(htqueshis, systemdate);
		//刷新成功后保存版本号
		if(flag){
			//创建版本号对象
			TaskVersion newversion = new TaskVersion();
			newversion.setTkey(Constants.ANALYSIS_VERSION_QUESTION);
			newversion.setTvalue(systemdate);
			newversion.setTdesc("试题分析版本号");
			newversion.setCreatetime(new Date());
			//设置数据库中版本号
	        taskVersionService.addVersion(newversion);
		}
	}
}
