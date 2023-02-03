package com.prgrms.kream.domain.order.dto.request;

import com.prgrms.kream.domain.order.model.OrderStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

@ApiModel("주문 상태 변경 요청")
public record OrderUpdateStatusRequest(
		@ApiModelProperty(value = "주문 id", example = "1")
		@NotNull(message = "상태를 변경하고자 하는 주문의 ID 입력은 필수입니다")
		Long id,
		@ApiModelProperty(value = "변경될 주문 상태", example = "ORDER_CONFIRMED")
		@NotNull(message = "변경하려는 상태는 필수 입니다")
		OrderStatus orderStatus
) {
}
