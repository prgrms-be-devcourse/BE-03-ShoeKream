package com.prgrms.kream.domain.bid.dto;

import java.time.LocalDateTime;

public record SellingBidDto(
		Long id,
		Long memberId,
		Long productOptionId,
		int price,
		LocalDateTime validUntil
) {
}
