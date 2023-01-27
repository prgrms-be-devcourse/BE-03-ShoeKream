package com.prgrms.kream.domain.member.controller;

import static com.prgrms.kream.domain.image.model.DomainType.*;
import static com.prgrms.kream.domain.member.model.Authority.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import javax.servlet.http.Cookie;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.image.model.Image;
import com.prgrms.kream.domain.image.repository.ImageRepository;
import com.prgrms.kream.domain.member.dto.request.MemberLoginRequest;
import com.prgrms.kream.domain.member.dto.request.MemberRegisterRequest;
import com.prgrms.kream.domain.member.model.Member;
import com.prgrms.kream.domain.member.repository.MemberRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MemberControllerTest extends MysqlTestContainer {

	@Autowired
	private MockMvc mockMvc;

	@Value("${jwt.accessToken}")
	private String accessToken;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private ImageRepository imageRepository;

	@BeforeEach
	void setUp() {
		memberRepository.save(
				Member.builder()
						.email("hello@naver.com")
						.name("name")
						.password("Pa!12345678")
						.phone("01012345678")
						.isMale(true)
						.authority(ROLE_USER)
						.build());

		imageRepository.save(
				Image.builder()
						.referenceId(1L)
						.domainType(MEMBER)
						.fullPath("/path/test1")
						.originalName("profile1")
						.build()
		);

		imageRepository.save(
				Image.builder()
						.referenceId(1L)
						.domainType(MEMBER)
						.fullPath("/path/test2")
						.originalName("profile2")
						.build()
		);

		SecurityContext context = SecurityContextHolder.getContext();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(
						1L, null, List.of(new SimpleGrantedAuthority(ROLE_USER.name()))
				);

		context.setAuthentication(usernamePasswordAuthenticationToken);
	}

	@AfterEach
	void tearDown() {
		memberRepository.deleteAll();
	}

	@Test
	@DisplayName("회원가입 - 성공")
	void register_success() throws Exception {
		MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest(
				"name", "email@naver.com", "01012345678", "aA12345678!", true, ROLE_USER);

		mockMvc.perform(post("/api/v1/member/signup")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(memberRegisterRequest))
				).andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.id").value(Matchers.any(Number.class)))
				.andDo(print())
		;
	}

	@Test
	@DisplayName("로그인 - 성공")
	void login_success() throws Exception {
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(
				"hello@naver.com", "Pa!12345678"
		);

		mockMvc.perform(post("/api/v1/member/login")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(memberLoginRequest))
				).andExpect(status().isOk())
				.andExpect(header().exists("Set-Cookie"))
				.andDo(print())
				.andReturn();
	}

	@Test
	@DisplayName("로그아웃 - 성공")
	void logout_success() throws Exception {
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(
				"hello@naver.com", "Pa!12345678"
		);

		MvcResult mvcResult = mockMvc.perform(post("/api/v1/member/login")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(memberLoginRequest))
				).andExpect(status().isOk())
				.andReturn();

		Cookie tokenCookie = mvcResult.getResponse().getCookie(accessToken);

		mockMvc.perform(get("/api/v1/member/logout").cookie(tokenCookie))
				.andExpect(status().isOk())
				.andExpect(header().string("Set-Cookie", Matchers.startsWith(accessToken + "=; Max-Age=0;")))
				.andDo(print());
	}

	@Test
	@DisplayName("사용자 정보 조회 성공")
	void get_success() throws Exception {
		mockMvc.perform(get("/api/v1/member/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(1))
				.andExpect(jsonPath("$.data.name").value("name"))
				.andExpect(jsonPath("$.data.email").value("hello@naver.com"))
				.andExpect(jsonPath("$.data.phone").value("01012345678"))
				.andExpect(jsonPath("$.data.isMale").value(true))
				.andExpect(jsonPath("$.data.authority").value(ROLE_USER.name()))
				.andExpect(jsonPath("$.data.imagePaths[0]").value("/path/test1"))
				.andExpect(jsonPath("$.data.imagePaths[1]").value("/path/test2"))
				.andDo(print());
	}
}