package com.example.dobbyga_station.config;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

		// ip
		String ipAddress = request.getHeader("X-Forwarded-For");
		if (ipAddress == null) ipAddress = request.getRemoteAddr();

		log.info(">>>>>>>>>>>>>>>>>> interceptor start");
		log.info("request url : {}", request.getRequestURI());
		log.info("response status : {}", response.getStatus());
		log.info("ipAddress : {}", ipAddress);
		log.info("interceptor end <<<<<<<<<<<<<<<<<");
	}
}
