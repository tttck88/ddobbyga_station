package com.example.dobbyga_station.config;

import com.example.dobbyga_station.constants.AuthConstants;
import com.example.dobbyga_station.user.domain.User;
import com.example.dobbyga_station.user.domain.UserDetail;
import com.example.dobbyga_station.utils.TokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
	                                    final Authentication authentication) {
		final User user = ((UserDetail) authentication.getPrincipal()).getUser();
		final String token = TokenUtils.generateJwtToken(user);
		response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);
	}
}
