package com.prgrms.kream.domain.member.facade;

import static com.prgrms.kream.common.mapper.MemberMapper.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.image.model.DomainType;
import com.prgrms.kream.domain.image.service.ImageService;
import com.prgrms.kream.domain.member.dto.request.MemberLoginRequest;
import com.prgrms.kream.domain.member.dto.request.MemberRegisterRequest;
import com.prgrms.kream.domain.member.dto.response.MemberGetFacadeResponse;
import com.prgrms.kream.domain.member.dto.response.MemberGetResponse;
import com.prgrms.kream.domain.member.dto.response.MemberLoginResponse;
import com.prgrms.kream.domain.member.dto.response.MemberRegisterResponse;
import com.prgrms.kream.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFacade {
	private final MemberService memberService;
	private final ImageService imageService;

	public MemberRegisterResponse register(MemberRegisterRequest memberRegisterRequest) {
		return memberService.register(memberRegisterRequest);
	}

	public MemberLoginResponse login(MemberLoginRequest memberLoginRequest) {
		return memberService.login(memberLoginRequest);
	}

	@Transactional(readOnly = true)
	public MemberGetResponse get(Long memberId) {
		MemberGetFacadeResponse memberGetFacadeResponse = memberService.get(memberId);
		List<String> imagePaths = imageService.getAll(memberId, DomainType.MEMBER);
		return toMemberGetResponse(memberGetFacadeResponse, imagePaths);
	}
}
