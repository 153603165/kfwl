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

@Table(name = "xuhl_sale")
@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@EntityListeners(AuditableListener.class)
public class Sale extends BaseEntity {

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
	 * 销量
	 */
	private Integer salesVolume;
	/**
	 * 销售额
	 */
	private Double salesPrice;
}