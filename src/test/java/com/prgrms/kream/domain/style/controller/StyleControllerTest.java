package com.prgrms.kream.domain.style.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import com.prgrms.kream.domain.member.model.Authority;
import com.prgrms.kream.domain.member.model.Member;
import com.prgrms.kream.domain.member.repository.MemberRepository;
import com.prgrms.kream.domain.style.dto.request.FeedLikeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedCommentRegisterRequest;
import com.prgrms.kream.domain.style.dto.request.FeedUpdateRequest;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("컨트롤러 계층을 통해")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StyleControllerTest extends MysqlTestContainer {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MemberRepository memberRepository;

	private static Long memberId;

	@Test
	@Order(1)
	@DisplayName("피드를 등록할 수 있다.")
	void testRegister() throws Exception {
		Member member = Member.builder()
				.name("김창규")
				.email("kimc980106@naver.com")
				.phone("01086107463")
				.password("qwer1234!")
				.isMale(true)
				.authority(Authority.ROLE_ADMIN)
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
						.param("authorId", String.valueOf(member.getId()))
						.param("productIds", "1, 2, 3"))
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Test
	@Order(2)
	@DisplayName("피드를 수정할 수 있다.")
	void testUpdate() throws Exception {
		mockMvc.perform(put("/api/v1/feed/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(
								new FeedUpdateRequest("이 피드의 태그는 총 #한개 입니다.", List.of(4L, 5L, 6L))
						)))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@Order(3)
	@DisplayName("피드에 사용자의 좋아요를 등록할 수 있다.")
	void testLikeFeed() throws Exception {
		mockMvc.perform(post("/api/v1/feed/1/like")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(
						new FeedLikeRequest(memberId)
				)))
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Test
	@Order(4)
	@DisplayName("피드에 사용자의 좋아요를 삭제할 수 있다.")
	void testUnlikeFeed() throws Exception {
		mockMvc.perform(delete("/api/v1/feed/1/like")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(
								new FeedLikeRequest(memberId)
						)))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@Order(5)
	@DisplayName("태그 기준으로 피드를 조회할 수 있다.")
	void testGetFeedsByTag() throws Exception {
		mockMvc.perform(get("/api/v1/feed/tags/한개"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@Order(6)
	@DisplayName("최신 순으로 피드를 조회할 수 있다.")
	void testGetNewestFeeds() throws Exception {
		mockMvc.perform(get("/api/v1/feed/newest"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@Order(7)
	@DisplayName("좋아요 순으로 피드를 조회할 수 있다.")
	void testGetTrendingFeeds() throws Exception {
		mockMvc.perform(get("/api/v1/feed/trending"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@Order(8)
	@DisplayName("사용자 기준으로 피드를 조회할 수 있다.")
	void testGetFeedsByMember() throws Exception {
		mockMvc.perform(get("/api/v1/feed/members/" + memberId))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@Order(9)
	@DisplayName("상품 기준으로 피드를 조회할 수 있다.")
	void testGetFeedsByProduct() throws Exception {
		mockMvc.perform(get("/api/v1/feed/products/4"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@Order(10)
	@DisplayName("피드의 사용자 댓글을 등록할 수 있다.")
	void testRegisterFeedComment() throws Exception {
		mockMvc.perform(post("/api/v1/feed/1/comments")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(
								new FeedCommentRegisterRequest("피드의 댓글입니다.", memberId)
						)))
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Test
	@Order(11)
	@DisplayName("피드의 댓글을 조회할 수 있다.")
	void testGetAllFeedComments() throws Exception {
		mockMvc.perform(get("/api/v1/feed/1/comments"))
				.andExpect(status().isOk())
				.andDo(print());
	}

}