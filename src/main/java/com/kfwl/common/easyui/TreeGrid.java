package com.kfwl.common.easyui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.kfwl.entity.base.Resource;

import lombok.Data;

@Data
public class TreeGrid {
	private Long id;
	private String resourceName;
	private String resourceDesc;
	private String menuUrl;
	private String iconCls;
	private Long parentId;
	private String state;
	private Integer type;
	private Integer seq;

	public static List<TreeGrid> resourceToTreeGrid(List<Resource> resources) {
		List<TreeGrid> tgList = new ArrayList<>();
		for (Resource rs : resources) {
			TreeGrid tg = new TreeGrid();
			BeanUtils.copyProperties(rs, tg);
			tgList.add(tg);
		}
		return tgList;

	}
}
