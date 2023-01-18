package com.prgrms.kream.style.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.member.model.Member;
import com.prgrms.kream.domain.member.repository.MemberRepository;
import com.prgrms.kream.domain.style.dto.UpdateFeedRequestOfFacade;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("컨트롤러 계층을 통해")
class StyleControllerTest extends MysqlTestContainer {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberRepository memberRepository;

	private static Long memberId;

	@Test
	@DisplayName("피드를 등록할 수 있다.")
	void testRegister() throws Exception {
		Member member = Member.builder()
				.name("김창규")
				.email("kimc980106@naver.com")
				.phone("010-8610-7463")
				.password("1234")
				.isMale(true)
				.authority("ADMIN")
				.build();
		memberId = memberRepository.save(member).getId();

		MockMultipartFile mockImage = new MockMultipartFile(
				"images",
				"test.png",
				MediaType.IMAGE_PNG_VALUE,
				"This is a binary data".getBytes()
		);

		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/feed")
						.file(mockImage)
						.param("content", "이 피드의 태그는 #총 #두개 입니다.")
						.param("author", String.valueOf(memberId)))
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Test
	@DisplayName("피드를 수정할 수 있다.")
	void testUpdate() throws Exception {
		mockMvc.perform(put("/api/v1/feed/" + memberId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(
								new UpdateFeedRequestOfFacade("이 피드의 태그는 총 #한개 입니다.")
						)))
				.andExpect(status().isOk())
				.andDo(print());
	}

}