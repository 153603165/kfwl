package com.kfwl.service.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kfwl.dao.base.AuthorityRepository;
import com.kfwl.dao.base.UserRepository;
import com.kfwl.entity.base.Authority;
import com.kfwl.entity.base.Role;
import com.kfwl.entity.base.User;

/**
 * 用户服务
 * 
 * @author Administrator
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService implements UserDetailsService {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthorityRepository authorityRepository;

	/**
	 * 构建oauth服务的方法
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		List<Role> roles = user.getRoles();

		List<Long> roleIds = new ArrayList<>();
		roles.stream().forEach(r -> roleIds.add(r.getId()));
		List<Authority> sysAuthorities = authorityRepository.listByRoleId(roleIds);
		String name = user.getUsername();
		String password = user.getPassword();
		return new org.springframework.security.core.userdetails.User(name, password, sysAuthorities);
	}

	/**
	 * 获取用户分页数据
	 * 
	 * @param pageable
	 * @param username
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public Page<User> pageUsers(Pageable pageable, String username, String realName, String dateFrom, String dateTo) {

		Page<User> billingRecordPage = userRepository.findAll((root, query, cb) -> {
			List<Predicate> list = new ArrayList<Predicate>();
			if (null != username && !"".equals(username)) {
				list.add(cb.equal(root.get("username").as(String.class), username));
			}
			if (null != realName && !"".equals(realName)) {
				list.add(cb.equal(root.get("realName").as(String.class), realName));
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
		}, pageable);
		return billingRecordPage;
	}

	/**
	 * 根据id获取用户
	 * 
	 * @param id
	 * @return
	 */
	public User getById(Long id) {
		return userRepository.findOne(id);
	}

	/**
	 * 根据ids获取用户集合
	 * 
	 * @param ids
	 * @return
	 */
	public List<User> listByIds(Long[] ids) {
		return userRepository.findAll(Arrays.asList(ids));
	}

	/**
	 * 根据用户名查询用户
	 * 
	 * @param username
	 * @return
	 */
	public User getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	/**
	 * 根据ids删除用户集合
	 * 
	 * @param ids
	 */
	public void delete(Long[] ids) {
		List<User> users = userRepository.findAll(Arrays.asList(ids));
		userRepository.delete(users);
	}

	/**
	 * 更新或保存用户
	 * 
	 * @param user
	 * @return
	 */
	public User saveOrUpdate(User user) {
		User result = userRepository.save(user);
		return result;
	}

	/**
	 * 更新或保存用户集合
	 * 
	 * @param users
	 * @return
	 */
	public List<User> saveOrUpdate(List<User> users) {
		List<User> result = userRepository.save(users);
		return result;
	}

}
