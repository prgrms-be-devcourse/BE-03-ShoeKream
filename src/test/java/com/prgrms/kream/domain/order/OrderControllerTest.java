package com.prgrms.kream.domain.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.bid.dto.request.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.model.SellingBid;
import com.prgrms.kream.domain.bid.repository.SellingBidRepository;
import com.prgrms.kream.domain.order.dto.request.OrderCreateFacadeRequest;
import com.prgrms.kream.domain.order.dto.request.OrderCreateServiceRequest;
import java.time.LocalDateTime;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrderControllerTest extends MysqlTestContainer {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	SellingBidRepository sellingBidRepository;

	@Autowired
	Jackson2ObjectMapperBuilder objectMapperBuilder;

	@Test
	@DisplayName("판매 입찰 기반 주문 생성 테스트")
	void createOrderBySellingBid() throws Exception {
		// Given
		SellingBid sellingBid = SellingBid.builder()
				.id(1L)
				.price(45600)
				.memberId(2L)
				.productOptionId(3L)
				.validUntil(LocalDateTime.now().plusDays(30))
				.build();
		sellingBidRepository.save(sellingBid);

		OrderCreateFacadeRequest orderCreateFacadeRequest =
				new OrderCreateFacadeRequest(4L, 1L, "문 앞에 놔주세요.");

		// When
		ResultActions resultActions =
				mockMvc.perform(
						post("/api/v1/order")
								.contentType(MediaType.APPLICATION_JSON)
								.characterEncoding("UTF-8")
								.content(
										objectMapperBuilder.build().writeValueAsString(orderCreateFacadeRequest)
								)
				).andDo(print());

		// Then
		resultActions.andExpect(status().isCreated());
	}
}
