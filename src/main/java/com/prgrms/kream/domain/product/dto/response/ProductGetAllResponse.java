package com.prgrms.kream.domain.product.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "상품 목록 조회 응답 중 하나의 상품 정보")
public record ProductGetAllResponse(
		@ApiModelProperty(value = "상품 아이디", example = "1")
		Long id,

		@ApiModelProperty(value = "상품 이름", example = "Nike Dunk Low")
		String name,

		@ApiModelProperty(value = "상품 출시가격", example = "129000")
		int releasePrice,

		@ApiModelProperty(value = "상품 설명", example = "Retro Black")
		String description) {
}
