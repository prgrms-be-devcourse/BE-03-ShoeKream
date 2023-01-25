package com.prgrms.kream.domain.member.controller;

import static org.springframework.http.HttpStatus.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.member.dto.request.MemberLoginRequest;
import com.prgrms.kream.domain.member.dto.request.MemberRegisterRequest;
import com.prgrms.kream.domain.member.dto.response.MemberRegisterResponse;
import com.prgrms.kream.domain.member.facade.MemberFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberFacade memberFacade;

	@Value("${jwt.accessToken}")
	private String accessToken;

	@PostMapping
	@ResponseStatus(CREATED)
	public ApiResponse<MemberRegisterResponse> register(
			@RequestBody @Valid MemberRegisterRequest memberRegisterRequest
	) {
		return ApiResponse.of(memberFacade.register(memberRegisterRequest));
	}

	@PostMapping("/login")
	@ResponseStatus(OK)
	public ApiResponse<String> login(
			@RequestBody @Valid MemberLoginRequest memberLoginRequest,
			HttpServletResponse httpServletResponse
	) {
		httpServletResponse.addCookie(
				new Cookie(accessToken, memberFacade.login(memberLoginRequest).token())
		);
		return ApiResponse.of("로그인 성공하였습니다.");
	}

	@GetMapping("/logout")
	@ResponseStatus(OK)
	public ApiResponse<String> logout(HttpServletResponse httpServletResponse) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
			Cookie cookie = new Cookie(accessToken, "");
			cookie.setMaxAge(0);
			httpServletResponse.addCookie(cookie);
		}

		return ApiResponse.of("로그아웃 성공하였습니다.");
	}

	@GetMapping("/test")
	@ResponseStatus(OK)
	public ApiResponse<String> test() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication);
		return ApiResponse.of("테스트 성공하였습니다.");
	}

}
