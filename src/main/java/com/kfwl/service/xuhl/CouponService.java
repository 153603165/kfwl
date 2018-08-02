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

import com.kfwl.dao.xuhl.CouponRepository;
import com.kfwl.entity.xuhl.Coupon;

@Service
@Transactional(rollbackFor = Exception.class)
public class CouponService {
	@Autowired
	private EntityManager entityManager;
	@Autowired
	CouponRepository couponRepository;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
	SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Page<Coupon> pageCoupons(Pageable pageable, String skuCode, String skuName, String dateFrom, String dateTo) {

		Page<Coupon> CouponPage = couponRepository.findAll(getPageDate(skuCode, skuName, dateFrom, dateTo), pageable);
		return CouponPage;
	}

	private Specification<Coupon> getPageDate(String skuCode, String skuName, String dateFrom, String dateTo) {
		return (root, query, cb) -> {
			List<Predicate> list = new ArrayList<Predicate>();
			if (null != skuCode && !"".equals(skuCode)) {
				list.add(cb.equal(root.get("skuCode").as(String.class), skuCode));
			}
			if (null != skuName && !"".equals(skuName)) {
				list.add(cb.like(root.get("skuName").as(String.class), "%" + skuName + "%"));
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

	public void removeByCondition(String skuCode, String skuName, String dateFrom, String dateTo) {
		List<Coupon> findAll = couponRepository.findAll(this.getPageDate(skuCode, skuName, dateFrom, dateTo));
		couponRepository.delete(findAll);
	}

	// 删除
	public void deleteCoupon(Long[] ids) {
		List<Coupon> Coupons = couponRepository.findAll(Arrays.asList(ids));
		couponRepository.delete(Coupons);
	}

	// 插入
	public void insert(List<Coupon> coupons) {
		couponRepository.save(coupons);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Coupon> listCoupons(String skuCode, String skuName, String dateFrom, String dateTo) {
		List<Coupon> result = new ArrayList<Coupon>();
		// 参数
		StringBuilder sql = new StringBuilder();
		sql.append("select date_format(create_time,'%Y/%m') as create_time,sku_code,sku_name,"
				+ "sum(concession_volume_reduction) as total_concession_volume_reduction from xuhl_coupon"
				+ " where date_format(create_time,'%Y/%m')>= :startTime and date_format(create_time,'%Y/%m')<= :endTime ");

		if (StringUtils.isNotBlank(skuCode)) {
			sql.append(" and sku_code=:skuCode");
		}
		if (StringUtils.isNotBlank(skuName)) {
			sql.append(" and sku_name=:skuName");
		}
		sql.append(" group by sku_code  order by sku_code");
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
			Coupon co = new Coupon();
			co.setCreateTime(new Date(temp[0].toString() + "/01"));
			co.setSkuCode(temp[1].toString());
			co.setSkuName(temp[2].toString());
			co.setConcessionVolumeReduction(Double.valueOf(temp[3].toString()));
			result.add(co);
		}
		return result;
	}

}
