package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.account.dto.request.AccountCreateRequest;
import com.prgrms.kream.domain.account.dto.response.AccountCreateResponse;
import com.prgrms.kream.domain.account.model.Account;

public class AccountMapper {
	public static AccountCreateResponse toAccountCreateResponse(Account account) {
		return new AccountCreateResponse(
				account.getId()
		);
	}

	public static Account toAccount(AccountCreateRequest accountCreateRequest){
		return Account.builder()
				.id(accountCreateRequest.id())
				.memberId(accountCreateRequest.memberId())
				.balance(0)
				.build();
	}
}
