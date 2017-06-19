package com.design.graduation.inquiry.dto;

import java.util.Date;

/**
 * 学历学籍
 */
public class AcademicRecord {
    //姓名
    private String userName;
    //性别
    private String userGender;
    //证件号
    private String userIdntify;
    //出生年月
    private String userBirthday;
    //学制
    private String censusLength;
    //入学日期
    private Date censusSchoolDate;
    //院校名称
    private String censusInstituation;
    //专业
    private String censusMajor;
    //学号
    private String censusNumber;
    //证书编号
    private String historyCertificate;
    //教育水平
    private String historyLeverl;
    //学习形式
    private String historyForm;
    //院校名称
    private String historyInstituation;
    //入学日期
    private Date historySchooldate;
    //毕业日期
    private Date historyGraduation;

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

    public String getUserIdntify() {
        return userIdntify;
    }

    public void setUserIdntify(String userIdntify) {
        this.userIdntify = userIdntify;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getCensusLength() {
        return censusLength;
    }

    public void setCensusLength(String censusLength) {
        this.censusLength = censusLength;
    }

    public Date getCensusSchoolDate() {
        return censusSchoolDate;
    }

    public void setCensusSchoolDate(Date censusSchoolDate) {
        this.censusSchoolDate = censusSchoolDate;
    }

    public String getCensusInstituation() {
        return censusInstituation;
    }

    public void setCensusInstituation(String censusInstituation) {
        this.censusInstituation = censusInstituation;
    }

    public String getCensusMajor() {
        return censusMajor;
    }

    public void setCensusMajor(String censusMajor) {
        this.censusMajor = censusMajor;
    }

    public String getCensusNumber() {
        return censusNumber;
    }

    public void setCensusNumber(String censusNumber) {
        this.censusNumber = censusNumber;
    }

    public String getHistoryCertificate() {
        return historyCertificate;
    }

    public void setHistoryCertificate(String historyCertificate) {
        this.historyCertificate = historyCertificate;
    }

    public String getHistoryLeverl() {
        return historyLeverl;
    }

    public void setHistoryLeverl(String historyLeverl) {
        this.historyLeverl = historyLeverl;
    }

    public String getHistoryForm() {
        return historyForm;
    }

    public void setHistoryForm(String historyForm) {
        this.historyForm = historyForm;
    }

    public String getHistoryInstituation() {
        return historyInstituation;
    }

    public void setHistoryInstituation(String historyInstituation) {
        this.historyInstituation = historyInstituation;
    }

    public Date getHistorySchooldate() {
        return historySchooldate;
    }

    public void setHistorySchooldate(Date historySchooldate) {
        this.historySchooldate = historySchooldate;
    }

    public Date getHistoryGraduation() {
        return historyGraduation;
    }

    public void setHistoryGraduation(Date historyGraduation) {
        this.historyGraduation = historyGraduation;
    }
}
