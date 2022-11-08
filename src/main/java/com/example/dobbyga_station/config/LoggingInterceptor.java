package com.example.dobbyga_station.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
public class LoggingInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		long currentTime = System.currentTimeMillis(); ;
		request.setAttribute("bTime", currentTime);

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
		// 처리시간
		long currentTime = System.currentTimeMillis();
		long beginTime = (long )request.getAttribute("bTime");
		long processedTime = currentTime - beginTime;

		// ip
		String ipAddress = request.getHeader("X-Forwarded-For");
		if (ipAddress == null) ipAddress = request.getRemoteAddr();

		log.info("request url : {}", request.getRequestURI());
		log.info("response status : {}", response.getStatus());
		log.info("time : {}", processedTime);
		log.info("ipAddress : {}", ipAddress);

		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
