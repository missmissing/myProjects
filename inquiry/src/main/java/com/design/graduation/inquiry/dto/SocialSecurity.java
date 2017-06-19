package com.design.graduation.inquiry.dto;

import com.design.graduation.inquiry.model.SocialTotal;

import java.util.List;

/**
 * 社保信息
 */
public class SocialSecurity {
    //姓名
    private String userName;
    //单位
    private String userWorkunit;
    //缴纳月数
    private String userPayment;
    //开户日期
    private String userOpendate;
    //累计缴费
    private List<SocialTotal> socialTotalList;

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

    public String getUserPayment() {
        return userPayment;
    }

    public void setUserPayment(String userPayment) {
        this.userPayment = userPayment;
    }

    public String getUserOpendate() {
        return userOpendate;
    }

    public void setUserOpendate(String userOpendate) {
        this.userOpendate = userOpendate;
    }

    public List<SocialTotal> getSocialTotalList() {
        return socialTotalList;
    }

    public void setSocialTotalList(List<SocialTotal> socialTotalList) {
        this.socialTotalList = socialTotalList;
    }
}
