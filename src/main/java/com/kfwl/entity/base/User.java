package com.kfwl.entity.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kfwl.entity.AduitBaseEntity;
import com.kfwl.security.AuditableListener;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString
@Setter
@Getter
@Table(name = "sys_user")
@SQLDelete(sql = "update sys_user set is_delete = 1 where id = ? and version= ? ")
@SQLDeleteAll(sql = "update sys_user set is_delete = 1 where id = ? and version= ? ")
@Where(clause = "is_delete = 0 ")
@EntityListeners(AuditableListener.class)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User extends AduitBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@Excel(name = "姓名", orderNum = "0")
	private String username;

	@Column(nullable = false)
	@Excel(name = "密码", orderNum = "1")
	private String password;

	/**
	 * 有序的关联对象集合
	 */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	@OrderBy("id")
	private List<Role> roles = new ArrayList<>();

	/**
	 * 有序的关联Role对象id集合
	 */
	@Transient
	private List<Long> roleIds = new ArrayList<>();
	/**
	 * 用户类型
	 */
	@Transient
	private String typeDesc;
	/**
	 * 角色名称
	 */
	@Transient
	private String roleNames;

	public String getRoleNames() {
		roleNames = "";
		if (roles != null && roles.size() > 0) {
			for (int i = 0; i < roles.size(); i++) {
				if (i == roles.size() - 1) {
					roleNames += roles.get(i).getRoleName();
				} else {
					roleNames += roles.get(i).getRoleName() + ",";
				}
			}
		}
		return roleNames;
	}

	public List<Long> getRoleIds() {
		if (roles != null && roles.size() > 0) {
			roles.stream().forEach(r -> roleIds.add(r.getId()));
		}
		return roleIds;
	}

}
