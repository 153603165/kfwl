package com.kfwl.dao.xuhl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kfwl.entity.xuhl.Sale;

/**
 * 采购退货操作
 */
public interface SaleRepository extends JpaRepository<Sale, Long>, JpaSpecificationExecutor<Sale> {
}
