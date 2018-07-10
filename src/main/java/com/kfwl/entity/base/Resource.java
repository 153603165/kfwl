package com.kfwl.entity.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kfwl.entity.BaseEntity;
import com.kfwl.security.AuditableListener;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 */
@Table(name = "sys_resource")
@Setter
@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditableListener.class)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Resource extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 权限名称 */
	private String resourceName;
	/** 权限描述 */
	private String resourceDesc;
	/** 菜单链接 */
	private String menuUrl;
	/** 图标样式 */
	private String iconCls;
	/** 排序号 */
	private Integer seq;
	/** 类型 0:菜单 1:链接 */
	private Integer type;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "pid")
	private Resource parentResource;

	@OneToMany(mappedBy = "parentResource", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private List<Resource> childResources = new ArrayList<Resource>();

	@Transient
	private Long parentId;

	public Long getParentId() {
		if (parentResource != null) {
			parentId = parentResource.getId();
		}
		return parentId;
	}

	@Transient
	private String state = "open";

	public String getState() {
		state = "open";
		if (type == 0) {
			state = "closed";
		}
		return state;

	}
}
