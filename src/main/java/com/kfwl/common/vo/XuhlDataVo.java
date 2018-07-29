package com.kfwl.common.vo;

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
public class XuhlDataVo {

	// 日期
	@Excel(name = "日期", exportFormat = "yyyy-MM-dd")
	private Date createTime;

	// 商品编号
	@Excel(name = "SKU")
	private String skuCode;

	// 商品名称
	@Excel(name = "SKU名称")
	private String skuName;

	// GMV
	@Excel(name = "GMV")
	private Double gmv;

	// 销售数量
	@Excel(name = "销售数量")
	private Integer salesVolume;

	// 采购单价
	@Excel(name = "采购单价")
	private Double purchasePrice;

	// 采购成本
	@Excel(name = "采购成本")
	private Double purchaseCost;

	// 优惠卷
	@Excel(name = "优惠卷")
	private Double concessionVolumeReduction;

	// 优惠卷承担金额
	@Excel(name = "优惠卷承担金额")
	private Double preferentialVolume;

	// 前台毛利额
	@Excel(name = "前台毛利额")
	private Double frontDeskMaori;

	// 月返抵扣比例
	@Excel(name = "月返抵扣比例")
	private Double monthlyReturnRatio;

	// 广告费承担比例
	@Excel(name = "广告费承担比例")
	private Double proportionOfAdvertising;

	// 综合毛利额
	@Excel(name = "综合毛利额")
	private Double comprehensiveGrossProfit;

	// 前台毛利占GMV比
	@Excel(name = "前台毛利占GMV比")
	private Double frontDeskMaoriAccountsForGMVRatio;

	// 广告费占GMV比
	@Excel(name = "广告费占GMV比")
	private Double advertisingCostsAccountForGMVRatio;

	// 月返占GMV比
	@Excel(name = "月返占GMV比")
	private Double monthlyReturnToGMVRatio;

	// 优惠卷占GMV比
	@Excel(name = "优惠卷占GMV比")
	private Double couponsAccountForGMVRatio;

	// -------------------------------
	// 销售额
	private Double salesPrice;

}
