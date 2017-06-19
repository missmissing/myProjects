/**
 * 
 */
package com.huatu.tb.category.enums;

/**
 * @ClassName: DeleteFlag
 * @Description: 删除标记枚举
 * @author LiXin
 * @date 2015年4月21日 下午3:12:32
 * @version 1.0
 * 
 */
public enum DeleteFlag {
	/** 删除 */
	YES("1"),

	/** 不删除 */
	NO("0"),
	
	/** 知识点未被引用 */
	CATEGORYNOTUSED("0");
	
	private String value;
	
	public String getDeleteFlag() {
		return this.value;
	}

	private DeleteFlag(String value) {
		this.value = value;
	}
	
	public long getCategoryNotUsed() {
		return Long.parseLong(this.value);
	}
}
