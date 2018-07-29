package com.kfwl.controller.xuhl;

import java.io.UnsupportedEncodingException;
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
import com.kfwl.common.dto.ExpressTrainDto;
import com.kfwl.controller.BasicController;
import com.kfwl.entity.xuhl.ExpressTrain;
import com.kfwl.exception.impl.NormalException;
import com.kfwl.service.xuhl.ExpressTrainService;
import com.kfwl.utils.ExcelUtil;
import com.kfwl.utils.PageUtil;

@Controller
@RequestMapping("/expressTrain")
public class ExpressTrainController extends BasicController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ExpressTrainController.class);

	@Autowired
	ExpressTrainService expressTrainService;

	/**
	 * 合并报表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "expressTrainPage")
	public String ExpressTrainPage() {
		return "xuhl/expressTrain/expressTrain";
	}

	/**
	 * 
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param skuCode
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('expressTrain:view')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getExpressTrains(@RequestParam(required = true) int page,
			@RequestParam(required = true) int rows, @RequestParam(required = true) String sort,
			@RequestParam(required = true) String order, @RequestParam(required = false) String skuCode,
			@RequestParam(required = false) String dateFrom, @RequestParam(required = false) String dateTo) {
		Pageable pageable = new PageRequest(page - 1, rows, Sort.Direction.fromString(order), sort);
		Page<ExpressTrain> ExpressTrains = expressTrainService.pageExpressTrains(pageable, skuCode, dateFrom, dateTo);
		Map<String, Object> page2Map = PageUtil.page2Map(ExpressTrains);
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
	@PreAuthorize("hasAnyAuthority('expressTrain:edit')")
	@RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	public ResponseEntity delExpressTrain(@PathVariable(required = false, value = "ids") @RequestBody Long[] ids) {
		expressTrainService.deleteExpressTrain(ids);
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
	@PreAuthorize("hasAnyAuthority('expressTrain:edit')")
	@RequestMapping(value = "/removeByCondition", method = RequestMethod.DELETE)
	public ResponseEntity removeByCondition(@RequestParam(required = false) String skuCode,
			@RequestParam(required = false) String dateFrom, @RequestParam(required = false) String dateTo) {
		expressTrainService.removeByCondition(skuCode, dateFrom, dateTo);
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
	@RequestMapping(value = "/uploadExpressTrain", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('expressTrain:edit')")
	public void uploadExpressTrain(@RequestParam("file") MultipartFile file, HttpServletResponse response)
			throws NormalException {
		List<ExpressTrainDto> personList = ExcelUtil.importExcel(file, 1, 1, ExpressTrainDto.class);
		List<ExpressTrain> noDataExpressTrains = new ArrayList<>();
		for (ExpressTrainDto ExpressTrainDto : personList) {
			ExpressTrain br = new ExpressTrain();
			BeanUtils.copyProperties(ExpressTrainDto, br);
			noDataExpressTrains.add(br);
		}
		if (noDataExpressTrains.size() > 0) {
			expressTrainService.insert(noDataExpressTrains);
		}
	}

}