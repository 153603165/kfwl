package com.kfwl.exception.impl;

import com.kfwl.exception.IBasicException;

/**
 * 基础异常类
 *
 */
public class BasicException extends Exception implements IBasicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String code;
	protected String info;

	protected BasicException(String code, String info, String message, Throwable t) {
		super(message, t);
		this.code = code;
		this.info = info;
	}

	protected BasicException(String code, String info, String message) {
		super(message);
		this.code = code;
		this.info = info;
	}

	protected BasicException(String code, String info) {
		super(info);
		this.code = code;
		this.info = info;
	}

	protected BasicException(String info) {
		super(info);
		this.info = info;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getInfo() {
		return info;
	}
}
