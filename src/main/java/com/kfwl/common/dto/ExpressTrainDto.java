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
@ExcelTarget("ExpressTrainDto")
public class ExpressTrainDto {

	@Excel(name = "商品编号")
	private String skuCode;

	@Excel(name = "总费用")
	private Double totalCost;

	@Excel(name = "日期")
	private Date createTime;

}
