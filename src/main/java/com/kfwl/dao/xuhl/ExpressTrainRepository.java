package com.kfwl.dao.xuhl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kfwl.entity.xuhl.ExpressTrain;

/**
 * 采购退货操作
 */
public interface ExpressTrainRepository
		extends JpaRepository<ExpressTrain, Long>, JpaSpecificationExecutor<ExpressTrain> {
}
