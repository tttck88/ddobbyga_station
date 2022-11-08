package com.example.dobbyga_station.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final LoggingInterceptor loggingInterceptor;
	private final JwtTokenInterceptor jwtTokenInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(jwtTokenInterceptor)
//			.addPathPatterns("/api/user/update")
//			.addPathPatterns("/api/user/detail")
//			.addPathPatterns("/api/user/list");

		registry.addInterceptor(loggingInterceptor)
			.addPathPatterns("/**");

	}
}
