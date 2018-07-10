package com.kfwl.service.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kfwl.dao.base.AuthorityRepository;
import com.kfwl.entity.base.Authority;

@Service
@Transactional(rollbackFor = Exception.class)
public class AuthorityService {
	@Autowired
	AuthorityRepository authorityRepository;

	public Page<Authority> pageAuthorityPage(Pageable pageable, String authName) {
		Page<Authority> authorityRecordPage = authorityRepository.findAll(getPageData(authName), pageable);
		return authorityRecordPage;
	}

	private Specification<Authority> getPageData(String authName) {
		return (root, query, cb) -> {
			List<Predicate> list = new ArrayList<Predicate>();
			if (null != authName && !"".equals(authName)) {
				list.add(cb.like(root.get("authName").as(String.class), "%" + authName + "%"));
			}
			Predicate[] p = new Predicate[list.size()];
			return cb.and(list.toArray(p));
		};
	}

	public Authority getById(Long id) {
		return authorityRepository.findOne(id);
	}

	public List<Authority> listAuthoritys() {
		return authorityRepository.findAll();
	}

	public List<Authority> listByIds(List<Long> ids) {
		return authorityRepository.findAll(ids);
	}

	public void delete(Long[] ids) {
		List<Authority> users = authorityRepository.findAll(Arrays.asList(ids));
		authorityRepository.delete(users);
	}

	public Authority saveOrUpdate(Authority authority) {
		Authority tempAuthority = new Authority();
		if (null != authority.getId()) {
			tempAuthority = getById(authority.getId());
			BeanUtils.copyProperties(authority, tempAuthority, "version");
		} else {
			BeanUtils.copyProperties(authority, tempAuthority, "version");
		}
		Authority result = authorityRepository.save(tempAuthority);
		return result;

	}

	public Long getMaxSeq() {
		return authorityRepository.getMaxSeq() + 1;
	}
}
