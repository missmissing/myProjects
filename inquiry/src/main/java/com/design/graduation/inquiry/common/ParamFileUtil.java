/**
 * ParamFileUtil.java
 * Created at 2016-12-06
 * Created by fuqinwei
 * Copyright (C) 2016 SHANGHAI DATABANK, All rights reserved.
 */
package com.design.graduation.inquiry.common;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 获取properties配置文件
 */
public class ParamFileUtil {
	/** Properties对象 */
    private Properties prop;

    public static Properties loadPropertiesFromBundlefile(String bundlefile) {
        return loadPropertiesFromBundlefile(bundlefile, new Properties());
    }

    public static Properties loadPropertiesFromBundlefile(String bundlefile, Properties toprop) {
        Properties properties = toprop;
        try {
            ResourceBundle rb = ResourceBundle.getBundle(bundlefile);
            Enumeration enu = rb.getKeys();
            while (enu.hasMoreElements()) {
                String strPName = (String) enu.nextElement();
                String strPValue = null;
                if (strPName != null) {
                    strPValue = rb.getString(strPName);
                }
                if ((strPName != null) && (strPValue != null))
                    properties.setProperty(strPName, strPValue);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return properties;
    }

    public static String getConfigProperty(String propertyName,String propertyValue){
        Properties prop = loadPropertiesFromBundlefile(propertyName);
        return prop.getProperty(propertyValue);
    }
    public String getProperty(String key) {
        return getProperty(key, "");
    }

    public String getProperty(String key, String nulldef) {
        String value = getProperties().getProperty(key);
        return ((value == null) ? nulldef : value);
    }

    public String getPropertyStringValue(String key) {
        return getProperty(key);
    }

    public int getPropertyIntegerValue(String key) {
        String str = getProperty(key);
        try {
            return Integer.parseInt(str);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public byte getPropertyByteValue(String key) {
        String str = getProperty(key);
        try {
            return Byte.parseByte(str);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public float getPropertyFloatValue(String key) {
        String str = getProperty(key);
        try {
            return Float.parseFloat(str);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0.0F;
    }

    public double getPropertyDoubleValue(String key) {
        String str = getProperty(key);
        try {
            return Double.parseDouble(str);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0.0D;
    }

    public boolean getPropertyBooleanValue(String key) {
        String str = getProperty(key);
        try {
            return Boolean.getBoolean(str);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    protected void setProperty(String key, String value) {
        getProperties().setProperty(key, value);
    }

    public void removeProperty(String key) {
        getProperties().remove(key);
    }

    public Properties getProperties() {
        return this.prop;
    }

    public void clear() {
        this.prop.clear();
    }
}