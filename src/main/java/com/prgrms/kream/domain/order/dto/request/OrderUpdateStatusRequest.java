package com.prgrms.kream.domain.order.dto.request;

import com.prgrms.kream.domain.order.model.OrderStatus;
import javax.validation.constraints.NotNull;

public record OrderUpdateStatusRequest(
		@NotNull(message = "상태를 변경하고자 하는 주문의 ID 입력은 필수입니다")
		Long id,
		@NotNull(message = "변경하려는 상태는 필수 입니다")
		OrderStatus orderStatus
) {
}
