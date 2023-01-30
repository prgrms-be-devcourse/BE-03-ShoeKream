package com.prgrms.kream.domain.bid.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.bid.dto.request.BuyingBidCreateRequest;
import com.prgrms.kream.domain.bid.model.BuyingBid;
import com.prgrms.kream.domain.bid.repository.BuyingBidRepository;
import com.prgrms.kream.domain.bid.service.BuyingBidService;
import com.prgrms.kream.domain.product.model.Product;
import com.prgrms.kream.domain.product.model.ProductOption;
import com.prgrms.kream.domain.product.repository.ProductOptionRepository;
import com.prgrms.kream.domain.product.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BuyingBidControllerTest extends MysqlTestContainer {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private Jackson2ObjectMapperBuilder objectMapperBuilder;

	@Autowired
	BuyingBidRepository buyingBidRepository;

	@Autowired
	ProductOptionRepository productOptionRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	BuyingBidService buyingBidService;

	@BeforeEach
	void wipeOut() {
		buyingBidRepository.deleteAll();
	}

	void addFiveBuyingBids() {
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

		BuyingBid buyingBid1 = BuyingBid.builder().id(1L).price(1560).productOptionId(1L).memberId(1L).build();
		BuyingBid buyingBid2 = BuyingBid.builder().id(2L).price(1500).productOptionId(1L).memberId(2L).build();
		BuyingBid buyingBid3 =
				BuyingBid.builder().id(3L).price(1440).productOptionId(1L).memberId(3L).isDeleted(true).build();
		BuyingBid buyingBid4 = BuyingBid.builder().id(4L).price(1500).productOptionId(2L).memberId(4L).build();
		BuyingBid buyingBid5 = BuyingBid.builder().id(5L).price(1500).productOptionId(2L).memberId(5L).build();

		buyingBidRepository.save(buyingBid1);
		buyingBidRepository.save(buyingBid2);
		buyingBidRepository.save(buyingBid3);
		buyingBidRepository.save(buyingBid4);
		buyingBidRepository.save(buyingBid5);
	}

	@Test
	@DisplayName("판매 입찰 등록 테스트")
	void insertTest() throws Exception {
		// Given
		addFiveBuyingBids();

		BuyingBidCreateRequest buyingBidCreateRequest =
				new BuyingBidCreateRequest(6L, 6L, 1L, 1560, LocalDateTime.now());

		// When
		ResultActions resultActions =
				mockMvc.perform(
						post("/api/v1/buying-bid")
								.contentType(MediaType.APPLICATION_JSON)
								.characterEncoding("UTF-8")
								.content(
										objectMapperBuilder.build().writeValueAsString(buyingBidCreateRequest)
								)
				);

		// Then
		resultActions
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.id").value(6));
	}

	@Test
	@DisplayName("컨트롤러에서 조회 테스트")
	void findTest() throws Exception {
		// Given
		addFiveBuyingBids();

		// When
		ResultActions resultActions = mockMvc.perform(get("/api/v1/buying-bid/{id}", 2));

		// Then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.memberId").value(2))
				.andExpect(jsonPath("$.data.productOptionId").value(1))
				.andExpect(jsonPath("$.data.price").value(1500))
				.andDo(print());
	}

	@Test
	@DisplayName("컨트롤러에서 지우기 테스트")
	void deleteTest() throws Exception {
		// Given
		addFiveBuyingBids();

		// When
		ResultActions resultActions = mockMvc.perform(put("/api/v1/buying-bid/delete/{id}", 1));

		// Then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").value("구매 입찰이 삭제되었습니다"))
				.andDo(print());
	}

	@Test
	@DisplayName("복구 테스트")
	void restoreTest() throws Exception {
		// Given
		addFiveBuyingBids();

		// When
		ResultActions resultActions = mockMvc.perform(put("/api/v1/buying-bid/restore/{id}", 3));

		// Then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").value("구매 입찰이 복구되었습니다"))
				.andDo(print());
	}

	@Test
	@DisplayName("낙관적 락 테스트")
	void optimisticLockTest() {
		// Given
		addFiveBuyingBids();

		int threadNum = 3;
		ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
		List<Future<?>> futures = new ArrayList<>();
		Exception result = new Exception();

		// When
		for (int i = 0; i < threadNum; i++) {
			futures.add(executorService.submit(() -> buyingBidRepository.deleteById(1L)));
		}

		try {
			for (Future<?> future : futures) {
				future.get();
			}
		} catch (Exception e) {
			result = (Exception)e.getCause();
		}

		// Then
		assertThat(result).isInstanceOf(OptimisticLockingFailureException.class);
	}
}
