package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.module.development.entity.ControlerMethod;
import com.windf.module.development.fdao.ControlerDao;
import com.windf.module.development.fdao.ControlerMethodDao;
import com.windf.module.development.modle.component.ControlerCoder;
import com.windf.module.development.service.ControlerMethodService;

@Service
public class ControlerMethodServiceImpl extends BaseManageGridServiceImpl<ControlerMethod> implements ControlerMethodService{
	
	@Resource
	private ControlerDao controlerDao;
	@Resource
	private ControlerMethodDao controlerMethodDao;

	@Override
	public Page<ControlerMethod> list(Map<String, Object> condition, Integer pageNo, Integer pageSize) throws Exception {
		List<ControlerMethod> conrolerMethodList = controlerMethodDao.listByControlerName((String) condition.get("moduleCode"), (String) condition.get("controlerName"));
		
		Page<ControlerMethod> page = new Page<ControlerMethod>(Long.valueOf(pageNo) , pageSize);
		page.setTotal(Long.valueOf(conrolerMethodList.size()));
		page.setData(conrolerMethodList.subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		
		return page;
	}

	@Override
	public int save(ControlerMethod controlerMethod) throws Exception {
		/*
		 * 内存模型更新
		 */
		controlerMethodDao.create(controlerMethod);
		/*
		 * 代码文件更新
		 */
		ControlerCoder controlerCoder = new ControlerCoder(controlerMethod.getControler());
		controlerCoder.addMethod(controlerMethod);
		return 0;
	}

	@Override
	public ControlerMethod detail(Serializable id) throws Exception {
		return null;
	}

	@Override
	public int update(ControlerMethod bean) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
