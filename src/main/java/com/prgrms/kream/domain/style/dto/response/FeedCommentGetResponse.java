package com.prgrms.kream.domain.style.dto.response;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("피드 댓글 응답 개별 정보")
public record FeedCommentGetResponse(
		@ApiModelProperty(value = "댓글 식별자", example = "1")
		Long id,

		@ApiModelProperty(value = "사용자 식별자", example = "1")
		Long memberId,

		@ApiModelProperty(value = "본문", example = "댓글 본문")
		String content,

		@ApiModelProperty(value = "생성시각", example = "2023-02-03T07:40:26.416Z")
		LocalDateTime createdAt,

		@ApiModelProperty(value = "수정시각", example = "2023-02-03T07:40:26.416Z")
		LocalDateTime updatedAt
) {
}
