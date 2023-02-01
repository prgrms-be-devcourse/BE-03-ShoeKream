package com.prgrms.kream.domain.style.dto.request;

import javax.validation.constraints.Positive;

public record GetFeedCommentRequest(
		Long cursorId,

		@Positive(message = "pageSize는 양수여야 합니다.")
		Integer pageSize
) {
	public GetFeedCommentRequest {
		if (pageSize == null || pageSize < 0) pageSize = 10;
	}
}
