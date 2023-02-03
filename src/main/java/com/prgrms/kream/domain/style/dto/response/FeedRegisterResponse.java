package com.prgrms.kream.domain.style.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("피드 등록 응답 정보")
public record FeedRegisterResponse(
		@ApiModelProperty(value = "피드 식별자", example = "1")
		Long id
) {
}
