package com.windf.plugins.web.response;

import java.util.HashMap;
import java.util.Map;

import com.windf.core.util.JSONUtil;
import com.windf.core.util.reflect.ReflectUtil;
import com.windf.plugins.web.BaseControler;

public class JsonReturn extends AbstractResponseRetrun {
	
	private BaseControler baseControler;
	private boolean onlyData = false;	// 是否只返回数据，不嵌套格式
	
	public JsonReturn(BaseControler baseControler) {
		this.baseControler = baseControler;
	}

	@Override
	public String parameterError() {
		return error("parameter error");
	}

	@Override
	public String success() {
		return returnData(true, "success");
	}

	@Override
	public String error(String tip) {
		return returnData(false, tip);
	}

	@Override
	public String success(String tip) {
		return returnData(true, tip);
	}

	@Override
	public String successData(Object data) {
		return returnData(true, "success", data);
	}

	@Override
	public String errorData(Object data) {
		return returnData(true, "error", data);
	}

	@Override
	public String returnData(Object data) {
		this.onlyData = true;
		return returnData(true, null, data);
	}

	@Override
	public String returnData(boolean success, String tip) {
		return returnData(success, tip, null);
	}

	@Override
	public String returnData(boolean success, String tip, Object data) {
		Object result = null;
		if (onlyData) {
			result = data;
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			if (success) {
				map.put("success", "Y");
			} else {
				map.put("success", "N");
			}
			if (data != null) {
				map.put("data", data);
			}
			map.put("tip", tip);
			result = map;
		}
		
		String jsonResult = null;
		if (ReflectUtil.isBaseType(result)) {
			jsonResult = result + "";
		} else {
			jsonResult = JSONUtil.toJSONStr(result);
		}
		
		baseControler.paramenter.setValue(RESULT_KEY, jsonResult);
		return JSON_PAGE;
	}

	@Override
	public String redirect(String url) {
		return successData(url);
	}
	
}
