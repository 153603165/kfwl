package com.kfwl.exception.impl;

import com.kfwl.exception.ICustomException;

public class AuthorityException extends BasicException implements ICustomException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthorityException(String info) {
		super(info);
	}

	public AuthorityException(String code, String info) {
		super(code, info);
	}
	
	public AuthorityException(String code, String info, String message) {
		super(code, info ,message);
	}
	
	public AuthorityException(String code, String info, String message, Throwable t) {
		super(code, info, message, t);
	}
}
