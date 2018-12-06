package com.kfwl.dao.base;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfwl.entity.base.Resource;

/**
 * 资源菜单管理
 */
public interface ResourceRepository extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource> {

	List<Resource> getByParentResourceIsNull(Sort sort);

	List<Resource> getByParentResourceId(Long id, Sort sort);

	@Query(value = "select Max(seq) from Resource")
	Long getMaxSeq();

	@Query(value = "from Resource r where r.type=0 and r.parentResource is null")
	List<Resource> listParentResources();

	@Query(value = "from Resource r where r.type=0 and r.parentResource.id=:id")
	List<Resource> listParentResourcesById(@Param("id") Long id);

}
