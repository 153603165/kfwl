package com.kfwl.dao.crm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kfwl.entity.crm.BillingRecord;

/**
 * 用户管理
 */
public interface BillingRecordRepository
		extends JpaRepository<BillingRecord, Long>, JpaSpecificationExecutor<BillingRecord> {
}
