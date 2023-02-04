package com.prgrms.kream.domain.product.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "상품 부가정보(사이즈와 사이즈마다의 최고가, 최저가)")
public record ProductOptionResponse(
		@ApiModelProperty(value = "상품 옵션 아이디", example = "1")
		Long id,

		@ApiModelProperty(value = "상품 사이즈", example = "230")
		int size,

		@ApiModelProperty(value = "해당 상품에 대해 등록된 구매 입찰 최고가", example = "140000")
		int highestPrice,

		@ApiModelProperty(value = "해당 상품에 대해 등록된 판매 입찰 최저가", example = "160000")
		int lowestPrice) {
}
