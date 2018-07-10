package com.kfwl.controller.crm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.kfwl.common.dto.BillingRecordDto;
import com.kfwl.common.easyui.TBody;
import com.kfwl.controller.BasicController;
import com.kfwl.controller.crm.helper.BillingRecordHelper;
import com.kfwl.entity.crm.BillingRecord;
import com.kfwl.exception.impl.BusinessException;
import com.kfwl.exception.impl.NormalException;
import com.kfwl.service.crm.BillingRecordService;
import com.kfwl.utils.DateUtil;
import com.kfwl.utils.ExcelUtil;
import com.kfwl.utils.FileUtil;
import com.kfwl.utils.PageUtil;

@Controller
@RequestMapping("/billingRecord")
public class BillingRecordController extends BasicController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(BillingRecordController.class);

	private static SimpleDateFormat excelSf = new SimpleDateFormat("yyyy/MM");
	private static SimpleDateFormat excelTitleSf = new SimpleDateFormat("yyyy年MM月");
	private static SimpleDateFormat excelDataSf = new SimpleDateFormat("yyyy_MM");
	@Autowired
	BillingRecordHelper billingRecordHelper;
	@Autowired
	BillingRecordService billingRecordService;

	/**
	 * 合并报表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "billingRecordPage")
	public String billingRecordPage() {
		return "crm/billingRecord/billingRecord";
	}

	/**
	 * 合并报表页面数据加载
	 * 
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param regName
	 * @param company
	 * @param creditCode
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('billingRecord:view')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getBillingRecords(@RequestParam(required = true) int page,
			@RequestParam(required = true) int rows, @RequestParam(required = true) String sort,
			@RequestParam(required = true) String order, @RequestParam(required = false) String regName,
			@RequestParam(required = false) String company, @RequestParam(required = false) String creditCode,
			@RequestParam(required = false) String dateFrom, @RequestParam(required = false) String dateTo) {
		Pageable pageable = new PageRequest(page - 1, rows, Sort.Direction.fromString(order), sort);
		Page<BillingRecord> billingRecords = billingRecordService.listBillingRecords(pageable, regName, company,
				creditCode, dateFrom, dateTo);
		Map<String, Object> page2Map = PageUtil.page2Map(billingRecords);
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
	@PreAuthorize("hasAnyAuthority('billingRecord:edit')")
	@RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	public ResponseEntity delBillingRecord(@PathVariable(required = false, value = "ids") @RequestBody Long[] ids) {
		billingRecordService.deleteBillingRecord(ids);
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
	@PreAuthorize("hasAnyAuthority('billingRecord:edit')")
	@RequestMapping(value = "/removeByCondition", method = RequestMethod.DELETE)
	public ResponseEntity removeByCondition(@RequestParam(required = false) String regName,
			@RequestParam(required = false) String company, @RequestParam(required = false) String creditCode,
			@RequestParam(required = false) String dateFrom, @RequestParam(required = false) String dateTo) {
		billingRecordService.removeByCondition(regName, company, creditCode, dateFrom, dateTo);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 导入模板下载
	 * 
	 * @param req
	 * @param resp
	 * @param context
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/excelDemoDownload", method = { RequestMethod.POST, RequestMethod.GET })
	public void downLoadFile(HttpServletRequest request, HttpServletResponse response) {
		String fileName = "合并系统导入模板.xls";
		FileUtil.downloadFile(response, request, fileName);
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
	@RequestMapping(value = "/uploadBillingRecord", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('billingRecord:edit')")
	public void uploadBillingRecord(@RequestParam("file") MultipartFile file, HttpServletResponse response)
			throws NormalException {
		List<BillingRecordDto> personList = ExcelUtil.importExcel(file, 1, 1, BillingRecordDto.class);
		List<BillingRecord> noDataBillingRecords = new ArrayList<>();
		for (BillingRecordDto billingRecordDto : personList) {
			BillingRecord br = new BillingRecord();
			BeanUtils.copyProperties(billingRecordDto, br);
			if (billingRecordService.getDistinctBillingRecord(br) == null) {
				noDataBillingRecords.add(br);
			}
		}
		if (noDataBillingRecords.size() > 0) {
			billingRecordService.insert(noDataBillingRecords);
		}
	}

	/**
	 * ------------导出页面操作------------
	 */
	@RequestMapping(value = "exportBillingRecordPage")
	public String exportBillingRecordPage() {
		return "crm/billingRecord/exportBillingRecord";
	}

	/**
	 * 动态表格页面数据
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('billingRecord:view')")
	@RequestMapping(value = "/exportBillingRecordDatagrId", method = RequestMethod.GET)
	public ResponseEntity<String> exportBillingRecordDatagrId(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) String startTime, @RequestParam(required = true) String endTime,
			@RequestParam(required = false) String regName, @RequestParam(required = false) String companyName,
			@RequestParam(required = false) Double totalTaxAmount) {
		List<Map<String, Object>> exportBillingRecordExcelData = new ArrayList<>();
		try {
			exportBillingRecordExcelData = billingRecordHelper.exportBillingRecordExcelData(startTime, endTime,
					companyName, totalTaxAmount, regName);
		} catch (ParseException e) {
			try {
				throw new BusinessException(e.getMessage());
			} catch (BusinessException e1) {
				e1.printStackTrace();
			}
		}
		return new ResponseEntity<String>(JSONObject.toJSONString(exportBillingRecordExcelData), HttpStatus.OK);

	}

	/**
	 * 获取表格title
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('billingRecord:view')")
	@RequestMapping(value = "billingRecordExportTitle")
	public ResponseEntity<String> billingRecordExportTitle(@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime) throws Exception {
		Map<String, List<TBody>> result = new HashMap<String, List<TBody>>();
		List<TBody> ths = new ArrayList<TBody>();
		List<TBody> thsfix = new ArrayList<TBody>();
		int monthLength = DateUtil.getMonthSubtraction(startTime, endTime) + 1;
		TBody tBody = new TBody("regName", "法人", 55, "center");
		TBody tBody1 = new TBody("individualName", "个体名称", 140, "center");
		TBody tBody2 = new TBody("creditCode", "信用代码", 150, "center");
		TBody tBody3 = new TBody("companyName", "所属公司", 140, "center");
		thsfix.add(tBody);
		thsfix.add(tBody1);
		thsfix.add(tBody2);
		thsfix.add(tBody3);
		for (int i = 0; i < monthLength; i++) {
			Calendar tempTime = Calendar.getInstance();
			tempTime.setTime(excelSf.parse(startTime));
			tempTime.add(Calendar.MONTH, i);
			String localDate = excelDataSf.format(tempTime.getTime());
			String localDateCh = excelTitleSf.format(tempTime.getTime());
			TBody tBodyTemp1 = new TBody("taxAmount_" + localDate, localDateCh + "含税金额", 125, "right");
			TBody tBodyTemp2 = new TBody("noTaxAmount_" + localDate, localDateCh + "不含税金额", 135, "right");
			TBody tBodyTemp3 = new TBody("tax_" + localDate, localDateCh + "增值税税金3%", 150, "right");
			ths.add(tBodyTemp1);
			ths.add(tBodyTemp2);
			ths.add(tBodyTemp3);
		}
		TBody tBody4 = new TBody("totalTaxAmount", "总含税金额", 80, "right");
		TBody tBody5 = new TBody("totalNoTaxAmount", "总不含税金额", 80, "right");
		TBody tBody6 = new TBody("totalTax", "总增值税合计", 80, "right");
		TBody tBody7 = new TBody("individualTaxAndUrbanConstruction", "个税和城建小计", 95, "right");
		TBody tBody8 = new TBody("individualTax", "个人所得税合计1.5%", 120, "right");
		TBody tBody9 = new TBody("urbanConstruction", "城建税合计7%", 90, "right");
		TBody tBody10 = new TBody("education", "教育附加3%", 80, "right");
		TBody tBody11 = new TBody("localEducation", "地方教育附加2%", 120, "right");
		TBody tBody12 = new TBody("jointTaxOfLandTax", "总地税合计税金", 95, "right");
		ths.add(tBody4);
		ths.add(tBody5);
		ths.add(tBody6);
		ths.add(tBody7);
		ths.add(tBody8);
		ths.add(tBody9);
		ths.add(tBody10);
		ths.add(tBody11);
		ths.add(tBody12);
		result.put("ths", ths);
		result.put("thsfix", thsfix);
		return new ResponseEntity<String>(JSONObject.toJSONString(result), HttpStatus.OK);
	}

	/**
	 * 下载报表
	 * 
	 * @param request
	 * @param response
	 * @param startTime
	 * @param endTime
	 * @param regName
	 * @param companyName
	 * @param totalTaxAmount
	 * @throws ParseException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('billingRecord:view')")
	@RequestMapping(value = "exportBillingRecordExcel")
	public void exportBillingRecordExcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) String startTime, @RequestParam(required = true) String endTime,
			@RequestParam(required = false) String regName, @RequestParam(required = false) String companyName,
			@RequestParam(required = false) Double totalTaxAmount) throws ParseException {
		// 生成提示信息
		String excelFileName = "财务报表_" + excelTitleSf.format(excelSf.parse(startTime)) + "-"
				+ excelTitleSf.format(excelSf.parse(endTime));
		try {
			regName = URLDecoder.decode(regName, "utf-8");
			companyName = URLDecoder.decode(companyName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 插入各月份数据
		int monthLength = DateUtil.getMonthSubtraction(startTime, endTime) + 1;
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		listResult = billingRecordHelper.exportBillingRecordExcelData(startTime, endTime, companyName, totalTaxAmount,
				regName);
		String[] title = null;
		if (listResult.size() > 0) {
			title = new String[23 + (6 * monthLength)];
			title[0] = "序号";
			title[1] = "法人";
			title[2] = "个体名称";
			title[3] = "信用代码";
			title[4] = "所属公司";
			for (int i = 0; i < monthLength; i++) {
				Calendar tempTime = Calendar.getInstance();
				tempTime.setTime(excelSf.parse(startTime));
				tempTime.add(Calendar.MONTH, i);
				String localDate = excelTitleSf.format(tempTime.getTime());

				title[5 + i * 6] = localDate + "含税金额";
				title[6 + i * 6] = localDate + "含税金额合计";
				title[7 + i * 6] = localDate + "不含税金额";
				title[8 + i * 6] = localDate + "不含税金额合计";
				title[9 + i * 6] = localDate + "增值税税金3%";
				title[10 + i * 6] = localDate + "增值税税金3%合计";
			}
			title[5 + 6 * monthLength] = "总含税金额";
			title[6 + 6 * monthLength] = "总含税金额汇总";
			title[7 + 6 * monthLength] = "总不含税金额";
			title[8 + 6 * monthLength] = "总不含税金额汇总";
			title[9 + 6 * monthLength] = "总增值税合计";
			title[10 + 6 * monthLength] = "总增值税合计汇总";
			title[11 + 6 * monthLength] = "个税和城建小计";
			title[12 + 6 * monthLength] = "个税和城建小计汇总";
			title[13 + 6 * monthLength] = "个人所得税合计1.5%";
			title[14 + 6 * monthLength] = "个人所得税合计1.5%汇总";
			title[15 + 6 * monthLength] = "城建税合计7%";
			title[16 + 6 * monthLength] = "城建税合计7%汇总";
			title[17 + 6 * monthLength] = "教育附加3%";
			title[18 + 6 * monthLength] = "教育附加3%汇总";
			title[19 + 6 * monthLength] = "地方教育附加2%";
			title[20 + 6 * monthLength] = "地方教育附加2%汇总";
			title[21 + 6 * monthLength] = "总地税合计税金";
			title[22 + 6 * monthLength] = "总地税合计税金汇总";
		} else {
			title = new String[0];
		}
		billingRecordHelper.isUploadInfoExcel(request, response, startTime, listResult, title, monthLength,
				excelFileName);

	}

}