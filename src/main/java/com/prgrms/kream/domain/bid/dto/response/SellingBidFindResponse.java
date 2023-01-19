package com.prgrms.kream.domain.bid.dto.response;

import java.time.LocalDateTime;

public record SellingBidFindResponse(
		Long id,
		Long memberId,
		Long productOptionId,
		int price,
		LocalDateTime validUntil
) {
}
