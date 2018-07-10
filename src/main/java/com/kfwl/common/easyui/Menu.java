package com.kfwl.common.easyui;

import java.util.ArrayList;
import java.util.List;

import com.kfwl.entity.base.Resource;

import lombok.Data;

@Data
public class Menu {
	private Long id;
	private String menuname;
	private String url;
	private List<Menu> menus;

	public static Menu getMenusData(List<Resource> resources) {
		Menu menu = new Menu();
		List<Menu> menuList = new ArrayList<>();
		menu.setMenus(menuList);
		for (Resource resource : resources) {
			if (resource.getParentResource() == null) {
				Menu tempMenu = new Menu();
				tempMenu.setId(resource.getId());
				tempMenu.setMenuname("<span style='margin-right:2px;' class='tree-icon tree-file "
						+ resource.getIconCls() + "'></span>" + resource.getResourceName());
				if (resource.getType().equals(1)) {
					tempMenu.setUrl(resource.getMenuUrl());
				}
				menuList.add(tempMenu);
			}
		}
		for (Menu parentMenu : menuList) {
			List<Menu> tempMenus = new ArrayList<>();
			for (Resource resource : resources) {
				if (resource.getParentId() != null && parentMenu.getId().equals(resource.getParentId())) {
					Menu tempMenu = new Menu();
					tempMenu.setId(resource.getId());
					tempMenu.setMenuname("<span style='padding-top:8px;margin-right:2px;' class='tree-icon tree-file "
							+ resource.getIconCls() + "'></span>" + resource.getResourceName());
					if (resource.getType().equals(1)) {
						tempMenu.setUrl(resource.getMenuUrl());
					}
					tempMenus.add(tempMenu);
				}
			}
			parentMenu.setMenus(tempMenus);
		}
		return menu;
	}
}
