package com.design.graduation.inquiry.service.impl;

import com.design.graduation.inquiry.dao.IQueryDao;
import com.design.graduation.inquiry.dto.*;
import com.design.graduation.inquiry.model.BaseInfo;
import com.design.graduation.inquiry.model.Query;
import com.design.graduation.inquiry.service.IQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class QueryServiceImpl implements IQueryService {
    @Autowired
    private IQueryDao queryDao;

    /**
     * 查询公积金信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    @Override
    public AccumulationFund queryFundByCondition(QueryCondition queryCondition) {
        List<AccumulationFund> accumulationFunds = queryDao.queryFundByCondition(queryCondition);
        if(null!=accumulationFunds && accumulationFunds.size()>0){
            return queryDao.queryFundByCondition(queryCondition).get(0);
        }
        return null;
    }

    /**
     * 插入查询记录信息
     * @param query 查询信息对象
     * @return 插入记录数
     */
    @Override
    public int insertQueryRecord(Query query) {
        return queryDao.insertQueryRecord(query);
    }

    /**
     * 查询社保信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    @Override
    public SocialSecurity querySocialByCondition(QueryCondition queryCondition) {
        return queryDao.querySocialByCondition(queryCondition);
    }

    /**
     * 查询学籍学历信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    @Override
    public AcademicRecord queryEducationByCondition(QueryCondition queryCondition) {
        return queryDao.queryEducationByCondition(queryCondition);
    }

    /**
     * 查询职业社交信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    @Override
    public ProfessionalSocial queryContactByCondition(QueryCondition queryCondition) {
        return queryDao.queryContactByCondition(queryCondition);
    }

    /**
     * 查询失信人信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    @Override
    public Dishonest queryDishonestByCondition(QueryCondition queryCondition) {
        return queryDao.queryDishonestByCondition(queryCondition);
    }

    /**
     * 删除查询记录信息
     * @param queryId 查询结果ID
     * @return 删除记录数
     */
    @Override
    public int deleteQueryRecordById(int queryId) {
        return queryDao.deleteQueryRecordById(queryId);
    }

    /**
     * 查询当前登录人查询记录信息
     * @param userId 用户ID
     * @return
     */
    @Override
    public Query queryQueryRecord(Integer userId) {
        return queryDao.queryQueryRecord(userId);
    }

    /**
     * 查询用户余额
     * @param userId 用户ID
     * @return
     */
    @Override
    public BaseInfo queryBaseInfo(Integer userId) {
        return queryDao.queryBaseInfo(userId);
    }

    /**
     * 更新用户帐户金额
     * @param baseInfo
     * @return
     */
    @Override
    public int updateUserMoney(BaseInfo baseInfo) {
        return queryDao.updateUserMoney(baseInfo);
    }
}
