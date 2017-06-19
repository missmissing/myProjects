/**
 * MessageInfo.java
 * Created at 2016-12-06
 * Created by fuqinwei
 * Copyright (C) 2016 SHANGHAI DATABANK, All rights reserved.
 */
package com.design.graduation.inquiry.common;

import java.util.Map;

/**
 * 前后端消息对象
 */
public class ResultInfo {
    /** 消息编号 */
    private String messageCode;
    /** 消息内容 */
    private String messageContent;
    /** 消息对象 */
    private Map<String,Object> result;

    public ResultInfo() {
        
    }
    
    public ResultInfo(String messageCode) {
        this.messageCode = messageCode;
    }
    
    public ResultInfo(String messageCode,String messageContent) {
        this.messageCode = messageCode;
        this.messageContent = messageContent;
    }
    
    public ResultInfo(String messageCode,String messageContent,Map<String,Object> result) {
        this.messageCode = messageCode;
        this.messageContent = messageContent;
        this.result = result;
    }
    
    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Map<String,Object> getResult() {
        return result;
    }

    public void setResult(Map<String,Object> result) {
        this.result = result;
    }

}
