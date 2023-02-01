package com.prgrms.kream.domain.order.dto.response;

import com.prgrms.kream.domain.order.model.OrderStatus;

public record OrderUpdateStatusResponse(
		OrderStatus orderStatus
) {
}
