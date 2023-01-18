package com.prgrms.kream.bid;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import com.prgrms.kream.domain.bid.dto.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.SellingBidDto;
import com.prgrms.kream.domain.bid.dto.SellingBidFindRequest;
import com.prgrms.kream.domain.bid.facade.SellingBidFacade;
import com.prgrms.kream.domain.bid.model.SellingBid;
import com.prgrms.kream.domain.bid.repository.SellingBidRepository;
import com.prgrms.kream.domain.bid.service.SellingBidService;

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
		SellingBidCreateResponse response = service.createSellingBid(createRequest);

		// Then
		assertThat(response.id()).isEqualTo(1L);
	}

	@Test
	@DisplayName("판매 입찰 찾기 테스트")
	void findByIdTest(){
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
		SellingBidDto dto = service.findOneSellingBidById(findRequest);

		// Then
		assertThat(dto.id()).isEqualTo(entity.getId());
	}

	@Test
	@DisplayName("삭제 테스트")
	void deleteByIdTest(){
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
		service.createSellingBid(createRequest);

		// Then
		assertThatThrownBy(() -> service.findOneSellingBidById(findRequest))
				.isInstanceOf(EntityNotFoundException.class);
	}
}
