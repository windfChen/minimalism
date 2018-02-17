package com.windf.module.development.service;

import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.module.development.entity.Controler;
import com.windf.plugins.manage.service.ManageGirdService;

public interface ControlerService extends ManageGirdService<Controler> {

	List<Controler> getAll(String moduleCode) throws UserException;

}
