package com.prgrms.kream.domain.account.facade;

import com.prgrms.kream.domain.account.dto.request.AccountCreateRequest;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateFacadeRequest;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateServiceRequest;
import com.prgrms.kream.domain.account.dto.response.AccountCreateResponse;
import com.prgrms.kream.domain.account.dto.response.AccountUpdateResponse;
import com.prgrms.kream.domain.account.service.AccountService;
import com.prgrms.kream.domain.account.service.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.prgrms.kream.common.mapper.AccountMapper.*;

@Service
@RequiredArgsConstructor
public class AccountFacade {
	private final AccountService accountService;
	private final TransactionHistoryService transactionHistoryService;

	private final Long myId = 0L;

	// todo 자신의 id를 가져오는 방법 생각하기
	@Transactional
	public AccountCreateResponse register(Long id) {
		AccountCreateRequest accountCreateRequest =
				new AccountCreateRequest(id, myId);
		return accountService.register(accountCreateRequest);
	}

	@Transactional
	public AccountUpdateResponse updateBalance(AccountUpdateFacadeRequest accountUpdateFacadeRequest){
		AccountUpdateServiceRequest accountUpdateServiceRequest =
				toAccountUpdateServiceRequest(accountUpdateFacadeRequest);
		AccountUpdateResponse accountUpdateResponse = accountService.updateBalance(accountUpdateServiceRequest);
		if (accountUpdateResponse.isSucceed()) {
			transactionHistoryService.register(toTransactionHistoryCreateRequest(accountUpdateFacadeRequest));
		}
		return accountUpdateResponse;
	}
}
