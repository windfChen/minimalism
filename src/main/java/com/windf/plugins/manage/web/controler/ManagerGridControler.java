package com.windf.plugins.manage.web.controler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.bean.Page;
import com.windf.core.exception.UserException;
import com.windf.core.util.CollectionUtil;
import com.windf.core.util.StringUtil;
import com.windf.plugins.manage.Constant;
import com.windf.plugins.manage.entity.GridConfig;
import com.windf.plugins.manage.service.ManageGirdService;

public abstract class ManagerGridControler<T> extends BaseManagerControler<T> {
	protected final static String MANAGE_PATH = Constant.WEB_BASE_PATH;
	
	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String index() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("queryString", request.getQueryString());
		data.put("controlerPath", path.getControlerPath());

		responseReturn.page(this.getAdapter().getGridPage());
		return responseReturn.successData(data);
	}

	@RequestMapping(value = "/grid", method = {RequestMethod.GET})
	public Object grid() {
		String code = getRequestCode();
		String roleId = "";
		Map<String, Object> condition = paramenter.getAll();
		condition = this.filterMapValue(condition);
		
		GridConfig gridConfig = null;
		try {
			/*
			 * 读取gridConfig信息
			 */
			gridConfig = this.getManagerGridService().getGridConfig(code, roleId, condition);
			/*
			 * 适配gridConfig信息
			 */
			gridConfig = this.getAdapter().resetGridConfig(gridConfig);
		} catch (UserException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseReturn.successData(gridConfig);
	}
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET})
	public String list() {
		Map<String, Object> condition = paramenter.getMap("condition");
		condition = this.filterMapValue(condition);
		Integer pageNo = paramenter.getInteger("page", 1);
		Integer pageSize = paramenter.getInteger("limit", 10);
		
		Map<String, Object> result = null;
		try {
			Page<T> page = this.getManagerGridService().list(condition, pageNo, pageSize);
			result = this.getAdapter().pageDataAdapter(page);
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
		
		return responseReturn.returnData(result);
	}
	
	@RequestMapping(value = "/detail", method = {RequestMethod.GET})
	public String detail() {
		String id = paramenter.getString("id");
		
		Object data = null;
		try {
			T entity = this.getManagerGridService().detail(id);
			data = this.getAdapter().entityDataAdapter(entity);
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
		
		return responseReturn.successData(data);
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/save", method = {RequestMethod.POST})
	public String save() {
		T entity = (T) this.getAdapter().getEntity(this);
		
		try {
			this.getManagerGridService().save(entity);
			return responseReturn.success();
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update", method = {RequestMethod.POST})
	public String update() {
		T entity = (T) this.getAdapter().getEntity(this);
		
		try {
			this.getManagerGridService().update(entity);
			return responseReturn.success();
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST})
	public String delete() {
		String ids = paramenter.getString("ids");
		
		List<String> idList = null;
		if (StringUtil.isNotEmpty(ids)) {
			idList = Arrays.asList(ids.split(","));
		}
		
		if (CollectionUtil.isEmpty(idList)) {
			return responseReturn.parameterError();
		}
		
		try {
			this.getManagerGridService().delete(idList);
			return responseReturn.success();
		} catch (Exception e) {
			e.printStackTrace();
			return responseReturn.error(e.getMessage());
		}
	}
	
	/**
	 * 获取实体类，如果不为空，则用实体初始化表格
	 * @return
	 */
	public abstract Class<T> getEntity();
	
	/**
	 * 获取管理表格服务
	 * @return
	 */
	protected abstract ManageGirdService<T> getManagerGridService();

	/**
	 * 获得请求地址，生成的code
	 * eg：http://localhost/m/test/grid.do?r=1 --> testGrid
	 * @return
	 */
	protected String getRequestCode() {
		String requestPath = path.getControlerPath();
		
		int index = requestPath.lastIndexOf('.');
		if (index > 0) {
			requestPath = requestPath.substring(0, index);
		}
		if (requestPath.startsWith(MANAGE_PATH)) {
			requestPath = requestPath.substring(MANAGE_PATH.length());
		}
		
		String result = StringUtil.toCamelCase(requestPath, "/");
		return result;
	}
	
	/**
	 * 统一添加参数
	 * @param map
	 */
	protected Map<String, Object> filterMapValue(Map<String, Object> map) {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		
		Map<String, String> queryMap = paramenter.getQueryStringValues();
		Iterator<String> iterator = queryMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = queryMap.get(key);
			
			map.put(key, value);
		}
		
		return map;
	}
}
