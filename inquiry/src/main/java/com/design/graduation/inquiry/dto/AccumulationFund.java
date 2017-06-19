package com.design.graduation.inquiry.dto;

import com.design.graduation.inquiry.model.Desipote;

import java.sql.Date;
import java.util.List;

public class AccumulationFund {
    //用户ID
    private Integer userId;
    //公积金账号
    private String userAccount;
    //姓名
    private String userName;
    //单位名称
    private String userWorkunit;
    //身份证号
    private String userIdntify;
    //账号状态
    private String userState;
    //月存缴
    private String userDeposit;
    //地区
    private String userRegion;
    //开户日期
    private Date userOpendate;

    //存缴信息
    private List<Desipote> desipoteList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
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

    public Date getUserOpendate() {
        return userOpendate;
    }

    public void setUserOpendate(Date userOpendate) {
        this.userOpendate = userOpendate;
    }

    public List<Desipote> getDesipoteList() {
        return desipoteList;
    }

    public void setDesipoteList(List<Desipote> desipoteList) {
        this.desipoteList = desipoteList;
    }
}
