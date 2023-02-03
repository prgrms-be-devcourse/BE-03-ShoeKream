package com.prgrms.kream.domain.style.dto.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("피드 댓글 응답 통합 정보")
public record FeedCommentGetResponses(
		@ApiModelProperty(value = "피드 댓글 응답 개별 정보")
		List<FeedCommentGetResponse> feedCommentGetResponses,

		@ApiModelProperty(value = "커서 식별자", example = "1")
		Long lastId
) {
}
