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
@ExcelTarget("PurchaseRebateDto")
public class PurchaseRebateDto {

	@Excel(name = "sku编号")
	private String skuCode;

	@Excel(name = "商品名称")
	private String skuName;

	@Excel(name = "sku数量")
	private Integer num;

	@Excel(name = "金额")
	private Double purchaseRebatePrice;

	@Excel(name = "业务时间")
	private Date createTime;

}
