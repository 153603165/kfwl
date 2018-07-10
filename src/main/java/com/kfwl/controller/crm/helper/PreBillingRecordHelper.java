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
import com.kfwl.entity.crm.PreBillingRecord;
import com.kfwl.service.crm.PreBillingRecordService;
import com.kfwl.utils.DateUtil;
import com.kfwl.utils.FileUtil;

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
public class PreBillingRecordHelper {
	private static Logger logger = Logger.getLogger(PreBillingRecordHelper.class);
	private static DecimalFormat df = new DecimalFormat("######0.00");

	private static SimpleDateFormat excelSf = new SimpleDateFormat("yyyy/MM");
	private static SimpleDateFormat excelDataSf = new SimpleDateFormat("yyyy_MM");

	private final static Double TaxLt90000 = 0.036;
	private final static Double TaxGE90000Lt300000 = 0.049;
	private final static Double TaxGE300000 = 0.05;

	private final static Double ZhongKa = 1.03;
	private final static Double ZhongKaTaxLt90000 = 0.035;
	@Autowired
	PreBillingRecordService preBillingRecordService;

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
	public List<Map<String, Object>> exportPreBillingRecordExcelData(String startTime, String endTime, String category,
			String companyName, String legalPerson) throws ParseException {
		List<PreBillingRecord> preBillingRecordExcelData = preBillingRecordService
				.listPreBillingRecordExcelData(startTime, endTime, category, companyName, legalPerson);
		// 插入基础数据
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		outer: for (PreBillingRecord preBillingRecord : preBillingRecordExcelData) {
			for (Map<String, Object> map : listResult) {
				if (preBillingRecord.getCreditCode().equals(map.get("creditCode"))) {
					continue outer;
				}
			}
			Map<String, Object> maptemp = Maps.newHashMap();
			maptemp.put("companyName", preBillingRecord.getCompanyName());
			maptemp.put("legalPerson", preBillingRecord.getLegalPerson());
			maptemp.put("individualName", preBillingRecord.getIndividualName());
			maptemp.put("creditCode", preBillingRecord.getCreditCode());
			maptemp.put("plateNum", preBillingRecord.getPlateNum());
			maptemp.put("category", preBillingRecord.getCategory());
			maptemp.put("orderNo", preBillingRecord.getOrderNo());
			maptemp.put("mulAccount", preBillingRecord.getMulAccount());
			listResult.add(maptemp);
		}
		// 插入各月份数据
		int monthLength = DateUtil.getMonthSubtraction(startTime, endTime) + 1;
		for (Map<String, Object> map : listResult) {
			for (int i = 0; i < monthLength; i++) {
				for (PreBillingRecord preBillingRecord : preBillingRecordExcelData) {
					if (preBillingRecord.getCreditCode().equals(map.get("creditCode"))) {
						Calendar tempTime = Calendar.getInstance();
						tempTime.setTime(excelSf.parse(startTime));
						tempTime.add(Calendar.MONTH, i);
						String localDate = excelDataSf.format(tempTime.getTime());

						if (localDate.equals(excelDataSf.format(preBillingRecord.getCreateTime()))) {
							Double sendAmount = Double.parseDouble(df.format(
									preBillingRecord.getSendAmount() == null ? "0" : preBillingRecord.getSendAmount()));
							if ("众卡".equals(preBillingRecord.getCategory())) {
								sendAmount = sendAmount * ZhongKa;
							}
							map.put("sendAmount_" + localDate, df.format(sendAmount));
						}
					}
				}
			}
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
			String filePath = upload.getAbsolutePath() + "/preBillingRecord.xls";
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
			sheet.setColumnView(1, 25);
			sheet.setColumnView(2, 12);
			sheet.setColumnView(3, 10);
			sheet.setColumnView(4, 25);
			sheet.setColumnView(5, 25);
			sheet.setColumnView(6, 12);
			for (int i = 7; i < 7 + monthLength * 9; i++) {
				sheet.setColumnView(i, 22);
			}
			// 冻结前5列 第一行
			sheet.getSettings().setHorizontalFreeze(7);
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
				label0 = new Label(0, j + 1, listResult.get(j).get("orderNo") + "", wcff);
				sheet.addCell(label0);

				Label label1 = null;
				label1 = new Label(1, j + 1, listResult.get(j).get("companyName") + "", wcff);
				sheet.addCell(label1);

				Label label2 = null;
				label2 = new Label(2, j + 1, listResult.get(j).get("plateNum") + "", wcff);
				sheet.addCell(label2);

				Label label3 = null;
				label3 = new Label(3, j + 1, listResult.get(j).get("legalPerson") + "", wcff);
				sheet.addCell(label3);

				Label label4 = null;
				label4 = new Label(4, j + 1, listResult.get(j).get("individualName") + "", wcff);
				sheet.addCell(label4);

				Label label5 = null;
				label5 = new Label(5, j + 1, listResult.get(j).get("creditCode") + "", wcff);
				sheet.addCell(label5);

				Label label6 = null;
				label6 = new Label(6, j + 1, listResult.get(j).get("mulAccount") + "", wcff);
				sheet.addCell(label6);

				Calendar tempTime = Calendar.getInstance();
				tempTime.setTime(excelSf.parse(startTime));
				String tempDate = excelDataSf.format(tempTime.getTime());

				Double oneSendAmount = listResult.get(j).get("sendAmount_" + tempDate) != null
						? Double.parseDouble(listResult.get(j).get("sendAmount_" + tempDate) + "")
						: 0;
				Label label7 = null;
				label7 = new Label(7, j + 1,
						listResult.get(j).get("sendAmount_" + tempDate) == null ? "0" : df.format(oneSendAmount), wcff);
				sheet.addCell(label7);

				Label label8 = null;
				if ("众卡".equals(listResult.get(j).get("category"))) {
					label8 = new Label(8, j + 1,
							oneSendAmount < 90000 ? df.format(oneSendAmount * ZhongKaTaxLt90000) : "", wcff);
				} else {
					label8 = new Label(8, j + 1, oneSendAmount < 90000 ? df.format(oneSendAmount * TaxLt90000) : "",
							wcff);
				}
				sheet.addCell(label8);

				Label label9 = null;
				label9 = new Label(9, j + 1,
						oneSendAmount >= 90000 && oneSendAmount < 300000 ? df.format(oneSendAmount * TaxGE90000Lt300000)
								: "",
						wcff);
				sheet.addCell(label9);

				Label label10 = null;
				label10 = new Label(10, j + 1, oneSendAmount > 300000 ? df.format(oneSendAmount * TaxGE300000) : "",
						wcff);
				sheet.addCell(label10);

				Label label11 = null;
				label11 = new Label(11, j + 1,
						df.format((sheet.getCell(8, j + 1).getContents() != null
								&& !"".equals(sheet.getCell(8, j + 1).getContents())
										? Double.valueOf(sheet.getCell(8, j + 1).getContents())
										: 0)
								+ (sheet.getCell(9, j + 1).getContents() != null
										&& !"".equals(sheet.getCell(9, j + 1).getContents())
												? Double.valueOf(sheet.getCell(9, j + 1).getContents())
												: 0)
								+ (sheet.getCell(10, j + 1).getContents() != null
										&& !"".equals(sheet.getCell(10, j + 1).getContents())
												? Double.valueOf(sheet.getCell(10, j + 1).getContents())
												: 0)),
						wcff);
				sheet.addCell(label11);

				Label label12 = null;
				if ("众卡".equals(listResult.get(j).get("category"))) {
					label12 = new Label(12, j + 1,
							df.format((sheet.getCell(7, j + 1).getContents() != null
									? Double.valueOf(sheet.getCell(7, j + 1).getContents())
									: 0) / ZhongKa
									+ (sheet.getCell(11, j + 1).getContents() != null
											? Double.valueOf(sheet.getCell(11, j + 1).getContents())
											: 0)),
							wcff);
					sheet.addCell(label12);
				} else {
					label12 = new Label(12, j + 1,
							df.format((sheet.getCell(7, j + 1).getContents() != null
									? Double.valueOf(sheet.getCell(7, j + 1).getContents())
									: 0)
									+ (sheet.getCell(11, j + 1).getContents() != null
											? Double.valueOf(sheet.getCell(11, j + 1).getContents())
											: 0)),
							wcff);
					sheet.addCell(label12);
				}

				// 拼接月份税
				for (int i = 0; i < monthLength - 1; i++) {
					tempTime.add(Calendar.MONTH, 1);
					String localDate = excelDataSf.format(tempTime.getTime());
					// 当月发来金额劳务报酬
					Double thisSendAmount = listResult.get(j).get("sendAmount_" + localDate) == null ? 0
							: Double.parseDouble(df
									.format(Double.parseDouble(listResult.get(j).get("sendAmount_" + localDate) + "")));
					Label localLabel = null;
					localLabel = new Label(13 + i * 9, j + 1, df.format(thisSendAmount), wcff);
					sheet.addCell(localLabel);

					// 合计发来金额劳务报酬
					Double totalSendAmount = 0.0;
					for (int z = 0; z <= i + 1; z++) {
						Calendar temporaryTime = Calendar.getInstance();
						temporaryTime.setTime(excelSf.parse(startTime));
						temporaryTime.add(Calendar.MONTH, z);
						String temporaryDate = excelDataSf.format(temporaryTime.getTime());
						totalSendAmount += listResult.get(j).get("sendAmount_" + temporaryDate) == null ? 0
								: Double.parseDouble(df.format(
										Double.parseDouble(listResult.get(j).get("sendAmount_" + temporaryDate) + "")));
					}

					Label localLabel2 = null;
					localLabel2 = new Label(14 + i * 9, j + 1, df.format(totalSendAmount), wcff);
					sheet.addCell(localLabel2);

					// 合计预收税费9万以下收费3.6%
					Double totalSendAmountTaxLT9 = 0.0;
					if ("众卡".equals(listResult.get(j).get("category"))) {
						totalSendAmountTaxLT9 = totalSendAmount < 90000
								? Double.parseDouble(df.format(totalSendAmount * ZhongKaTaxLt90000))
								: 0;
					} else {
						totalSendAmountTaxLT9 = totalSendAmount < 90000
								? Double.parseDouble(df.format(totalSendAmount * TaxLt90000))
								: 0;
					}
					Label localLabel3 = null;
					localLabel3 = new Label(15 + i * 9, j + 1, df.format(totalSendAmountTaxLT9), wcff);
					sheet.addCell(localLabel3);

					// 合计预收税费9以上-30万收费4.90%
					Double totalSendAmountTaxGE9LT30 = totalSendAmount >= 90000 && totalSendAmount < 300000
							? Double.parseDouble(df.format(totalSendAmount * TaxGE90000Lt300000))
							: 0;
					Label localLabel4 = null;
					localLabel4 = new Label(16 + i * 9, j + 1, df.format(totalSendAmountTaxGE9LT30), wcff);
					sheet.addCell(localLabel4);

					// 合计预收税费30万以上收费5%
					Double totalSendAmountTaxLE30 = totalSendAmount >= 300000
							? Double.parseDouble(df.format(totalSendAmount * TaxGE300000))
							: 0;
					Label localLabel5 = null;
					localLabel5 = new Label(17 + i * 9, j + 1, df.format(totalSendAmountTaxLE30), wcff);
					sheet.addCell(localLabel5);

					// 汇总预收税费合计
					Double totalSendAmountTax = totalSendAmountTaxLT9 + totalSendAmountTaxGE9LT30
							+ totalSendAmountTaxLE30;
					Label localLabel6 = null;
					localLabel6 = new Label(18 + i * 9, j + 1, df.format(totalSendAmountTax), wcff);
					sheet.addCell(localLabel6);

					// 冲往月预售税费
					Double afterSendAmountTax = 0.0;
					WritableCell cell = null;
					if (i == 0) {
						cell = sheet.getWritableCell(11, j + 1);
					} else if (i == 1) {
						cell = sheet.getWritableCell(18, j + 1);
					} else {
						cell = sheet.getWritableCell(18 + (i - 1) * 9, j + 1);
					}
					afterSendAmountTax = Double.valueOf(cell.getContents());
					Label localLabel7 = null;
					localLabel7 = new Label(19 + i * 9, j + 1, df.format(afterSendAmountTax), wcff);
					sheet.addCell(localLabel7);

					// 当月实际收税费合计
					Double localSendAmountTax = totalSendAmountTax - afterSendAmountTax;
					Label localLabel8 = null;
					localLabel8 = new Label(20 + i * 9, j + 1, df.format(localSendAmountTax), wcff);
					sheet.addCell(localLabel8);

					// 当月开票金额
					Double thisKaiPiao = 0.0;
					if ("众卡".equals(listResult.get(j).get("category"))) {
						thisKaiPiao = thisSendAmount / ZhongKa + localSendAmountTax;
					} else {
						thisKaiPiao = thisSendAmount + localSendAmountTax;
					}
					Label localLabel9 = null;
					localLabel9 = new Label(21 + i * 9, j + 1, df.format(thisKaiPiao), wcff);
					sheet.addCell(localLabel9);

				}
			}

			// 0.00标记为0
			for (int i = 0; i < sheet.getColumns(); i++) {
				for (int k = 0; k < sheet.getRows(); k++) {
					WritableCell cell = sheet.getWritableCell(i, k);
					if ("0.00".equals(cell.getContents()) || "0.0".equals(cell.getContents())
							|| "0".equals(cell.getContents())) {
						CellFormat cf = cell.getCellFormat();
						Label lbl = new jxl.write.Label(i, k, "");
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