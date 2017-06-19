package com.design.graduation.inquiry.dto;

public class QueryCondition {
    //用户姓名查询条件
    private String userNameCondition;
    //身份证查询条件
    private String idCardCondition;
    //手机号查询条件
    private String phoneCondition;

    public String getUserNameCondition() {
        return userNameCondition;
    }

    public void setUserNameCondition(String userNameCondition) {
        this.userNameCondition = userNameCondition;
    }

    public String getIdCardCondition() {
        return idCardCondition;
    }

    public void setIdCardCondition(String idCardCondition) {
        this.idCardCondition = idCardCondition;
    }

    public String getPhoneCondition() {
        return phoneCondition;
    }

    public void setPhoneCondition(String phoneCondition) {
        this.phoneCondition = phoneCondition;
    }
}
