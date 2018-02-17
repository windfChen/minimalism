package com.windf.module.development.service;

import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.module.development.entity.Service;
import com.windf.plugins.manage.service.ManageGirdService;

public interface ServiceService extends ManageGirdService<Service> {

	/**
	 * 获得module的所有service
	 * @param moduleCode
	 * @return
	 * @throws UserException
	 */
	List<Service> getAllService(String moduleCode) throws UserException;
}
