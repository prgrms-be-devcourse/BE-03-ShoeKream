package com.prgrms.kream.domain.bid.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public record BuyingBidCreateRequest(
		@NotNull(message = "ID는 필수적으로 있어야 합니다")
		Long id,

		@NotNull(message = "구매 입찰을 올린 사람의 ID는 필수적으로 필요합니다")
		Long memberId,

		@NotNull(message = "상품의 사이즈는 필수 입력값입니다")
		Long productOptionId,

		@NotNull(message = "상품의 가격은 필수 입력값입니다")
		@Positive(message = "가격은 0월 보다 많아야 합니다")
		int price,
		@NotNull(message = "구매 입찰의 유효 기간을 정확하게 입력해주세요")
		LocalDateTime validUntil
) {
}
