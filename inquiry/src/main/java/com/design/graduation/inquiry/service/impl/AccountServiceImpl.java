package com.design.graduation.inquiry.service.impl;

import com.design.graduation.inquiry.dao.IAccountDao;
import com.design.graduation.inquiry.model.Account;
import com.design.graduation.inquiry.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private IAccountDao accountDao;

    /**
     * 用户登录
     * @param accountCode 账号
     * @param accountPassword 密码
     * @return 用户密码正确数量
     */
    @Override
    public Integer login(String accountCode,String accountPassword) {
        return accountDao.login(accountCode,accountPassword);
    }

    /**
     * 用户注册
     * @param account 账号信息
     * @return 注册成功数
     */
    @Override
    public int register(Account account) {
        return accountDao.register(account);
    }

    /**
     * 修改密码
     * @param account 账号信息
     * @return 修改密码成功数
     */
    @Override
    public int updatePassword(Account account) {
        return accountDao.updatePassword(account);
    }

    /**
     * 检查账号是否已存在
     * @param accountCode 账号
     * @return 账号数量
     */
    @Override
    public int checkAccountIsExist(String accountCode) {
        return accountDao.checkAccountIsExist(accountCode);
    }
}
