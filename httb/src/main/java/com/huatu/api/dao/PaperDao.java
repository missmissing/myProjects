package com.huatu.api.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huatu.api.task.util.TaskMarkKeyUtil;
import com.huatu.api.vo.PaperVo;
import com.huatu.core.dao.NoSqlBaseDao;
import com.huatu.core.exception.HttbException;
import com.huatu.core.redis.service.IRedisService;
import com.huatu.core.util.Constants;
import com.huatu.tb.paper.model.Paper;

@Component
public class PaperDao {
	
	@Autowired
	private NoSqlBaseDao noSqlBaseDao;
	
	@Autowired
	private IRedisService iRedisService;
	
	public List<Paper> getPaperList(Map<String, Object> filter) throws HttbException {
		List<Paper> paperList ;
		String tclassify = (String) filter.get("tclassify");
		String attribute = (String) filter.get("attribute");
		String area = (String)filter.get("area");
		String key = "";
		if(attribute.equals("0")){
			key = TaskMarkKeyUtil.ZHEN_PAPER_LIST;
		}else{
			key = TaskMarkKeyUtil.MONI_PAPER_LIST;
		}
		if(tclassify.equals("300")){
			paperList = (List<Paper>) iRedisService.get(key+area);
		}else{
			paperList = (List<Paper>) iRedisService.get(key+tclassify);
		}
		
		return paperList;
		
	}

}
