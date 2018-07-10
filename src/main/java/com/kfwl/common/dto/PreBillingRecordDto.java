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
@ExcelTarget("billingRecordDto")
public class PreBillingRecordDto {

	@Excel(name = "序号")
	private String orderNo;

	@Excel(name = "日期")
	private Date createTime;

	@Excel(name = "类别")
	private String category;

	@Excel(name = "所属公司")
	private String companyName;

	@Excel(name = "车牌")
	private String plateNum;

	@Excel(name = "法人")
	private String legalPerson;

	@Excel(name = "个体名称")
	private String individualName;

	@Excel(name = "信用代码")
	private String creditCode;

	@Excel(name = "多级账号")
	private String mulAccount;

	@Excel(name = "发来金额")
	private Double sendAmount;
}
