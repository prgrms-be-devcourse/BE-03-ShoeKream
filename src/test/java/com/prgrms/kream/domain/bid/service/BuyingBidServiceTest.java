package com.prgrms.kream.domain.bid.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import com.prgrms.kream.domain.bid.dto.request.BuyingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.request.BuyingBidFindRequest;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidFindResponse;
import com.prgrms.kream.domain.bid.model.BuyingBid;
import com.prgrms.kream.domain.bid.repository.BuyingBidRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class BuyingBidServiceTest {

	@Mock
	BuyingBidRepository repository;

	@InjectMocks
	BuyingBidService service;

	Long id = 1L;

	@Test
	@DisplayName("구매 입찰 삽입 테스트")
	void buyingBidFindTest() {
		BuyingBidCreateRequest buyingBidCreateRequest =
				new BuyingBidCreateRequest(1L, 1L, 1L, 100, LocalDateTime.now());
		BuyingBid buyingBid = BuyingBid.builder()
				.id(1L)
				.memberId(1L)
				.productOptionId(1L)
				.price(100)
				.validUntil(LocalDateTime.now())
				.build();

		when(repository.save(any(BuyingBid.class))).thenReturn(buyingBid);
		BuyingBidCreateResponse buyingBidCreateResponse = service.registerBuyingBid(buyingBidCreateRequest);

		assertThat(buyingBidCreateResponse.id()).isEqualTo(buyingBid.getId());
	}

	@Test
	@DisplayName("판매 입찰 찾기 테스트")
	void findByIdTest() {
		// Given
		BuyingBidFindRequest buyingBidFindRequest = new BuyingBidFindRequest(Collections.singletonList(1L));
		BuyingBid buyingBid = BuyingBid.builder()
				.id(1L)
				.memberId(1L)
				.productOptionId(1L)
				.price(100)
				.validUntil(LocalDateTime.now())
				.build();

		when(repository.findById(id)).thenReturn(Optional.of(buyingBid));

		// When
		BuyingBidFindResponse buyingBidFindResponse = service.getBuyingBid(buyingBidFindRequest);

		// Then
		assertThat(buyingBidFindResponse.id()).isEqualTo(buyingBid.getId());
	}

	@Test
	@DisplayName("삭제 테스트")
	void deleteByIdTest() {
		// Given
		BuyingBidCreateRequest buyingBidCreateRequest =
				new BuyingBidCreateRequest(1L, 1L, 1L, 100, LocalDateTime.now());
		BuyingBidFindRequest buyingBidFindRequest =
				new BuyingBidFindRequest(Collections.singletonList(1L));

		BuyingBid buyingBid = BuyingBid.builder()
				.id(1L)
				.memberId(1L)
				.productOptionId(1L)
				.price(100)
				.validUntil(LocalDateTime.now())
				.build();

		// When
		when(repository.save(any(BuyingBid.class))).thenReturn(buyingBid);
		service.registerBuyingBid(buyingBidCreateRequest);

		// Then
		assertThatThrownBy(() -> service.getBuyingBid(buyingBidFindRequest))
				.isInstanceOf(EntityNotFoundException.class);
	}

	@Test
	@DisplayName("가장 가격이 높은 구매 입찰을 가져오는지 확인")
	void highestPriceTest() {
		// Given
		BuyingBid buyingBid1 = BuyingBid.builder().id(1L).productOptionId(1L).memberId(1L).price(1500)
				.validUntil(LocalDateTime.now().plusDays(30)).build();
		BuyingBid buyingBid2 = BuyingBid.builder().id(2L).productOptionId(1L).memberId(2L).price(1600)
				.validUntil(LocalDateTime.now().plusDays(30)).build();
		BuyingBid buyingBid3 = BuyingBid.builder().id(3L).productOptionId(1L).memberId(3L).price(1600)
				.validUntil(LocalDateTime.now().plusDays(30)).build();

		BuyingBidFindRequest buyingBidFindRequest = new BuyingBidFindRequest(Collections.singletonList(1L));

		// When
		when(repository.findHighestBuyingBidByProductOptionId(any(Long.class))).thenReturn(Optional.of(buyingBid2));
		BuyingBidFindResponse buyingBidFindResponse = service.findHighestBuyingBidByProductOptionId(buyingBidFindRequest);

		// Then
		assertThat(buyingBidFindResponse.id()).isEqualTo(2L);
	}
}
