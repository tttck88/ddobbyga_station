package com.example.dobbyga_station.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter("/**")
@Component
public class LoggingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
		ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

		chain.doFilter(httpServletRequest, httpServletResponse);

		String url = httpServletRequest.getRequestURI();
		String reqContent = new String(httpServletRequest.getContentAsByteArray());

		String resContent = new String(httpServletResponse.getContentAsByteArray());
		int httpStatus = httpServletResponse.getStatus();

		httpServletResponse.copyBodyToResponse();

		String ipAddress = httpServletRequest.getHeader("X-Forwarded-For");
		if (ipAddress == null) ipAddress = request.getRemoteAddr();

		log.info(">>>>>>>>>>>>>>>>>> filter start");
		log.info("request url : {}, request body : {}", url, reqContent);
		log.info("response status : {}, responseBody : {}", httpStatus, resContent);
		log.info("ipAddress : {}", ipAddress);
		log.info("filter end <<<<<<<<<<<<<<<<<");
	}
}
