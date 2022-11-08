package com.example.dobbyga_station.config;

import com.example.dobbyga_station.constants.AuthConstants;
import com.example.dobbyga_station.utils.TokenUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		final String header = request.getHeader(AuthConstants.AUTH_HEADER);

		if(header != null) {
			final String token = TokenUtils.getTokenFromHeader(header);
			if(TokenUtils.isValidToken(token)) {
				return true;
			}
		}

		response.sendRedirect("/error/unauthorized");
		return false;
	}
}
