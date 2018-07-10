package com.kfwl.controller;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kfwl.exception.impl.AuthorityException;
import com.kfwl.exception.impl.BusinessException;

/**
 * 基础Controller, 集成统一异常处理
 *
 */
public class BasicController {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = Throwable.class)
	public Object handleExcepiton(HttpServletRequest request, HttpServletResponse response, Throwable t) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getRequestURL());
		if (StringUtils.isNotBlank(request.getQueryString())) {
			sb.append("?").append(request.getQueryString());
		}

		String code = null;
		String info = null;
		HttpStatus httpStatus = null;
		if (t instanceof ServletRequestBindingException || t instanceof TypeMismatchException) {
			// Spring请求异常
			code = "request invalid";
			info = "系统繁忙, 请稍后再试";
			if (log.isInfoEnabled()) {
				log.info("=====Request Param Invalid【{}】【{}】=====", sb.toString(), t.getMessage());
			}
			httpStatus = HttpStatus.BAD_REQUEST;
		} else if (t instanceof HttpMediaTypeException) {
			// MediaType不符合
			code = "unsupport mediatype";
			info = "不支持的请求类型";
			if (log.isInfoEnabled()) {
				log.info("=====MediaType Invalid【{}】【{}】=====", sb.toString(), t.getMessage());
			}
			httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
		} else if (t instanceof AuthorityException) {
			// 权限异常
			code = ((AuthorityException) t).getCode();
			info = ((AuthorityException) t).getInfo();
			if (log.isInfoEnabled()) {
				log.info("=====Authority Invalid【{}】【{}】=====", sb.toString(), t.getMessage());
			}
			httpStatus = HttpStatus.UNAUTHORIZED;
		} else if (t instanceof BusinessException) {
			// 业务异常
			code = ((BusinessException) t).getCode();
			info = ((BusinessException) t).getInfo();
			if (log.isInfoEnabled()) {
				log.info("=====Business Error【{}】【{}】=====", sb.toString(), t.getMessage());
			}
			httpStatus = HttpStatus.BAD_REQUEST;
		} else {
			// 其它【编程异常】或者【网络异常】
			code = "error";
			info = "系统繁忙, 请稍后再试";
			log.error(MessageFormat.format("=====Internal Error【{0}】【{1}】=====", sb.toString(), t.getMessage()), t);
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		/* if (isJsonRequest(request)) { */
		// 返回调用失败json
		return ResponseEntityBuilder.failure(code, info, httpStatus);
		/*
		 * } else { // 返回错误页 response.setStatus(500); return new
		 * ModelAndView("/error/500"); }
		 */
	}

	/*
	 * private Boolean isJsonRequest(HttpServletRequest request) { String accept =
	 * request.getHeader("accept"); return accept != null &&
	 * (accept.contains("json")); }
	 */

	@Deprecated
	protected Map<String, Object> successMap(Map<String, Object> dataMap) {
		if (dataMap == null) {
			dataMap = new HashMap<String, Object>();
		}
		dataMap.put("success", true);
		return dataMap;
	}

	@Deprecated
	protected Map<String, Object> errorMap(String errorMsg) {
		Map<String, Object> errorMap = new HashMap<String, Object>(2);
		errorMap.put("success", false);
		errorMap.put("msg", errorMsg);
		return errorMap;
	}

	/**
	 * http异常信息传输载体
	 * 
	 *
	 */
	protected static class FailureResponse {
		private String code;
		private String message;

		private FailureResponse(String code, String message) {
			super();
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}

	/**
	 * ResponseEntity工厂
	 * 
	 *
	 * @param <T>
	 */
	public static class ResponseEntityBuilder {
		public static <T> ResponseEntity<T> success(T t, HttpStatus status) {
			return new ResponseEntity<T>(t, status);
		}

		public static <T> ResponseEntity<T> success(T t) {
			return success(t, HttpStatus.OK);
		}

		public static ResponseEntity<Object> success() {
			return new ResponseEntity<Object>(HttpStatus.OK);
		}

		public static ResponseEntity<FailureResponse> failure(String code, String message, HttpStatus status) {
			return new ResponseEntity<FailureResponse>(new FailureResponse(code, message), status);
		}

		public static ResponseEntity<FailureResponse> failure(String code, String message) {
			return failure(code, message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * http数据格式传输载体
	 * 
	 *
	 * @param <T>
	 */
	// @JsonInclude(value = Include.NON_NULL)
	// public static class ResponseObject<T> {
	// private Msg msg;
	//
	// private T data;
	//
	// private ResponseObject(Msg msg) {
	// this.msg = msg;
	// }
	//
	// private ResponseObject(T t) {
	// this.msg = Msg.DEFAULT_SUCCESS_MSG;
	// this.data = t;
	// }
	//
	// private ResponseObject(T t, Msg msg) {
	// this.msg = msg;
	// this.data = t;
	// }
	//
	// public Msg getMsg() {
	// return msg;
	// }
	//
	// public T getData() {
	// return data;
	// }
	// }
	//
	// /**
	// * http返回数据消息头
	// *
	// *
	// */
	// protected static class Msg {
	// private final static Msg DEFAULT_SUCCESS_MSG = new Msg(true, "0", "done");
	//
	// private Boolean success;
	// private String code;
	// private String info;
	//
	// public Msg(Boolean success, String code, String info) {
	// this.success = success;
	// this.code = code;
	// this.info = info;
	// }
	//
	// public Boolean getSuccess() {
	// return success;
	// }
	//
	// public String getCode() {
	// return code;
	// }
	//
	// public String getInfo() {
	// return info;
	// }
	// }
	//
	// /**
	// * http数据格式传输载体工厂类
	// *
	// *
	// */
	// public static class ResponseObjectBuilder {
	// public static <T> ResponseObject<T> buildSuccess() {
	// return new ResponseObject<T>(Msg.DEFAULT_SUCCESS_MSG);
	// }
	//
	// public static <T> ResponseObject<T> buildSuccess(String code, String info) {
	// return new ResponseObject<T>(new Msg(true, code, info));
	// }
	//
	// public static <T> ResponseObject<T> buildSuccess(T t) {
	// return new ResponseObject<T>(t, Msg.DEFAULT_SUCCESS_MSG);
	// }
	//
	// public static <T> ResponseObject<T> buildSuccess(T t, String code, String
	// info) {
	// return new ResponseObject<T>(t, new Msg(true, code, info));
	// }
	//
	// public static <T> ResponseObject<T> buildFailure(String code, String info) {
	// Msg msg = new Msg(false, code, info);
	// return new ResponseObject<T>(msg);
	// }
	// }
}