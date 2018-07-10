package com.kfwl.controller.base;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.kfwl.common.easyui.Combobox;
import com.kfwl.common.easyui.TreeGrid;
import com.kfwl.controller.BasicController;
import com.kfwl.entity.base.Resource;
import com.kfwl.exception.impl.BusinessException;
import com.kfwl.service.base.ResourceService;

@Controller
@RequestMapping("/resource")
public class ResourceController extends BasicController {

	@Autowired
	ResourceService resourceService;

	/**
	 * 资源管理页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/resourceManager")
	public String resourceManager(Model model, HttpServletRequest request) {
		return "base/resource/resourceManager";
	}

	/**
	 * 资源管理页面数据加载
	 * 
	 * @param request
	 * @param id
	 * @param sort
	 * @param order
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('resource:view')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getResources(HttpServletRequest request, @RequestParam(required = false) Long id,
			@RequestParam(required = false) String sort, @RequestParam(required = false) String order) {
		if (StringUtils.isBlank(sort)) {
			sort = "seq";
		}
		if (StringUtils.isBlank(order)) {
			order = "asc";
		}
		Order or = new Order(Direction.fromString(order), sort);
		Sort s = new Sort(or);
		List<Resource> resources = resourceService.listAuthorityMenus(s, id);
		List<TreeGrid> tgList = TreeGrid.resourceToTreeGrid(resources);
		return new ResponseEntity<String>(JSONObject.toJSONString(tgList), HttpStatus.OK);
	}

	/**
	 * 获取所有资源
	 * 
	 * @param request
	 * @param id
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('resource:view')")
	@RequestMapping(value = "/geAllResources", method = RequestMethod.GET)
	public ResponseEntity<String> geAllResources(HttpServletRequest request, @RequestParam(required = false) Long id,
			Model model) {
		Order or = new Order(Direction.fromString("asc"), "seq");
		Sort s = new Sort(or);
		List<Resource> resources = resourceService.listAuthorityMenus(s, id);
		List<Combobox> cbList = Combobox.resourceToCombobox(resources);
		cbList.stream().forEach(cb -> {
			if (id != null && cb.getId().equals(id))
				cb.setSelected(true);
		});
		model.addAttribute("id", id);
		return new ResponseEntity<String>(JSONObject.toJSONString(cbList), HttpStatus.OK);
	}

	/**
	 * 根据id获取资源父类
	 * 
	 * @param request
	 * @param id
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('resource:view')")
	@RequestMapping(value = "/getParentResources", method = RequestMethod.GET)
	public ResponseEntity<String> getParentResources(HttpServletRequest request,
			@RequestParam(required = false) Long id, Model model) {
		List<Resource> resources = resourceService.listParentResources(id);
		List<Combobox> cbList = Combobox.resourceToCombobox(resources);
		cbList.stream().forEach(cb -> {
			if (id != null && cb.getId().equals(id))
				cb.setSelected(true);
		});
		model.addAttribute("id", id);
		return new ResponseEntity<String>(JSONObject.toJSONString(cbList), HttpStatus.OK);
	}

	/**
	 * 保存或更新资源实体
	 * 
	 * @param request
	 * @param resource
	 * @return
	 * @throws BusinessException
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('resource:edit')")
	@RequestMapping(value = "/saveOrUpdateResource", method = RequestMethod.POST)
	public ResponseEntity<String> saveOrUpdateResource(HttpServletRequest request, @RequestBody Resource resource)
			throws BusinessException {
		if (resource.getId() != null) {
			if (resource.getParentId() != null && resource.getParentId().equals(resource.getId())) {
				throw new BusinessException("不能选取自己作为父集菜单");
			}

		} else {
			if (resource.getParentId() != null && resource.getType().equals(0)) {
				throw new BusinessException("最多只能形成3级菜单");
			}
		}
		resourceService.saveOrUpdate(resource);
		return new ResponseEntity<String>("success", HttpStatus.OK);

	}

	/**
	 * 删除资源
	 * 
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('resource:edit')")
	@RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	public ResponseEntity delResources(@PathVariable(required = false, value = "ids") @RequestBody Long[] ids) {
		resourceService.delete(ids);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 获取排序最大值
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('resource:view')")
	@RequestMapping(value = "/getMaxSeq", method = RequestMethod.GET)
	public ResponseEntity<Long> getMaxSeq(HttpServletRequest request) {
		return new ResponseEntity<Long>(resourceService.getMaxSeq(), HttpStatus.OK);
	}
}
