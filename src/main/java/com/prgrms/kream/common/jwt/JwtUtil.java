package com.prgrms.kream.common.jwt;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {
	public static Long getMemberId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
			throw new AccessDeniedException("잘못된 접근입니다.");
		}
		return (Long)authentication.getPrincipal();
	}
}
