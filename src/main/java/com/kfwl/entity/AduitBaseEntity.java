package com.kfwl.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.kfwl.common.AduitEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 统一定义审核的entity基类. <br>
 */
@MappedSuperclass
@Setter
@Getter
public class AduitBaseEntity extends BaseEntity implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 0:待审核 1:审核通过 2:审核失败
	 */
	protected Integer aduitStatus = 0;

	@Transient
	protected String aduitStatusDesc;

	public String getAduitStatusDesc() {
		return AduitEnum.getAuditStateByValue(aduitStatus).getDescription();
	}

}
