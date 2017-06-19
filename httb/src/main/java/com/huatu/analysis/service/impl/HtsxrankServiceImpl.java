/**
 * 项目名：				httb
 * 包名：				com.huatu.analysis.service.impl
 * 文件名：				HtsxrankServiceImpl.java
 * 日期：				2015年6月23日-上午10:55:26
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.analysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.analysis.dao.HtsxrankDao;
import com.huatu.analysis.model.Htqueshis;
import com.huatu.analysis.model.Htsxrank;
import com.huatu.analysis.service.HtsxrankService;
import com.huatu.analysis.util.AnaCommonUtil;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.tb.category.service.CategoryService;

/**
 * 类名称：				HtsxrankServiceImpl
 * 类描述：				顺序排名service实现类
 * 创建人：				Aijunbo
 * 创建时间：				2015年6月23日 上午10:55:26
 * @version 			0.0.1
 */
@Service
public class HtsxrankServiceImpl implements HtsxrankService {
	public  Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private HtsxrankDao htsxrankDao;

	@Autowired
	private IRedisService iRedisService;

	@Autowired
	private CategoryService categoryService;

	@Override
	public int getSXRank(String uid, String cid) throws HttbException{
		//保存排序对象
		int rank = 0;
		if(CommonUtil.isNotEmpty(uid) && CommonUtil.isNotEmpty(cid)){
			try {
				String catePid = categoryService.getRootId(cid);
				//根据用户id,获取用户排序对象
				Htsxrank _htsxrank = htsxrankDao.getByUPID(uid, catePid);
				Object obj = iRedisService.get(Constants.ANALYSIS_RANK_SX);
				if(CommonUtil.isNotNull(_htsxrank) && CommonUtil.isNotNull(obj)){
					@SuppressWarnings("unchecked")
					Map<String, int[]> arrRankMap =  (Map<String, int[]>) obj;
					int[] ranks = arrRankMap.get(cid);
					if(ranks != null && ranks.length >= _htsxrank.getSxcount()){
						//返回新答题数对应排名
						rank = ranks[_htsxrank.getSxcount()];
					}
				}else{
					log.info("用户id为："+uid+",知识点id为："+cid+"的顺序答题无记录！");
				}
			} catch (Exception e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入用户顺序答题排序时发生异常", e);
			}
		}
		return rank;
	}

	@Override
	public int saveSXRank(List<Htqueshis> htqueshis) throws HttbException{
		int rank = 0;
		//List<Htsxrank> rankList = new ArrayList<Htsxrank>();
		if(CommonUtil.isNotNull(htqueshis)){
			//把答题记录集合转成顺序答题集合
			Map<String, Htsxrank> htsxranksMap = getRootCateRank(htqueshis);
			//如果不为空
			if(htsxranksMap.size() > 0){
				//遍历顺序答题集合
				for(Htsxrank sxrank: htsxranksMap.values()){
					//rankList.add(sxrank);
					//默认每次保存的试题属于同一个根节点下
					rank = refreshRank(sxrank);
				}
			}
			//rank = refreshRank(rankList.get(0));
		}
		return rank;
	}

	/**
	 *
	 * refreshRank					(刷新缓存中排名)
	 * 								(保存顺序答题时调用)
	 * @param htsxrank
	 * @return
	 * @throws HttbException
	 */
	private int refreshRank(Htsxrank htsxrank) throws HttbException{
		//保存排序对象
		int rank = 0;
		//数据库中答题数
		int oldCount = 0;
		//新增加的答题数
		int newCount = 0;
		if(htsxrank != null && CommonUtil.isNotEmpty(htsxrank.getSxuid()) && CommonUtil.isNotEmpty(htsxrank.getSxpoint())){
			try {
				//根据用户id,获取用户排序对象
				Htsxrank _htsxrank = htsxrankDao.getByUPID(htsxrank.getSxuid(), htsxrank.getSxpoint());
				//如果不为空
				if(CommonUtil.isNotNull(_htsxrank)){
					//原答题数
					oldCount = _htsxrank.getSxcount();
				}
				//新答题数
				newCount = htsxrank.getSxcount()+oldCount;
				//设置新答题数
				htsxrank.setSxcount(newCount);
				//刷新缓存,返回排名
				rank = refreshSXRedis(htsxrank, oldCount, newCount);
				htsxrankDao.save(htsxrank);
			} catch (Exception e) {
				throw new HttbException(ModelException.JAVA_CASSANDRA_INSERT, this.getClass() + "插入用户顺序答题排序时发生异常", e);
			}
		}
		return rank;
	}

	@Override
	public boolean savebatch(List<Htsxrank> htsxrank) throws HttbException {
		return htsxrankDao.savebatch(htsxrank);
	}


	@Override
	public List<Htsxrank> getList() throws HttbException {
		return htsxrankDao.getList();
	}


	@Override
	public List<Htsxrank> getByUid(String uid) throws HttbException {
		return htsxrankDao.getByUid(uid);
	}

	/**
	 *
	 * refreshSXRedis				(刷新顺序答题缓存)
	 * 								(保存用户顺序答题时调用)
	 * @param 		oldCount		原答题数
	 * @param 		newCount		新答题数
	 * @return		int				返回新答题数对应排名
	 * @throws 		HttbException
	 */
	@SuppressWarnings("unchecked")
	private int refreshSXRedis(Htsxrank _htsxrank, int oldCount, int newCount) throws HttbException{
		int returnRank = 0;
		//顺序排名数组
		Object obj = iRedisService.get(Constants.ANALYSIS_RANK_SX);
		Map<String, int[]> arrRankMap = new HashMap<String, int[]>();
		int[] ranks = null;
		if(CommonUtil.isNotNull(obj)){
			arrRankMap = (Map<String, int[]>) obj;
			ranks = arrRankMap.get(_htsxrank.getSxpoint());
			if(ranks != null){
				//数组是以0开始，所以需要长度加1
				if(ranks.length < newCount+1){
					//每次增量
					int newlen = ranks.length+10000;
					int[] newranks = new int[newlen];
					//把原来的数组值赋到新的数组中
					for (int i = 0; i < ranks.length; i++) {
						newranks[i] = ranks[i];
					}
					ranks = newranks;
				}
			}

			//如果是该类型第一次答题
			if(ranks == null || ranks.length == 0){
				//默认初始长度为10000
				ranks = new int[10000];
				ranks[newCount] = 1;
				for (int i = 0; i < newCount; i++) {
					ranks[i] = 2;
				}
			}else{
				for (int i = oldCount; i < newCount; i++) {
					if(ranks[i] == 0){
						ranks[i] = 1;
					}
					ranks[i] = ranks[i]+1;
				}
				if(ranks[newCount] == 0){
					ranks[newCount] = 1;
				}

			}
		}else{
			//默认初始长度为10000
			ranks = new int[10000];
			ranks[newCount] = 1;
			for (int i = 0; i<newCount; i++) {
				ranks[i] = 2;
			}
		}
		arrRankMap.put(_htsxrank.getSxpoint(), ranks);
		//保存缓存
		iRedisService.put(Constants.ANALYSIS_RANK_SX, arrRankMap);
		returnRank = ranks[newCount];
		return returnRank;
	}

	/**
	 *
	 * getRootCateRank				(把答题记录集合转换成顺序答题集合)
	 * @param 		htqueshis		答题记录集合
	 * @return
	 * @throws 		HttbException
	 */
	private Map<String, Htsxrank> getRootCateRank(List<Htqueshis> htqueshis) throws HttbException{
		//排序集合(k:uid_pointid v:答题顺序对象)
		Map<String, Htsxrank> ucateMap = new HashMap<String, Htsxrank>();
		//所有知识点根节点的排序集合(k:uid_rootpointid v:答题顺序对象)
		Map<String, Htsxrank> ucateRootMap = new HashMap<String, Htsxrank>();
		try {
			//将答题记录集合转换成顺序答题集合
			ucateMap = transQuesToRank(htqueshis, ucateMap);
		} catch (Exception e) {
			throw new HttbException(ModelException.JAVA_STRING_DECODE, this.getClass() + "将答题记录集合转换成顺序答题集合时发生异常", e);
		}

		//如果顺序答题排序集合不为空
		if(ucateMap.size() > 0){
			try {
				//转换成知识分类根节点排序集合
				ucateRootMap = transToRootMap(ucateMap, ucateRootMap);
			} catch (Exception e) {
				throw new HttbException(ModelException.JAVA_STRING_DECODE, this.getClass() + "转换成知识分类根节点排序集合时发生异常",e);
			}
		}
		return ucateRootMap;
	}

	@Override
	public List<Htsxrank> saveQueshisList(List<Htqueshis> htqueshis)
			throws HttbException {
		//需要刷新的排名集合
		List<Htsxrank> refreshList = new ArrayList<Htsxrank>();
		if(CommonUtil.isNotNull(htqueshis)){
			//转换成知识分类根节点排序集合
			Map<String, Htsxrank> ucateRootMap = getRootCateRank(htqueshis);
			if(ucateRootMap.size() > 0){
				try {
					refreshList.addAll(ucateRootMap.values());
					//删除原始数据
					//htsxrankDao.batchDel();
					//批量添加
					savebatch(refreshList);
				} catch (Exception e) {
					throw new HttbException(ModelException.JAVA_STRING_DECODE, this.getClass() + "顺序答题业务处理时发生异常",e);
				}
			}
		}
		return refreshList;
	}

	/**
	 * transToRootMap				(转换成知识分类根节点排序集合)
	 * 								(需要统计每个知识分类根节点排名)
	 * @param 		ucateMap		知识分类排名集合
	 * @param 		ucateRootMap	知识分类根节点排名集合
	 * @throws 		HttbException
	 */
	private Map<String, Htsxrank> transToRootMap(Map<String, Htsxrank> ucateMap,
			Map<String, Htsxrank> ucateRootMap) throws HttbException {
		//遍历排序集和(将所有顺序答题数记录到排序知识节点答题数上)
		for(Map.Entry<String, Htsxrank> entry:ucateMap.entrySet()){
			//顺序答题对象
		    Htsxrank _htsxrank = entry.getValue();
		    //获得当前节点对应排序父节点
		    String cateRankId = categoryService.getRootId(_htsxrank.getSxpoint());
		    //用户id_知识点id
		    String key = _htsxrank.getSxuid()+"_"+cateRankId;
			//如果map中有对应【用户_节点】的顺序答题排名
			if(CommonUtil.isNotNull(ucateRootMap.get(key))){
				Htsxrank htsxrankMap = ucateRootMap.get(key);
				htsxrankMap.setSxcount(htsxrankMap.getSxcount()+_htsxrank.getSxcount());
			}else{
				Htsxrank htsxrankMap = new Htsxrank();
				htsxrankMap.setSxuid(_htsxrank.getSxuid());
				htsxrankMap.setSxcount(_htsxrank.getSxcount());
				htsxrankMap.setSxpoint(cateRankId);
				htsxrankMap.setQrecorddate(AnaCommonUtil.getRecordDate());
				ucateRootMap.put(key, htsxrankMap);
			}
		}
		return ucateRootMap;
	}

	/**
	 *
	 * transQuesToRank				(将答题记录集合转换成顺序答题集合)
	 * 								(k:uid_pointid v:答题顺序对象)
	 * @param 		htqueshis		答题记录集合
	 * @param 		raskMap			顺序答题集合
	 * @return
	 */
	private Map<String, Htsxrank> transQuesToRank(List<Htqueshis> htqueshis,
			Map<String, Htsxrank> raskMap) {
		//遍历答题记录集合
		for (int i = 0; i < htqueshis.size(); i++) {
			Htsxrank htsxrank = null;
			//用户id
			String uid = htqueshis.get(i).getQhuid();
			//知识点id
			List<String> qpoint = htqueshis.get(i).getQhpoint();

			//只统计知识节点不为空的试题
			if(CommonUtil.isNotNull(qpoint)){
				//遍历知识点集合
					//用户Id和知识点id标识出唯一一个顺序答题排序
					String key = uid+"_"+qpoint.get(0);
					//判断是否已存在
					if(CommonUtil.isNotNull(raskMap.get(key))){
						//从map中获取
						htsxrank = raskMap.get(key);
						//在原有基础上加1
						int sxcount = htsxrank.getSxcount()+1;
						htsxrank.setSxcount(sxcount);
					}else{
						//新建一个
						htsxrank = new Htsxrank();
						htsxrank.setSxuid(uid);
						htsxrank.setSxcount(1);
						htsxrank.setSxpoint(qpoint.get(0));
					}
					//设置时间戳
					htsxrank.setQrecorddate(AnaCommonUtil.getRecordDate());
					raskMap.put(key, htsxrank);
			}
		}
		return raskMap;
	}

	@Override
	public boolean batchDel() throws HttbException {
		return htsxrankDao.batchDel();
	}

}
