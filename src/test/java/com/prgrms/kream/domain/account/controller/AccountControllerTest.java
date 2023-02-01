package com.prgrms.kream.domain.account.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;
import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateFacadeRequest;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateServiceRequest;
import com.prgrms.kream.domain.account.facade.AccountFacade;
import com.prgrms.kream.domain.account.model.Account;
import com.prgrms.kream.domain.account.model.TransactionHistory;
import com.prgrms.kream.domain.account.model.TransactionType;
import com.prgrms.kream.domain.account.repository.AccountRepository;
import com.prgrms.kream.domain.account.repository.TransactionHistoryRepository;
import com.prgrms.kream.domain.account.service.AccountService;
import com.prgrms.kream.domain.account.service.TransactionHistoryService;
import java.util.List;
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

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AccountControllerTest extends MysqlTestContainer {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private Jackson2ObjectMapperBuilder objectMapperBuilder;

	@Autowired
	AccountService accountService;
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	TransactionHistoryRepository transactionHistoryRepository;
	@Autowired
	TransactionHistoryService transactionHistoryService;

	@Autowired
	AccountFacade accountFacade;

	Account account1 = Account.builder().id(1L).memberId(1L).balance(10000).build();
	Account account2 = Account.builder().id(2L).memberId(2L).balance(20000).build();
	Account account3 = Account.builder().id(3L).memberId(3L).balance(30000).build();

	@BeforeEach
	void wipeOut() {
		accountRepository.deleteAll();
	}

	@Test
	@DisplayName("계좌 등록 테스트")
	void accountRegisterTest() throws Exception {
		// Given

		// When
		ResultActions resultActions =
				mockMvc.perform(post("/api/v1/accounts/{id}", 1)
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
				);

		// Then
		resultActions
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.data.id").value(1))
				.andDo(print());
	}

	@Test
	@DisplayName("계좌 입금 테스트")
	void depositTest() throws Exception {
		// Given
		accountRepository.save(account1);
		AccountUpdateServiceRequest accountUpdateServiceRequest =
				new AccountUpdateServiceRequest(1L, TransactionType.DEPOSIT, 15000);

		// When
		ResultActions resultActions =
				mockMvc.perform(put("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(objectMapperBuilder.build().writeValueAsString(accountUpdateServiceRequest)
						)
				);

		// Then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.isSucceed").value(true))
				.andDo(print());
	}

	@Test
	@DisplayName("계좌 출금 테스트")
	void withdrawTest() throws Exception {
		// Given
		accountRepository.save(account1);
		AccountUpdateServiceRequest accountUpdateServiceRequest =
				new AccountUpdateServiceRequest(1L, TransactionType.WITHDRAW, 5000);

		// When
		ResultActions resultActions =
				mockMvc.perform(put("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(objectMapperBuilder.build().writeValueAsString(accountUpdateServiceRequest)
						)
				);

		// Then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.isSucceed").value(true))
				.andDo(print());
	}

	@Test
	@DisplayName("계좌 초과 출금 테스트")
	void overWithdrawTest() throws Exception {
		// Given
		accountRepository.save(account1);
		AccountUpdateServiceRequest accountUpdateServiceRequest =
				new AccountUpdateServiceRequest(1L, TransactionType.WITHDRAW, 15000);

		// When
		ResultActions resultActions =
				mockMvc.perform(put("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(objectMapperBuilder.build().writeValueAsString(accountUpdateServiceRequest)
						)
				);

		// Then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.isSucceed").value(false))
				.andDo(print());
	}

	@Test
	@DisplayName("출금시 거래 내역 생셩 테스트")
	void transactionHistoryRegisterWithDrawTest() throws Exception{
		// Given
		accountRepository.save(account1);
		AccountUpdateFacadeRequest accountUpdateFacadeRequest =
				new AccountUpdateFacadeRequest(1L, 1L, TransactionType.WITHDRAW, 5000);

		// When
		ResultActions resultActions =
				mockMvc.perform(put("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(objectMapperBuilder.build().writeValueAsString(accountUpdateFacadeRequest)
						)
				);

		List<TransactionHistory> transactionHistories = transactionHistoryRepository.findAll();
		Account retrieved = accountRepository.findByMemberId(1L).get();

		// Then
		assertThat(transactionHistories).hasSize(1);
		assertThat(transactionHistories.get(0).getId()).isEqualTo(1L);
		assertThat(transactionHistories.get(0).getAmount()).isEqualTo(5000);
		assertThat(transactionHistories.get(0).getTransactionType()).isEqualTo(TransactionType.WITHDRAW);
		assertThat(retrieved.getBalance()).isEqualTo(5000);
	}

	@Test
	@DisplayName("입금시 거래 내역 생셩 테스트")
	void transactionHistoryRegisterDepositTest() throws Exception{
		// Given
		accountRepository.save(account1);
		AccountUpdateFacadeRequest accountUpdateFacadeRequest =
				new AccountUpdateFacadeRequest(1L, 1L, TransactionType.DEPOSIT, 5000);

		// When
		ResultActions resultActions =
				mockMvc.perform(put("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(objectMapperBuilder.build().writeValueAsString(accountUpdateFacadeRequest)
						)
				);
		List<TransactionHistory> transactionHistories = transactionHistoryRepository.findAll();
		Account retrieved = accountRepository.findByMemberId(1L).get();

		// Then
		assertThat(transactionHistories).hasSize(1);
		assertThat(transactionHistories.get(0).getId()).isEqualTo(1L);
		assertThat(transactionHistories.get(0).getAmount()).isEqualTo(5000);
		assertThat(transactionHistories.get(0).getTransactionType()).isEqualTo(TransactionType.DEPOSIT);
		assertThat(retrieved.getBalance()).isEqualTo(15000);
	}

	@Test
	@DisplayName("입금시 거래 내역 생셩 테스트")
	void transactionHistoryRegisterOverWithDrawTestTest() throws Exception{
		// Given
		accountRepository.save(account1);
		AccountUpdateFacadeRequest accountUpdateFacadeRequest =
				new AccountUpdateFacadeRequest(1L, 1L, TransactionType.WITHDRAW, 20000);

		// When
		ResultActions resultActions =
				mockMvc.perform(put("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(objectMapperBuilder.build().writeValueAsString(accountUpdateFacadeRequest)
						)
				);
		List<TransactionHistory> transactionHistories = transactionHistoryRepository.findAll();
		Account retrieved = accountRepository.findByMemberId(1L).get();

		// Then
		assertThat(transactionHistories).hasSize(0);
		assertThat(retrieved.getBalance()).isEqualTo(10000);
	}
}
