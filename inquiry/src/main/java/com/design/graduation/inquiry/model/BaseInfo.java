package com.design.graduation.inquiry.model;

import java.math.BigDecimal;

public class BaseInfo {
    //主键
    private Integer userId;
    //姓名
    private String userName;
    //单位名称
    private String userWorkunit;
    //身份证号
    private String userIdntify;
    //用户状态
    private String userState;
    //公积金账号
    private String userAccount;
    //月存缴
    private String userDeposit;
    //用户地区
    private String userRegion;
    //开户日期
    private String userOpendate;
    //户籍
    private String userHousehold;
    //出生年月
    private String userBirthday;
    //芝麻信用分
    private String userSesname;
    //自我介绍
    private String userDescription;
    //充值金额
    private BigDecimal userMoney;
    //性别
    private String userGender;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserWorkunit() {
        return userWorkunit;
    }

    public void setUserWorkunit(String userWorkunit) {
        this.userWorkunit = userWorkunit;
    }

    public String getUserIdntify() {
        return userIdntify;
    }

    public void setUserIdntify(String userIdntify) {
        this.userIdntify = userIdntify;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserDeposit() {
        return userDeposit;
    }

    public void setUserDeposit(String userDeposit) {
        this.userDeposit = userDeposit;
    }

    public String getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(String userRegion) {
        this.userRegion = userRegion;
    }

    public String getUserOpendate() {
        return userOpendate;
    }

    public void setUserOpendate(String userOpendate) {
        this.userOpendate = userOpendate;
    }

    public String getUserHousehold() {
        return userHousehold;
    }

    public void setUserHousehold(String userHousehold) {
        this.userHousehold = userHousehold;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserSesname() {
        return userSesname;
    }

    public void setUserSesname(String userSesname) {
        this.userSesname = userSesname;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public BigDecimal getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(BigDecimal userMoney) {
        this.userMoney = userMoney;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}
