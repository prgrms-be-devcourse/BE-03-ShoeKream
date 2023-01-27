package com.prgrms.kream.domain.bid;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.prgrms.kream.domain.bid.model.BuyingBid;
import com.prgrms.kream.domain.bid.repository.BuyingBidRepository;
import java.time.LocalDateTime;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.bid.dto.request.BuyingBidCreateRequest;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BuyingBidControllerTest extends MysqlTestContainer {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Jackson2ObjectMapperBuilder objectMapper;

	@Autowired
	BuyingBidRepository repository;

	@Test
	@DisplayName("판매 입찰 등록 테스트")
	void insertTest() throws Exception {
		// Given
		BuyingBidCreateRequest buyingBidCreateRequest =
				new BuyingBidCreateRequest(1L, 2L, 3L, 45600, LocalDateTime.now());

		// When
		ResultActions resultActions =
				mockMvc.perform(
						post("/api/v1/buying-bid")
								.contentType(MediaType.APPLICATION_JSON)
								.characterEncoding("UTF-8")
								.content(
										objectMapper.build().writeValueAsString(buyingBidCreateRequest)
								)
				);

		// Then
		resultActions.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("컨트롤러에서 조회 테스트")
	void findTest() throws Exception {
		// Given

		// When
		ResultActions resultActions = mockMvc.perform(get("/api/v1/buying-bid/{id}", 1));

		// Then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.memberId").value(2))
				.andExpect(jsonPath("$.data.productOptionId").value(3))
				.andExpect(jsonPath("$.data.price").value(45600))
				.andDo(print());
	}

	@Test
	@DisplayName("컨트롤러에서 지우기 테스트")
	void deleteTest() throws Exception {
		// Given

		// When
		ResultActions resultActions = mockMvc.perform(delete("/api/v1/buying-bid/{id}", 1));

		// Then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").value("구매 입찰이 삭제되었습니다"))
				.andDo(print());
	}

	@Test
	@DisplayName("가장 비싼 가격의 구매 입찰을 가져오는 테스트")
	void findHighestPriceBuyingBidTest() throws Exception {
		// Given
		List<BuyingBid> buyingBids = List.of(
				BuyingBid.builder().id(1L).productOptionId(1L).memberId(1L).price(1500)
				.validUntil(LocalDateTime.now().plusDays(30)).build(),
				BuyingBid.builder().id(2L).productOptionId(1L).memberId(2L).price(1600)
				.validUntil(LocalDateTime.now().plusDays(30)).build(),
				BuyingBid.builder().id(3L).productOptionId(1L).memberId(3L).price(1600)
				.validUntil(LocalDateTime.now().plusDays(30)).build()
		);
		repository.saveAll(buyingBids);

		// When
		ResultActions resultActions = mockMvc.perform(get("/api/v1/buying-bid/highest/1"));

		// Then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(2))
				.andExpect(jsonPath("$.data.price").value(1600))
				.andDo(print());
	}
}
