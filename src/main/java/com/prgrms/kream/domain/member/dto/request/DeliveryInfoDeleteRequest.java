package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;

public record DeliveryInfoDeleteRequest(
		@ApiModelProperty(value = "배송정보 아이디", required = true, example = "1")
		@NotNull(message = "deliveryInfo는 빈값일 수 없습니다.")
		@Positive(message = "deliveryInfoId는 자연수여야 합니다.")
		Long deliveryInfoId
) {
}
