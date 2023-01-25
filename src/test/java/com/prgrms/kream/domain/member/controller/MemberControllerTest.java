package com.prgrms.kream.domain.member.controller;

import static com.prgrms.kream.domain.member.model.Authority.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.kream.MysqlTestContainer;
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

		mockMvc.perform(post("/api/v1/member")
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(memberRegisterRequest))
				).andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.id").value(1L))
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
}