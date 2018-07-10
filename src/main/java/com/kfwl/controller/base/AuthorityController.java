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
import com.kfwl.entity.base.Authority;
import com.kfwl.service.base.AuthorityService;
import com.kfwl.utils.PageUtil;

@Controller
@RequestMapping("/authority")
public class AuthorityController extends BasicController {
	@Autowired
	AuthorityService authorityService;

	/**
	 * 权限管理页面
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/authorityManager")
	public String authorityManager(Model model, HttpServletRequest request) {
		return "base/authority/authorityManager";
	}

	/**
	 * 权限管理页面数据加载
	 * 
	 * @param request
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param authName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('authority:view')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getAuthorityPages(HttpServletRequest request, @RequestParam(required = true) int page,
			@RequestParam(required = true) int rows, @RequestParam(required = true) String sort,
			@RequestParam(required = true) String order, @RequestParam(required = false) String authName) {
		Pageable pageable = new PageRequest(page - 1, rows, Sort.Direction.fromString(order), sort);
		Page<Authority> authoritys = authorityService.pageAuthorityPage(pageable, authName);
		Map<String, Object> page2Map = PageUtil.page2Map(authoritys);
		return new ResponseEntity<String>(JSONObject.toJSONString(page2Map), HttpStatus.OK);
	}

	/**
	 * 获取所有权限
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('authority:view')")
	@RequestMapping(value = "/getAuthoritys", method = RequestMethod.GET)
	public ResponseEntity<String> getAuthoritys(HttpServletRequest request) {
		List<Authority> authoritys = authorityService.listAuthoritys();
		return new ResponseEntity<String>(JSONObject.toJSONString(authoritys), HttpStatus.OK);
	}

	/**
	 * 保存/更新权限实体
	 * 
	 * @param request
	 * @param authority
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('authority:edit')")
	@RequestMapping(value = "/saveOrUpdateAuthority", method = RequestMethod.POST)
	public ResponseEntity<String> saveOrUpdateAuthority(HttpServletRequest request, @RequestBody Authority authority) {
		authorityService.saveOrUpdate(authority);
		return new ResponseEntity<String>("success", HttpStatus.OK);

	}

	/**
	 * 删除权限
	 * 
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('authority:edit')")
	@RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	public ResponseEntity delAuthoritys(@PathVariable(required = false, value = "ids") @RequestBody Long[] ids) {
		authorityService.delete(ids);
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
	@PreAuthorize("hasAnyAuthority('authority:view')")
	@RequestMapping(value = "/getMaxSeq", method = RequestMethod.GET)
	public ResponseEntity<Long> getMaxSeq(HttpServletRequest request) {
		return new ResponseEntity<Long>(authorityService.getMaxSeq(), HttpStatus.OK);
	}
}
