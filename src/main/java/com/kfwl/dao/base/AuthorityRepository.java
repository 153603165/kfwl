package com.kfwl.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kfwl.entity.base.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>, JpaSpecificationExecutor<Authority> {

	@Query(nativeQuery = true, value = "select * from sys_authority a where exists (select 1 from sys_role_authority ra where role_id in :roleId AND ra.auth_id = a.id) order by a.seq ASC")
	List<Authority> listByRoleId(@Param("roleId") List<Long> roleId);

	@Query(value = "select Max(seq) from Authority")
	Long getMaxSeq();

}
