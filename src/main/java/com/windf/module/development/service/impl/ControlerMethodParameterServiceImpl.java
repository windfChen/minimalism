package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.core.util.CollectionUtil;
import com.windf.module.development.entity.ControlerMethod;
import com.windf.module.development.entity.Parameter;
import com.windf.module.development.fdao.ControlerDao;
import com.windf.module.development.fdao.ControlerMethodDao;
import com.windf.module.development.modle.component.ControlerCoder;
import com.windf.module.development.service.ControlerMethodParameterService;

@Service
public class ControlerMethodParameterServiceImpl extends BaseManageGridServiceImpl<Parameter> implements ControlerMethodParameterService{
	
	@Resource
	private ControlerDao controlerDao;
	@Resource
	private ControlerMethodDao controlerMethodDao;

	@Override
	public Page<Parameter> list(Map<String, Object> condition, Integer pageNo, Integer pageSize) throws Exception {
		/*
		 * 获取method
		 */
		ControlerMethod controlerMethod = controlerMethodDao.read((String) condition.get("moduleCode"), (String) condition.get("codeEntityName"), (String) condition.get("methodName"));
		/*
		 * 获得Parameter
		 */
		List<Parameter> parameters = controlerMethod.getParameters();
		/*
		 * 分页
		 */
		Page<Parameter> page = new Page<Parameter>(Long.valueOf(pageNo) , pageSize);
		page.setTotal(Long.valueOf(parameters.size()));
		page.setData(parameters.subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		
		return page;
	}

	@Override
	public int save(Parameter bean) throws Exception {
		/*
		 * 获取method
		 */
		ControlerMethod controlerMethod = controlerMethodDao.read(bean.getModule().getCode(), bean.getCodeEntity().getName(), bean.getMethodName());
		/*
		 * 设置参数
		 */
		List<Parameter> parameters = controlerMethod.getParameters();
		if (CollectionUtil.isEmpty(parameters)) {
			parameters = new ArrayList<Parameter>();
			controlerMethod.setParameters(parameters);
		}
		Iterator<Parameter> iterator = parameters.iterator();
		while (iterator.hasNext()) {
			Parameter parameter = (Parameter) iterator.next();
			if (parameter.getName().equals(bean.getName())) {
				iterator.remove();
				break;
			}
		}
		parameters.add(bean);
		/*
		 * 内存模型更新
		 */
		controlerMethodDao.create(controlerMethod);
		/*
		 * 代码文件更新
		 */
		ControlerCoder controlerCoder = new ControlerCoder(controlerMethod.getControler());
		controlerCoder.addMethod(controlerMethod);
		return 1;
	}

	@Override
	public Parameter detail(Serializable id) throws Exception {
		return null;
	}

	@Override
	public int update(Parameter bean) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
