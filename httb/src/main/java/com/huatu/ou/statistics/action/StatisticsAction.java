package com.huatu.ou.statistics.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huatu.core.action.BaseAction;
import com.huatu.core.exception.HttbException;
import com.huatu.core.exception.ModelException;
import com.huatu.core.model.Message;
import com.huatu.core.model.Page;
import com.huatu.core.util.CommonUtil;
import com.huatu.core.util.Constants;
import com.huatu.ou.menu.model.Menu;
import com.huatu.ou.menu.service.MenuService;
import com.huatu.ou.organization.model.Organization;
import com.huatu.ou.organization.service.OrganizationService;
import com.huatu.ou.statistics.service.StatisticsService;
import com.huatu.sys.session.HtSessionManager;
import com.huatu.upload.model.Image;
@Controller
@RequestMapping("statistics")
public class StatisticsAction extends BaseAction {
	
	@Autowired
	private StatisticsService statisticsService;
	
	@Autowired
	private HtSessionManager htSessionManager;
	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) throws HttbException{
		return "statistics/list";
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public List<Long> query(Model model) throws HttbException {
		try{
			List list = new ArrayList();
			long historyNum = statisticsService.queryHistoryNum();		
			long currentNum = htSessionManager.getCurrentUsersCount(); 
			list.add(currentNum);
			list.add(historyNum);
			return list;
		}catch(Exception e){
			throw new HttbException(ModelException.JAVA_REDIS_SEARCH, this.getClass() + "统计在线用户异常", e);
		}
	}
	
	
}
