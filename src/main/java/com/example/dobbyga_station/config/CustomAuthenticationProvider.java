package com.example.dobbyga_station.config;

import com.example.dobbyga_station.domain.User;
import com.example.dobbyga_station.domain.UserDetail;
import com.example.dobbyga_station.service.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final UserDetailServiceImpl userDetailsService;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
		final String userEmail = token.getName();
		final String userPw = (String)token.getCredentials();

		final UserDetail userDetails = (UserDetail) userDetailsService.loadUserByUsername(userEmail);

		if(!passwordEncoder.matches(userPw, userDetails.getUser().getPw())) {
			throw new BadCredentialsException(userDetails.getUser().getName() + "Invalid password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, userPw, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
