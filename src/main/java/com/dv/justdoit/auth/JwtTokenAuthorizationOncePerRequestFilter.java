package com.dv.justdoit.auth;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.dv.justdoit.auth.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * @author Dhawal Verma
 */
@Component
public class JwtTokenAuthorizationOncePerRequestFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value ("${jwt.http.request.header}")
	private String tokenHeader;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
					throws ServletException, IOException {
		logger.debug("Authentication Request For '{}'", request.getRequestURL());

		final String requestTokenHeader = request.getHeader(this.tokenHeader);

		String username = null;
		String jwtToken = null;
		if((requestTokenHeader != null) && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			}
			catch (IllegalArgumentException e) {
				logger.error("JWT TOKEN UNABLE TO GET USERNAME", e);
			}
			catch (ExpiredJwtException e) {
				logger.warn("JWT TOKEN EXPIRED", e);
			}
		}
		else {
			logger.warn("JWT TOKEN DOES NOT START WITH BEARER STRING");
		}

		logger.debug("JWT TOKEN USERNAME VALUE '{}'", username);
		if((username != null) && (SecurityContextHolder.getContext()
													   .getAuthentication() == null)) {

			UserDetails userDetails = this.jwtInMemoryUserDetailsService.loadUserByUsername(username);

			if(jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
								new UsernamePasswordAuthenticationToken(userDetails, null,
																		userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(
															   new WebAuthenticationDetailsSource().buildDetails(
																												 request));
				SecurityContextHolder.getContext()
									 .setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		chain.doFilter(request, response);
	}
}


