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
@ExcelTarget("SaleDto")
public class SaleDto {

	@Excel(name = "SKU")
	private String skuCode;

	@Excel(name = "商品名称")
	private String skuName;

	@Excel(name = "销量")
	private Integer salesVolume;

	@Excel(name = "销售额")
	private Double salesPrice;

	@Excel(name = "日期")
	private Date createTime;

}
