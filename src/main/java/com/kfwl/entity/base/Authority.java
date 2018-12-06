package com.kfwl.entity.base;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.kfwl.entity.BaseEntity;
import com.kfwl.security.AuditableListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 权限类
 * 
 * @author Administrator
 *
 */
@Table(name = "sys_authority")
@Setter
@Getter
@Entity
@ToString
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
	public String getAuthority() {
		return authKey;
	}

}
