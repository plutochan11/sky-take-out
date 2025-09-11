package com.pluto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(auth -> auth
			  .requestMatchers(
				    "/doc.html",
				    "/webjars/**",
				    "/swagger-resource/**",
				    "/v3/api-docs/**",
				    "/favicon.ico",
				    "/management/admin/login"
			  ).permitAll()
			  .anyRequest().authenticated()
		);
		return httpSecurity.build();
	}
}
