package com.windf.module.development.service;

import com.windf.module.development.entity.Field;

public interface ManageDevelopmentService {
	void saveField(Field field) throws Exception;
	Field getFieldById(String fieldId) throws Exception;
}
