package com.kfwl.security;

import java.util.Date;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.kfwl.entity.BaseEntity;

import lombok.extern.java.Log;

/**
 *
 * 审计监听.
 * <p>
 * 在实体保存时添加审计信息，比如创建人、创建时间、最后修改人，最后修改时间等
 *
 */
@SuppressWarnings({ "unused" })
@Log
public class AuditableListener {

	@PrePersist
	@PreUpdate
	@PostUpdate
	@PostPersist
	public void prePersist(Object object) {
		setAuditableInfo(object);
	}

	private void setAuditableInfo(Object object) {

		if (object == null)
			return;

		if (!(object instanceof BaseEntity))
			return;

		BaseEntity entity = (BaseEntity) object;
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		
		if (entity.getCreateTime() == null) {
			entity.setCreateTime(new Date());
		}
		if (entity.getCreateUser() == null) {
			entity.setCreateUser(userDetails.getUsername());
		}
		entity.setUpdateUser(userDetails.getUsername());
		entity.setUpdateTime(new Date());

	}
}