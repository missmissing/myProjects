package com.design.graduation.inquiry.dto;

import java.math.BigDecimal;

/**
 * 失信人信息
 */
public class Dishonest {
    /** 姓名*/
    private String userName;
    /** 性别*/
    private String userGender;
    /** 身份证号 */
    private String executeIdentify;
    /** 立案时间 */
    private String executeFiling;
    /** 执行法院 */
    private String executeCourt;
    /** 履约情况 */
    private String executeCompliance;
    /** 失信类型 */
    private String executeType;
    /** 关注次数 */
    private String executeAttention;
    /** 省份 */
    private String executeProvincee;
    /** 已履行 */
    private BigDecimal executeExecute;
    /** 未履行 */
    private BigDecimal executeUnfulfiled;
    /** 失信人类型 */
    private String nonexecuteType;
    /** 年龄 */
    private String nonexecuteAge;
    /** 未履行情况 */
    private BigDecimal nonexecuteUnfulfiled;
    /** 执行标的 */
    private String nonexecuteObject;
    /** 立案时间 */
    private String nonexecuteFiling;
    /** 公布时间 */
    private String nonexecutePublictime;
    /** 执行法院 */
    private String nonexecuteCourt;
    /** 已履行 */
    private String nonexecuteExecute;
    /** 关注次数 */
    private String nonexecuteAttention;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getExecuteIdentify() {
        return executeIdentify;
    }

    public void setExecuteIdentify(String executeIdentify) {
        this.executeIdentify = executeIdentify;
    }

    public String getExecuteFiling() {
        return executeFiling;
    }

    public void setExecuteFiling(String executeFiling) {
        this.executeFiling = executeFiling;
    }

    public String getExecuteCourt() {
        return executeCourt;
    }

    public void setExecuteCourt(String executeCourt) {
        this.executeCourt = executeCourt;
    }

    public String getExecuteCompliance() {
        return executeCompliance;
    }

    public void setExecuteCompliance(String executeCompliance) {
        this.executeCompliance = executeCompliance;
    }

    public String getExecuteType() {
        return executeType;
    }

    public void setExecuteType(String executeType) {
        this.executeType = executeType;
    }

    public String getExecuteAttention() {
        return executeAttention;
    }

    public void setExecuteAttention(String executeAttention) {
        this.executeAttention = executeAttention;
    }

    public String getExecuteProvincee() {
        return executeProvincee;
    }

    public void setExecuteProvincee(String executeProvincee) {
        this.executeProvincee = executeProvincee;
    }

    public BigDecimal getExecuteExecute() {
        return executeExecute;
    }

    public void setExecuteExecute(BigDecimal executeExecute) {
        this.executeExecute = executeExecute;
    }

    public BigDecimal getExecuteUnfulfiled() {
        return executeUnfulfiled;
    }

    public void setExecuteUnfulfiled(BigDecimal executeUnfulfiled) {
        this.executeUnfulfiled = executeUnfulfiled;
    }

    public String getNonexecuteType() {
        return nonexecuteType;
    }

    public void setNonexecuteType(String nonexecuteType) {
        this.nonexecuteType = nonexecuteType;
    }

    public String getNonexecuteAge() {
        return nonexecuteAge;
    }

    public void setNonexecuteAge(String nonexecuteAge) {
        this.nonexecuteAge = nonexecuteAge;
    }

    public BigDecimal getNonexecuteUnfulfiled() {
        return nonexecuteUnfulfiled;
    }

    public void setNonexecuteUnfulfiled(BigDecimal nonexecuteUnfulfiled) {
        this.nonexecuteUnfulfiled = nonexecuteUnfulfiled;
    }

    public String getNonexecuteObject() {
        return nonexecuteObject;
    }

    public void setNonexecuteObject(String nonexecuteObject) {
        this.nonexecuteObject = nonexecuteObject;
    }

    public String getNonexecuteFiling() {
        return nonexecuteFiling;
    }

    public void setNonexecuteFiling(String nonexecuteFiling) {
        this.nonexecuteFiling = nonexecuteFiling;
    }

    public String getNonexecutePublictime() {
        return nonexecutePublictime;
    }

    public void setNonexecutePublictime(String nonexecutePublictime) {
        this.nonexecutePublictime = nonexecutePublictime;
    }

    public String getNonexecuteCourt() {
        return nonexecuteCourt;
    }

    public void setNonexecuteCourt(String nonexecuteCourt) {
        this.nonexecuteCourt = nonexecuteCourt;
    }

    public String getNonexecuteExecute() {
        return nonexecuteExecute;
    }

    public void setNonexecuteExecute(String nonexecuteExecute) {
        this.nonexecuteExecute = nonexecuteExecute;
    }

    public String getNonexecuteAttention() {
        return nonexecuteAttention;
    }

    public void setNonexecuteAttention(String nonexecuteAttention) {
        this.nonexecuteAttention = nonexecuteAttention;
    }
}
