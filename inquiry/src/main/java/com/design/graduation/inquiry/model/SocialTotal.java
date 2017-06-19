package com.design.graduation.inquiry.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 累计缴费
 */
public class SocialTotal {
    //主键
    private Integer totalId;
    //用户ID
    private String userId;
    //险种
    private String totalInsurance;
    //帐户余额
    private BigDecimal totalBalance;
    //截止日期
    private Date totalExpore;
    //帐户状态
    private String totalState;

    public Integer getTotalId() {
        return totalId;
    }

    public void setTotalId(Integer totalId) {
        this.totalId = totalId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTotalInsurance() {
        return totalInsurance;
    }

    public void setTotalInsurance(String totalInsurance) {
        this.totalInsurance = totalInsurance;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Date getTotalExpore() {
        return totalExpore;
    }

    public void setTotalExpore(Date totalExpore) {
        this.totalExpore = totalExpore;
    }

    public String getTotalState() {
        return totalState;
    }

    public void setTotalState(String totalState) {
        this.totalState = totalState;
    }
}
