package com.prgrms.kream.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.common.exception.DuplicatedEmailException;
import com.prgrms.kream.common.mapper.MemberMapper;
import com.prgrms.kream.domain.member.dto.MemberRegisterRequest;
import com.prgrms.kream.domain.member.dto.MemberRegisterResponse;
import com.prgrms.kream.domain.member.model.Member;
import com.prgrms.kream.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public MemberRegisterResponse register(MemberRegisterRequest memberRegisterRequest) {
		if (isDuplicatedEmail(memberRegisterRequest.email())) {
			throw new DuplicatedEmailException("중복된 이메일입니다.");
		}
		Member member = memberRepository.save(
				MemberMapper.memberRegisterRequestToMember(memberRegisterRequest)
		);
		return new MemberRegisterResponse(member.getId());
	}

	private boolean isDuplicatedEmail(String email) {
		return memberRepository.existsMemberByEmail(email);
	}

}
