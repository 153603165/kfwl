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

@Table(name = "xuhl_express_train")
@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@EntityListeners(AuditableListener.class)
public class ExpressTrain extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 商品
	 */
	private String skuCode;
	/**
	 * 总费用
	 */
	private Double totalCost;
}