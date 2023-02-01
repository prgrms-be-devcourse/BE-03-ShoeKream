package com.prgrms.kream.domain.account.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateRequest;
import com.prgrms.kream.domain.account.model.Account;
import com.prgrms.kream.domain.account.model.TransactionType;
import com.prgrms.kream.domain.account.repository.AccountRepository;
import com.prgrms.kream.domain.account.service.AccountService;
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
		AccountUpdateRequest accountUpdateRequest =
				new AccountUpdateRequest(1L, TransactionType.DEPOSIT, 15000);

		// When
		ResultActions resultActions =
				mockMvc.perform(put("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(objectMapperBuilder.build().writeValueAsString(accountUpdateRequest)
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
		AccountUpdateRequest accountUpdateRequest =
				new AccountUpdateRequest(1L, TransactionType.WITHDRAW, 5000);

		// When
		ResultActions resultActions =
				mockMvc.perform(put("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(objectMapperBuilder.build().writeValueAsString(accountUpdateRequest)
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
		AccountUpdateRequest accountUpdateRequest =
				new AccountUpdateRequest(1L, TransactionType.WITHDRAW, 15000);

		// When
		ResultActions resultActions =
				mockMvc.perform(put("/api/v1/accounts")
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(objectMapperBuilder.build().writeValueAsString(accountUpdateRequest)
						)
				);

		// Then
		resultActions
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.isSucceed").value(false))
				.andDo(print());
	}
}
