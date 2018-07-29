package com.kfwl.dao.xuhl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kfwl.entity.xuhl.Coupon;

/**
 * 采购退货操作
 */
public interface CouponRepository extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon> {
}
