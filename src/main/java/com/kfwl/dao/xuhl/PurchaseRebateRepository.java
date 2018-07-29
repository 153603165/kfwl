package com.kfwl.dao.xuhl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kfwl.entity.xuhl.PurchaseRebate;

/**
 * 采购退货操作
 */
public interface PurchaseRebateRepository
		extends JpaRepository<PurchaseRebate, Long>, JpaSpecificationExecutor<PurchaseRebate> {
}
