package com.prgrms.kream.domain.account.dto.request;

import com.prgrms.kream.domain.account.model.TransactionType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record TransactionHistoryCreateRequest(
		// @NotNull(message = "거래 내역 ID는 필수 입력 값입니다.")
		Long id,

		@NotNull(message = "거래 당사자 ID는 필수 입력 값입니다.")
		Long accountId,
		@NotNull(message = "거래 종류는 필수 입력값입니다.")
		TransactionType transactionType,
		@NotNull(message = "거래 금액은 필수 입력 값입니다")
		@Positive(message = "거래 금액은 양수여야 합니다")
		int amount
) {
}
