package com.kfwl.controller.crm.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.google.common.collect.Maps;
import com.kfwl.entity.crm.BillingRecord;
import com.kfwl.service.crm.BillingRecordService;
import com.kfwl.utils.DateUtil;
import com.kfwl.utils.FileUtil;

import jxl.Cell;
import jxl.HeaderFooter;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Component
public class BillingRecordHelper {
	private static Logger logger = Logger.getLogger(BillingRecordHelper.class);
	private static DecimalFormat df = new DecimalFormat("######0.00");

	private static SimpleDateFormat excelSf = new SimpleDateFormat("yyyy/MM");
	private static SimpleDateFormat excelDataSf = new SimpleDateFormat("yyyy_MM");
	@Autowired
	BillingRecordService billingRecordService;

	/**
	 * 动态表格数据
	 * 
	 * @param startTime
	 * @param endTime
	 * @param companyName
	 * @param totalTaxAmountParam
	 * @param regName
	 * @return
	 * @throws ParseException
	 */
	public List<Map<String, Object>> exportBillingRecordExcelData(String startTime, String endTime, String companyName,
			Double totalTaxAmountParam, String regName) throws ParseException {
		List<BillingRecord> BillingRecordExcelData = billingRecordService.listBillingRecordExcelData(startTime, endTime,
				companyName, totalTaxAmountParam, regName);
		// 插入基础数据
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		outer: for (BillingRecord BillingRecord : BillingRecordExcelData) {
			for (Map<String, Object> map : listResult) {
				if (BillingRecord.getCompanyName().equals(map.get("companyName"))
						&& BillingRecord.getRegName().equals(map.get("regName"))
						&& BillingRecord.getIndividualName().equals(map.get("individualName"))
						&& BillingRecord.getCreditCode().equals(map.get("creditCode"))) {
					continue outer;
				}
			}
			Map<String, Object> maptemp = Maps.newHashMap();
			maptemp.put("companyName", BillingRecord.getCompanyName());
			maptemp.put("regName", BillingRecord.getRegName());
			maptemp.put("individualName", BillingRecord.getIndividualName());
			maptemp.put("creditCode", BillingRecord.getCreditCode());
			listResult.add(maptemp);
		}
		// 插入各月份数据
		int monthLength = DateUtil.getMonthSubtraction(startTime, endTime) + 1;
		for (Map<String, Object> map : listResult) {
			for (int i = 0; i < monthLength; i++) {
				for (BillingRecord BillingRecord : BillingRecordExcelData) {
					if (BillingRecord.getCompanyName().equals(map.get("companyName"))
							&& BillingRecord.getRegName().equals(map.get("regName"))
							&& BillingRecord.getIndividualName().equals(map.get("individualName"))
							&& BillingRecord.getCreditCode().equals(map.get("creditCode"))) {
						Calendar tempTime = Calendar.getInstance();
						tempTime.setTime(excelSf.parse(startTime));
						tempTime.add(Calendar.MONTH, i);
						String localDate = excelDataSf.format(tempTime.getTime());
						if (localDate.equals(excelDataSf.format(BillingRecord.getCreateTime()))) {
							map.put("taxAmount_" + localDate, df
									.format(BillingRecord.getTaxAmount() == null ? "0" : BillingRecord.getTaxAmount()));
							map.put("totalTaxAmount_" + localDate, df
									.format(BillingRecord.getTaxAmount() == null ? "0" : BillingRecord.getTaxAmount()));
							map.put("noTaxAmount_" + localDate, df.format(
									BillingRecord.getNoTaxAmount() == null ? "0" : BillingRecord.getNoTaxAmount()));
							map.put("totalNoTaxAmount_" + localDate, df.format(
									BillingRecord.getNoTaxAmount() == null ? "0" : BillingRecord.getNoTaxAmount()));
							map.put("tax_" + localDate,
									df.format(BillingRecord.getTax() == null ? "0" : BillingRecord.getTax()));
						}
					}
				}
			}
		}
		Map<String, Double> groupByregNameCreditCode = billingRecordService
				.getImportBillingRecordNoTaxAmountForRegNameCreditCode(startTime, endTime);
		// 统计求和
		for (Map<String, Object> map : listResult) {
			// 总含税金额
			double totalTaxAmount = 0.0;
			// 不含税金额
			double totalNoTaxAmount = 0.0;
			// 增值税合计3%
			double totalTax = 0.0;
			// 个人所得税和城市城建小计
			double individualTaxAndUrbanConstruction = 0.0;
			// 个人所得税
			double individualTax = 0.0;
			// 城建
			double urbanConstruction = 0.0;
			// 教育
			double education = 0.0;
			// 地方教育
			double localEducation = 0.0;
			// 地税合计税金
			double joinTaxOfLandTax = 0.0;
			for (int i = 0; i < monthLength; i++) {
				Calendar tempTime = Calendar.getInstance();
				tempTime.setTime(excelSf.parse(startTime));
				tempTime.add(Calendar.MONTH, i);
				String localDate = excelDataSf.format(tempTime.getTime());
				totalTaxAmount += map.get("taxAmount_" + localDate) == null ? 0
						: Double.valueOf(map.get("taxAmount_" + localDate).toString());
				totalNoTaxAmount += map.get("noTaxAmount_" + localDate) == null ? 0
						: Double.valueOf(map.get("noTaxAmount_" + localDate).toString());
				totalTax += map.get("tax_" + localDate) == null ? 0
						: Double.valueOf(map.get("tax_" + localDate).toString());
			}
			// 格式化2位小数
			totalTaxAmount = Double.valueOf(df.format(totalTaxAmount));
			totalNoTaxAmount = Double.valueOf(df.format(totalNoTaxAmount));
			totalTax = Double.valueOf(df.format(totalTax));
			Double groupByregNameCreditCodeNoTaxAmount = groupByregNameCreditCode
					.get(map.get("regName") + "_" + map.get("creditCode"));
			if (groupByregNameCreditCodeNoTaxAmount - 90000 > 0) {
				individualTax = Double.valueOf(df.format(totalNoTaxAmount * 0.015));
			}
			urbanConstruction = Double.valueOf(df.format(totalTax * 0.07));
			individualTaxAndUrbanConstruction = individualTax + urbanConstruction;
			if (totalNoTaxAmount - 300000 > 0) {
				education = Double.valueOf(df.format(totalTax * 0.03));
				localEducation = Double.valueOf(df.format(totalTax * 0.02));
			}
			joinTaxOfLandTax = urbanConstruction + individualTax + education + localEducation;

			map.put("totalTaxAmount", df.format(totalTaxAmount));
			map.put("totalNoTaxAmount", df.format(totalNoTaxAmount));
			map.put("totalTax", df.format(totalTax));
			map.put("individualTaxAndUrbanConstruction", df.format(individualTaxAndUrbanConstruction));
			map.put("individualTax", df.format(individualTax));
			map.put("urbanConstruction", df.format(urbanConstruction));
			map.put("education", df.format(education));
			map.put("localEducation", df.format(localEducation));
			map.put("joinTaxOfLandTax", df.format(joinTaxOfLandTax));
		}
		return listResult;
	}

	/**
	 * 下载文件
	 * 
	 * @param request
	 * @param response
	 * @param startTime
	 * @param listResult
	 * @param title
	 * @param monthLength
	 * @param excelFileName
	 */
	public void isUploadInfoExcel(HttpServletRequest request, HttpServletResponse response, String startTime,
			List<Map<String, Object>> listResult, String[] title, int monthLength, String excelFileName) {

		try {
			// 获取跟目录
			File path = new File(ResourceUtils.getURL("classpath:").getPath());
			if (!path.exists())
				path = new File("");
			File upload = new File(path.getAbsolutePath(), "static/upload/");
			if (!upload.exists())
				upload.mkdirs();
			String filePath = upload.getAbsolutePath() + "/billingRecord.xls";

			OutputStream os = new FileOutputStream(filePath);
			WritableWorkbook wwb = Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("财务报表", 0);

			HeaderFooter hf = new HeaderFooter();
			hf.getCentre().append("财务报表");
			sheet.getSettings().setHeader(hf);

			HeaderFooter hf1 = new HeaderFooter();
			hf1.getLeft().append("页脚");
			hf1.getCentre().append(new Date() + "");
			hf1.getRight().append("页脚");
			sheet.getSettings().setFooter(hf1);

			Label label = null;
			// 设置单元格长度
			sheet.setColumnView(0, 7);
			sheet.setColumnView(1, 10);
			sheet.setColumnView(2, 23);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 30);
			for (int i = 5; i < 23 + monthLength * 6; i++) {
				sheet.setColumnView(i, 22);
			}
			// 冻结前5列 第一行
			sheet.getSettings().setHorizontalFreeze(5);
			sheet.getSettings().setVerticalFreeze(1);

			WritableFont wfc = new WritableFont(WritableFont.COURIER, 16, WritableFont.NO_BOLD, false);
			WritableCellFormat demo = new WritableCellFormat(wfc);
			demo.setWrap(true);
			wfc = new WritableFont(WritableFont.COURIER, 10, WritableFont.BOLD, false);
			for (int i = 0; i < title.length; i++) {
				WritableCellFormat wcff = new WritableCellFormat(wfc);
				wcff.setWrap(true);
				wcff.setVerticalAlignment(VerticalAlignment.CENTRE); // 设置居中对齐
				wcff.setAlignment(Alignment.CENTRE);
				wcff.setBackground(Colour.YELLOW);
				wcff.setBorder(Border.ALL, BorderLineStyle.THIN);
				label = new Label(i, 0, title[i], wcff);
				sheet.addCell(label);
			}
			wfc = new WritableFont(WritableFont.createFont("微软雅黑"), 10, WritableFont.NO_BOLD, false);
			WritableCellFormat wcff = new WritableCellFormat(wfc);
			wcff.setWrap(true);
			wcff.setVerticalAlignment(VerticalAlignment.CENTRE); // 设置居中对齐
			wcff.setAlignment(Alignment.CENTRE);
			wcff.setBackground(Colour.WHITE);
			wcff.setBorder(Border.ALL, BorderLineStyle.THIN);
			for (int j = 0; j < listResult.size(); j++) {
				Label label0 = null;
				label0 = new Label(0, j + 1, (j + 1) + "", wcff);
				sheet.addCell(label0);

				Label label1 = null;
				label1 = new Label(1, j + 1, listResult.get(j).get("regName") + "", wcff);
				sheet.addCell(label1);

				Label label2 = null;
				label2 = new Label(2, j + 1, listResult.get(j).get("individualName") + "", wcff);
				sheet.addCell(label2);

				Label label3 = null;
				label3 = new Label(3, j + 1, listResult.get(j).get("creditCode") + "", wcff);
				sheet.addCell(label3);

				Label label4 = null;
				label4 = new Label(4, j + 1, listResult.get(j).get("companyName") + "", wcff);
				sheet.addCell(label4);

				// 拼接月份税
				for (int i = 0; i < monthLength; i++) {
					Calendar tempTime = Calendar.getInstance();
					tempTime.setTime(excelSf.parse(startTime));
					tempTime.add(Calendar.MONTH, i);
					String localDate = excelDataSf.format(tempTime.getTime());
					Label tempLabel = null;
					tempLabel = new Label(5 + i * 6, j + 1,
							listResult.get(j).get("taxAmount_" + localDate) == null ? "0"
									: listResult.get(j).get("taxAmount_" + localDate) + "",
							wcff);
					sheet.addCell(tempLabel);

					Label tempLabel2 = null;
					tempLabel2 = new Label(6 + i * 6, j + 1,
							listResult.get(j).get("taxAmount_" + localDate) == null ? "0"
									: listResult.get(j).get("taxAmount_" + localDate) + "",
							wcff);
					sheet.addCell(tempLabel2);

					Label tempLabel3 = null;
					tempLabel3 = new Label(7 + i * 6, j + 1,
							listResult.get(j).get("noTaxAmount_" + localDate) == null ? "0"
									: listResult.get(j).get("noTaxAmount_" + localDate) + "",
							wcff);
					sheet.addCell(tempLabel3);

					Label tempLabel4 = null;
					tempLabel4 = new Label(8 + i * 6, j + 1,
							listResult.get(j).get("noTaxAmount_" + localDate) == null ? "0"
									: listResult.get(j).get("noTaxAmount_" + localDate) + "",
							wcff);
					sheet.addCell(tempLabel4);

					Label tempLabel5 = null;
					tempLabel5 = new Label(9 + i * 6, j + 1, listResult.get(j).get("tax_" + localDate) == null ? "0"
							: listResult.get(j).get("tax_" + localDate) + "", wcff);
					sheet.addCell(tempLabel5);

					Label tempLabel6 = null;
					tempLabel6 = new Label(10 + i * 6, j + 1, listResult.get(j).get("tax_" + localDate) == null ? "0"
							: listResult.get(j).get("tax_" + localDate) + "", wcff);
					sheet.addCell(tempLabel6);

				}
				Label label5 = null;
				label5 = new Label(5 + 6 * monthLength, j + 1, listResult.get(j).get("totalTaxAmount") + "", wcff);
				sheet.addCell(label5);

				Label label6 = null;
				label6 = new Label(6 + 6 * monthLength, j + 1, listResult.get(j).get("totalTaxAmount") + "", wcff);
				sheet.addCell(label6);

				Label label7 = null;
				label7 = new Label(7 + 6 * monthLength, j + 1, listResult.get(j).get("totalNoTaxAmount") + "", wcff);
				sheet.addCell(label7);

				Label label8 = null;
				label8 = new Label(8 + 6 * monthLength, j + 1, listResult.get(j).get("totalNoTaxAmount") + "", wcff);
				sheet.addCell(label8);

				Label label9 = null;
				label9 = new Label(9 + 6 * monthLength, j + 1, listResult.get(j).get("totalTax") + "", wcff);
				sheet.addCell(label9);

				Label label10 = null;
				label10 = new Label(10 + 6 * monthLength, j + 1, listResult.get(j).get("totalTax") + "", wcff);
				sheet.addCell(label10);

				Label label11 = null;
				label11 = new Label(11 + 6 * monthLength, j + 1,
						listResult.get(j).get("individualTaxAndUrbanConstruction") + "", wcff);
				sheet.addCell(label11);

				Label label12 = null;
				label12 = new Label(12 + 6 * monthLength, j + 1,
						listResult.get(j).get("individualTaxAndUrbanConstruction") + "", wcff);
				sheet.addCell(label12);

				Label label13 = null;
				label13 = new Label(13 + 6 * monthLength, j + 1, listResult.get(j).get("individualTax") + "", wcff);
				sheet.addCell(label13);

				Label label14 = null;
				label14 = new Label(14 + 6 * monthLength, j + 1, listResult.get(j).get("individualTax") + "", wcff);
				sheet.addCell(label14);

				Label label15 = null;
				label15 = new Label(15 + 6 * monthLength, j + 1, listResult.get(j).get("urbanConstruction") + "", wcff);
				sheet.addCell(label15);

				Label label16 = null;
				label16 = new Label(16 + 6 * monthLength, j + 1, listResult.get(j).get("urbanConstruction") + "", wcff);
				sheet.addCell(label16);

				Label label17 = null;
				label17 = new Label(17 + 6 * monthLength, j + 1, listResult.get(j).get("education") + "", wcff);
				sheet.addCell(label17);

				Label label18 = null;
				label18 = new Label(18 + 6 * monthLength, j + 1, listResult.get(j).get("education") + "", wcff);
				sheet.addCell(label18);

				Label label19 = null;
				label19 = new Label(19 + 6 * monthLength, j + 1, listResult.get(j).get("localEducation") + "", wcff);
				sheet.addCell(label19);

				Label label20 = null;
				label20 = new Label(20 + 6 * monthLength, j + 1, listResult.get(j).get("localEducation") + "", wcff);
				sheet.addCell(label20);

				Label label21 = null;
				label21 = new Label(21 + 6 * monthLength, j + 1, listResult.get(j).get("joinTaxOfLandTax") + "", wcff);
				sheet.addCell(label21);

				Label label22 = null;
				label22 = new Label(22 + 6 * monthLength, j + 1, listResult.get(j).get("joinTaxOfLandTax") + "", wcff);
				sheet.addCell(label22);
			}

			// 合并单元格计算汇总
			int j = 0;
			while (j < listResult.size()) {
				int width = 0;
				for (int k = 0; k + j < listResult.size(); k++) {
					if (listResult.get(j).get("regName").equals(listResult.get(j + k).get("regName"))
							&& listResult.get(j).get("individualName")
									.equals(listResult.get(j + k).get("individualName"))
							&& listResult.get(j).get("creditCode").equals(listResult.get(j + k).get("creditCode"))) {
						width++;
					}
				}
				// 前三行合并单元格
				sheet.mergeCells(0, j + 1, 0, j + width);
				sheet.mergeCells(1, j + 1, 1, j + width);
				sheet.mergeCells(2, j + 1, 2, j + width);
				sheet.mergeCells(3, j + 1, 3, j + width);

				for (int i = 6; i < title.length; i = i + 2) {
					sheet.mergeCells(i, j + 1, i, j + width);
					if (i != title.length - 9) {
						Double tempValue = 0D;
						for (int a = 0; a < width; a++) {
							Cell cell = sheet.getCell(i, j + a + 1);
							tempValue += Double.valueOf(cell.getContents());
						}
						Label tempLabel = null;
						tempLabel = new Label(i, j + 1, df.format(tempValue) + "", wcff);
						sheet.addCell(tempLabel);
					} else {
						Cell cell = sheet.getCell(i - 6, j + 1);
						Double tempValue = 0D;
						Label tempLabel = null;
						if (Double.valueOf(cell.getContents()) >= 90000D) {
							tempValue = Double.valueOf(cell.getContents());
						}
						tempLabel = new Label(i, j + 1, df.format(tempValue * 0.015) + "", wcff);
						sheet.addCell(tempLabel);

					}
				}
				j = j + width;
			}
			// 0.00标记为0
			for (int i = 0; i < sheet.getColumns(); i++) {
				for (int k = 0; k < sheet.getRows(); k++) {
					WritableCell cell = sheet.getWritableCell(i, k);
					if ("0.00".equals(cell.getContents())) {
						CellFormat cf = cell.getCellFormat();
						Label lbl = new jxl.write.Label(i, k, "0");
						lbl.setCellFormat(cf);
						sheet.addCell(lbl);
					}
				}
			}

			wwb.write();
			wwb.close();
			if (!"".equals(filePath)) {
				filePath = new String(filePath.getBytes("ISO-8859-1"), "UTF-8");
				File file = new File(filePath);
				if (file.exists()) {
					InputStream ins = new FileInputStream(filePath);
					BufferedInputStream bins = new BufferedInputStream(ins);
					OutputStream outs = response.getOutputStream();
					BufferedOutputStream bouts = new BufferedOutputStream(outs);
					response.setContentType("application/x-download");
					response.setHeader("Content-disposition",
							"attachment;filename=" + FileUtil.getStr(request, excelFileName + ".xls"));
					int bytesRead = 0;
					byte[] buffer = new byte[8192];

					while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
						bouts.write(buffer, 0, bytesRead);
					}
					bouts.flush();
					ins.close();
					bins.close();
					outs.close();
					bouts.close();
				} else {
					logger.error("下载的文件不存在");
				}
			} else {
				logger.error("下载文件时参数错误");
			}
		} catch (Exception e) {
			logger.error("获取结果集失败或操作没有及时关闭", e);
			e.printStackTrace();
		}

	}

}