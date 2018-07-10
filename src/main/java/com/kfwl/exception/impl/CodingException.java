package com.kfwl.exception.impl;

import com.kfwl.exception.IUnSolveException;

/**
 * 代码编写异常(例如 空指针校验 / 数组越界校验)
 *
 */
public class CodingException extends BasicException implements IUnSolveException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CodingException(String info) {
		super(info);
	}

	public CodingException(String code, String info) {
		super(code, info);
	}

	public CodingException(String code, String info, String message) {
		super(code, info, message);
	}

	public CodingException(String code, String info, String message, Throwable t) {
		super(code, info, message, t);
	}
}
