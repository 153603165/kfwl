package com.kfwl.service.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kfwl.dao.base.ResourceRepository;
import com.kfwl.entity.base.Resource;

/**
 * 资源服务
 * 
 * @author Administrator
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceService {
	@Autowired
	ResourceRepository resourceRepository;

	/**
	 * 获取页面资源数据
	 * 
	 * @param sort
	 * @param id
	 * @return
	 */
	public List<Resource> listResourceMenus(Sort sort, Long id) {
		List<Resource> resourceRecordPage = new ArrayList<>();
		if (id == null) {
			resourceRecordPage = resourceRepository.getByParentResourceIsNull(sort);
		} else {
			resourceRecordPage = resourceRepository.getByParentResourceId(id, sort);

		}
		return resourceRecordPage;
	}

	/**
	 * 获取父资源id的所有子资源数据
	 * 
	 * @param id
	 * @return
	 */
	public List<Resource> listParentResources(Long id) {

		List<Resource> parentResources = new ArrayList<>();
		if (id != null) {
			parentResources = resourceRepository.listParentResourcesById(id);
		} else {
			parentResources = resourceRepository.listParentResources();
		}
		return parentResources;

	}

	/**
	 * 根据id获取资源数据
	 * 
	 * @param id
	 * @return
	 */
	public Resource getById(Long id) {
		return resourceRepository.findOne(id);
	}

	/**
	 * 根据ids获取资源数据集合
	 * 
	 * @param ids
	 * @return
	 */
	public List<Resource> listByIds(List<Long> ids) {
		return resourceRepository.findAll(ids);
	}

	/**
	 * 根据ids删除资源数据
	 * 
	 * @param ids
	 */
	public void delete(Long[] ids) {
		List<Resource> users = resourceRepository.findAll(Arrays.asList(ids));
		resourceRepository.delete(users);
	}

	/**
	 * 更新胡保存资源
	 * 
	 * @param resource
	 * @return
	 */
	public Resource saveOrUpdate(Resource resource) {
		Resource tempResource = new Resource();
		if (null != resource.getId()) {
			tempResource = getById(resource.getId());
			BeanUtils.copyProperties(resource, tempResource, "state", "type", "version");
		} else {
			BeanUtils.copyProperties(resource, tempResource, "state", "version");
		}
		if (tempResource.getParentId() != null) {
			Resource parentResource = getById(tempResource.getParentId());
			tempResource.setParentResource(parentResource);
		}
		Resource result = resourceRepository.save(tempResource);
		return result;

	}

	/**
	 * 获取资源排序最大值+1
	 * 
	 * @return
	 */
	public Long getMaxSeq() {
		return resourceRepository.getMaxSeq() + 1;
	}
}
