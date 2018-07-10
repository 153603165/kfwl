package com.kfwl.entity.crm;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import com.kfwl.entity.BaseEntity;
import com.kfwl.security.AuditableListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@EntityListeners(AuditableListener.class)
public class PreBillingRecord extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 序号
	private String orderNo;
	// 类别
	private String category;
	// 所属公司
	private String companyName;
	// 车牌
	private String plateNum;
	// 法人
	private String legalPerson;
	// 个体名称
	private String individualName;
	// 信用代码
	private String creditCode;
	// 多级账号
	private String mulAccount;
	// 发来金额
	private Double sendAmount;
}
