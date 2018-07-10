package com.kfwl.common.easyui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.kfwl.entity.base.Resource;

import lombok.Data;

/**
 * easyui可装载组合框combobox模型.
 * 
 */
@Data
public class Combobox {

	/**
	 * 值域
	 */
	private Long id;
	/**
	 * 文本域
	 */
	private String text;
	/**
	 * 分组
	 */
	private String group;
	/**
	 * 上级资源
	 */
	private Long parentId;
	/**
	 * 图标
	 */
	private String iconCls;
	/**
	 * 是否选中
	 */
	private boolean selected = false;
	/**
	 * 状态
	 */
	private String state;

	public Combobox() {
		super();
	}

	/**
	 *
	 * @param value
	 *            值域
	 * @param text
	 *            文本域
	 */
	public Combobox(Long id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	/**
	 * 
	 * @param value
	 *            值域
	 * @param text
	 *            文本域
	 * @param group
	 *            分组
	 */
	public Combobox(Long id, String text, String group) {
		super();
		this.id = id;
		this.text = text;
		this.group = group;
	}

	/**
	 * 
	 * @param value
	 *            值域
	 * @param text
	 *            文本域
	 * @param group
	 *            分组
	 * @param selected
	 *            是否选中
	 */
	public Combobox(Long id, String text, String group, boolean selected) {
		super();
		this.id = id;
		this.text = text;
		this.group = group;
		this.selected = selected;
	}

	public static List<Combobox> resourceToCombobox(List<Resource> resources) {
		List<Combobox> cbList = new ArrayList<>();
		for (Resource au : resources) {
			Combobox cb = new Combobox();
			cb.setText(au.getResourceName());
			cb.setId(au.getId());
			cb.setParentId(au.getParentId());
			cb.setIconCls(au.getIconCls());
			cb.setState(au.getState());

			cbList.add(cb);
		}
		return cbList;

	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
