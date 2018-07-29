package com.kfwl.common.dto;

import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ExcelTarget("BillingRecordDto")
public class BillingRecordDto {

	@Excel(name = "日期")
	private Date createTime;

	@Excel(name = "所属公司")
	private String companyName;

	@Excel(name = "个体名称")
	private String individualName;

	@Excel(name = "注册人")
	private String regName;

	@Excel(name = "信用代码")
	private String creditCode;

	@Excel(name = "含税金额")
	private Double taxAmount;

	@Excel(name = "不含税金额")
	private Double noTaxAmount;

	@Excel(name = "税金3%")
	private Double tax;
}
