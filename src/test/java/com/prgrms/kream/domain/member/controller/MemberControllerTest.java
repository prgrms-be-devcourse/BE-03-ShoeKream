package com.prgrms.kream.domain.member.controller;

import static com.prgrms.kream.domain.member.model.Authority.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.member.dto.MemberRegisterRequest;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MemberControllerTest extends MysqlTestContainer {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

	@Test
	@DisplayName("회원가입 controller test - 성공")
	void register_success() throws Exception {

		ObjectMapper objectMapper = jackson2ObjectMapperBuilder.build();

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
}