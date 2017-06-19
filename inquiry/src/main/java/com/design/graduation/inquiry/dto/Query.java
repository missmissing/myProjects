package com.design.graduation.inquiry.dto;


import java.util.Date;

public class Query {
    //查询记录主键
    private Integer queryId;
    //用户id
    private Integer accountId;
    //查询日期
    private Date queryDate;
    //查询内容
    private String queryCon;
    //姓名
    private String userName;

    public Integer getQueryId() {
        return queryId;
    }

    public void setQueryId(Integer queryId) {
        this.queryId = queryId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public String getQueryCon() {
        return queryCon;
    }

    public void setQueryCon(String queryCon) {
        this.queryCon = queryCon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
