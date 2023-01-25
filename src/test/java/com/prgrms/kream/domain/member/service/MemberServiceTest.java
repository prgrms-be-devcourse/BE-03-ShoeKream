package com.prgrms.kream.domain.member.service;

import static com.prgrms.kream.domain.member.model.Authority.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

import com.prgrms.kream.common.jwt.Jwt;
import com.prgrms.kream.domain.member.dto.request.MemberLoginRequest;
import com.prgrms.kream.domain.member.dto.request.MemberRegisterRequest;
import com.prgrms.kream.domain.member.dto.response.MemberLoginResponse;
import com.prgrms.kream.domain.member.dto.response.MemberRegisterResponse;
import com.prgrms.kream.domain.member.model.Member;
import com.prgrms.kream.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;

	@Mock
	private Jwt jwt;

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
				"name", "email@naver.com", "01012345678", "aA12345678!", true, ROLE_USER
		);

		when(memberRepository.save(any(Member.class)))
				.thenReturn(member);

		MemberRegisterResponse register = memberService.register(memberRegisterRequest);
		verify(memberRepository, times(1)).save(any(Member.class));
	}

	@Test
	@DisplayName("로그인 실패 - 비밀번호 불일치")
	void login_success() {
		String password = "Pa!12345678";
		Member member = Member.builder()
				.id(1L)
				.email("hello@naver.com")
				.password(password)
				.name("hello")
				.phone("01012345678")
				.authority(ROLE_USER)
				.isMale(true)
				.build();

		when(memberRepository.findByEmail(member.getEmail()))
				.thenReturn(Optional.of(member));

		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(member.getEmail(), "wrongPassword");
		Assertions.assertThatThrownBy(() -> memberService.login(memberLoginRequest))
				.isInstanceOf(BadCredentialsException.class);

		verify(memberRepository, times(1)).findByEmail(member.getEmail());
		verify(jwt, times(0)).sign(any(Jwt.Claims.class));
	}

	@Test
	@DisplayName("로그인 성공")
	void login_fail() {
		String password = "Pa!12345678";
		Member member = Member.builder()
				.id(1L)
				.email("hello@naver.com")
				.password(password)
				.name("hello")
				.phone("01012345678")
				.authority(ROLE_USER)
				.isMale(true)
				.build();

		when(memberRepository.findByEmail(member.getEmail()))
				.thenReturn(Optional.of(member));

		String accessToken = "access_token";
		when(jwt.sign(any(Jwt.Claims.class))).thenReturn(accessToken);

		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(member.getEmail(), password);
		MemberLoginResponse login = memberService.login(memberLoginRequest);

		Assertions.assertThat(login.token()).isEqualTo(accessToken);
		verify(memberRepository, times(1)).findByEmail(member.getEmail());
		verify(jwt, times(1)).sign(any(Jwt.Claims.class));
	}
}
