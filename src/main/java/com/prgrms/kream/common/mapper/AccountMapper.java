package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.account.dto.request.AccountCreateRequest;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateFacadeRequest;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateServiceRequest;
import com.prgrms.kream.domain.account.dto.request.TransactionHistoryCreateRequest;
import com.prgrms.kream.domain.account.dto.response.AccountCreateResponse;
import com.prgrms.kream.domain.account.dto.response.AccountGetResponse;
import com.prgrms.kream.domain.account.dto.response.TransactionHistoryCreateResponse;
import com.prgrms.kream.domain.account.model.Account;
import com.prgrms.kream.domain.account.model.TransactionHistory;

public class AccountMapper {
	public static AccountCreateResponse toAccountCreateResponse(Account account) {
		return new AccountCreateResponse(
				account.getId()
		);
	}

	public static Account toAccount(AccountCreateRequest accountCreateRequest) {
		return Account.builder()
				.id(accountCreateRequest.id())
				.memberId(accountCreateRequest.memberId())
				.balance(0)
				.build();
	}

	public static AccountGetResponse toAccountGetResponse(Account account){
		return new AccountGetResponse(
				account.getId(), account.getMemberId(), account.getBalance()
		);
	}

	public static AccountUpdateServiceRequest toAccountUpdateServiceRequest(AccountUpdateFacadeRequest accountUpdateFacadeRequest){
		return new AccountUpdateServiceRequest(
						accountUpdateFacadeRequest.memberId(),
						accountUpdateFacadeRequest.transactionType(),
						accountUpdateFacadeRequest.amount()
				);
	}

	public static TransactionHistoryCreateResponse toTransactionHistoryCreateResponse(
			TransactionHistory transactionHistory) {
		return new TransactionHistoryCreateResponse(
				transactionHistory.getId(), transactionHistory.getAccountId(), transactionHistory.getTransactionType(),
				transactionHistory.getAmount()
		);
	}

	public static TransactionHistory toTransactionHistory(TransactionHistoryCreateRequest transactionHistoryCreateRequest){
		return TransactionHistory.builder()
				.id(transactionHistoryCreateRequest.id())
				.accountId(transactionHistoryCreateRequest.accountId())
				.transactionType(transactionHistoryCreateRequest.transactionType())
				.amount(transactionHistoryCreateRequest.amount())
				.build();
	}

	public static TransactionHistoryCreateRequest toTransactionHistoryCreateRequest(AccountUpdateFacadeRequest accountUpdateFacadeRequest){
		return new TransactionHistoryCreateRequest(
				accountUpdateFacadeRequest.transactionHistoryId(),
				accountUpdateFacadeRequest.memberId(),
				accountUpdateFacadeRequest.transactionType(),
				accountUpdateFacadeRequest.amount()
		);
	}
}
