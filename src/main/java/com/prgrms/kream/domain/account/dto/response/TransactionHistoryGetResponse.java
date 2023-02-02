package com.prgrms.kream.domain.account.dto.response;

import com.prgrms.kream.domain.account.model.TransactionType;

public record TransactionHistoryGetResponse(
		Long id,
		Long memberId,
		TransactionType transactionType,
		int amount
) {
}
