package com.windf.plugins.web.response;

public interface ResponseReturn {
	public static final String RESULT_KEY = "result";
	public static final String RESULT_SUCCESS_KEY = "success";
	public static final String RESULT_DATA_KEY = "data";
	public static final String RESULT_MESSAGE_KEY = "message";

	public static final String JSON_PAGE = "/common/json";
	public static final String PARAMETER_ERROR_PAGE = "/common/error/parameter";
	public static final String ERROR_PAGE = "/common/error";
	public static final String SUCCESS_MESSAGE_PAGE = "/common/info/success";

	/**
	 * 设置页面返回，并直接返回
	 * @param page
	 * @return
	 */
	public String page(String page);
	
	/**
	 * 返回错误信息
	 * @return
	 */
	public String parameterError();

	/**
	 * 返回成功提示
	 * @return
	 */
	public String success();

	/**
	 * 返回成功提示
	 * @return
	 */
	public String success(String tip);
	
	/**
	 * 返回错误信息
	 * @param Message
	 * @return
	 */
	public String error(String tip);
	
	/**
	 * 返回带数据的成功信息
	 * @param data
	 * @return
	 */
	public String successData(Object data);

	/**
	 * 返回带数据的错误信息
	 * @param data
	 * @return
	 */
	public String errorData(Object data);

	/**
	 * 只返回数据
	 * @param result
	 * @return
	 */
	public String returnData(Object data);
	
	/**
	 * 返回map信息,没有数据
	 * @param success
	 * @param tip
	 * @return
	 */
	public String returnData(boolean success, String tip);
	
	/**
	 * 返回带数据和提示的信息
	 * @param success
	 * @param tip
	 * @param data
	 * @return
	 */
	public String returnData(boolean success, String tip, Object data);

	/**
	 * 重定向到某个页面
	 * @param string
	 * @return
	 */
	public String redirect(String string);
}
