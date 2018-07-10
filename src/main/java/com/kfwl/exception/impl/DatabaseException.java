package com.kfwl.exception.impl;

import com.kfwl.exception.IUnSolveException;

/**
 * 数据库异常
 *
 */
public class DatabaseException extends BasicException implements IUnSolveException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseException(String info) {
		super(info);
	}

	public DatabaseException(String code, String info) {
		super(code, info);
	}

	public DatabaseException(String code, String info, String message) {
		super(code, info, message);
	}

	public DatabaseException(String code, String info, String message, Throwable t) {
		super(code, info, message, t);
	}
}
