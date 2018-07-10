package com.kfwl.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

public class PageUtil {
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> page2Map(Page page) {
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		return map;
	}
}
