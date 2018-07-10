package com.kfwl.controller.crm;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
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
import com.google.common.collect.Lists;
import com.kfwl.common.CategoryEnum;
import com.kfwl.common.dto.PreBillingRecordDto;
import com.kfwl.common.easyui.TBody;
import com.kfwl.controller.BasicController;
import com.kfwl.controller.crm.helper.PreBillingRecordHelper;
import com.kfwl.entity.crm.PreBillingRecord;
import com.kfwl.exception.impl.NormalException;
import com.kfwl.service.crm.PreBillingRecordService;
import com.kfwl.utils.DateUtil;
import com.kfwl.utils.ExcelUtil;
import com.kfwl.utils.FileUtil;
import com.kfwl.utils.PageUtil;

@Controller
@RequestMapping("/preBillingRecord")
public class PreBillingRecordController extends BasicController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PreBillingRecordController.class);
	private static DecimalFormat df = new DecimalFormat("######0.00");

	private static SimpleDateFormat excelSf = new SimpleDateFormat("yyyy/MM");
	private static SimpleDateFormat excelTitleSf = new SimpleDateFormat("yyyy年MM月");
	private static SimpleDateFormat excelDataSf = new SimpleDateFormat("yyyy_MM");

	private final static Double TaxLt90000 = 0.036;
	private final static Double TaxGE90000Lt300000 = 0.049;
	private final static Double TaxGE300000 = 0.05;

	private final static Double ZhongKa = 1.03;
	private final static Double ZhongKaTaxLt90000 = 0.035;
	@Autowired
	PreBillingRecordHelper preBillingRecordHelper;
	@Autowired
	PreBillingRecordService preBillingRecordService;

	/**
	 * 收费报表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "preBillingRecordPage")
	public String preBillingRecordPage() {
		return "crm/preBillingRecord/preBillingRecord";
	}

	/**
	 * 收费报表页面数据加载
	 * 
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param regName
	 * @param company
	 * @param creditCode
	 * @param category
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('preBillingRecord:view')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<String> getPreBillingRecords(@RequestParam(required = true) int page,
			@RequestParam(required = true) int rows, @RequestParam(required = true) String sort,
			@RequestParam(required = true) String order, @RequestParam(required = false) String regName,
			@RequestParam(required = false) String company, @RequestParam(required = false) String creditCode,
			@RequestParam(required = false) String category, @RequestParam(required = false) String dateFrom,
			@RequestParam(required = false) String dateTo) {
		Pageable pageable = new PageRequest(page - 1, rows, Sort.Direction.fromString(order), sort);
		Page<PreBillingRecord> billingRecords = preBillingRecordService.listPreBillingRecords(pageable, category,
				regName, company, creditCode, dateFrom, dateTo);
		Map<String, Object> page2Map = PageUtil.page2Map(billingRecords);
		return new ResponseEntity<String>(JSONObject.toJSONString(page2Map), HttpStatus.OK);
	}

	/**
	 * 获取类别下拉框
	 * 
	 * @return
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('preBillingRecord:view')")
	@RequestMapping(value = "/categoryCombobox", method = RequestMethod.GET)
	public ResponseEntity<String> categoryCombobox() {
		List<Map<String, String>> cList = Lists.newArrayList();
		for (CategoryEnum ct : CategoryEnum.values()) {
			Map<String, String> map = new HashMap<>();
			map.put("id", ct.getValue());
			map.put("text", ct.getDescription());
			cList.add(map);
		}
		return new ResponseEntity<String>(JSONObject.toJSONString(cList), HttpStatus.OK);
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('preBillingRecord:edit')")
	@RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	public ResponseEntity delPreBillingRecord(@PathVariable(required = false, value = "ids") @RequestBody Long[] ids) {
		preBillingRecordService.deletePreBillingRecord(ids);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * 根据查询条件删除数据
	 * 
	 * @param regName
	 * @param company
	 * @param creditCode
	 * @param category
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('preBillingRecord:edit')")
	@RequestMapping(value = "/removeByCondition", method = RequestMethod.DELETE)
	public ResponseEntity removeByCondition(@RequestParam(required = false) String regName,
			@RequestParam(required = false) String company, @RequestParam(required = false) String creditCode,
			@RequestParam(required = false) String category, @RequestParam(required = false) String dateFrom,
			@RequestParam(required = false) String dateTo) {
		preBillingRecordService.removeByCondition(category, regName, company, creditCode, dateFrom, dateTo);
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
		String fileName = "收费系统导入模板.xls";
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
	@RequestMapping(value = "/uploadPreBillingRecord", method = RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('preBillingRecord:edit')")
	public void uploadPreBillingRecord(@RequestParam("file") MultipartFile file, HttpServletResponse response)
			throws NormalException {
		List<PreBillingRecordDto> personList = ExcelUtil.importExcel(file, 1, 1, PreBillingRecordDto.class);
		List<PreBillingRecord> noDataBillingRecords = new ArrayList<>();
		for (PreBillingRecordDto PreBillingRecordDto : personList) {
			PreBillingRecord br = new PreBillingRecord();
			BeanUtils.copyProperties(PreBillingRecordDto, br);
			if (preBillingRecordService.getDistinctPreBillingRecord(br) == null) {
				noDataBillingRecords.add(br);
			}
		}
		if (noDataBillingRecords.size() > 0) {
			preBillingRecordService.insert(noDataBillingRecords);
		}
	}

	/**
	 * ------------导出页面操作------------
	 */
	@RequestMapping(value = "exportPreBillingRecordPage")
	public String exportPreBillingRecordPage() {
		return "crm/preBillingRecord/exportPreBillingRecord";
	}

	/**
	 * 动态表格页面数据
	 * 
	 * @throws ParseException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('preBillingRecord:view')")
	@RequestMapping(value = "/exportPreBillingRecordDatagrId", method = RequestMethod.GET)
	public ResponseEntity<String> exportPreBillingRecordDatagrId(HttpServletRequest request,
			HttpServletResponse response, @RequestParam(required = true) String startTime,
			@RequestParam(required = true) String endTime, @RequestParam(required = false) String legalPerson,
			@RequestParam(required = false) String companyName, @RequestParam(required = false) String category,
			@RequestParam(required = false) Double totalTaxAmount) throws ParseException {
		List<Map<String, Object>> exportBillingRecordExcelData = preBillingRecordHelper
				.exportPreBillingRecordExcelData(startTime, endTime, category, companyName, legalPerson);
		try {
			for (Map<String, Object> map : exportBillingRecordExcelData) {
				Calendar tempTime = Calendar.getInstance();
				tempTime.setTime(excelSf.parse(startTime));
				String oneDate = excelDataSf.format(tempTime.getTime());

				Double oneSendAmount = map.get("sendAmount_" + oneDate) != null
						? Double.parseDouble(map.get("sendAmount_" + oneDate) + "")
						: 0;
				map.put("sendAmount_" + oneDate, df.format(oneSendAmount));

				Double oneSendAmountTaxLt9W = 0.0;
				Double oneSendAmountTaxGE9WLT30W = 0.0;
				Double oneSendAmountTaxGE30W = 0.0;
				if (oneSendAmount < 90000) {
					if ("众卡".equals(map.get("category"))) {
						oneSendAmountTaxLt9W = oneSendAmount * ZhongKaTaxLt90000;
					} else {
						oneSendAmountTaxLt9W = oneSendAmount * TaxLt90000;
					}
				} else if (oneSendAmount >= 90000 && oneSendAmount < 300000) {
					oneSendAmountTaxGE9WLT30W = oneSendAmount * TaxGE90000Lt300000;
				} else {
					oneSendAmountTaxGE30W = oneSendAmount * TaxGE300000;
				}
				map.put("sendAmountTaxLT9W_" + oneDate, df.format(oneSendAmountTaxLt9W));
				map.put("sendAmountTaxGE9WLT30W_" + oneDate, df.format(oneSendAmountTaxGE9WLT30W));
				map.put("sendAmountTaxGE30W_" + oneDate, df.format(oneSendAmountTaxGE30W));

				Double oneForecastTax = oneSendAmountTaxLt9W + oneSendAmountTaxGE9WLT30W + oneSendAmountTaxGE30W;
				map.put("forecastTax_" + oneDate, df.format(oneForecastTax));

				Double oneInvoiceValue = 0.0;
				if ("众卡".equals(map.get("category"))) {
					oneInvoiceValue = oneSendAmount / ZhongKa + oneForecastTax;
				} else {
					oneInvoiceValue = oneSendAmount + oneForecastTax;
				}

				map.put("invoiceValue_" + oneDate, df.format(oneInvoiceValue));

				// 插入各月份数据
				int monthLength = DateUtil.getMonthSubtraction(startTime, endTime) + 1;

				// 拼接月份税
				for (int i = 0; i < monthLength - 1; i++) {
					tempTime.add(Calendar.MONTH, 1);
					String localDate = excelDataSf.format(tempTime.getTime());
					// 当月发来金额劳务报酬
					Double thisSendAmount = map.get("sendAmount_" + localDate) == null ? 0
							: Double.parseDouble(
									df.format(Double.parseDouble(map.get("sendAmount_" + localDate) + "")));
					map.put("sendAmount_" + localDate, df.format(thisSendAmount));

					// 合计发来金额劳务报酬
					Double totalSendAmount = 0.0;
					for (int z = 0; z <= i + 1; z++) {
						Calendar temporaryTime = Calendar.getInstance();
						temporaryTime.setTime(excelSf.parse(startTime));
						temporaryTime.add(Calendar.MONTH, z);
						String temporaryDate = excelDataSf.format(temporaryTime.getTime());
						totalSendAmount += map.get("sendAmount_" + temporaryDate) == null ? 0
								: Double.parseDouble(
										df.format(Double.parseDouble(map.get("sendAmount_" + temporaryDate) + "")));
					}
					map.put("sendAmount_" + oneDate + "_" + localDate, df.format(totalSendAmount));

					// 合计预收税费9万以下收费3.6%
					Double totalSendAmountTaxLT9 = 0.0;
					if ("众卡".equals(map.get("category"))) {
						totalSendAmountTaxLT9 = totalSendAmount < 90000
								? Double.parseDouble(df.format(totalSendAmount * ZhongKaTaxLt90000))
								: 0;
					} else {
						totalSendAmountTaxLT9 = totalSendAmount < 90000
								? Double.parseDouble(df.format(totalSendAmount * TaxLt90000))
								: 0;
					}
					map.put("sendAmountTaxLT9W_" + oneDate + "_" + localDate, df.format(totalSendAmountTaxLT9));

					// 合计预收税费9以上-30万收费4.90%
					Double totalSendAmountTaxGE9LT30 = totalSendAmount >= 90000 && totalSendAmount < 300000
							? Double.parseDouble(df.format(totalSendAmount * TaxGE90000Lt300000))
							: 0;
					map.put("sendAmountTaxGE9WLT30W_" + oneDate + "_" + localDate,
							df.format(totalSendAmountTaxGE9LT30));

					// 合计预收税费30万以上收费5%
					Double totalSendAmountTaxLE30 = totalSendAmount >= 300000
							? Double.parseDouble(df.format(totalSendAmount * TaxGE300000))
							: 0;
					map.put("sendAmountTaxGE30W_" + oneDate + "_" + localDate, df.format(totalSendAmountTaxLE30));

					// 汇总预收税费合计
					Double totalSendAmountTax = totalSendAmountTaxLT9 + totalSendAmountTaxGE9LT30
							+ totalSendAmountTaxLE30;

					map.put("forecastTax_" + oneDate + "_" + localDate, df.format(totalSendAmountTax));

					// 冲往月预售税费
					Double afterSendAmountTax = 0.0;
					if (i == 0) {
						afterSendAmountTax = Double.parseDouble(map.get("forecastTax_" + oneDate).toString());
						map.put("lastForecastTax_" + oneDate, df.format(afterSendAmountTax));
					} else {
						tempTime.add(Calendar.MONTH, -1);
						String tempDate = excelDataSf.format(tempTime.getTime());
						tempTime.add(Calendar.MONTH, 1);
						afterSendAmountTax = Double
								.parseDouble(map.get("forecastTax_" + oneDate + "_" + tempDate).toString());
						map.put("lastForecastTax_" + oneDate + "_" + tempDate, df.format(afterSendAmountTax));
					}

					// 当月实际收税费合计
					Double localSendAmountTax = totalSendAmountTax - afterSendAmountTax;
					map.put("actualTaxCollection_" + localDate, df.format(localSendAmountTax));

					// 当月开票金额
					Double thisKaiPiao = 0.0;
					if ("众卡".equals(map.get("category"))) {
						thisKaiPiao = localSendAmountTax + thisSendAmount / ZhongKa;
					} else {
						thisKaiPiao = localSendAmountTax + thisSendAmount;
					}
					map.put("invoiceValue_" + localDate, df.format(thisKaiPiao));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(JSONObject.toJSONString(exportBillingRecordExcelData), HttpStatus.OK);

	}

	/**
	 * 获取数据title
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('preBillingRecord:view')")
	@RequestMapping(value = "preBillingRecordExportTitle")
	public ResponseEntity<String> preBillingRecordExportTitle(@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime) throws Exception {
		Map<String, List<TBody>> result = new HashMap<String, List<TBody>>();
		List<TBody> ths = new ArrayList<TBody>();
		List<TBody> thsfix = new ArrayList<TBody>();

		int monthLength = DateUtil.getMonthSubtraction(startTime, endTime) + 1;
		TBody tBody = new TBody("orderNo", "序号", 55, "center");
		TBody tBody1 = new TBody("companyName", "所属公司", 145, "center");
		TBody tBody2 = new TBody("plateNum", "车辆", 70, "center");
		TBody tBody3 = new TBody("legalPerson", "法人", 55, "center");
		TBody tBody4 = new TBody("individualName", "个体名称", 140, "center");
		TBody tBody5 = new TBody("creditCode", "信用代码", 150, "center");
		TBody tBody6 = new TBody("mulAccount", "九江银行多级账号", 102, "center");
		thsfix.add(tBody);
		thsfix.add(tBody1);
		thsfix.add(tBody2);
		thsfix.add(tBody3);
		thsfix.add(tBody4);
		thsfix.add(tBody5);
		thsfix.add(tBody6);

		Calendar tempTime = Calendar.getInstance();
		tempTime.setTime(excelSf.parse(startTime));
		String localDate = excelDataSf.format(tempTime.getTime());
		String localDateCh = excelTitleSf.format(tempTime.getTime());
		TBody tBody7 = new TBody("sendAmount_" + localDate, localDateCh + "发来金额劳务报酬", 173, "center");
		TBody tBody8 = new TBody("sendAmountTaxLT9W_" + localDate, localDateCh + "预收税费9万以下收费3.6%", 220, "center");
		TBody tBody9 = new TBody("sendAmountTaxGE9WLT30W_" + localDate, localDateCh + "预收税费9以上-30万收费4.90%", 240,
				"center");
		TBody tBody10 = new TBody("sendAmountTaxGE30W_" + localDate, localDateCh + "30万以上收费5%", 167, "center");
		TBody tBody11 = new TBody("forecastTax_" + localDate, localDateCh + "预收税费合计", 148, "center");
		TBody tBody12 = new TBody("invoiceValue_" + localDate, localDateCh + "开票金额", 122, "center");
		ths.add(tBody7);
		ths.add(tBody8);
		ths.add(tBody9);
		ths.add(tBody10);
		ths.add(tBody11);
		ths.add(tBody12);

		for (int i = 0; i < monthLength - 1; i++) {
			tempTime.add(Calendar.MONTH, 1);
			String tempDate = excelDataSf.format(tempTime.getTime());
			String tempLocalDateCh = excelTitleSf.format(tempTime.getTime());

			TBody tBodyTemp1 = new TBody("sendAmount_" + tempDate, tempLocalDateCh + "发来金额劳务报酬", 172, "center");
			TBody tBodyTemp2 = new TBody("sendAmount_" + localDate + "_" + tempDate,
					localDateCh + "-" + tempLocalDateCh + "发来金额劳务报酬", 240, "center");
			TBody tBodyTemp3 = new TBody("sendAmountTaxLT9W_" + localDate + "_" + tempDate,
					localDateCh + "-" + tempLocalDateCh + "预收税费9万以下收费3.6%", 290, "center");
			TBody tBodyTemp4 = new TBody("sendAmountTaxGE9WLT30W_" + localDate + "_" + tempDate,
					localDateCh + "-" + tempLocalDateCh + "预收税费9以上-30万收费4.90%", 313, "center");
			TBody tBodyTemp5 = new TBody("sendAmountTaxGE30W_" + localDate + "_" + tempDate,
					localDateCh + "-" + tempLocalDateCh + "30万以上收费5%", 220, "center");
			TBody tBodyTemp6 = new TBody("forecastTax_" + localDate + "_" + tempDate,
					localDateCh + "-" + tempLocalDateCh + "预收税费合计", 240, "center");
			TBody tBodyTemp7 = null;
			if (i == 0) {
				tBodyTemp7 = new TBody("lastForecastTax_" + localDate, "冲" + localDateCh + "预收税费", 135, "center");
			} else {
				tempTime.add(Calendar.MONTH, -1);
				String tempDate2 = excelDataSf.format(tempTime.getTime());
				String tempLocalDateCh2 = excelTitleSf.format(tempTime.getTime());
				tempTime.add(Calendar.MONTH, 1);
				tBodyTemp7 = new TBody("lastForecastTax_" + localDate + "_" + tempDate2,
						"冲" + localDateCh + "-" + tempLocalDateCh2 + "预收税费", 230, "center");
			}
			TBody tBodyTemp8 = new TBody("actualTaxCollection_" + tempDate, tempLocalDateCh + "实际收税费合计", 160, "center");
			TBody tBodyTemp9 = new TBody("invoiceValue_" + tempDate, tempLocalDateCh + "开票金额", 125, "right");

			ths.add(tBodyTemp1);
			ths.add(tBodyTemp2);
			ths.add(tBodyTemp3);
			ths.add(tBodyTemp4);
			ths.add(tBodyTemp5);
			ths.add(tBodyTemp6);
			ths.add(tBodyTemp7);
			ths.add(tBodyTemp8);
			ths.add(tBodyTemp9);
		}
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
	 * @param legalPerson
	 * @param companyName
	 * @param category
	 * @throws ParseException
	 */
	@ResponseBody
	@PreAuthorize("hasAnyAuthority('preBillingRecord:view')")
	@RequestMapping(value = "exportPreBillingRecordExcel")
	public void exportPreBillingRecordExcel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) String startTime, @RequestParam(required = true) String endTime,
			@RequestParam(required = false) String legalPerson, @RequestParam(required = false) String companyName,
			@RequestParam(required = false) String category) throws ParseException {
		// 生成提示信息
		String excelFileName = "财务报表_" + excelTitleSf.format(excelSf.parse(startTime)) + "-"
				+ excelTitleSf.format(excelSf.parse(endTime));
		// 插入各月份数据
		int monthLength = DateUtil.getMonthSubtraction(startTime, endTime) + 1;
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		listResult = preBillingRecordHelper.exportPreBillingRecordExcelData(startTime, endTime, category, companyName,
				legalPerson);
		String[] title = null;
		if (listResult.size() > 0) {
			title = new String[13 + (9 * (monthLength - 1))];
			title[0] = "序号";
			title[1] = "所属公司";
			title[2] = "车牌";
			title[3] = "法人";
			title[4] = "个体名称";
			title[5] = "信用代码";
			title[6] = "九江银行多级账号";
			Calendar tempTime = Calendar.getInstance();
			tempTime.setTime(excelSf.parse(startTime));
			String localDate = excelTitleSf.format(tempTime.getTime());
			title[7] = localDate + "发来金额劳务报酬";
			title[8] = localDate + "预收税费9万以下收费3.6%";
			title[9] = localDate + "预收税费9以上-30万收费4.90%";
			title[10] = localDate + "30万以上收费5%";
			title[11] = localDate + "预收税费合计";
			title[12] = localDate + "开票金额";

			for (int i = 0; i < monthLength - 1; i++) {
				tempTime.add(Calendar.MONTH, 1);
				String tempLocalDate = excelTitleSf.format(tempTime.getTime());
				title[13 + i * 9] = tempLocalDate + "发来金额劳务报酬";
				title[14 + i * 9] = localDate + "-" + tempLocalDate + "发来金额劳务报酬";
				title[15 + i * 9] = localDate + "-" + tempLocalDate + "预收税费9万以下收费3.6%";
				title[16 + i * 9] = localDate + "-" + tempLocalDate + "预收税费9以上-30万收费4.90%";
				title[17 + i * 9] = localDate + "-" + tempLocalDate + "30万以上收费5%";
				title[18 + i * 9] = tempLocalDate + "预收税费合计";
				if (i == 0) {
					title[19 + i * 9] = "冲" + localDate + "预收税费";
				} else {
					tempTime.add(Calendar.MONTH, -1);
					String tempDate = excelTitleSf.format(tempTime.getTime());
					tempTime.add(Calendar.MONTH, 1);
					title[19 + i * 9] = "冲" + localDate + "-" + tempDate + "预收税费";
				}
				title[20 + i * 9] = tempLocalDate + "实际收税费合计";
				title[21 + i * 9] = tempLocalDate + "开票金额";
			}
		} else {
			title = new String[0];
		}
		preBillingRecordHelper.isUploadInfoExcel(request, response, startTime, listResult, title, monthLength,
				excelFileName);

	}

}