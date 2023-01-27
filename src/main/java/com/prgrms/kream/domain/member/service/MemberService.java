package com.prgrms.kream.domain.member.service;

import static com.prgrms.kream.common.mapper.MemberMapper.*;

import java.util.Objects;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.common.exception.DuplicatedEmailException;
import com.prgrms.kream.common.jwt.Jwt;
import com.prgrms.kream.common.jwt.JwtUtil;
import com.prgrms.kream.common.mapper.MemberMapper;
import com.prgrms.kream.domain.member.dto.request.MemberLoginRequest;
import com.prgrms.kream.domain.member.dto.request.MemberRegisterRequest;
import com.prgrms.kream.domain.member.dto.response.MemberGetFacadeResponse;
import com.prgrms.kream.domain.member.dto.response.MemberLoginResponse;
import com.prgrms.kream.domain.member.dto.response.MemberRegisterResponse;
import com.prgrms.kream.domain.member.model.Member;
import com.prgrms.kream.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	private final Jwt jwt;

	@Transactional
	public MemberRegisterResponse register(MemberRegisterRequest memberRegisterRequest) {
		if (isDuplicatedEmail(memberRegisterRequest.email())) {
			throw new DuplicatedEmailException("중복된 이메일입니다.");
		}
		Member member = memberRepository.save(
				MemberMapper.toMember(memberRegisterRequest)
		);
		return new MemberRegisterResponse(member.getId());
	}

	@Transactional
	public MemberLoginResponse login(MemberLoginRequest memberLoginRequest) {
		Member member = memberRepository.findByEmail(memberLoginRequest.email())
				.orElseThrow(EntityNotFoundException::new);

		if (member.isNotValidPassword(memberLoginRequest.password())) {
			throw new BadCredentialsException("잘못된 비밀번호 입니다.");
		}
		String[] roles = new String[] {member.getAuthority().name()};

		return new MemberLoginResponse(jwt.sign(Jwt.Claims.from(member.getId(), roles)));
	}

	private boolean isDuplicatedEmail(String email) {
		return memberRepository.existsMemberByEmail(email);
	}

	@Transactional(readOnly = true)
	public MemberGetFacadeResponse get(Long id) {
		if (!Objects.equals(JwtUtil.getMemberId(), id)) {
			throw new AccessDeniedException("잘못된 접근입니다");
		}

		Member member = memberRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않은 회원입니다."));
		return toMemberGetFacadeResponse(member);
	}
}
