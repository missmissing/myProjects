package com.huatu.api.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huatu.analysis.model.Htexamresult;
import com.huatu.analysis.service.HtexamanalysisService;
import com.huatu.analysis.service.HtexamresultService;
import com.huatu.analysis.util.AnaCommonUtil;
import com.huatu.api.version.model.TaskVersion;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.util.Constants;

@Component
public class ExamAnalysisTask {

	public Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private TaskVersionService taskVersionService;

	@Autowired
	private HtexamanalysisService htexamanalysisService;

	@Autowired
	private HtexamresultService htexamresultService;

	public void execute() throws HttbException {
		//nosql中的版本号
		String version = "";
		//获取当前系统时间戳
		String systemdate = AnaCommonUtil.getRecordDate();
		if (Constants.ISTIMEDTASK) {
			try {
				log.info("开始比较试卷分析的版本号");
				TaskVersion taskVersion = taskVersionService.getVersion(Constants.ANALYSIS_VERSION_EXAM);
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
				log.info("结束比较试卷分析的版本号");
			} catch (Exception e) {
				log.error("比较试卷分析版本号和刷新分析结果时出错：" + e.getMessage(), e);
			}

		}

	}

	private List<Htexamresult> getExamList(String version) throws HttbException{
		List<Htexamresult> exams = new ArrayList<Htexamresult>();

		Map<String,Object> filter = new HashMap<String, Object>();
		filter.put("qrecorddate", version);

		try {
			exams = htexamresultService.getlist(filter);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_SEARCH, this.getClass()+"查询考试试卷时发生异常", e);
		}
		return exams;
	}
	
	private void updateVersionAndRefresh(String systemdate,String version) throws HttbException{
		//创建版本号对象
		TaskVersion newversion = new TaskVersion();
		newversion.setTkey(Constants.ANALYSIS_VERSION_EXAM);
		newversion.setTvalue(systemdate);
		newversion.setTdesc("试卷分析版本号");
		newversion.setCreatetime(new Date());
		//设置数据库中版本号
        taskVersionService.addVersion(newversion);

        List<Htexamresult> exams = null;
        //查询试卷集合
		exams = getExamList(version);
		//刷新试卷分析
		htexamanalysisService.refreshExamAnalysis(exams, systemdate);
	}

}
