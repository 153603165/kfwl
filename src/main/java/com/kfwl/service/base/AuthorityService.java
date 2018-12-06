package com.kfwl.service.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kfwl.dao.base.AuthorityRepository;
import com.kfwl.entity.base.Authority;

/**
 * 权限管理服务
 * 
 * @author Administrator
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AuthorityService {
	@Autowired
	AuthorityRepository authorityRepository;

	/**
	 * 获取页面权限分页数据
	 * 
	 * @param pageable
	 * @param authName
	 * @return
	 */
	public Page<Authority> pageAuthorityPage(Pageable pageable, String authName) {
		Page<Authority> authorityRecordPage = authorityRepository.findAll((root, query, cb) -> {
			List<Predicate> list = new ArrayList<Predicate>();
			if (null != authName && !"".equals(authName)) {
				list.add(cb.like(root.get("authName").as(String.class), "%" + authName + "%"));
			}
			Predicate[] p = new Predicate[list.size()];
			return cb.and(list.toArray(p));
		}, pageable);
		return authorityRecordPage;
	}

	/**
	 * 根据id获取权限实体
	 * 
	 * @param id
	 * @return
	 */
	public Authority getById(Long id) {
		return authorityRepository.findOne(id);
	}

	/**
	 * 获取所有权限数据
	 * 
	 * @return
	 */
	public List<Authority> listAuthoritys() {
		return authorityRepository.findAll();
	}

	/**
	 * 根据ids获取权限数据集合
	 * 
	 * @param ids
	 * @return
	 */
	public List<Authority> listByIds(List<Long> ids) {
		return authorityRepository.findAll(ids);
	}

	/**
	 * 根据ids集合删除权限
	 * 
	 * @param ids
	 */
	public void delete(Long[] ids) {
		List<Authority> users = authorityRepository.findAll(Arrays.asList(ids));
		authorityRepository.delete(users);
	}

	/**
	 * 更新或保存权限
	 * 
	 * @param authority
	 * @return
	 */
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

	/**
	 * 获取权限最大排序值+1
	 * 
	 * @return
	 */
	public Long getMaxSeq() {
		return authorityRepository.getMaxSeq() + 1;
	}
}
