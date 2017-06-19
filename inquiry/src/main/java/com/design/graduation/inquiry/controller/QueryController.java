package com.design.graduation.inquiry.controller;

import com.design.graduation.inquiry.common.DateUtils;
import com.design.graduation.inquiry.common.ResultInfo;
import com.design.graduation.inquiry.dto.QueryCondition;
import com.design.graduation.inquiry.model.BaseInfo;
import com.design.graduation.inquiry.model.Query;
import com.design.graduation.inquiry.service.IQueryService;
import com.design.graduation.inquiry.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/query")
public class QueryController {

    private static String QUERY_CONTENT_FUND = "公积金信息";
    private static String QUERY_CONTENT_SOCIAL = "社保信息";
    private static String QUERY_CONTENT_EDUCATION = "学籍学历信息";
    private static String QUERY_CONTENT_CONTACT = "职业社交信息";
    private static String QUERY_CONTENT_DISHONEST = "失信人信息";

    //公积金查询扣费
    private static BigDecimal QUERY_DEDUCTION_FUND = new BigDecimal(1.00);
    //社保查询扣费
    private static BigDecimal QUERY_DEDUCTION_SOCIAL = new BigDecimal(1.50);
    //学籍学历查询扣费
    private static BigDecimal QUERY_DEDUCTION_EDUCATION = new BigDecimal(2.00);
    //职业社交查询扣费
    private static BigDecimal QUERY_DEDUCTION_CONTACT = new BigDecimal(1.50);
    //失信人查询扣费
    private static BigDecimal QUERY_DEDUCTION_DISHONEST = new BigDecimal(2.00);

    @Autowired
    private IQueryService queryService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 查询公积金信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    @RequestMapping(value = "/queryFundByCondition",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo queryFundByCondition(@RequestBody QueryCondition queryCondition){
        Map<String,Object> map = new HashMap<String,Object>();
        BaseInfo baseInfo = queryService.queryBaseInfo((Integer) httpServletRequest.getSession().getAttribute("userId"));
        if (baseInfo.getUserMoney().compareTo(QUERY_DEDUCTION_FUND) < 0) {
            return new ResultInfo(Constant.SUCCESS_CODE,"unbalance");
        } else {
            map.put("accumulationFund",queryService.queryFundByCondition(queryCondition));
            queryService.insertQueryRecord(genQuery(QUERY_CONTENT_FUND,queryCondition.getUserNameCondition()));
            queryService.updateUserMoney(calculateUserMoney(baseInfo,QUERY_DEDUCTION_FUND));
            return new ResultInfo(Constant.SUCCESS_CODE,Constant.SUCCESS,map);
        }

    }

    /**
     * 查询社保信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    @RequestMapping(value = "/querySocialByCondition",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultInfo querySocialByCondition(@RequestBody QueryCondition queryCondition){
        Map<String,Object> map = new HashMap<String,Object>();
        BaseInfo baseInfo = queryService.queryBaseInfo((Integer)httpServletRequest.getSession().getAttribute("userId"));
        if (baseInfo.getUserMoney().compareTo(QUERY_DEDUCTION_SOCIAL) < 0) {
            return new ResultInfo(Constant.SUCCESS_CODE,"unbalance");
        } else {
            //查询社保信息放入结果map
            map.put("socialSecurity", queryService.querySocialByCondition(queryCondition));
            //插入查询记录
            queryService.insertQueryRecord(genQuery(QUERY_CONTENT_SOCIAL, queryCondition.getUserNameCondition()));
            //更新帐户余额
            queryService.updateUserMoney(calculateUserMoney(baseInfo, QUERY_DEDUCTION_SOCIAL));
            return new ResultInfo(Constant.SUCCESS_CODE, Constant.SUCCESS, map);
        }
    }

    /**
     * 查询学籍学历信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    @RequestMapping(value = "/queryEducationByCondition",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultInfo queryEducationByCondition(@RequestBody QueryCondition queryCondition){
        Map<String,Object> map = new HashMap<String,Object>();
        BaseInfo baseInfo = queryService.queryBaseInfo((Integer)httpServletRequest.getSession().getAttribute("userId"));
        if (baseInfo.getUserMoney().compareTo(QUERY_DEDUCTION_EDUCATION) < 0) {
            return new ResultInfo(Constant.SUCCESS_CODE,"unbalance");
        } else {
            map.put("academicRecord", queryService.queryEducationByCondition(queryCondition));
            queryService.insertQueryRecord(genQuery(QUERY_CONTENT_EDUCATION, queryCondition.getUserNameCondition()));
            queryService.updateUserMoney(calculateUserMoney(baseInfo, QUERY_DEDUCTION_EDUCATION));
            return new ResultInfo(Constant.SUCCESS_CODE, Constant.SUCCESS, map);
        }
    }

    /**
     * 查询职业社交信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    @RequestMapping(value = "/queryContactByCondition",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultInfo queryContactByCondition(@RequestBody QueryCondition queryCondition){
        Map<String,Object> map = new HashMap<String,Object>();
        BaseInfo baseInfo = queryService.queryBaseInfo((Integer)httpServletRequest.getSession().getAttribute("userId"));
        if (baseInfo.getUserMoney().compareTo(QUERY_DEDUCTION_CONTACT) < 0) {
            return new ResultInfo(Constant.SUCCESS_CODE,"unbalance");
        } else {
            map.put("professionalSocial", queryService.queryContactByCondition(queryCondition));
            queryService.insertQueryRecord(genQuery(QUERY_CONTENT_CONTACT, queryCondition.getUserNameCondition()));
            queryService.updateUserMoney(calculateUserMoney(baseInfo, QUERY_DEDUCTION_CONTACT));
            return new ResultInfo(Constant.SUCCESS_CODE, Constant.SUCCESS, map);
        }
    }

    /**
     * 查询失信人信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    @RequestMapping(value = "/queryDishonestByCondition",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultInfo queryDishonestByCondition(@RequestBody QueryCondition queryCondition){
        Map<String,Object> map = new HashMap<String,Object>();
        BaseInfo baseInfo = queryService.queryBaseInfo((Integer)httpServletRequest.getSession().getAttribute("userId"));
        if (baseInfo.getUserMoney().compareTo(QUERY_DEDUCTION_DISHONEST) < 0) {
            return new ResultInfo(Constant.SUCCESS_CODE,"unbalance");
        } else {
            map.put("dishonest", queryService.queryDishonestByCondition(queryCondition));
            queryService.insertQueryRecord(genQuery(QUERY_CONTENT_DISHONEST, queryCondition.getUserNameCondition()));
            queryService.updateUserMoney(calculateUserMoney(baseInfo, QUERY_DEDUCTION_DISHONEST));
            return new ResultInfo(Constant.SUCCESS_CODE, Constant.SUCCESS, map);
        }
    }

    /**
     * 查询当前登录人查询记录信息
     * @return 结果对象
     */
    @RequestMapping(value = "/queryQueryRecord",method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultInfo queryQueryRecord(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("queryRecord", queryService.queryQueryRecord((Integer) httpServletRequest.getSession().getAttribute("userId")));
        return new ResultInfo(Constant.SUCCESS_CODE,Constant.SUCCESS,map);
    }

    /**
     * 查询用户基本信息-管理中心
     * @return
     */
    @RequestMapping(value = "/queryBaseInfo",method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo queryBaseInfo(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("queryBaseInfo", queryService.queryBaseInfo((Integer) httpServletRequest.getSession().getAttribute("userId")));
        return new ResultInfo(Constant.SUCCESS_CODE,Constant.SUCCESS,map);
    }

    /**
     * 删除查询记录信息
     * @param queryId 查询结果ID
     * @return 删除记录数
     */
    @RequestMapping(value = "/deleteQueryRecordById/{queryId}",method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo deleteQueryRecordById(@RequestParam int queryId){
        Map<String,Object> map = new HashMap<String,Object>();
        if(1 == queryService.deleteQueryRecordById(queryId)){
            return new ResultInfo(Constant.SUCCESS_CODE,Constant.SUCCESS);
        } else {
            return new ResultInfo(Constant.ERROR_USER_CHECK,"");
        }
    }
    /**
     * 生成查询记录对象
     * @param queryContent 查询内容
     * @param queryUserName 被查询人的名称
     * @return 查询记录对象
     */
    public Query genQuery(String queryContent,String queryUserName){
        Query query = new Query();
        query.setAccountId((Integer) httpServletRequest.getSession().getAttribute("userId"));
        query.setQueryCon(queryContent + "(" + queryUserName + ")");
        query.setQueryDate(DateUtils.getCurrentDate("yyyy-MM-dd"));
        query.setQueryDele("0");
        return query;
    }

    /**
     * 计算查询后余额
     * @param money
     * @return
     */
    public BaseInfo calculateUserMoney(BaseInfo baseInfo,BigDecimal money){
        baseInfo.setUserMoney(baseInfo.getUserMoney().subtract(money));
        return baseInfo;
    }
}
