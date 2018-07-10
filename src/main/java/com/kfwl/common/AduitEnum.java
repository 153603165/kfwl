package com.kfwl.common;

/**
 * 
 * 审核状态 枚举类型.
 * 
 * 
 */

public enum AduitEnum {
	/**
	 * 待审核 (0)
	 */
	NOT_APPROVAl(0, "待审核"),
	/**
	 * 通过 (1)
	 */
	PASS(1, "通过"),
	/**
	 * 不通过 (2)
	 */
	FAIL(2, "不通过");
	

	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

	AduitEnum(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * 获取值
	 * @return value
	 */
	public Integer getValue() {
		return value;
	}

	/**
     * 获取描述信息
     * @return description
     */
	public String getDescription() {
		return description;
	}

	public static AduitEnum getAuditStateByValue(Integer value) {
		if (null == value)
			return null;
		for (AduitEnum _enum : AduitEnum.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static AduitEnum getAuditStateByDesc(String description) {
		if (null == description)
			return null;
		for (AduitEnum _enum : AduitEnum.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}
