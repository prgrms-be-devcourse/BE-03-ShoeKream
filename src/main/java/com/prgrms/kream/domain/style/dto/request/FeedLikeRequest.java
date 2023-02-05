package com.prgrms.kream.domain.style.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("좋아요 요청 정보")
public record FeedLikeRequest(
		@NotNull(message = "사용자 식별값은 비어있을 수 없습니다.")
		@Positive(message = "사용자 식별값이 올바르지 않습니다.")
		@ApiModelProperty(value = "사용자 식별자", required = true, example = "1")
		Long memberId
) {
}
