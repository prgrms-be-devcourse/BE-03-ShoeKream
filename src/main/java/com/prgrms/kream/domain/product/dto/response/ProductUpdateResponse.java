package com.prgrms.kream.domain.product.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "상품 수정 응답 정보")
public record ProductUpdateResponse(
		@ApiModelProperty(value = "수정된 상품 아이디", example = "1")
		Long id) {
}
