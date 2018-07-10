package com.kfwl.service.crm;

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

import com.kfwl.dao.crm.PreBillingRecordRepository;
import com.kfwl.entity.crm.PreBillingRecord;

@Service
@Transactional(rollbackFor = Exception.class)
public class PreBillingRecordService {
	@Autowired
	PreBillingRecordRepository preBillingRecordRepository;
	@Autowired
	private EntityManager entityManager;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
	SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Page<PreBillingRecord> listPreBillingRecords(Pageable pageable, String category, String legalPerson,
			String companyName, String creditCode, String dateFrom, String dateTo) {
		Page<PreBillingRecord> PreBillingRecordPage = preBillingRecordRepository
				.findAll(getPageDate(category, legalPerson, companyName, creditCode, dateFrom, dateTo), pageable);
		return PreBillingRecordPage;
	}

	private Specification<PreBillingRecord> getPageDate(String category, String legalPerson, String companyName,
			String creditCode, String dateFrom, String dateTo) {
		return (root, query, cb) -> {
			List<Predicate> list = new ArrayList<Predicate>();
			if (null != category && !"".equals(category)) {
				list.add(cb.equal(root.get("category").as(String.class), category));
			}
			if (null != legalPerson && !"".equals(legalPerson)) {
				list.add(cb.equal(root.get("legalPerson").as(String.class), legalPerson));
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

	public void removeByCondition(String category, String legalPerson, String companyName, String creditCode,
			String dateFrom, String dateTo) {
		List<PreBillingRecord> findAll = preBillingRecordRepository
				.findAll(this.getPageDate(category, legalPerson, companyName, creditCode, dateFrom, dateTo));
		preBillingRecordRepository.delete(findAll);
	}

	/**
	 * 查询重复的数据
	 * 
	 * @param br
	 * @return
	 */
	public PreBillingRecord getDistinctPreBillingRecord(PreBillingRecord br) {
		PreBillingRecord PreBillingRecord = preBillingRecordRepository.findOne(this.getByfindRepeat(br));
		return PreBillingRecord;
	}

	private Specification<PreBillingRecord> getByfindRepeat(PreBillingRecord br) {
		return (root, query, cb) -> {
			Predicate predicate = cb.conjunction();
			Predicate p1 = cb.equal(root.get("createTime").as(Date.class), br.getCreateTime());
			predicate.getExpressions().add(p1);

			Predicate p2 = cb.equal(root.get("companyName").as(String.class), br.getCompanyName());
			predicate.getExpressions().add(p2);

			Predicate p3 = cb.equal(root.get("individualName").as(String.class), br.getIndividualName());
			predicate.getExpressions().add(p3);

			Predicate p4 = cb.equal(root.get("category").as(String.class), br.getCategory());
			predicate.getExpressions().add(p4);

			Predicate p5 = cb.equal(root.get("creditCode").as(String.class), br.getCreditCode());
			predicate.getExpressions().add(p5);

			Predicate p6 = cb.equal(root.get("mulAccount").as(String.class), br.getMulAccount());
			predicate.getExpressions().add(p6);

			Predicate p7 = cb.equal(root.get("sendAmount").as(String.class), br.getSendAmount());
			predicate.getExpressions().add(p7);

			Predicate p8 = cb.equal(root.get("legalPerson").as(String.class), br.getLegalPerson());
			predicate.getExpressions().add(p8);

			return predicate;
		};
	}

	// 删除
	public void deletePreBillingRecord(Long[] ids) {
		List<PreBillingRecord> PreBillingRecords = preBillingRecordRepository.findAll(Arrays.asList(ids));
		preBillingRecordRepository.delete(PreBillingRecords);
	}

	// 插入
	public void insert(List<PreBillingRecord> PreBillingRecords) {
		preBillingRecordRepository.save(PreBillingRecords);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<PreBillingRecord> listPreBillingRecordExcelData(String startTime, String endTime, String category,
			String companyName, String legalPerson) {
		List<PreBillingRecord> result = new ArrayList<PreBillingRecord>();
		// 参数
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select * from (select date_format(create_time,'%Y/%m') as create_time,order_no,category,plate_num,legal_person,mul_account,"
						+ "company_name,individual_name,credit_code,sum(send_amount) as total_send_amount"
						+ " from pre_billing_record "
						+ " where date_format(create_time,'%Y/%m')>= :startTime and date_format(create_time,'%Y/%m')<= :endTime ");

		if (StringUtils.isNotBlank(companyName)) {
			sql.append(" and company_name=:companyName");
		}
		if (StringUtils.isNotBlank(category)) {
			sql.append(" and category=:category");
		}
		if (StringUtils.isNotBlank(legalPerson)) {
			sql.append(" and legal_person=:legalPerson");
		}
		sql.append(" group by credit_code,date_format(create_time,'%Y/%m')  order by create_time) t");
		sql.append(" order by t.order_no+0");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("startTime", startTime).setParameter("endTime", endTime);
		if (StringUtils.isNotBlank(companyName)) {
			query.setParameter("companyName", companyName);
		}
		if (StringUtils.isNotBlank(category)) {
			query.setParameter("category", category);
		}
		if (StringUtils.isNotBlank(legalPerson)) {
			query.setParameter("legalPerson", legalPerson);
		}
		List<Object[]> resultList = query.getResultList();
		for (Object[] temp : resultList) {
			PreBillingRecord br = new PreBillingRecord();
			br.setCreateTime(new Date(temp[0].toString() + "/01"));
			br.setOrderNo(temp[1].toString());
			br.setCategory(temp[2].toString());
			br.setPlateNum(temp[3].toString());
			br.setLegalPerson(temp[4].toString());
			br.setMulAccount(temp[5].toString());
			br.setCompanyName(temp[6].toString());
			br.setIndividualName(temp[7].toString());
			br.setCreditCode(temp[8].toString());
			br.setSendAmount(Double.valueOf(temp[9].toString()));
			result.add(br);
		}
		return result;
	}
}
