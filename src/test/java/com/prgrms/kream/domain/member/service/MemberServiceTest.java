package com.prgrms.kream.domain.member.service;

import static com.prgrms.kream.domain.member.model.Authority.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prgrms.kream.domain.member.dto.request.MemberRegisterRequest;
import com.prgrms.kream.domain.member.dto.response.MemberRegisterResponse;
import com.prgrms.kream.domain.member.model.Member;
import com.prgrms.kream.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;

	@Mock
	private MemberRepository memberRepository;

	@Test
	@DisplayName("회원가입 - 성공")
	void register_success() {
		Member member = Member.builder()
				.id(1L)
				.name("name")
				.email("email@naver.com")
				.password("aA!12345678")
				.phone("01012345678")
				.isMale(true)
				.authority(ROLE_USER)
				.build();

		MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest(
				"name", "email@naver.com", "01012345678", "aA12345678!", true, ROLE_USER);

		when(memberRepository.save(any(Member.class)))
				.thenReturn(member);

		MemberRegisterResponse register = memberService.register(memberRegisterRequest);
		verify(memberRepository, times(1)).save(any(Member.class));
	}
}