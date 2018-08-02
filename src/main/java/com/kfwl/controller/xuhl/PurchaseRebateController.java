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
import com.kfwl.common.dto.PurchaseRebateDto;
import com.kfwl.controller.BasicController;
import com.kfwl.entity.xuhl.PurchaseRebate;
import com.kfwl.exception.impl.NormalException;
import com.kfwl.service.xuhl.PurchaseRebateService;
import com.kfwl.utils.ExcelUtil;
import com.kfwl.utils.PageUtil;

@Controller
@RequestMapping("/purchaseRebate")
public class PurchaseRebateController extends BasicController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PurchaseRebateController.class);

	@Autowired
	PurchaseRebateService purchaseRebateService;

	/**
	 * 合并报表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "purchaseRebatePage")
	public String purchaseRebatePage() {
		return "xuhl/purchaseRebate/purchaseRebate";
	}

	/**
	 * 
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param skuCode
	 * @param type
	 * @param skuName
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('purchaseRebate:view')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getpurchaseRebates(@RequestParam(required = true) int page,
			@RequestParam(required = true) int rows, @RequestParam(required = true) String sort,
			@RequestParam(required = true) String order, @RequestParam(required = false) String skuCode,
			@RequestParam(required = false) Integer type, @RequestParam(required = false) String skuName,
			@RequestParam(required = false) String dateFrom, @RequestParam(required = false) String dateTo) {
		Pageable pageable = new PageRequest(page - 1, rows, Sort.Direction.fromString(order), sort);
		Page<PurchaseRebate> purchaseRebates = purchaseRebateService.pagePurchaseRebates(pageable, skuCode, skuName,
				type, dateFrom, dateTo);
		Map<String, Object> page2Map = PageUtil.page2Map(purchaseRebates);
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
	@PreAuthorize("hasAnyAuthority('purchaseRebate:edit')")
	@RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	public ResponseEntity delPurchaseRebate(@PathVariable(required = false, value = "ids") @RequestBody Long[] ids) {
		purchaseRebateService.deletePurchaseRebate(ids);
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
	@PreAuthorize("hasAnyAuthority('purchaseRebate:edit')")
	@RequestMapping(value = "/removeByCondition", method = RequestMethod.DELETE)
	public ResponseEntity removeByCondition(@RequestParam(required = false) String skuCode,
			@RequestParam(required = false) String skuName, @RequestParam(required = false) Integer type,
			@RequestParam(required = false) String dateFrom, @RequestParam(required = false) String dateTo) {
		purchaseRebateService.removeByCondition(skuCode, skuName, type, dateFrom, dateTo);
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
	@RequestMapping(value = "/uploadPurchaseRebate", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('purchaseRebate:edit')")
	public void uploadpurchaseRebate(@RequestParam("file") MultipartFile file, @RequestParam Integer type,
			HttpServletResponse response) throws NormalException {
		List<PurchaseRebateDto> personList = ExcelUtil.importExcel(file, 0, 1, PurchaseRebateDto.class);
		List<PurchaseRebate> noDatapurchaseRebates = new ArrayList<>();
		for (PurchaseRebateDto purchaseRebateDto : personList) {
			PurchaseRebate br = new PurchaseRebate();
			BeanUtils.copyProperties(purchaseRebateDto, br);
			br.setType(type);
			br.setNum(Math.abs(br.getNum()));
			br.setPurchaseRebatePrice(Math.abs(br.getPurchaseRebatePrice()));
			noDatapurchaseRebates.add(br);
		}
		if (noDatapurchaseRebates.size() > 0) {
			purchaseRebateService.insert(noDatapurchaseRebates);
		}
	}

}