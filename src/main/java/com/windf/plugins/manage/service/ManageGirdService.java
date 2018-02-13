package com.windf.plugins.manage.service;

import java.util.Map;

import com.windf.core.exception.UserException;
import com.windf.core.general.service.CrudService;
import com.windf.plugins.manage.entity.GridConfig;

/**
 * 管理端服务
 * 
 * @author chenyafeng
 *
 */
public interface ManageGirdService extends CrudService {
	/**
	 * 获得表格配置
	 * @param code
	 * @param roleId
	 * @param condition
	 * @return
	 * @throws UserException
	 */
	GridConfig getGridConfig(String code, String roleId, Map<String, Object> condition) throws Exception;
}
