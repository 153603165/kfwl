package com.kfwl.controller.xuhl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kfwl.common.vo.XuhlDataVo;
import com.kfwl.controller.BasicController;
import com.kfwl.entity.xuhl.Coupon;
import com.kfwl.entity.xuhl.ExpressTrain;
import com.kfwl.entity.xuhl.Purchase;
import com.kfwl.entity.xuhl.PurchaseRebate;
import com.kfwl.entity.xuhl.Sale;
import com.kfwl.exception.impl.NormalException;
import com.kfwl.service.xuhl.CouponService;
import com.kfwl.service.xuhl.ExpressTrainService;
import com.kfwl.service.xuhl.PurchaseRebateService;
import com.kfwl.service.xuhl.PurchaseService;
import com.kfwl.service.xuhl.SaleService;
import com.kfwl.utils.ExcelUtil;

@Controller
@RequestMapping("/xuhlManage")
public class XuhlManageController extends BasicController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(XuhlManageController.class);
	private static DecimalFormat df = new DecimalFormat("######0.00");
	@Autowired
	PurchaseService purchaseService;
	@Autowired
	CouponService couponService;
	@Autowired
	ExpressTrainService expressTrainService;
	@Autowired
	PurchaseRebateService purchaseRebateService;
	@Autowired
	SaleService saleService;

	/**
	 * 合并报表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "xuhlManagePage")
	public String purchaseRebatePage() {
		return "xuhl/xuhlManage";
	}

	@ResponseBody
	@PreAuthorize("hasAnyAuthority('xuhlManage:view')")
	@RequestMapping(value = "/exportXuhlManageExcel", method = RequestMethod.GET)
	public void getDatas(HttpServletResponse response, @RequestParam(required = false) String skuCode,
			@RequestParam(required = false) String skuName, @RequestParam(required = false) String dateFrom,
			@RequestParam(required = false) String dateTo) throws NormalException, UnsupportedEncodingException {
		skuName = URLDecoder.decode(skuName, "utf-8");
		List<Sale> listSales = saleService.listSales(skuCode, skuName, dateFrom, dateTo);
		List<Coupon> listCoupons = couponService.listCoupons(skuCode, skuName, dateFrom, dateTo);
		List<ExpressTrain> listExpressTrains = expressTrainService.listExpressTrains(skuCode, dateFrom, dateTo);
		List<PurchaseRebate> listPurchaseRebates = purchaseRebateService.listPurchaseRebates(skuCode, skuName, dateFrom,
				dateTo, null);
		List<Purchase> listPurchases = purchaseService.listPurchases(skuCode, skuName, dateFrom, dateTo);
		List<XuhlDataVo> result = new ArrayList<>();
		for (Sale sa : listSales) {
			XuhlDataVo xd = new XuhlDataVo();
			BeanUtils.copyProperties(sa, xd);
			result.add(xd);
		}
		result.stream().forEach(xd -> {
			// GMV
			Double gmv = 0.0;
			// 销售数量
			Integer salesVolume = xd.getSalesVolume();
			// 采购单价
			Double purchasePrice = 0.0;
			// 采购成本
			Double purchaseCost = 0.0;
			// 优惠卷
			Double concessionVolumeReduction = 0.0;
			// 优惠卷承担金额
			Double preferentialVolume = 0.0;
			// 前台毛利额
			Double frontDeskMaori = 0.0;
			// 月返抵扣比例
			Double monthlyReturnRatio = 0.0;
			// 广告费承担比例
			Double proportionOfAdvertising = 0.0;
			// 综合毛利额
			Double comprehensiveGrossProfit = 0.0;
			// 前台毛利占GMV比
			Double frontDeskMaoriAccountsForGMVRatio = 0.0;
			// 广告费占GMV比
			Double advertisingCostsAccountForGMVRatio = 0.0;
			// 月返占GMV比
			Double monthlyReturnToGMVRatio = 0.0;
			// 优惠卷占GMV比
			Double couponsAccountForGMVRatio = 0.0;
			// ---------
			// 快车总费用
			Double totalCost = 0.0;
			// 销售额
			Double salesPrice = xd.getSalesPrice();
			// 采购数量
			Integer purchaseRebateNum = 0;
			// 采购数量
			Double purchaseRebatePrice = 0.0;
			// 退货数量
			Integer returnNum = 0;
			// 退货金额
			Double returnPrice = 0.0;
			// 采购退货金额
			Integer purchaseReturnNum = 0;
			// 采购退货金额
			Double purchaseReturnPrice = 0.0;

			for (ExpressTrain et : listExpressTrains) {
				if (xd.getSkuCode().equals(et.getSkuCode())) {
					totalCost = et.getTotalCost();
				}
			}
			for (Purchase pu : listPurchases) {
				if (xd.getSkuCode().equals(pu.getSkuCode())) {
					purchasePrice = pu.getPurchasePrice();

				}
			}
			for (Coupon cp : listCoupons) {
				if (xd.getSkuCode().equals(cp.getSkuCode())) {
					concessionVolumeReduction = cp.getConcessionVolumeReduction();
				}
			}
			for (PurchaseRebate pr : listPurchaseRebates) {
				if (xd.getSkuCode().equals(pr.getSkuCode())) {
					if (pr.getType().equals(1)) {
						purchaseReturnNum = pr.getNum();
						purchaseReturnPrice = pr.getPurchaseRebatePrice();

					} else if (pr.getType().equals(2)) {
						purchaseRebateNum = pr.getNum();
						purchaseRebatePrice = pr.getPurchaseRebatePrice();
					} else if (pr.getType().equals(3)) {
						returnNum = pr.getNum();
						returnPrice = pr.getPurchaseRebatePrice();
					}
				}
			}
			gmv = salesPrice - concessionVolumeReduction - returnPrice;
			purchaseCost = purchasePrice * salesVolume;
			preferentialVolume = concessionVolumeReduction * 0.6;
			frontDeskMaori = gmv - purchaseCost - concessionVolumeReduction;
			monthlyReturnRatio = (purchaseRebateNum * purchaseRebatePrice - returnNum * returnPrice
					- purchaseReturnNum * purchaseReturnPrice) * 0.1;
			proportionOfAdvertising = totalCost * 0.7;
			comprehensiveGrossProfit = frontDeskMaori + preferentialVolume + monthlyReturnRatio
					+ proportionOfAdvertising;
			frontDeskMaoriAccountsForGMVRatio = gmv != 0 ? comprehensiveGrossProfit / gmv : 0;
			advertisingCostsAccountForGMVRatio = gmv != 0 ? proportionOfAdvertising / gmv : 0;
			monthlyReturnToGMVRatio = gmv != 0 ? monthlyReturnRatio / gmv : 0;
			purchaseReturnPrice = gmv != 0 ? preferentialVolume / gmv : 0;
			xd.setAdvertisingCostsAccountForGMVRatio(Double.valueOf(df.format(advertisingCostsAccountForGMVRatio)));
			xd.setComprehensiveGrossProfit(Double.valueOf(df.format(comprehensiveGrossProfit)));
			xd.setConcessionVolumeReduction(Double.valueOf(df.format(concessionVolumeReduction)));
			xd.setCouponsAccountForGMVRatio(Double.valueOf(df.format(couponsAccountForGMVRatio)));
			xd.setFrontDeskMaori(Double.valueOf(df.format(frontDeskMaori)));
			xd.setFrontDeskMaoriAccountsForGMVRatio(Double.valueOf(df.format(frontDeskMaoriAccountsForGMVRatio)));
			xd.setGmv(Double.valueOf(df.format(gmv)));
			xd.setSalesVolume(salesVolume);
			xd.setMonthlyReturnRatio(Double.valueOf(df.format(monthlyReturnRatio)));
			xd.setMonthlyReturnToGMVRatio(Double.valueOf(df.format(monthlyReturnToGMVRatio)));
			xd.setPreferentialVolume(Double.valueOf(df.format(preferentialVolume)));
			xd.setProportionOfAdvertising(Double.valueOf(df.format(proportionOfAdvertising)));
			xd.setPurchaseCost(Double.valueOf(df.format(purchaseCost)));
			xd.setPurchasePrice(Double.valueOf(df.format(purchasePrice)));
			xd.setSalesPrice(Double.valueOf(df.format(salesPrice)));
		});

		// 导出操作
		ExcelUtil.exportExcel(result, "测算模板", "测算模板", XuhlDataVo.class, "测算模板.xls", response);
	}

}