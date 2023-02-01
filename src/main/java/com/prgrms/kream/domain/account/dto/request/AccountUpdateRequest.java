package com.prgrms.kream.domain.account.dto.request;

import com.prgrms.kream.domain.account.model.TransactionType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record AccountUpdateRequest(
		@NotNull(message = "계좌 주인 ID는 필수 입력값입니다")
		Long memberId,

		@NotNull(message = "입출금 여부 입력은 필수입니다")
		TransactionType transactionType,

		@NotNull(message = "거래 금액 입력은 필수입니다")
		@Positive(message = "거래 금액은 양수여야합니다.")
		int amount
) {
}
