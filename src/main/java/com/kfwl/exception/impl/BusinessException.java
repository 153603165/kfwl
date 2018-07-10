package com.kfwl.exception.impl;

import com.kfwl.exception.ICustomException;

/**
 * 业务异常
 *
 */
public class BusinessException extends BasicException implements ICustomException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String info) {
		super(info);
	}

	public BusinessException(String code, String info) {
		super(code, info);
	}

	public BusinessException(String code, String info, String message) {
		super(code, info, message);
	}

	public BusinessException(String code, String info, String message, Throwable t) {
		super(code, info, message, t);
	}
}
