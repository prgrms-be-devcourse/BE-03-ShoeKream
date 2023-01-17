package com.prgrms.kream.domain.bid.dto;

import java.time.LocalDateTime;

public record SellingBidCreateRequest(
		Long memberId,
		Long productOptionId,
		int price,
		LocalDateTime validUntil
) {
}
