package com.design.graduation.inquiry.service;

import com.design.graduation.inquiry.model.Account;

public interface IAccountService {
    /**
     * 用户登录
     * @param accountCode 账号
     * @param accountPassword 密码
     * @return 用户密码正确数量
     */
    public Integer login(String accountCode,String accountPassword);

    /**
     * 用户注册
     * @param account 账号信息
     * @return 注册成功数
     */
    public int register(Account account);

    /**
     * 修改密码
     * @param account 账号信息
     * @return 修改密码成功数
     */
    public int updatePassword(Account account);

    /**
     * 检查账号是否已存在
     * @param accountCode 账号
     * @return 账号数量
     */
    public int checkAccountIsExist(String accountCode);
}
