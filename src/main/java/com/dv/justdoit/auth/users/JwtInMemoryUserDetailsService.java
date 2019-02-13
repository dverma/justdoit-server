package com.dv.justdoit.auth.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Dhawal Verma
 */
@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {

	static List<JwtUserDetails> inMemoryUserList = new ArrayList<>();

	static {
		inMemoryUserList.add(
							 new JwtUserDetails(1L, "dverma",
												"$2a$10$3zHzb.Npv1hfZbLEU5qsdOju/tk2je6W6PnNnY.c1ujWPcZh4PL6e",
												"ROLE_USER"));
		inMemoryUserList.add(
							 new JwtUserDetails(2L, "dhawal",
												"$2a$10$3zHzb.Npv1hfZbLEU5qsdOju/tk2je6W6PnNnY.c1ujWPcZh4PL6e",
												"ROLE_USER"));
		inMemoryUserList.add(
							 new JwtUserDetails(3L, "user",
												"$2a$10$3zHzb.Npv1hfZbLEU5qsdOju/tk2je6W6PnNnY.c1ujWPcZh4PL6e",
												"ROLE_USER"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<JwtUserDetails> findFirst = inMemoryUserList.stream()
															 .filter(
																	 user -> user.getUsername()
																				 .equals(username))
															 .findFirst();

		if( ! findFirst.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER NOT FOUND '%s'.", username));
		}

		return findFirst.get();
	}

}


