package com.prgrms.kream.domain.member.controller;

import static org.springframework.http.HttpStatus.*;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.member.dto.MemberRegisterRequest;
import com.prgrms.kream.domain.member.dto.MemberRegisterResponse;
import com.prgrms.kream.domain.member.facade.MemberFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberFacade memberFacade;

	@PostMapping
	@ResponseStatus(CREATED)
	public ApiResponse<MemberRegisterResponse> register(
			@RequestBody @Valid MemberRegisterRequest memberRegisterRequest
	) {
		return ApiResponse.of(memberFacade.register(memberRegisterRequest));
	}
}
