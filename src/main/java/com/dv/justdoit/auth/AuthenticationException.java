package com.dv.justdoit.auth;

/**
 * @author Dhawal Verma
 */
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = - 4885200974553165800L;

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}

