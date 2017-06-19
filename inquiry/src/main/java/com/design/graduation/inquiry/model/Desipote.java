package com.design.graduation.inquiry.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Desipote {
    //主键
    private Integer desipoteId;
    //用户ID
    private String userId;
    //存缴日期
    private Date desipoteDate;
    //存缴单位名称
    private String  desipoteWorkunit;
    //存缴金额
    private BigDecimal desipoteMoney;
    //业务描述
    private String desipoteBdescripttion;

    public Integer getDesipoteId() {
        return desipoteId;
    }

    public void setDesipoteId(Integer desipoteId) {
        this.desipoteId = desipoteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDesipoteDate() {
        return desipoteDate;
    }

    public void setDesipoteDate(Date desipoteDate) {
        this.desipoteDate = desipoteDate;
    }

    public String getDesipoteWorkunit() {
        return desipoteWorkunit;
    }

    public void setDesipoteWorkunit(String desipoteWorkunit) {
        this.desipoteWorkunit = desipoteWorkunit;
    }

    public BigDecimal getDesipoteMoney() {
        return desipoteMoney;
    }

    public void setDesipoteMoney(BigDecimal desipoteMoney) {
        this.desipoteMoney = desipoteMoney;
    }

    public String getDesipoteBdescripttion() {
        return desipoteBdescripttion;
    }

    public void setDesipoteBdescripttion(String desipoteBdescripttion) {
        this.desipoteBdescripttion = desipoteBdescripttion;
    }
}
