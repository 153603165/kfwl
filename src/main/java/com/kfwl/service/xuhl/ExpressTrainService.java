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

import com.kfwl.dao.xuhl.ExpressTrainRepository;
import com.kfwl.entity.xuhl.ExpressTrain;

@Service
@Transactional(rollbackFor = Exception.class)
public class ExpressTrainService {
	@Autowired
	private EntityManager entityManager;
	@Autowired
	ExpressTrainRepository expressTrainRepository;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
	SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Page<ExpressTrain> pageExpressTrains(Pageable pageable, String skuCode, String dateFrom, String dateTo) {

		Page<ExpressTrain> expressTrainPage = expressTrainRepository.findAll(getPageDate(skuCode, dateFrom, dateTo),
				pageable);
		return expressTrainPage;
	}

	private Specification<ExpressTrain> getPageDate(String skuCode, String dateFrom, String dateTo) {
		return (root, query, cb) -> {
			List<Predicate> list = new ArrayList<Predicate>();
			if (null != skuCode && !"".equals(skuCode)) {
				list.add(cb.equal(root.get("skuCode").as(String.class), skuCode));
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

	public void removeByCondition(String skuCode, String dateFrom, String dateTo) {
		List<ExpressTrain> findAll = expressTrainRepository.findAll(this.getPageDate(skuCode, dateFrom, dateTo));
		expressTrainRepository.delete(findAll);
	}

	// 删除
	public void deleteExpressTrain(Long[] ids) {
		List<ExpressTrain> expressTrains = expressTrainRepository.findAll(Arrays.asList(ids));
		expressTrainRepository.delete(expressTrains);
	}

	// 插入
	public void insert(List<ExpressTrain> expressTrains) {
		expressTrainRepository.save(expressTrains);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<ExpressTrain> listExpressTrains(String skuCode, String dateFrom, String dateTo) {
		List<ExpressTrain> result = new ArrayList<ExpressTrain>();
		// 参数
		StringBuilder sql = new StringBuilder();
		sql.append("select date_format(create_time,'%Y/%m') as create_time,sku_code,"
				+ "sum(total_cost) as total_total_cost from xuhl_express_train"
				+ " where date_format(create_time,'%Y/%m')>= :startTime and date_format(create_time,'%Y/%m')<= :endTime ");

		if (StringUtils.isNotBlank(skuCode)) {
			sql.append(" and sku_code=:skuCode");
		}
		sql.append(" group by sku_code  order by sku_code");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("startTime", dateFrom).setParameter("endTime", dateTo);
		if (StringUtils.isNotBlank(skuCode)) {
			query.setParameter("skuCode", skuCode);
		}
		List<Object[]> resultList = query.getResultList();
		for (Object[] temp : resultList) {
			ExpressTrain et = new ExpressTrain();
			et.setCreateTime(new Date(temp[0].toString() + "/01"));
			et.setSkuCode(temp[1].toString());
			et.setTotalCost(Double.valueOf(temp[2].toString()));
			result.add(et);
		}
		return result;
	}
}
