package com.example.dobbyga_station.config;

import com.example.dobbyga_station.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public CustomAuthenticationFilter(final AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
		try {
			final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
			usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPw());
		} catch (IOException exception) {
			throw new IllegalArgumentException();
		}
		setDetails(request, usernamePasswordAuthenticationToken);
		return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
	}
}
