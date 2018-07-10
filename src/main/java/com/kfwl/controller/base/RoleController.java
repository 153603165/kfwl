package com.kfwl.controller.base;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.kfwl.controller.BasicController;
import com.kfwl.entity.base.Role;
import com.kfwl.exception.impl.BusinessException;
import com.kfwl.service.base.RoleService;
import com.kfwl.utils.PageUtil;

@Controller
@RequestMapping("/role")
public class RoleController extends BasicController {

	@Autowired
	RoleService roleService;

	/**
	 * 角色管理页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/roleManager")
	public String roleManager(Model model, HttpServletRequest request) {
		return "base/role/roleManager";
	}

	/**
	 * 角色管理数据加载
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param roleName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('role:view')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getRoles(HttpServletRequest request, @RequestParam(required = true) int page,
			@RequestParam(required = true) int rows, @RequestParam(required = true) String sort,
			@RequestParam(required = true) String order, @RequestParam(required = false) String roleName) {
		Pageable pageable = new PageRequest(page - 1, rows, Sort.Direction.fromString(order), sort);
		Page<Role> roles = roleService.pageRoles(pageable, roleName);
		Map<String, Object> page2Map = PageUtil.page2Map(roles);
		return new ResponseEntity<String>(JSONObject.toJSONString(page2Map), HttpStatus.OK);
	}

	/**
	 * 获取所有角色
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('role:view')")
	@RequestMapping(value = "/getRoleComboboxs", method = RequestMethod.GET)
	public ResponseEntity<String> getRoleComboboxs() {
		List<Role> roles = roleService.listRoleComboboxs();
		return new ResponseEntity<String>(JSONObject.toJSONString(roles), HttpStatus.OK);
	}

	/**
	 * 保存或更新角色实体
	 * 
	 * @param request
	 * @param role
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('role:edit')")
	@RequestMapping(value = "/saveOrUpdateRole", method = RequestMethod.POST)
	public ResponseEntity<String> addRole(HttpServletRequest request, @RequestBody Role role) throws BusinessException {
		roleService.saveOrUpdate(role);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	/**
	 * 删除角色实体
	 * 
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('role:edit')")
	@RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	public ResponseEntity delRoles(@PathVariable(required = false, value = "ids") @RequestBody Long[] ids) {
		roleService.delete(ids);
		return new ResponseEntity(HttpStatus.OK);
	}

}
