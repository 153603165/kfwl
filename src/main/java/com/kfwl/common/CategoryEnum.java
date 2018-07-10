package com.kfwl.common;

/**
 * 开票报表类别 申丝/众卡
 * 
 */
public enum CategoryEnum {
	ZHONGKA("众卡", "众卡"),
	SHENSI("申丝", "申丝");

	/**
	 * 值 Integer型
	 */
	private final String value;
	/**
	 * 描述 String型
	 */
	private final String description;

	CategoryEnum(String value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * 获取值
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 获取描述信息
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	public static CategoryEnum getCategoryTypeByValue(String value) {
		if (null == value)
			return null;
		for (CategoryEnum _enum : CategoryEnum.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}

	public static CategoryEnum getReplyStateByDesc(String description) {
		if (null == description)
			return null;
		for (CategoryEnum _enum : CategoryEnum.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}