package com.prgrms.kream.domain.style.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("피드 응답 개별 정보")
public record FeedGetResponse(
		@ApiModelProperty(value = "피드 식별자", example = "1")
		Long id,

		@ApiModelProperty(value = "작성자 식별자", example = "1")
		Long authorId,

		@ApiModelProperty(value = "본문", example = "피드 본문")
		String content,

		@ApiModelProperty(value = "좋아요", example = "100")
		Long likes,

		@ApiModelProperty(value = "상품 태그", example = "[1, 2, 3]")
		List<Long> products,

		@ApiModelProperty(value = "피드 이미지 경로", example = "image.png")
		List<String> images,

		@ApiModelProperty(value = "생성시각", example = "2023-02-03T07:40:26.416Z")
		LocalDateTime createdAt,

		@ApiModelProperty(value = "수정시각", example = "2023-02-03T07:40:26.416Z")
		LocalDateTime updatedAt
) {
}
