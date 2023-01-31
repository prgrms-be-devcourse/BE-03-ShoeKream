package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record DeliveryInfoDeleteRequest(
		@NotNull
		@Positive(message = "deliveryInfoId는 자연수여야 합니다.")
		Long deliveryInfoId
) {
}
