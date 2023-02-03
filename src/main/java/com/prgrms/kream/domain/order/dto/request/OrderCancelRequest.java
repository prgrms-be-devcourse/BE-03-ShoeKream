package com.prgrms.kream.domain.order.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("주문 취소 요청")
public record OrderCancelRequest(
		@ApiModelProperty(value = "주문 id", required = true, example = "1")
		Long id
) {
}
