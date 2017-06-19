/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.init
 * 文件名：				InitExam.java
 * 日期：				2015年6月26日-上午11:02:12
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.init;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;

/**
 * 类名称：				InitExam
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月26日 上午11:02:12
 * @version 			0.0.1
 */
@Component
public class InitExamAnalysis {
	@Autowired
	private IRedisService iRedisService;

	@Autowired
	private HtexamresultService htexamresultService;

	@Autowired
	private HtexamanalysisService htexamanalysisService;

	@Autowired
	private TaskVersionService taskVersionService;
	public boolean initExamAnalysis() throws HttbException{
		boolean flag = true;
		//试卷更新版本号
		String version = null;
		List<Htexamresult> exams = null;
		try {
			//获取版本号
			version = getVersion();

			//查询试卷集合
			exams = getExamList(version);
			//获取当前系统时间戳
			String qrecorddate = AnaCommonUtil.getRecordDate();

			flag = htexamanalysisService.refreshExamAnalysis(exams, qrecorddate);
			//如果试卷分析刷新成功，则重置试卷分析版本号
			if(flag){
				setVersion(qrecorddate);
			}
		} catch (HttbException e) {
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass()+"获取考试版本缓存时发生异常", e);
		}

		return flag;
	}

	/**
	 * getVersion					(获取版本号)
	 * 								(初始化时调用)
	 * @return
	 * @throws 		HttbException
	 */
	private String getVersion() throws HttbException {
		String version;
		//从缓存中获取版本号
		version = (String) iRedisService.get(Constants.ANALYSIS_VERSION_EXAM);
		//如果缓存中为空，则取数据库中取
		if (CommonUtil.isNotEmpty(version)) {
			TaskVersion taskVersion = taskVersionService.getVersion(Constants.ANALYSIS_VERSION_EXAM);
			if(taskVersion != null)
					version = taskVersion.getTvalue();
		}
		return version;
	}

	/**
	 * setVersion					(设置版本号)
	 * 								(初始化时调用)
	 * @return
	 * @throws 		HttbException
	 */
	private boolean setVersion(String qrecorddate) throws HttbException {
		//设置缓存中获取版本号
		iRedisService.put(Constants.ANALYSIS_VERSION_EXAM, qrecorddate);
		//创建版本号对象
		TaskVersion taskVersion = new TaskVersion();
		taskVersion.setTkey(Constants.ANALYSIS_VERSION_EXAM);
		taskVersion.setTvalue(qrecorddate);
		taskVersion.setTdesc("试卷分析版本号");
		taskVersion.setCreatetime(new Date());
		//设置数据库中版本号
		boolean flag = taskVersionService.addVersion(taskVersion);
		return flag;
	}

	/**
	 *
	 * getExamList				(查询新增的试卷答题结果)
	 * @param 		exams
	 * @param version
	 * @return
	 * @throws HttbException
	 */
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
}
