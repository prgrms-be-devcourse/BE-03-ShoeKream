package com.prgrms.kream.domain.account.dto.response;

import com.prgrms.kream.domain.account.model.TransactionType;

public record TransactionHistoryCreateResponse(
		Long id,
		Long accountId,
		TransactionType transactionType,
		int amount
) {
}
