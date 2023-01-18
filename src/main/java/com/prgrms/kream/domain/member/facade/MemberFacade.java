package com.prgrms.kream.domain.member.facade;

import org.springframework.stereotype.Service;

import com.prgrms.kream.domain.member.dto.MemberRegisterRequest;
import com.prgrms.kream.domain.member.dto.MemberRegisterResponse;
import com.prgrms.kream.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFacade {
	private final MemberService memberService;

	public MemberRegisterResponse register(MemberRegisterRequest memberRegisterRequest) {
		return memberService.register(memberRegisterRequest);
	}

}
