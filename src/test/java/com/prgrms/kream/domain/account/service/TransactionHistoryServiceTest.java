package com.prgrms.kream.domain.account.service;

import com.prgrms.kream.domain.account.dto.request.TransactionHistoryCreateRequest;
import com.prgrms.kream.domain.account.dto.response.TransactionHistoryCreateResponse;
import com.prgrms.kream.domain.account.model.TransactionHistory;
import com.prgrms.kream.domain.account.model.TransactionType;
import com.prgrms.kream.domain.account.repository.TransactionHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TransactionHistoryServiceTest {
	@Mock
	TransactionHistoryRepository transactionHistoryRepository;

	@InjectMocks
	TransactionHistoryService transactionHistoryService;

	@Test
	@DisplayName("거래 내역 등록 테스트")
	void transactionHistoryRegisterTest(){
		// Given
		TransactionHistoryCreateRequest transactionHistoryCreateRequest =
				new TransactionHistoryCreateRequest(1L, 1L , TransactionType.DEPOSIT, 15000);
		TransactionHistory transactionHistory = TransactionHistory.builder()
				.id(1L)
				.accountId(1L)
				.transactionType(TransactionType.DEPOSIT)
				.amount(15000)
				.build();

		// When
		when(transactionHistoryRepository.save(any(TransactionHistory.class))).thenReturn(transactionHistory);
		TransactionHistoryCreateResponse transactionHistoryCreateResponse =
				transactionHistoryService.registerTransactionHistory(transactionHistoryCreateRequest);

		// Then
		assertThat(transactionHistoryCreateResponse.id()).isEqualTo(1L);
		assertThat(transactionHistoryCreateResponse.transactionType()).isEqualTo(TransactionType.DEPOSIT);
		assertThat(transactionHistoryCreateResponse.amount()).isEqualTo(15000);
	}
}
