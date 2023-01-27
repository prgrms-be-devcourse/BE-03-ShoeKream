package com.prgrms.kream.domain.style.dto.request;

import javax.validation.constraints.Positive;

public record GetFeedRequest(
		Long cursorId,

		@Positive(message = "pageSize는 양수여야 합니다.")
		Integer pageSize
) {
	public GetFeedRequest {
		if (pageSize == null) pageSize = 10;
	}
}
