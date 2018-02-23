package com.windf.module.development.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.bean.Page;
import com.windf.core.exception.UserException;
import com.windf.core.util.StringUtil;
import com.windf.module.development.entity.Controler;
import com.windf.module.development.fdao.ControlerDao;
import com.windf.module.development.modle.component.ControlerCoder;
import com.windf.module.development.service.ControlerService;

@Service
public class ControlerServiceImpl extends BaseManageGridServiceImpl<Controler> implements ControlerService{
	private static final String BASE_PARENT_CONTROLER = "BaseControler";
	
	@Resource
	private ControlerDao controlerDao;
	
	@Override
	public List<Controler> getAll(String moduleCode) throws UserException {
		return controlerDao.listByModuleCode(moduleCode);
	}

	@Override
	public Page<Controler> list(Map<String, Object> condition, Integer pageNo, Integer pageSize) throws Exception {
		List<Controler> controlerList = controlerDao.listByModuleCode((String) condition.get("moduleCode"));
		
		Page<Controler> page = new Page<Controler>(Long.valueOf(pageNo) , pageSize);
		if (controlerList.size() > 0) {
			page.setTotal(Long.valueOf(controlerList.size()));
			page.setData(controlerList.subList(page.getStartIndex().intValue(), page.getEndIndex().intValue()));
		}
		
		return page;
	}

	@Override
	public int save(Controler controler) throws Exception {
		/*
		 * 默认参数
		 */
		if (StringUtil.isEmpty(controler.getParent())) {
			controler.setParent(BASE_PARENT_CONTROLER);
		}
		/*
		 * 创建对象模型
		 */
		controlerDao.create(controler);
		/*
		 * 创建代码
		 */
		ControlerCoder contolerCoder = new ControlerCoder(controler);
		contolerCoder.create();
		return 0;
	}

	@Override
	public Controler detail(Serializable id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Controler bean) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<? extends Serializable> id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
