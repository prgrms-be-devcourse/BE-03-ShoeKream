package com.prgrms.kream.domain.account.service;

import static com.prgrms.kream.common.mapper.AccountMapper.*;
import com.prgrms.kream.domain.account.dto.request.AccountCreateRequest;
import com.prgrms.kream.domain.account.dto.response.AccountCreateResponse;
import com.prgrms.kream.domain.account.repository.AccountRepository;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository accountRepository;

	@Transactional
	public AccountCreateResponse register(AccountCreateRequest accountCreateRequest) {
		if (accountRepository.findByMemberId(accountCreateRequest.memberId()) != null) {
			throw new EntityExistsException();
		}
		return toAccountCreateResponse(accountRepository.save(toAccount(accountCreateRequest)));
	}
}
