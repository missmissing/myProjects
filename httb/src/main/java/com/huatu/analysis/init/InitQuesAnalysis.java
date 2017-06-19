/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.init
 * 文件名：				InitQuesAnalysis.java
 * 日期：				2015年6月27日-下午12:04:51
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.init;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.service.HtquesanalysisService;
import com.huatu.analysis.service.HtqueshisService;
import com.huatu.analysis.util.AnaCommonUtil;
import com.huatu.api.version.model.TaskVersion;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;

/**
 * 类名称：				InitQuesAnalysis
 * 类描述：  				初始化试题分析
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月27日 下午12:04:51
 * @version 			0.0.1
 */
@Component
public class InitQuesAnalysis {
	@Autowired
	private TaskVersionService taskVersionService;

	@Autowired
	private IRedisService iRedisService;

	@Autowired
	private HtqueshisService htqueshisService;

	@Autowired
	private HtquesanalysisService htquesanalysisService;

	public boolean initExamAnalysis() throws HttbException{
		boolean flag = true;
		//试卷更新版本号
		String version = null;
		try {
			//获取版本号
			version = getVersion();

			//获取当前系统时间戳
			String qrecorddate = AnaCommonUtil.getRecordDate();

			//查询条件
			Map<String,Object> filter = new HashMap<String, Object>();
			if(CommonUtil.isNotEmpty(version)){
				filter.put("qrecorddate", version);
			}
			//答题记录集合
			List<Htqueshis> htqueshis = htqueshisService.getlist(filter);

			htquesanalysisService.refreshQuesAnalysis(htqueshis, qrecorddate);
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
		version = (String) iRedisService.get(Constants.ANALYSIS_VERSION_QUESTION);
		//如果缓存中为空，则取数据库中取
		if (CommonUtil.isNull(version)) {
			TaskVersion taskVersion = taskVersionService.getVersion(Constants.ANALYSIS_VERSION_QUESTION);
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
		iRedisService.put(Constants.ANALYSIS_VERSION_QUESTION, qrecorddate);
		//创建版本号对象
		TaskVersion taskVersion = new TaskVersion();
		taskVersion.setTkey(Constants.ANALYSIS_VERSION_QUESTION);
		taskVersion.setTvalue(qrecorddate);
		taskVersion.setTdesc("试题分析版本号");
		taskVersion.setCreatetime(new Date());
		//设置数据库中版本号
		boolean flag = taskVersionService.addVersion(taskVersion);
		return flag;
	}

}
