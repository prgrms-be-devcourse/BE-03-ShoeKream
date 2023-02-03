package com.prgrms.kream.domain.account.dto.request;

import com.prgrms.kream.domain.account.model.TransactionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ApiModel("거래 내역 생성 요청")
public record TransactionHistoryCreateRequest(
		@ApiModelProperty(value = "거래 내역 id", required = true, example = "1")
		Long id,

		@ApiModelProperty(value = "거래 당사자 id", required = true, example = "2")
		@NotNull(message = "거래 당사자 ID는 필수 입력 값입니다.")
		Long accountId,

		@ApiModelProperty(value = "거래 종류", required = true, example = "DEPOSIT")
		@NotNull(message = "거래 종류는 필수 입력값입니다.")
		TransactionType transactionType,

		@ApiModelProperty(value = "거래 금액", required = true, example = "15000")
		@NotNull(message = "거래 금액은 필수 입력 값입니다")
		@Positive(message = "거래 금액은 양수여야 합니다")
		int amount
) {
}
