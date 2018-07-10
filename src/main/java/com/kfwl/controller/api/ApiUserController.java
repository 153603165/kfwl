package com.kfwl.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.kfwl.controller.BasicController;
import com.kfwl.entity.base.User;
import com.kfwl.service.base.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "用户查询Api接口", description = "用户查询Api的RESTful接口")
@RestController
@RequestMapping("/api/user")
public class ApiUserController extends BasicController {

	@Autowired
	UserService userService;

	/**
	 * 根据id获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据用户Id查询用户")
	@PreAuthorize("hasAnyAuthority('user:view')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getUserById(@PathVariable(required = true, value = "id") Long id) {
		User user = userService.getById(id);
		if (user != null) {
			user.setPassword(null);
		}
		return new ResponseEntity<String>(JSONObject.toJSONString(user), HttpStatus.OK);
	}

}
