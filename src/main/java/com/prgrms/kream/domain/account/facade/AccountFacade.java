package com.prgrms.kream.domain.account.facade;

import com.prgrms.kream.domain.account.dto.request.AccountCreateRequest;
import com.prgrms.kream.domain.account.dto.response.AccountCreateResponse;
import com.prgrms.kream.domain.account.service.AccountService;
import com.prgrms.kream.domain.account.service.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountFacade {
	private final AccountService accountService;
	private final TransactionHistoryService transactionHistoryService;

	// todo 자신의 id를 가져오는 방법 생각하기
	@Transactional
	public AccountCreateResponse register(Long id) {
		AccountCreateRequest accountCreateRequest =
				new AccountCreateRequest(id, 0L);
		return accountService.register(accountCreateRequest);
	}
}
