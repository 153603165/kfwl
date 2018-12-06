package com.kfwl.common.easyui;

import java.util.ArrayList;
import java.util.List;

import com.kfwl.entity.base.Resource;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * easyui可装载组合框combobox模型.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
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

}
