package com.prgrms.kream.domain.product.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "상품 등록 응답 정보")
public record ProductRegisterResponse(
		@ApiModelProperty(value = "등록된 상품 아이디", example = "1")
		Long id) {
}
