/**
 * 项目名：				httb
 * 包名：					com.huatu.upload.action
 * 文件名：				ImageManageAction.java
 * 日期：					2015年4月24日-上午11:15:13
 * Copyright			(c) 2015华图教育-版权所有
 */
package com.huatu.upload.enmu;


/**
 *
 * 类名称：								PictureTypeEnmu
 * 类描述：  								常用图片文件类型（Response.ContentType）
 * 创建人：								Aijunbo
 * 创建时间：								2015-4-24 下午9:11:14
 */
public enum PictureTypeEnmu {

	TYPE_JPG("image/jpeg", "jpg"),
	TYPE_GIF("image/gif", "gif"),
	TYPE_ICO("image/x-icon", "ico"),
	TYPE_JPEG("image/jpeg", "jpeg"),
	TYPE_PNG("image/png", "png"),
	TYPE_TIF("image/tiff", "tif"),
	TYPE_TIFF("image/tiff", "tiff"),
	TYPE_BMP("application/x-bmp", "bmp");

	/**状态值*/
	private String value;
	/**状态key*/
	private String key;

	/**
	 *
	 * 创建一个新的实例 DisabledZtEnmu
	 * @param 		value					状态值
	 * @param 		key						状态key
	 */
	private PictureTypeEnmu(String value, String key) {
		this.value = value;
		this.key = key;
	}

	/**
	 *
	 * getTypeByKey(根据key获取图片文件类型)
	 * @param 		key						key
	 * @return  	String   				返回的图片文件类型
	 */
	public static String getTypeByKey(String key) {
		// 默认图片类型jpg
		String type = "image/jpeg";
		// 遍历所有枚举类型
		for (PictureTypeEnmu c : PictureTypeEnmu.values()) {
			// 若为所求的key则取对应的文件类型
			if (c.getKey().equals(key)) {
				type = c.value;
			}
		}
		return type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
