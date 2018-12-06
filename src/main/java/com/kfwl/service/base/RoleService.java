package com.kfwl.service.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kfwl.dao.base.RoleRepository;
import com.kfwl.entity.base.Authority;
import com.kfwl.entity.base.Resource;
import com.kfwl.entity.base.Role;
import com.kfwl.exception.impl.BusinessException;

/**
 * 角色服务
 * 
 * @author Administrator
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	ResourceService resourceService;
	@Autowired
	AuthorityService authorityService;

	/**
	 * 获取角色分页数据
	 * 
	 * @param pageable
	 * @param roleName
	 * @return
	 */
	public Page<Role> pageRoles(Pageable pageable, String roleName) {

		Page<Role> billingRecordPage = roleRepository.findAll((root, query, cb) -> {
			List<Predicate> list = new ArrayList<Predicate>();
			if (null != roleName && !"".equals(roleName)) {
				list.add(cb.equal(root.get("roleName").as(String.class), roleName));
			}
			Predicate[] p = new Predicate[list.size()];
			return cb.and(list.toArray(p));
		}, pageable);
		return billingRecordPage;
	}

	/**
	 * 根据id获取角色
	 * 
	 * @param id
	 * @return
	 */
	public Role getById(Long id) {
		return roleRepository.findOne(id);
	}

	/**
	 * 根据ids集合获取角色集合
	 * 
	 * @param ids
	 * @return
	 */
	public List<Role> listByIds(List<Long> ids) {
		return roleRepository.findAll(ids);
	}

	/**
	 * 根据ids集合删除角色
	 * 
	 * @param ids
	 */
	public void delete(Long[] ids) {
		List<Role> users = roleRepository.findAll(Arrays.asList(ids));
		roleRepository.delete(users);
	}

	/**
	 * 更新或保存角色
	 * 
	 * @param roleVo
	 * @return
	 * @throws BusinessException
	 */
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
		} else {
			role.setResources(null);
		}
		if (roleVo.getAuthoritysIds() != null && roleVo.getAuthoritysIds().size() > 0) {
			List<Authority> authoritys = authorityService.listByIds(roleVo.getAuthoritysIds());
			role.setAuthoritys(authoritys);
		} else {
			role.setAuthoritys(null);
		}
		Role result = roleRepository.save(role);
		return result;
	}

	/**
	 * 根据角色名称获取角色
	 * 
	 * @param roleName
	 * @return
	 */
	public Role getByRoleName(String roleName) {
		return roleRepository.getByRoleName(roleName);

	}

	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	public List<Role> listRoleComboboxs() {
		return roleRepository.findAll();
	}
}
