package com.example.dobbyga_station.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(jwtTokenInterceptor())
//			.addPathPatterns("/api/user/update")
//			.addPathPatterns("/api/user/detail")
//			.addPathPatterns("/api/user/list");

		registry.addInterceptor(loggingInterceptor())
			.addPathPatterns("/**");

	}
	@Bean
	public JwtTokenInterceptor jwtTokenInterceptor() {
		return new JwtTokenInterceptor();
	}

	@Bean
	public LoggingInterceptor loggingInterceptor() {
		return new LoggingInterceptor();
	}
}
