package com.kfwl.dao.crm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kfwl.entity.crm.PreBillingRecord;

/**
 * 用户管理
 */
public interface PreBillingRecordRepository
		extends JpaRepository<PreBillingRecord, Long>, JpaSpecificationExecutor<PreBillingRecord> {
}
