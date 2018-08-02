package com.kfwl.controller.xuhl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.kfwl.common.dto.CouponDto;
import com.kfwl.controller.BasicController;
import com.kfwl.entity.xuhl.Coupon;
import com.kfwl.exception.impl.NormalException;
import com.kfwl.service.xuhl.CouponService;
import com.kfwl.utils.ExcelUtil;
import com.kfwl.utils.PageUtil;

@Controller
@RequestMapping("/coupon")
public class CouponController extends BasicController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(CouponController.class);

	@Autowired
	CouponService couponService;

	/**
	 * 合并报表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "couponPage")
	public String CouponPage() {
		return "xuhl/coupon/coupon";
	}

	/**
	 * 
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param skuCode
	 * @param skuName
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('coupon:view')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getCoupons(@RequestParam(required = true) int page,
			@RequestParam(required = true) int rows, @RequestParam(required = true) String sort,
			@RequestParam(required = true) String order, @RequestParam(required = false) String skuCode,
			@RequestParam(required = false) String skuName, @RequestParam(required = false) String dateFrom,
			@RequestParam(required = false) String dateTo) {
		Pageable pageable = new PageRequest(page - 1, rows, Sort.Direction.fromString(order), sort);
		Page<Coupon> Coupons = couponService.pageCoupons(pageable, skuCode, skuName, dateFrom, dateTo);
		Map<String, Object> page2Map = PageUtil.page2Map(Coupons);
		return new ResponseEntity<String>(JSONObject.toJSONString(page2Map), HttpStatus.OK);
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('coupon:edit')")
	@RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	public ResponseEntity delCoupon(@PathVariable(required = false, value = "ids") @RequestBody Long[] ids) {
		couponService.deleteCoupon(ids);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 根据查询条件删除数据
	 * 
	 * @param regName
	 * @param company
	 * @param creditCode
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('coupon:edit')")
	@RequestMapping(value = "/removeByCondition", method = RequestMethod.DELETE)
	public ResponseEntity removeByCondition(@RequestParam(required = false) String skuCode,
			@RequestParam(required = false) String skuName, @RequestParam(required = false) String dateFrom,
			@RequestParam(required = false) String dateTo) {
		couponService.removeByCondition(skuCode, skuName, dateFrom, dateTo);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 文件上传具体实现方法（单文件上传）
	 *
	 * @param file
	 * @return
	 * 
	 * @author 单红宇(CSDN CATOOP)
	 * @throws NormalException
	 * @create 2017年3月11日
	 */
	@RequestMapping(value = "/uploadCoupon", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('coupon:edit')")
	public void uploadCoupon(@RequestParam("file") MultipartFile file, HttpServletResponse response)
			throws NormalException {
		List<CouponDto> personList = ExcelUtil.importExcel(file, 0, 1, CouponDto.class);
		List<Coupon> noDataCoupons = new ArrayList<>();
		for (CouponDto CouponDto : personList) {
			Coupon br = new Coupon();
			BeanUtils.copyProperties(CouponDto, br);
			noDataCoupons.add(br);
		}
		if (noDataCoupons.size() > 0) {
			couponService.insert(noDataCoupons);
		}
	}

}