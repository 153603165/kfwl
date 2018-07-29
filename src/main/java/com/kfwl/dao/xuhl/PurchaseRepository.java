package com.kfwl.dao.xuhl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kfwl.entity.xuhl.Purchase;

/**
 * 采购退货操作
 */
public interface PurchaseRepository
		extends JpaRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {
}
