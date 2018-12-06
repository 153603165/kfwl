package com.kfwl.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * 状态标识 枚举类型.
 * 
 * 
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {
	/** 正常(0) */
	NORMAL(0, "正常"),
	/** 已删除(1) */
	DELETE(1, "已删除");

	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

	public static StatusEnum getStatusState(Integer value) {
		if (null == value)
			return null;
		for (StatusEnum _enum : StatusEnum.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}

	public static StatusEnum getStatusState(String description) {
		if (null == description || "".equals(description))
			return null;
		for (StatusEnum _enum : StatusEnum.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}