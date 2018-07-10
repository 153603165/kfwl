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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kfwl.dao.base.AuthorityRepository;
import com.kfwl.dao.base.UserRepository;
import com.kfwl.entity.base.Authority;
import com.kfwl.entity.base.Role;
import com.kfwl.entity.base.User;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService implements UserDetailsService {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthorityRepository authorityRepository;

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

	public Page<User> pageUsers(Pageable pageable, String username, String dateFrom, String dateTo) {

		Page<User> billingRecordPage = userRepository.findAll(getPageData(username, dateFrom, dateTo), pageable);
		return billingRecordPage;
	}

	private Specification<User> getPageData(String username, String dateFrom, String dateTo) {
		return (root, query, cb) -> {
			List<Predicate> list = new ArrayList<Predicate>();
			if (null != username && !"".equals(username)) {
				list.add(cb.equal(root.get("username").as(String.class), username));
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

	public User getById(Long id) {
		return userRepository.findOne(id);
	}

	public List<User> listByIds(Long[] ids) {
		return userRepository.findAll(Arrays.asList(ids));
	}

	public User getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void delete(Long[] ids) {
		List<User> users = userRepository.findAll(Arrays.asList(ids));
		userRepository.delete(users);
	}

	public User saveOrUpdate(User user) {
		User result = userRepository.save(user);
		return result;
	}

	public List<User> saveOrUpdate(List<User> users) {
		List<User> result = userRepository.save(users);
		return result;
	}

}
