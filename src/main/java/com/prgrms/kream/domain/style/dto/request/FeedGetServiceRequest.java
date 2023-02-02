package com.prgrms.kream.domain.style.dto.request;

public record FeedGetServiceRequest(
		Long cursorId,
		Integer pageSize,
		SortType sortType
) {
}
