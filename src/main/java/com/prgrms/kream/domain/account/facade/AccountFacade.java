package com.prgrms.kream.domain.account.facade;

import static com.prgrms.kream.common.mapper.AccountMapper.*;
import com.prgrms.kream.domain.account.dto.request.AccountCreateRequest;
import com.prgrms.kream.domain.account.dto.request.AccountGetRequest;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateFacadeRequest;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateOtherServiceRequest;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateServiceRequest;
import com.prgrms.kream.domain.account.dto.request.TransactionHistoryGetFacadeRequest;
import com.prgrms.kream.domain.account.dto.request.TransactionHistoryGetServiceRequest;
import com.prgrms.kream.domain.account.dto.response.AccountCreateResponse;
import com.prgrms.kream.domain.account.dto.response.AccountUpdateResponse;
import com.prgrms.kream.domain.account.dto.response.TransactionHistoryGetResponse;
import com.prgrms.kream.domain.account.service.AccountService;
import com.prgrms.kream.domain.account.service.TransactionHistoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public AccountUpdateResponse updateBalance(AccountUpdateFacadeRequest accountUpdateFacadeRequest) {
		AccountUpdateServiceRequest accountUpdateServiceRequest =
				toAccountUpdateServiceRequest(accountUpdateFacadeRequest);
		AccountUpdateResponse accountUpdateResponse = accountService.updateBalance(accountUpdateServiceRequest);
		if (accountUpdateResponse.isSucceed()) {
			transactionHistoryService.register(toTransactionHistoryCreateRequest(accountUpdateFacadeRequest));
		}
		return accountUpdateResponse;
	}
	@Transactional
	public AccountUpdateResponse updateBalance(AccountUpdateOtherServiceRequest accountUpdateOtherServiceRequest) {
		AccountUpdateServiceRequest accountUpdateServiceRequest =
				toAccountUpdateServiceRequest(accountUpdateOtherServiceRequest);
		AccountUpdateResponse accountUpdateResponse = accountService.updateBalance(accountUpdateServiceRequest);
		if (accountUpdateResponse.isSucceed()) {
			transactionHistoryService.register(toTransactionHistoryCreateRequest(accountUpdateOtherServiceRequest));
		}
		return accountUpdateResponse;
	}

	@Transactional(readOnly = true)
	public List<TransactionHistoryGetResponse> getAllTransactionHistories(
			TransactionHistoryGetFacadeRequest transactionHistoryGetFacadeRequest) {
		Long accountId = accountService.get(new AccountGetRequest(transactionHistoryGetFacadeRequest.memberId())).id();
		return transactionHistoryService.getAll(new TransactionHistoryGetServiceRequest(accountId));
	}
}
