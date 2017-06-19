package com.design.graduation.inquiry.service;

import com.design.graduation.inquiry.dto.*;
import com.design.graduation.inquiry.model.BaseInfo;
import com.design.graduation.inquiry.model.Query;

import java.math.BigDecimal;

public interface IQueryService {

    /**
     * 查询公积金信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    AccumulationFund queryFundByCondition(QueryCondition queryCondition);

    /**
     * 插入查询记录信息
     * @param query 查询信息对象
     * @return 插入记录数
     */
    int insertQueryRecord(Query query);

    /**
     * 查询社保信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    SocialSecurity querySocialByCondition(QueryCondition queryCondition);

    /**
     * 查询学籍学历信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    AcademicRecord queryEducationByCondition(QueryCondition queryCondition);

    /**
     * 查询职业社交信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    ProfessionalSocial queryContactByCondition(QueryCondition queryCondition);

    /**
     * 查询失信人信息
     * @param queryCondition 查询条件对象
     * @return 结果对象
     */
    Dishonest queryDishonestByCondition(QueryCondition queryCondition);

    /**
     * 删除查询记录信息
     * @param queryId 查询结果ID
     * @return 删除记录数
     */
    int deleteQueryRecordById(int queryId);

    /**
     * 查询当前登录人查询记录信息
     * @param userId 用户ID
     * @return
     */
    Query queryQueryRecord(Integer userId);

    /**
     * 查询用户余额
     * @param userId 用户ID
     * @return
     */
    BaseInfo queryBaseInfo(Integer userId);

    /**
     * 更新用户帐户金额
     * @param baseInfo
     * @return
     */
    int updateUserMoney(BaseInfo baseInfo);
}
