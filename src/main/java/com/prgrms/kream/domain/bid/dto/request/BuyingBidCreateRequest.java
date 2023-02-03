package com.prgrms.kream.domain.bid.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ApiModel("구매 입찰 생성 요청")
public record BuyingBidCreateRequest(
		@ApiModelProperty(value = "구매 입찰 id", example = "1")
		@NotNull(message = "ID는 필수적으로 있어야 합니다")
		Long id,

		@ApiModelProperty(value = "구매 입찰 생성 요청자 id", example = "2")
		@NotNull(message = "구매 입찰을 올린 사람의 ID는 필수적으로 필요합니다")
		Long memberId,

		@ApiModelProperty(value = "상품 사이즈 id", example = "3")
		@NotNull(message = "상품의 사이즈는 필수 입력값입니다")
		Long productOptionId,

		@ApiModelProperty(value = "가격", example = "45000")
		@NotNull(message = "상품의 가격은 필수 입력값입니다")
		@Positive(message = "가격은 0월 보다 많아야 합니다")
		int price,
		@ApiModelProperty(value = "구매 입찰 유효 기간", required = true, example = "2023-02-03T00:43:45.494741")
		@NotNull(message = "구매 입찰의 유효 기간을 정확하게 입력해주세요")
		LocalDateTime validUntil
) {
}
