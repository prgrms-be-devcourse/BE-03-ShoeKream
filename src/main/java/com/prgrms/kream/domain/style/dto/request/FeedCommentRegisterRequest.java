package com.prgrms.kream.domain.style.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("댓글 등록 요청 정보")
public record FeedCommentRegisterRequest(
		@NotEmpty(message = "댓글은 공백일 수 없습니다.")
		@ApiModelProperty(value = "본문", required = true, example = "댓글 본문에 들어갈 내용")
		String content,

		@NotNull(message = "작성자 식별값은 비어있을 수 없습니다.")
		@Positive(message = "작성자 식별값이 올바르지 않습니다.")
		@ApiModelProperty(value = "사용자 식별자", required = true, example = "1")
		Long memberId
) {
}
