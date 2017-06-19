/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.init
 * 文件名：				InitSXRank.java
 * 日期：				2015年6月23日-上午10:43:54
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.model.Htsxrank;
import com.huatu.analysis.service.HtqueshisService;
import com.huatu.analysis.service.HtsxrankService;
import com.huatu.analysis.util.SXRankUtil;
import com.huatu.api.version.service.TaskVersionService;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;

/**
 * 类名称：				InitSXRank
 * 类描述：  				初始化顺序做题
 * 创建人：				Aijunbo
 * 创建时间：			2015年6月23日 上午10:43:54
 * @version 			0.0.1
 */
@Component
public class InitSXRankAnalysis {
	@Autowired
	private IRedisService iRedisService;

	@Autowired
	private HtsxrankService htsxrankService;

	@Autowired
	private TaskVersionService taskVersionService;

	@Autowired
	private HtqueshisService htqueshisService;

	/**
	 *
	 * initSXRankAnalysis			(增量分析顺序排序表)
	 * 								(程序启动或定时扫描时调用)
	 * @return
	 * @throws 		HttbException
	 */
	public void initSXRankAnalysis() throws HttbException{
		//顺序答题记录集合
		List<Htqueshis> htqueshis = getHtQueshisList();

		//分析是否成功
		htsxrankService.saveQueshisList(htqueshis);

		//初始化顺序答题在内存中的缓存
		initSXRank();
	}

	/**
	 *
	 * initSXRank					(初始化顺序排名集合)
	 * 								(程序启动或定时扫描时调用)
	 * @return
	 * @throws 		HttbException
	 */
	@SuppressWarnings("unchecked")
	private boolean initSXRank() throws HttbException{
		boolean flag = true;
		try {
			//查询试卷顺序排名集合
			List<Htsxrank> sxrankList = htsxrankService.getList();
			//按知识分类排列的顺序答题排名集合
			Map<String, List<Htsxrank>> cateRankMap = new HashMap<String, List<Htsxrank>>();
			//暗之时分类排列的排序数组
			Map<String, int[]> arrRankMap = new HashMap<String, int[]>();
			if(CommonUtil.isNotNull(sxrankList)){

				//遍历排序集合，装进map中
				for (int i = 0; i < sxrankList.size(); i++) {
					List<Htsxrank> cateRankList = null;
					//知识分类id
					String point = sxrankList.get(i).getSxpoint();
					//从map中获取
					Object pointObj = cateRankMap.get(point);
					//如果已经存在，则取出
					//不存在就新建一个
					if (CommonUtil.isNotNull(pointObj)) {
						cateRankList = (List<Htsxrank>) pointObj;
					}else{
						cateRankList = new ArrayList<Htsxrank>();
					}
					cateRankList.add(sxrankList.get(i));
					cateRankMap.put(point, cateRankList);
				}

				for(Map.Entry<String, List<Htsxrank>> entry:cateRankMap.entrySet()){
					//答题数排名集合
					int[] sortArrs = SXRankUtil.getRankArrs(entry.getValue());
					arrRankMap.put(entry.getKey(), sortArrs);
				}
				//放入缓存
				iRedisService.put(Constants.ANALYSIS_RANK_SX, arrRankMap);
			}
		} catch (HttbException e) {
			throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "查询顺序排名时发生异常", e);
		}
		return flag;
	}

	/**
	 *
	 * getHtQueshisList				(获取顺序答题记录集合)
	 * @return
	 * @throws 		HttbException
	 */
	private List<Htqueshis> getHtQueshisList() throws HttbException {
		//查询条件集合
		Map<String,Object> filter = new HashMap<String, Object>();
		//所有正确的题
		filter.put("qhisright", 0);
		//试题类型(0=>真题,1=>模拟,2=>顺序,3=>模块)
		filter.put("qhtype", "2");

		//查询答题记录集合
		return htqueshisService.getlist(filter);
	}
}
