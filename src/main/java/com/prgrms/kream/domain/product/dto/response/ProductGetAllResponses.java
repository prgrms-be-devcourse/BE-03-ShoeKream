package com.prgrms.kream.domain.product.dto.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "상품 목록 조회 응답 정보")
public record ProductGetAllResponses(
		@ApiModelProperty(value = "상품 목록")
		List<ProductGetAllResponse> productGetAllResponses,

		@ApiModelProperty(value = "상품 목록 중 마지막 상품 아이디", example = "1")
		Long lastId) {
}
