/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.service.impl
 * 文件名：				HtexamresultServiceImpl.java
 * 日期：				2015年6月22日-下午9:38:28
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.analysis.dao.HtexamresultDao;
import com.huatu.analysis.model.Htexamresult;
import com.huatu.analysis.service.HtexamresultService;
import com.huatu.analysis.util.ArrayLongIns;
import com.huatu.core.exception.HttbException;
import com.huatu.core.util.CommonUtil;

/**
 * 类名称：				HtexamresultServiceImpl
 * 类描述：
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月22日 下午9:38:28
 * @version 			0.0.1
 */
@Service
public class HtexamresultServiceImpl implements HtexamresultService{
	@Autowired
	private HtexamresultDao htexamresultDao;

	@Override
	public boolean save(Htexamresult htexamresult) throws HttbException {
		return htexamresultDao.save(htexamresult);
	}

	@Override
	public boolean savebatch(List<Htexamresult> htexamresult)
			throws HttbException {
		return htexamresultDao.savebatch(htexamresult);
	}

	@Override
	public List<Htexamresult> getlist(Map<String, Object> filter)
			throws HttbException {
		return htexamresultDao.getlist(filter);
	}

	@Override
	public List<Htexamresult> getRecentExam(Map<String, Object> filter, int recentCount)
			throws HttbException {
		//查询出用户某套卷子的所有答题结果
		List<Htexamresult> htexamresults = htexamresultDao.getlist(filter);
		//返回最近的结果集合
		List<Htexamresult> recentList = null;
		//判断是否为空且是否长度大于需要条数
		if(CommonUtil.isNotNull(htexamresults) && htexamresults.size() > recentCount){
			//转成map集合
			Map<Long, Htexamresult> resultMap = new HashMap<Long, Htexamresult>();
			for (int i = 0; i < htexamresults.size(); i++) {
				Htexamresult htexamresult = htexamresults.get(i);
				resultMap.put(htexamresult.getCreatetime().getTime(), htexamresult);
			}
			//排序，然后取出若干条
			recentList = orderHtexamresults(resultMap, recentCount);
		}else{
			//如果不满足，直接返回
			recentList = htexamresults;
		}
		return recentList;
	}

	/**
	 *
	 * orderHtexamresults			(对考试结果集合进行排序，然后取出最近若干条)
	 * @param 		resultMap		考试结果集合
	 * @param 		recentCount		若干条数
	 * @return
	 */
	private List<Htexamresult> orderHtexamresults(Map<Long, Htexamresult> resultMap, int recentCount){
		List<Htexamresult> returnList = new ArrayList<Htexamresult>();
		List<Long> keys = new ArrayList<Long>();
		keys.addAll(resultMap.keySet());

		ArrayLongIns arr = new ArrayLongIns(keys.size());
		for (int j = 0; j < keys.size(); j++) {
			arr.insert(keys.get(j));
		}
		arr.quickSort();
		//升序排列
		long[] orderKeys = arr.getTheArray();
		//翻转成倒序排序(按时间倒序排序，只取最近的考试结果集合)
		for (int i = 0; i < orderKeys.length / 2; i++) {
			long temp = orderKeys[i];
			orderKeys[i] = orderKeys[orderKeys.length - 1 - i];
			orderKeys[orderKeys.length - 1 - i] = temp;
		}
		//循环遍历倒序排序号的集合，长度为指定的recentCount
		for (int i = 0; i < recentCount; i++) {
			//获取map中的集合
			returnList.add(resultMap.get(orderKeys[i]));
		}
		return returnList;
	}



}

