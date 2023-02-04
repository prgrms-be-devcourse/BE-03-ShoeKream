package com.prgrms.kream.domain.product.dto.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "상품 상세 조회 응답 정보")
public record ProductGetResponse(
		@ApiModelProperty(value = "상품 아이디", example = "1")
		Long id,

		@ApiModelProperty(value = "상품 이름", example = "Nike Dunk Low")
		String name,

		@ApiModelProperty(value = "상품 출시가격", example = "129000")
		int releasePrice,

		@ApiModelProperty(value = "상품 설명", example = "Retro Black")
		String description,

		@ApiModelProperty(value = "상품 부가정보(사이즈와 사이즈마다의 최고가, 최저가)")
		List<ProductOptionResponse> productOptionResponses,

		@ApiModelProperty(value = "상품 이미지 경로", example = "https://shoe-kream/sed5-2023-02-02.png")
		List<String> imagePaths) {
}
