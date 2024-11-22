package com.webproject.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] AUTH_WHITELIST = {
					"/favicon.ico",
					"/api/public",
					"/"
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)	throws Exception {
		http
				.authorizeHttpRequests(authorize -> authorize
					.requestMatchers(AUTH_WHITELIST).permitAll()
					.anyRequest().authenticated()
				)
				.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				);

		return http.build();
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withJwkSetUri("http://localhost:8080/realms/WebAppRealm/protocol/openid-connect/certs").build();
	}
}
