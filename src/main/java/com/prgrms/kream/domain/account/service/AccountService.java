package com.prgrms.kream.domain.account.service;

import static com.prgrms.kream.common.mapper.AccountMapper.*;
import com.prgrms.kream.common.exception.BalanceNotEnoughException;
import com.prgrms.kream.domain.account.dto.request.AccountCreateRequest;
import com.prgrms.kream.domain.account.dto.request.AccountGetRequest;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateServiceRequest;
import com.prgrms.kream.domain.account.dto.response.AccountCreateResponse;
import com.prgrms.kream.domain.account.dto.response.AccountGetResponse;
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
	public AccountCreateResponse registerAccount(AccountCreateRequest accountCreateRequest) {
		if (accountRepository.findByMemberId(accountCreateRequest.memberId()).isPresent()) {
			throw new EntityExistsException("이미 계좌가 존재하는 회원입니다");
		}
		return toAccountCreateResponse(accountRepository.save(toAccount(accountCreateRequest)));
	}

	@Transactional
	public AccountUpdateResponse updateBalance(AccountUpdateServiceRequest accountUpdateServiceRequest) {
		try {
			getAccountEntityByMemberId(accountUpdateServiceRequest.memberId())
					.updateBalance(accountUpdateServiceRequest.transactionType(), accountUpdateServiceRequest.amount());
		} catch (EntityNotFoundException | BalanceNotEnoughException e) {
			return new AccountUpdateResponse(false);
		}
		return new AccountUpdateResponse(true);
	}

	@Transactional(readOnly = true)
	public AccountGetResponse getAccount(AccountGetRequest accountGetRequest) {
		return toAccountGetResponse(
				accountRepository.findByMemberId(accountGetRequest.memberId())
						.orElseThrow(() -> new EntityNotFoundException("계좌가 존재하지 않습니다")));
	}

	private Account getAccountEntityByMemberId(Long memberId) {
		return accountRepository.findByMemberId(memberId).orElseThrow(() -> new EntityNotFoundException("계좌가 존재하지 않습니다"));
	}

	private Account getAccountEntityById(Long id) {
		return accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("계좌가 존재하지 않습니다"));
	}
}
