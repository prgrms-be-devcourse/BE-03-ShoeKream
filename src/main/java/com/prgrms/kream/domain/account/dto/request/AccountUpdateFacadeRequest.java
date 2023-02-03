package com.prgrms.kream.domain.account.dto.request;

import com.prgrms.kream.domain.account.model.TransactionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ApiModel("잔액 수정 요청")
public record AccountUpdateFacadeRequest(
		@ApiModelProperty(value = "계좌 주인 id", required = true, example = "1")
		@NotNull(message = "계좌 주인 ID는 필수 입력값입니다")
		Long memberId,

		@ApiModelProperty(value = "거래 내역 id", required = true, example = "2")
		@NotNull(message = "거래 내역 ID는 필수 입력값입니다")
		Long transactionHistoryId,

		@ApiModelProperty(value = "입출금 여부", required = true, example = "WITHDRAW")
		@NotNull(message = "입출금 여부 입력은 필수입니다")
		TransactionType transactionType,

		@ApiModelProperty(value = "거래 금액", required = true, example = "25000")
		@NotNull(message = "거래 금액 입력은 필수입니다")
		@Positive(message = "거래 금액은 양수여야합니다.")
		int amount
) {
}
