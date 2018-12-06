package com.kfwl.entity.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import com.kfwl.entity.BaseEntity;
import com.kfwl.security.AuditableListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色类
 * 
 * @author Administrator
 *
 */
@Table(name = "sys_role")
@Setter
@Getter
@Entity
@ToString
@NoArgsConstructor
@EntityListeners(AuditableListener.class)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Role extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 555405769809694013L;
	/**
	 * 
	 */
	/** 角色名称 */
	private String roleName;
	/** 角色描述 */
	private String roleDesc;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "sys_role_resource", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
			@JoinColumn(name = "resource_id") })
	@OrderBy("id")
	private List<Resource> resources = new ArrayList<Resource>();

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "sys_role_authority", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
			@JoinColumn(name = "auth_id") })
	@OrderBy("id")
	private List<Authority> authoritys = new ArrayList<Authority>();
	/**
	 * 关联权限ID集合 @Transient
	 */
	@Transient
	private List<Long> authoritysIds = Lists.newArrayList();

	/**
	 * 关联资源ID集合 @Transient
	 */
	@Transient
	private List<Long> resourcesIds = Lists.newArrayList();

	public List<Long> getResourcesIds() {
		if (null != resources && resources.size() > 0) {
			resources.stream().forEach(a -> resourcesIds.add(a.getId()));
		}
		return resourcesIds;
	}

	public List<Long> getAuthoritysIds() {
		if (null != authoritys && authoritys.size() > 0) {
			authoritys.stream().forEach(a -> authoritysIds.add(a.getId()));
		}
		return authoritysIds;
	}

}
