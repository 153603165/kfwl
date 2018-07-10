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

@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceService {
	@Autowired
	ResourceRepository resourceRepository;

	public List<Resource> listAuthorityMenus(Sort sort, Long id) {
		List<Resource> resourceRecordPage = new ArrayList<>();
		if (id == null) {
			resourceRecordPage = resourceRepository.listMenuData(sort);
		} else {
			resourceRecordPage = resourceRepository.listMenuDataById(id, sort);

		}
		return resourceRecordPage;
	}

	public List<Resource> listParentResources(Long id) {

		List<Resource> parentResources = new ArrayList<>();
		if (id != null) {
			parentResources = resourceRepository.listParentResourcesById(id);
		} else {
			parentResources = resourceRepository.listParentResources();
		}
		return parentResources;

	}

	public Resource getById(Long id) {
		return resourceRepository.findOne(id);
	}

	public List<Resource> listByIds(List<Long> ids) {
		return resourceRepository.findAll(ids);
	}

	public void delete(Long[] ids) {
		List<Resource> users = resourceRepository.findAll(Arrays.asList(ids));
		resourceRepository.delete(users);
	}

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

	public Long getMaxSeq() {
		return resourceRepository.getMaxSeq() + 1;
	}
}
