package com.prgrms.kream.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prgrms.kream.common.exception.CustomAccessDeniedHandler;
import com.prgrms.kream.common.exception.CustomAuthenticationEntryPoint;
import com.prgrms.kream.common.jwt.Jwt;
import com.prgrms.kream.common.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtConfig jwtConfig;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Jwt jwt() {
		return new Jwt(jwtConfig.getIssuer(), jwtConfig.getClientSecret(), jwtConfig.getExpirySeconds());
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwt(), jwtConfig.getAccessToken());
	}

	@Bean
	public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
		return new CustomAuthenticationEntryPoint();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/api/v1/admin/**").hasRole("ADMIN")
				.antMatchers(
						"/api/v1/members/signup",
						"/api/v1/members/login",
						"/api/v1/products",
						"/api/v1/feeds"
				).permitAll()
				.anyRequest().hasRole("USER")
				.and()
				.csrf().disable()
				.headers().disable()
				.formLogin().disable()
				.httpBasic().disable()
				.rememberMe().disable()
				.logout().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilterAfter(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling()
				.authenticationEntryPoint(customAuthenticationEntryPoint())
				.accessDeniedHandler(customAccessDeniedHandler())
		;
		return http.build();
	}
}