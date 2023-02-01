package com.prgrms.kream.domain.order.dto.response;

import com.prgrms.kream.domain.order.model.OrderStatus;

public record OrderFindResponse(
		Long id,
		Long bidId,
		boolean isBasedOnSellingBid,
		Long buyerId,
		Long sellerId,
		Long productOptionId,
		int price,
		OrderStatus orderStatus,
		String orderRequest
) {
}
