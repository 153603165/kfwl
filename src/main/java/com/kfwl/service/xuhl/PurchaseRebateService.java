package com.kfwl.service.xuhl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kfwl.dao.xuhl.PurchaseRebateRepository;
import com.kfwl.entity.xuhl.PurchaseRebate;

@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseRebateService {
	@Autowired
	private EntityManager entityManager;
	@Autowired
	PurchaseRebateRepository PurchaseRebateRepository;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
	SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Page<PurchaseRebate> pagePurchaseRebates(Pageable pageable, String skuCode, String skuName, Integer type,
			String dateFrom, String dateTo) {

		Page<PurchaseRebate> PurchaseRebatePage = PurchaseRebateRepository
				.findAll(getPageDate(skuCode, skuName, type, dateFrom, dateTo), pageable);
		return PurchaseRebatePage;
	}

	private Specification<PurchaseRebate> getPageDate(String skuCode, String skuName, Integer type, String dateFrom,
			String dateTo) {
		return (root, query, cb) -> {
			List<Predicate> list = new ArrayList<Predicate>();
			if (null != skuCode && !"".equals(skuCode)) {
				list.add(cb.equal(root.get("skuCode").as(String.class), skuCode));
			}
			if (null != skuName && !"".equals(skuName)) {
				list.add(cb.like(root.get("skuName").as(String.class), "%" + skuName + "%"));
			}
			if (null != type && !"".equals(type)) {
				list.add(cb.equal(root.get("type").as(Integer.class), type));
			}
			try {
				if (StringUtils.isNotEmpty(dateFrom)) {
					list.add(cb.greaterThanOrEqualTo(root.get("createTime"),
							sdfmat.parse(sdfmat.format(sdf.parse(dateFrom).getTime()))));
				}
				if (StringUtils.isNotEmpty(dateTo)) {
					list.add(cb.lessThanOrEqualTo(root.get("createTime"),
							sdfmat.parse(sdfmat.format(sdf.parse(dateTo).getTime() + 86400000))));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Predicate[] p = new Predicate[list.size()];
			return cb.and(list.toArray(p));
		};
	}

	public void removeByCondition(String skuCode, String skuName, Integer type, String dateFrom, String dateTo) {
		List<PurchaseRebate> findAll = PurchaseRebateRepository
				.findAll(this.getPageDate(skuCode, skuName, type, dateFrom, dateTo));
		PurchaseRebateRepository.delete(findAll);
	}

	// 删除
	public void deletePurchaseRebate(Long[] ids) {
		List<PurchaseRebate> purchaseRebates = PurchaseRebateRepository.findAll(Arrays.asList(ids));
		PurchaseRebateRepository.delete(purchaseRebates);
	}

	// 插入
	public void insert(List<PurchaseRebate> purchaseRebates) {
		PurchaseRebateRepository.save(purchaseRebates);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<PurchaseRebate> listPurchaseRebates(String skuCode, String skuName, String dateFrom, String dateTo,
			Integer type) {
		List<PurchaseRebate> result = new ArrayList<PurchaseRebate>();
		// 参数
		StringBuilder sql = new StringBuilder();
		sql.append("select date_format(create_time,'%Y/%m') as create_time,sku_code,sku_name,type, "
				+ "sum(num) as total_num,sum(purchase_rebate_price) as total_purchase_rebate_price from xuhl_purchase_rebate "
				+ " where date_format(create_time,'%Y/%m')>= :startTime and date_format(create_time,'%Y/%m')<= :endTime ");

		if (StringUtils.isNotBlank(skuCode)) {
			sql.append(" and sku_code=:skuCode");
		}
		if (StringUtils.isNotBlank(skuName)) {
			sql.append(" and sku_name=:skuName");
		}
		sql.append(" group by sku_code,type  order by sku_code");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("startTime", dateFrom).setParameter("endTime", dateTo);
		if (StringUtils.isNotBlank(skuCode)) {
			query.setParameter("skuCode", skuCode);
		}
		if (StringUtils.isNotBlank(skuName)) {
			query.setParameter("skuName", skuName);
		}
		List<Object[]> resultList = query.getResultList();
		for (Object[] temp : resultList) {
			PurchaseRebate et = new PurchaseRebate();
			et.setCreateTime(new Date(temp[0].toString() + "/01"));
			et.setSkuCode(temp[1].toString());
			et.setSkuName(temp[2].toString());
			et.setType(Integer.valueOf(temp[3].toString()));
			et.setNum(Integer.valueOf(temp[4].toString()));
			et.setPurchaseRebatePrice(Double.valueOf(temp[5].toString()));
			result.add(et);
		}
		return result;
	}
}
