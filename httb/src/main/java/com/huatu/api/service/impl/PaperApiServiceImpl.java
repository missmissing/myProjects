/**   
 * 项目名：				httb
 * 包名：				com.huatu.api.service.impl  
 * 文件名：				PaperApiServiceImpl.java    
 * 日期：				2015年6月13日-下午4:50:54  
 * Copyright			(c) 2015华图教育-版权所有     
 */
package com.huatu.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huatu.api.service.PaperApiService;
import com.huatu.api.task.util.TaskMarkKeyUtil;
import com.huatu.api.util.ModelToVoUtil;
import com.huatu.api.vo.PaperVo;
import com.huatu.core.exception.HttbException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.AppTypePropertyUtil;
import com.huatu.core.util.CommonUtil;
import com.huatu.tb.paper.model.Paper;

/**   
 * 类名称：				PaperApiServiceImpl  
 * 类描述：  
 * 创建人：				LiXin
 * 创建时间：			2015年6月13日 下午4:50:54  
 * @version 		1.0
 */
@Service
public class PaperApiServiceImpl implements PaperApiService{
	
	@Autowired
	private IRedisService iRedisService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PaperVo> getPaperList(Map<String, Object> filter) throws HttbException {
		
		String attribute = (String) filter.get("attribute");//属性 0==>真题，1==>模拟题
		String tclassify = (String) filter.get("tclassify");  //一级分类标识
		String keyword =""; //关键字
		String area ="";//地区
		int count = (int) filter.get("count");   //查询个数
		int pageindex = Integer.parseInt((String)filter.get("pageindex"));  //页数 
		
		if(filter.containsKey("keyword")){
			keyword = (String) filter.get("keyword"); 
		}
		if(filter.containsKey("area")){
			area = (String) filter.get("area");  
		}
		
		
		List<PaperVo> resultList = new ArrayList<PaperVo>();//真正需要的的数据
		List<PaperVo> pvList = new ArrayList<PaperVo>();//符合条件的的数据
		List<Paper> paperList = new ArrayList<Paper>();//试卷列表
		Map<String,String> verMap = new HashMap<String, String>();//版本消息
		if("0".equals(attribute)){//真题试卷列表
			paperList = (List<Paper>) iRedisService.get(TaskMarkKeyUtil.ZHEN_PAPER_LIST);
			verMap = (Map<String, String>) iRedisService.get(TaskMarkKeyUtil.ZHEN_VER_GATHER);
		}else{//模拟题试卷列表
			paperList = (List<Paper>) iRedisService.get(TaskMarkKeyUtil.MONI_PAPER_LIST);
			verMap = (Map<String, String>) iRedisService.get(TaskMarkKeyUtil.MONI_VER_GATHER);
		}
		
		if(AppTypePropertyUtil.APP_TYPE == 300){//公务员
			for(Paper paper : paperList){
				if(CommonUtil.isNotNull(paper.getPareas()) && paper.getPareas().contains(area)){
					if(CommonUtil.isNotNull(keyword)){
						if(containsAny(paper.getPtitle(),keyword)){
							pvList.add(ModelToVoUtil.paperToVo(paper));
						}
					}else{
						pvList.add(ModelToVoUtil.paperToVo(paper));
					}
				}
			}
		}else{
			for(Paper paper : paperList){
				if(CommonUtil.isNotNull(paper.getPcategorys()) &&paper.getPcategorys().contains(tclassify)){
					if(CommonUtil.isNotNull(keyword)){
						if(containsAny(paper.getPtitle(),keyword)){
							pvList.add(ModelToVoUtil.paperToVo(paper));
						}
					}else{
						pvList.add(ModelToVoUtil.paperToVo(paper));
					}
				}
			}
		}
		
		int mincount = (pageindex-1)*count;//开始下标数
		int maxcount = pageindex*count;//开始下标数
		if(pvList.size() > mincount){
			if(pvList.size() >maxcount){
				for(int i=mincount;i<maxcount;i++){
					resultList.add(pvList.get(i));
				}
			}else{
				for(int i=mincount;i<pvList.size();i++){
					resultList.add(pvList.get(i));
				}
			}
		}
		//遍历设置版本
		for(PaperVo rpv : resultList){
			rpv.setVersions(verMap.get(rpv.getPid()));
		}
		return resultList;
	}

	@Override
	public String getQuestionsByPaperId(String id) throws HttbException {
		String queStr = (String) iRedisService.get(TaskMarkKeyUtil.PAPER_+id);
		return queStr;
	}
	/**
	 * 是否包含
	 * containsAny  
	 * @exception 
	 * @param str
	 * @param searchChars
	 * @return
	 */
	private boolean containsAny(String str, String searchChars){
		return str.contains(searchChars);
	}

}
