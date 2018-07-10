package com.kfwl.entity.base;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

import com.kfwl.entity.BaseEntity;
import com.kfwl.security.AuditableListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 */
@Table(name = "sys_authority")
@Setter
@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditableListener.class)
public class Authority extends BaseEntity implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// columns START
	/** 当前key */
	private String authKey;
	/** 权限名称 */
	private String authName;
	/** 权限描述 */
	private String authDesc;
	/** 排序 */
	private String seq;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public String getAuthority() {
		return authKey;
	}

}
