package com.prgrms.kream.domain.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.bid.model.SellingBid;
import com.prgrms.kream.domain.bid.repository.BuyingBidRepository;
import com.prgrms.kream.domain.bid.repository.SellingBidRepository;
import com.prgrms.kream.domain.order.dto.request.OrderCreateFacadeRequest;
import com.prgrms.kream.domain.order.dto.request.OrderCreateServiceRequest;
import com.prgrms.kream.domain.order.repository.OrderRepository;
import com.prgrms.kream.domain.order.service.OrderService;
import com.prgrms.kream.domain.product.model.Product;
import com.prgrms.kream.domain.product.model.ProductOption;
import com.prgrms.kream.domain.product.repository.ProductOptionRepository;
import com.prgrms.kream.domain.product.repository.ProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrderControllerTest extends MysqlTestContainer {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	SellingBidRepository sellingBidRepository;

	@Autowired
	BuyingBidRepository buyingBidRepository;

	@Autowired
	Jackson2ObjectMapperBuilder objectMapperBuilder;
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	ProductOptionRepository productOptionRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	OrderService orderService;

	// @Test
	// @DisplayName("판매 입찰 기반 주문 생성 테스트")
	// void createOrderBySellingBid() throws Exception {
	// 	// Given
	// 	SellingBid sellingBid = SellingBid.builder()
	// 			.id(1L)
	// 			.price(45600)
	// 			.memberId(2L)
	// 			.productOptionId(3L)
	// 			.validUntil(LocalDateTime.now().plusDays(30))
	// 			.build();
	// 	sellingBidRepository.save(sellingBid);
	//
	// 	OrderCreateFacadeRequest orderCreateFacadeRequest =
	// 			new OrderCreateFacadeRequest(4L, 1L, "문 앞에 놔주세요.");
	//
	// 	// When
	// 	ResultActions resultActions =
	// 			mockMvc.perform(
	// 					post("/api/v1/order/selling-bid")
	// 							.contentType(MediaType.APPLICATION_JSON)
	// 							.characterEncoding("UTF-8")
	// 							.content(
	// 									objectMapperBuilder.build().writeValueAsString(orderCreateFacadeRequest)
	// 							)
	// 			).andDo(print());
	//
	// 	// Then
	// 	resultActions.andExpect(status().isCreated());
	// }
	//
	// @Test
	// @DisplayName("판매 입찰 기반 주문 생성 테스트")
	// void createOrderByBuyingBid() throws Exception {
	// 	// Given
	// 	BuyingBid buyingBid = BuyingBid.builder()
	// 			.id(1L)
	// 			.price(45600)
	// 			.memberId(2L)
	// 			.productOptionId(3L)
	// 			.validUntil(LocalDateTime.now().plusDays(30))
	// 			.build();
	// 	buyingBidRepository.save(buyingBid);
	//
	// 	 OrderCreateFacadeRequest orderCreateFacadeRequest =
	// 			new OrderCreateFacadeRequest(4L, 1L, "문 앞에 놔주세요.");
	//
	// 	// When
	// 	ResultActions resultActions =
	// 			mockMvc.perform(
	// 					post("/api/v1/order/buying-bid")
	// 							.contentType(MediaType.APPLICATION_JSON)
	// 							.characterEncoding("UTF-8")
	// 							.content(
	// 									objectMapperBuilder.build().writeValueAsString(orderCreateFacadeRequest)
	// 							)
	// 			).andDo(print());
	//
	// 	// Then
	// 	resultActions.andExpect(status().isCreated());
	// }

	@BeforeEach
	void wipeOut(){
		orderRepository.deleteAll();
	}

	void addFiveSellingBids() {
		Product product1 = Product.builder()
				.id(1L)
				.name("")
				.description("")
				.releasePrice(100)
				.build();
		Product product2 = Product.builder()
				.id(1L)
				.name("")
				.description("")
				.releasePrice(100)
				.build();

		ProductOption productOption1 =
				ProductOption.builder()
						.id(1L)
						.size(100)
						.product(product1)
						.build();
		ProductOption productOption2 =
				ProductOption.builder()
						.id(2L)
						.size(100)
						.product(product2)
						.build();

		productRepository.save(product1);
		productRepository.save(product2);

		productOptionRepository.save(productOption1);
		productOptionRepository.save(productOption2);

		SellingBid sellingBid1 = SellingBid.builder().id(1L).price(1560).productOptionId(1L).memberId(1L).build();
		SellingBid sellingBid2 = SellingBid.builder().id(2L).price(1500).productOptionId(1L).memberId(2L).build();
		SellingBid sellingBid3 =
				SellingBid.builder().id(3L).price(1440).productOptionId(1L).memberId(3L).isDeleted(true).build();
		SellingBid sellingBid4 = SellingBid.builder().id(4L).price(1560).productOptionId(2L).memberId(4L).build();
		SellingBid sellingBid5 = SellingBid.builder().id(5L).price(1560).productOptionId(2L).memberId(5L).build();

		sellingBidRepository.save(sellingBid1);
		sellingBidRepository.save(sellingBid2);
		sellingBidRepository.save(sellingBid3);
		sellingBidRepository.save(sellingBid4);
		sellingBidRepository.save(sellingBid5);
	}

	@Test
	@DisplayName("주문 생성 테스트")
	void orderCreateTest() throws Exception {
		// Given
		addFiveSellingBids();
		OrderCreateFacadeRequest orderCreateFacadeRequest =
				new OrderCreateFacadeRequest(1L, 2L, true, "문 앞에 놔주세요");


		// When
		ResultActions resultActions =
				mockMvc.perform(post("/api/v1/orders/selling-bids")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(
								objectMapperBuilder.build().writeValueAsString(orderCreateFacadeRequest)
						)
				);
		Optional<SellingBid> retrieved = sellingBidRepository.findById(2L);

		// Then
		resultActions
				.andExpect(status().isCreated())
				.andDo(print());
		assertThat(retrieved).isPresent();
		assertThat(retrieved.get().isDeleted()).isTrue();
	}

	@Test
	@DisplayName("주문 삭제 테스트")
	void deleteTest() throws Exception {
		// Given
		addFiveSellingBids();
		OrderCreateServiceRequest orderCreateServiceRequest =
				new OrderCreateServiceRequest(1L, 2L, true, 150L, 2L, 1L,
						1500, "문 앞에 놔주세요");
		orderService.register(orderCreateServiceRequest);

		// When
		ResultActions resultAction =
				mockMvc.perform(delete("/api/v1/orders/{id}", 1L)
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
				);
		Optional<SellingBid> retrieved = sellingBidRepository.findById(2L);

		// Then
		resultAction
				.andExpect(status().isOk())
				.andDo(print());

		assertThat(retrieved).isPresent();
		assertThat(retrieved.get().isDeleted()).isFalse();
	}
}
