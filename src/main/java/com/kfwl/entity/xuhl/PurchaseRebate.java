package com.kfwl.entity.xuhl;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import com.kfwl.entity.BaseEntity;
import com.kfwl.security.AuditableListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "xuhl_purchase_rebate")
@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@EntityListeners(AuditableListener.class)
public class PurchaseRebate extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 商品
	 */
	private String skuCode;

	private String skuName;
	/**
	 * 类型 1：采购退货 2、采购 3、退货
	 */
	private Integer type;
	/**
	 * 商品数量
	 */
	private Integer num;
	/**
	 * 金额
	 */
	private Double purchaseRebatePrice;
}