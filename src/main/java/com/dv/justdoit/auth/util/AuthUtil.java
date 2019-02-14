package com.dv.justdoit.auth.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;
import com.dv.justdoit.auth.users.JwtUserDetails;

public class AuthUtil {

	private AuthUtil() {
		throw new IllegalStateException("Utility class");
	}



	public static boolean validateUserAuthorization(JwtUserDetails user) {
		JwtUserDetails authUser = (JwtUserDetails) SecurityContextHolder.getContext()
																		.getAuthentication()
																		.getPrincipal();
		if( ! authUser.equals(user)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot access another user's tasks.");
		}
		return true;
	}


}
