package com.kfwl.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kfwl.common.AduitEnum;
import com.kfwl.common.easyui.Menu;
import com.kfwl.entity.base.Resource;
import com.kfwl.entity.base.Role;
import com.kfwl.entity.base.User;
import com.kfwl.exception.impl.BusinessException;
import com.kfwl.service.base.UserService;

@Controller
public class IndexController extends BasicController {
	@Autowired
	UserService userService;

	@GetMapping("/oauth/user")
	@ResponseBody
	public Principal user(Principal principal) {
		return principal;
	}

	@RequestMapping(value = "/index")
	public String index(Model model, HttpServletRequest request) throws BusinessException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.getByUsername(userDetails.getUsername());
		List<Role> roles = user.getRoles();

		if (!user.getAduitStatus().equals(AduitEnum.PASS.ordinal())) {
			throw new BusinessException("该账号审核未通过，不允许登入，请联系管理员！");
		}
		if (null == roles || roles.size() == 0) {
			throw new BusinessException("该账号未分配角色，请联系管理员！");
		}
		List<Resource> resources = new ArrayList<>();
		for (Role role : roles) {
			resources.addAll(role.getResources());
		}
		List<Resource> parents = new ArrayList<>();
		resources.stream().filter(r -> r.getParentResource() != null).forEach(r -> parents.add(r.getParentResource()));
		resources.addAll(parents);
		resources = resources.stream().distinct().collect(Collectors.toList());
		resources.sort((Resource r1, Resource r2) -> r1.getSeq().compareTo(r2.getSeq()));
		Menu menu = Menu.getMenusData(resources);
		model.addAttribute("menu", menu);
		model.addAttribute("username", user.getUsername());
		return "index";
	}
}