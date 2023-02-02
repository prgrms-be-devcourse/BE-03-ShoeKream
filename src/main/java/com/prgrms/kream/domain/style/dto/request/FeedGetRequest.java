package com.prgrms.kream.domain.style.dto.request;

import javax.validation.constraints.Positive;

public record FeedGetRequest(
		Long cursorId,

		@Positive(message = "pageSize는 양수여야 합니다.")
		Integer pageSize,

		SortType sortType
) {
	public FeedGetRequest {
		if (pageSize == null || pageSize < 0) pageSize = 10;
		if (sortType == null) sortType = SortType.POPULAR;
	}
}
