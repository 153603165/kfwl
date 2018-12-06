package com.kfwl.common.easyui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TBody {
	/**
	 * 字段名
	 */
	private String field;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 宽度
	 */
	private int width = 150;
	/**
	 * 位置
	 */
	private String align;

}