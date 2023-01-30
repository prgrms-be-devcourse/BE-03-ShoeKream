package com.prgrms.kream.domain.member.dto.response;

import lombok.Builder;

@Builder
public record DeliveryInfoGetResponse(
		Long id,
		String name,
		String phone,
		String postCode,
		String address,
		String detail
) {

}
