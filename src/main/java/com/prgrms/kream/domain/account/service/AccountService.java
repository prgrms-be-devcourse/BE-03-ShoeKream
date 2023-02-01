package com.prgrms.kream.domain.account.service;

import static com.prgrms.kream.common.mapper.AccountMapper.*;
import com.prgrms.kream.common.exception.BalanceNotEnoughException;
import com.prgrms.kream.domain.account.dto.request.AccountCreateRequest;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateRequest;
import com.prgrms.kream.domain.account.dto.response.AccountCreateResponse;
import com.prgrms.kream.domain.account.dto.response.AccountUpdateResponse;
import com.prgrms.kream.domain.account.model.Account;
import com.prgrms.kream.domain.account.repository.AccountRepository;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository accountRepository;

	@Transactional
	public AccountCreateResponse register(AccountCreateRequest accountCreateRequest) {
		if (accountRepository.findByMemberId(accountCreateRequest.memberId()).isPresent()) {
			throw new EntityExistsException();
		}
		return toAccountCreateResponse(accountRepository.save(toAccount(accountCreateRequest)));
	}

	@Transactional
	public AccountUpdateResponse updateBalance(AccountUpdateRequest accountUpdateRequest){
		try{
			getAccountEntityByMemberId(accountUpdateRequest.memberId())
					.updateBalance(accountUpdateRequest.transactionType(), accountUpdateRequest.amount());
		}catch (EntityNotFoundException | BalanceNotEnoughException e){
			return new AccountUpdateResponse(false);
		}
		return new AccountUpdateResponse(true);
	}

	private Account getAccountEntityByMemberId(Long memberId){
		return accountRepository.findByMemberId(memberId).orElseThrow(EntityNotFoundException::new);
	}
	private Account getAccountEntityById(Long id){
		return accountRepository.findById(id).orElseThrow(EntityExistsException::new);
	}
}
