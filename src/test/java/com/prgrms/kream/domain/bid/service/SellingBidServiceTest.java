package com.prgrms.kream.domain.bid.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.prgrms.kream.domain.bid.dto.request.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.request.SellingBidFindRequest;
import com.prgrms.kream.domain.bid.dto.response.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.SellingBidFindResponse;
import com.prgrms.kream.domain.bid.facade.SellingBidFacade;
import com.prgrms.kream.domain.bid.model.SellingBid;
import com.prgrms.kream.domain.bid.repository.SellingBidRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class SellingBidServiceTest {

	@Mock
	SellingBidRepository repository;

	@InjectMocks
	SellingBidService service;

	@InjectMocks
	SellingBidFacade facade;

	Long id = 1L;

	@Test
	@DisplayName("판매 입찰 삽입 테슷흐")
	void sellingBidFindTest() {
		// Given
		SellingBidCreateRequest createRequest =
				new SellingBidCreateRequest(1L, 1L, 1L, 100, LocalDateTime.now());
		SellingBid entity = SellingBid.builder()
				.id(1L)
				.memberId(1L)
				.productOptionId(1L)
				.price(100)
				.validUntil(LocalDateTime.now())
				.build();

		// When
		when(repository.save(any(SellingBid.class))).thenReturn(entity);
		SellingBidCreateResponse response = service.register(createRequest);

		// Then
		assertThat(response.id()).isEqualTo(1L);
	}

	@Test
	@DisplayName("판매 입찰 찾기 테스트")
	void findByIdTest() {
		// Given
		SellingBidFindRequest findRequest = new SellingBidFindRequest(Collections.singletonList(1L));
		SellingBid entity = SellingBid.builder()
				.id(1L)
				.memberId(1L)
				.productOptionId(1L)
				.price(100)
				.validUntil(LocalDateTime.now())
				.build();

		when(repository.findById(id)).thenReturn(Optional.of(entity));

		// When
		SellingBidFindResponse dto = service.findById(findRequest);

		// Then
		assertThat(dto.id()).isEqualTo(entity.getId());
	}

	@Test
	@DisplayName("삭제 테스트")
	void deleteByIdTest() {
		// Given
		SellingBidCreateRequest createRequest =
				new SellingBidCreateRequest(1L, 1L, 1L, 100, LocalDateTime.now());
		SellingBidFindRequest findRequest =
				new SellingBidFindRequest(Collections.singletonList(1L));

		SellingBid entity = SellingBid.builder()
				.id(1L)
				.memberId(1L)
				.productOptionId(1L)
				.price(100)
				.validUntil(LocalDateTime.now())
				.build();

		// When
		when(repository.save(any(SellingBid.class))).thenReturn(entity);
		service.register(createRequest);

		// Then
		assertThatThrownBy(() -> service.findById(findRequest))
				.isInstanceOf(EntityNotFoundException.class);
	}

	@Test
	@DisplayName("가장 가격이 낮은 판매 입찰을 가져오는지 확인")
	void highestPriceTest() {
		// Given
		SellingBid sellingBid1 = SellingBid.builder().id(1L).productOptionId(1L).memberId(1L).price(1500)
				.validUntil(LocalDateTime.now().plusDays(30)).build();
		SellingBid sellingBid2 = SellingBid.builder().id(2L).productOptionId(1L).memberId(2L).price(1500)
				.validUntil(LocalDateTime.now().plusDays(30)).build();
		SellingBid sellingBid3 = SellingBid.builder().id(3L).productOptionId(1L).memberId(3L).price(1600)
				.validUntil(LocalDateTime.now().plusDays(30)).build();

		SellingBidFindRequest sellingBidFindRequest = new SellingBidFindRequest(Collections.singletonList(1L));

		// When
		when(repository.findLowestSellingBidByProductOptionId(any(Long.class))).thenReturn(
				Optional.of(sellingBid2));
		SellingBidFindResponse sellingBidFindResponse =
				service.findLowestSellingBidByProductOptionId(sellingBidFindRequest);

		// Then
		assertThat(sellingBidFindResponse.id()).isEqualTo(2L);
	}
}
