package com.prgrms.kream.domain.order.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("주문 조회 요청")
public record OrderFindRequest(
		@ApiModelProperty(value = "주문 id", example = "1")
		Long id
) {
}
