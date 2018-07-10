package com.kfwl.service.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kfwl.dao.base.RoleRepository;
import com.kfwl.entity.base.Authority;
import com.kfwl.entity.base.Resource;
import com.kfwl.entity.base.Role;
import com.kfwl.exception.impl.BusinessException;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	ResourceService resourceService;
	@Autowired
	AuthorityService authorityService;

	public Page<Role> pageRoles(Pageable pageable, String roleName) {

		Page<Role> billingRecordPage = roleRepository.findAll(getPageData(roleName), pageable);
		return billingRecordPage;
	}

	private Specification<Role> getPageData(String roleName) {
		return (root, query, cb) -> {
			List<Predicate> list = new ArrayList<Predicate>();
			if (null != roleName && !"".equals(roleName)) {
				list.add(cb.equal(root.get("roleName").as(String.class), roleName));
			}
			Predicate[] p = new Predicate[list.size()];
			return cb.and(list.toArray(p));
		};
	}

	public Role getById(Long id) {
		return roleRepository.findOne(id);
	}

	public List<Role> listByIds(List<Long> ids) {
		return roleRepository.findAll(ids);
	}

	public void delete(Long[] ids) {
		List<Role> users = roleRepository.findAll(Arrays.asList(ids));
		roleRepository.delete(users);
	}

	public Role saveOrUpdate(Role roleVo) throws BusinessException {
		Role tempRole = getByRoleName(roleVo.getRoleName());
		Role role = new Role();
		if (roleVo.getId() != null) {
			if (tempRole != null && !tempRole.getId().equals(roleVo.getId())) {
				throw new BusinessException("该角色名已存在,请重新输入");
			}
			role = getById(roleVo.getId());
		} else {
			if (tempRole != null) {
				throw new BusinessException("该角色名已存在,请重新输入");
			}
		}
		role.setRoleName(roleVo.getRoleName());
		role.setRoleDesc(roleVo.getRoleDesc());
		if (roleVo.getResourcesIds() != null && roleVo.getResourcesIds().size() > 0) {
			List<Resource> resources = resourceService.listByIds(roleVo.getResourcesIds());
			role.setResources(resources);
		}
		if (roleVo.getAuthoritysIds() != null && roleVo.getAuthoritysIds().size() > 0) {
			List<Authority> authoritys = authorityService.listByIds(roleVo.getAuthoritysIds());
			role.setAuthoritys(authoritys);
		}
		Role result = roleRepository.save(role);
		return result;
	}

	public List<Role> saveOrUpdate(List<Role> roles) {
		List<Role> result = roleRepository.save(roles);
		return result;
	}

	public Role getByRoleName(String roleName) {
		return roleRepository.getByRoleName(roleName);

	}

	public List<Role> listRoleComboboxs() {
		return roleRepository.findAll();
	}
}
