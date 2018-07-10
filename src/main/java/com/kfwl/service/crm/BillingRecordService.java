package com.kfwl.service.crm;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.kfwl.dao.crm.BillingRecordRepository;
import com.kfwl.entity.crm.BillingRecord;

@Service
@Transactional(rollbackFor = Exception.class)
public class BillingRecordService {
	@Autowired
	BillingRecordRepository billingRecordRepository;
	@Autowired
	private EntityManager entityManager;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
	SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DecimalFormat df = new DecimalFormat("######0.00");

	public Page<BillingRecord> listBillingRecords(Pageable pageable, String regName, String companyName,
			String creditCode, String dateFrom, String dateTo) {
		Page<BillingRecord> billingRecordPage = billingRecordRepository
				.findAll(getPageDate(regName, companyName, creditCode, dateFrom, dateTo), pageable);
		return billingRecordPage;
	}

	private Specification<BillingRecord> getPageDate(String regName, String companyName, String creditCode,
			String dateFrom, String dateTo) {
		return (root, query, cb) -> {
			List<Predicate> list = new ArrayList<Predicate>();
			if (null != regName && !"".equals(regName)) {
				list.add(cb.equal(root.get("regName").as(String.class), regName));
			}
			if (null != creditCode && !"".equals(creditCode)) {
				list.add(cb.equal(root.get("creditCode").as(String.class), creditCode));
			}
			if (null != companyName && !"".equals(companyName)) {
				list.add(cb.equal(root.get("companyName").as(String.class), companyName));
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

	public void removeByCondition(String regName, String companyName, String creditCode, String dateFrom,
			String dateTo) {
		List<BillingRecord> findAll = billingRecordRepository
				.findAll(this.getPageDate(regName, companyName, creditCode, dateFrom, dateTo));
		billingRecordRepository.delete(findAll);
	}

	/**
	 * 查询重复的数据
	 * 
	 * @param br
	 * @return
	 */
	public BillingRecord getDistinctBillingRecord(BillingRecord br) {
		BillingRecord billingRecord = billingRecordRepository.findOne(this.getByfindRepeat(br));
		return billingRecord;
	}

	private Specification<BillingRecord> getByfindRepeat(BillingRecord br) {
		return (root, query, cb) -> {
			Predicate predicate = cb.conjunction();

			Predicate p1 = cb.equal(root.get("createTime").as(Date.class), br.getCreateTime());
			predicate.getExpressions().add(p1);

			Predicate p2 = cb.equal(root.get("companyName").as(String.class), br.getCompanyName());
			predicate.getExpressions().add(p2);

			Predicate p3 = cb.equal(root.get("individualName").as(String.class), br.getIndividualName());
			predicate.getExpressions().add(p3);

			Predicate p4 = cb.equal(root.get("regName").as(String.class), br.getRegName());
			predicate.getExpressions().add(p4);

			Predicate p5 = cb.equal(root.get("creditCode").as(String.class), br.getCreditCode());
			predicate.getExpressions().add(p5);

			Predicate p6 = cb.equal(root.get("taxAmount").as(Double.class),
					Double.parseDouble(df.format(br.getTaxAmount())));
			predicate.getExpressions().add(p6);

			Predicate p7 = cb.equal(root.get("noTaxAmount").as(Double.class),
					Double.parseDouble(df.format(br.getNoTaxAmount())));
			predicate.getExpressions().add(p7);

			Predicate p8 = cb.equal(root.get("tax").as(Double.class), Double.parseDouble(df.format(br.getTax())));
			predicate.getExpressions().add(p8);

			return predicate;
		};
	}

	// 删除
	public void deleteBillingRecord(Long[] ids) {
		List<BillingRecord> billingRecords = billingRecordRepository.findAll(Arrays.asList(ids));
		billingRecordRepository.delete(billingRecords);
	}

	// 插入
	public void insert(List<BillingRecord> billingRecords) {
		billingRecordRepository.save(billingRecords);
	}

	/**
	 * 根据名称和信用代码和个体名称分组获取总不含税金额
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Double> getImportBillingRecordNoTaxAmountForRegNameCreditCode(String startTime, String endTime) {
		Map<String, Double> result = new HashMap<>();
		StringBuilder sql = new StringBuilder();
		sql.append("select reg_name,credit_code,sum(no_tax_amount) from t_crm_import_billingrecord"
				+ " where date_format(create_time,'%Y/%m')>= ? and date_format(create_time,'%Y/%m')<= ? "
				+ " group by reg_name,credit_code,individual_name");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("startTime", startTime).setParameter("endTime", endTime);
		List<Object[]> resultList = query.getResultList();
		for (Object[] temp : resultList) {
			result.put(temp[0].toString() + "_" + temp[1].toString(), Double.valueOf(temp[2].toString()));
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<BillingRecord> listBillingRecordExcelData(String startTime, String endTime, String companyName,
			Double totalTaxAmount, String regName) {
		List<BillingRecord> result = new ArrayList<BillingRecord>();
		// 参数
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select * from (select date_format(create_time,'%Y/%m') as createTime,company_name as companyName,reg_name as regName,individual_name as individualname,"
						+ "credit_code as creditCode,sum(tax_amount) as taxAmount,sum(no_tax_amount) as noTaxAmount,sum(tax) as tax"
						+ " from billing_record "
						+ " where date_format(create_time,'%Y/%m')>= :startTime and date_format(create_time,'%Y/%m')<= :endTime ");

		if (totalTaxAmount != null) {
			sql.append(" and id in(select id from billing_record t2 where t2.credit_code in ( "
					+ " select credit_code from t_crm_import_billingrecord t3  where 1=1 "
					+ "and date_format(create_time,'%Y/%m')>= :startTime and date_format(create_time,'%Y/%m')<= :endTime");
			if (StringUtils.isNotBlank(companyName)) {
				sql.append(" and t3.company_name=:companyName");
			}
			if (StringUtils.isNotBlank(regName)) {
				sql.append(" and t3.reg_name=:regName");
			}
			sql.append(
					" group by t3.reg_name,t3.individual_name,t3.credit_code having sum(t3.tax_amount)>=:totalTaxAmount))");
		} else {
			if (StringUtils.isNotBlank(companyName)) {
				sql.append(" and companyName=:companyName");
			}
			if (StringUtils.isNotBlank(regName)) {
				sql.append(" and regName=:regName");
			}
		}
		sql.append(
				" group by regName,individualName,creditCode,companyName,date_format(createTime,'%Y/%m')  order by createTime) t");
		sql.append(" order by t.regName,t.individualName,creditCode");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("startTime", startTime).setParameter("endTime", endTime);
		if (StringUtils.isNotBlank(companyName)) {
			query.setParameter("companyName", companyName);
		}
		if (totalTaxAmount != null) {
			query.setParameter("totalTaxAmount", totalTaxAmount);
		}
		if (StringUtils.isNotBlank(regName)) {
			query.setParameter("regName", regName);
		}
		List<Object[]> resultList = query.getResultList();
		for (Object[] temp : resultList) {
			BillingRecord br = new BillingRecord();
			br.setCreateTime(new Date(temp[0].toString() + "/01"));
			br.setCompanyName(temp[1].toString());
			br.setRegName(temp[2].toString());
			br.setIndividualName(temp[3].toString());
			br.setCreditCode(temp[4].toString());
			br.setTaxAmount(Double.valueOf(temp[5].toString()));
			br.setNoTaxAmount(Double.valueOf(temp[6].toString()));
			br.setTax(Double.valueOf(temp[7].toString()));
			result.add(br);
		}
		return result;
	}
}
