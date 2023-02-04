package com.prgrms.kream.domain.product.dto.request;

import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "상품 목록 조회 요청 정보")
public record ProductGetAllRequest(
		@ApiModelProperty(value = "커서 아이디", required = false, example = "5")
		Long cursorId,

		@Positive(message = "page size는 0 또는 음수일 수 없습니다.")
		@ApiModelProperty(value = "페이지 사이즈", required = true, example = "5")
		int pageSize,

		@ApiModelProperty(value = "검색어", required = false, example = "Ni")
		String searchWord) {
}
