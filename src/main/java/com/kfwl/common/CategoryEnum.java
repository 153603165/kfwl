package com.kfwl.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 开票报表类别 申丝/众卡
 * 
 */
@Getter
@AllArgsConstructor
public enum CategoryEnum {
	/**
	 * 众卡类型
	 */
	ZHONGKA("众卡", "众卡"),
	/**
	 * 申丝类型
	 */
	SHENSI("申丝", "申丝");

	/**
	 * 值 Integer型
	 */
	private final String value;
	/**
	 * 描述 String型
	 */
	private final String description;

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
		if (null == description || "".equals(description))
			return null;
		for (CategoryEnum _enum : CategoryEnum.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}