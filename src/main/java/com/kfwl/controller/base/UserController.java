package com.kfwl.controller.base;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.kfwl.entity.base.User;
import com.kfwl.exception.impl.BusinessException;
import com.kfwl.service.base.RoleService;
import com.kfwl.service.base.UserService;
import com.kfwl.utils.PageUtil;

@Controller
@RequestMapping("/user")
public class UserController extends BasicController {

	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	/**
	 * 用户管理页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/userManager")
	public String userManager(Model model, HttpServletRequest request, HttpServletResponse response) {
		return "base/user/userManager";
	}

	/**
	 * 用户页面数据加载
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param username
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('user:view')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getUsers(HttpServletRequest request, @RequestParam(required = true) int page,
			@RequestParam(required = true) int rows, @RequestParam(required = true) String sort,
			@RequestParam(required = true) String order, @RequestParam(required = false) String username,
			@RequestParam(required = false) String dateFrom, @RequestParam(required = false) String dateTo) {
		Pageable pageable = new PageRequest(page - 1, rows, Sort.Direction.fromString(order), sort);
		Page<User> users = userService.pageUsers(pageable, username, dateFrom, dateTo);
		Map<String, Object> page2Map = PageUtil.page2Map(users);
		return new ResponseEntity<String>(JSONObject.toJSONString(page2Map), HttpStatus.OK);
	}

	/**
	 * 添加用户
	 * 
	 * @param request
	 * @param userVo
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('user:edit')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> addUser(HttpServletRequest request, @RequestBody User userVo)
			throws BusinessException {
		if (userService.getByUsername(userVo.getUsername()) != null) {
			throw new BusinessException("该用户名已存在");
		}
		User user = new User();
		List<Role> roles = roleService.listByIds(userVo.getRoleIds());
		user.setRoles(roles);
		user.setUsername(userVo.getUsername());
		user.setPassword(new BCryptPasswordEncoder(8).encode(userVo.getPassword()));
		userService.saveOrUpdate(user);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	/**
	 * 修改用户角色
	 * 
	 * @param request
	 * @param id
	 * @param roleIds
	 * @return
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('user:edit')")
	@RequestMapping(value = "/updateRole/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateRole(HttpServletRequest request,
			@PathVariable(required = true, value = "id") @RequestBody Long id,
			@RequestParam(required = true, value = "roleIds[]") @RequestBody Long[] roleIds) {
		User user = userService.getById(id);
		List<Role> role = roleService.listByIds(Arrays.asList(roleIds));
		user.setRoles(role);
		userService.saveOrUpdate(user);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('user:edit')")
	@RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@RequestParam(required = true) String oldPassword,
			@RequestParam(required = true) String newPassword) throws BusinessException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.getByUsername(userDetails.getUsername());
		if (!new BCryptPasswordEncoder(8).matches(oldPassword, user.getPassword())) {
			throw new BusinessException("原密码错误，请重新输入");
		}
		user.setPassword(new BCryptPasswordEncoder(8).encode(newPassword));
		userService.saveOrUpdate(user);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	/**
	 * 删除用户
	 * 
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('user:edit')")
	@RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	public ResponseEntity delUsers(@PathVariable(required = true, value = "ids") Long[] ids) {
		userService.delete(ids);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 审核用户
	 * 
	 * @param ids
	 * @param aduitStatus
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('user:aduit')")
	@RequestMapping(value = "/aduitUsers", method = RequestMethod.PUT)
	public ResponseEntity aduitUsers(@RequestParam(required = true, value = "ids[]") @RequestBody Long[] ids,
			@RequestParam(required = true) Integer aduitStatus) {
		List<User> users = userService.listByIds(ids);
		users.stream().forEach(u -> u.setAduitStatus(aduitStatus));
		userService.saveOrUpdate(users);
		return new ResponseEntity(HttpStatus.OK);
	}
}
