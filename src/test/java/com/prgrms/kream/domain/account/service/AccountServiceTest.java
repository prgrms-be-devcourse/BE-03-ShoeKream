package com.prgrms.kream.domain.account.service;

import static com.prgrms.kream.domain.account.model.TransactionType.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.prgrms.kream.domain.account.dto.request.AccountCreateRequest;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateServiceRequest;
import com.prgrms.kream.domain.account.dto.response.AccountCreateResponse;
import com.prgrms.kream.domain.account.dto.response.AccountUpdateResponse;
import com.prgrms.kream.domain.account.model.Account;
import com.prgrms.kream.domain.account.repository.AccountRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
	@Mock
	AccountRepository accountRepository;

	@InjectMocks
	AccountService accountService;


	Account account1 = Account.builder().id(1L).memberId(1L).balance(10000).build();
	Account account2 = Account.builder().id(2L).memberId(2L).balance(20000).build();
	Account account3 = Account.builder().id(3L).memberId(3L).balance(30000).build();

	@Test
	@DisplayName("계좌 생성 테스트")
	void accountRegisterTest() {
		// Given
		AccountCreateRequest accountCreateRequest =
				new AccountCreateRequest(1L, 1L);
		Account account = Account.builder().id(1L).memberId(1L).balance(15000).build();

		// When
		when(accountRepository.save(any(Account.class))).thenReturn(account);
		AccountCreateResponse accountCreateResponse = accountService.registerAccount(accountCreateRequest);

		// Then
		assertThat(accountCreateResponse.id()).isEqualTo(1L);
	}

	@Test
	@DisplayName("계좌 중복 생성 테스트")
	void accountRegisterDuplicateTest() {
		// Given
		AccountCreateRequest accountCreateRequest =
				new AccountCreateRequest(1L, 1L);
		Account account = Account.builder().id(1L).memberId(1L).balance(15000).build();

		// When
		when(accountRepository.findByMemberId(any(Long.class))).thenReturn(Optional.of(account));
		// accountService.register(accountCreateRequest);

		// Then
		assertThatThrownBy(
				() -> accountService.registerAccount(accountCreateRequest)
		).isInstanceOf(EntityExistsException.class);
	}

	@Test
	@DisplayName("계좌 진액 변동 테스트")
	void updateBalanceTest(){
		// Given
		accountRepository.saveAll(List.of(account1, account2, account3));
		AccountUpdateServiceRequest accountUpdateServiceRequest1 =
				new AccountUpdateServiceRequest(1L, WITHDRAW, 1000 );
		AccountUpdateServiceRequest accountUpdateServiceRequest2 =
				new AccountUpdateServiceRequest(1L, DEPOSIT, 20000 );
		AccountUpdateServiceRequest accountUpdateServiceRequest3 =
				new AccountUpdateServiceRequest(1L, WITHDRAW, 50000 );

		// When
		when(accountRepository.findByMemberId(any(Long.class))).thenReturn(Optional.of(account1));
		AccountUpdateResponse accountUpdateResponse1 = accountService.updateBalance(accountUpdateServiceRequest1);
		AccountUpdateResponse accountUpdateResponse2 = accountService.updateBalance(accountUpdateServiceRequest2);
		AccountUpdateResponse accountUpdateResponse3 = accountService.updateBalance(accountUpdateServiceRequest3);
		Account retreived = accountRepository.findByMemberId(1L).get();


		// Then
		assertThat(accountUpdateResponse1.isSucceed()).isTrue();
		assertThat(accountUpdateResponse2.isSucceed()).isTrue();
		assertThat(accountUpdateResponse3.isSucceed()).isFalse();
		assertThat(retreived.getBalance()).isEqualTo(29000);
	}
}
