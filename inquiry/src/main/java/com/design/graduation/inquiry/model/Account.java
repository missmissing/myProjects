package com.design.graduation.inquiry.model;

public class Account {
    //主键
    private Integer accountId;
    //账号
    private String accountCode;
    //密码
    private String accountPassword;
    //新密码
    private String newAccountPassword;
    //状态
    private String accountStatus;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getNewAccountPassword() {
        return newAccountPassword;
    }

    public void setNewAccountPassword(String newAccountPassword) {
        this.newAccountPassword = newAccountPassword;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}
