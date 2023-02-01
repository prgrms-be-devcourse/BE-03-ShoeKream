package com.prgrms.kream.domain.style.dto.request;

public record FeedGetFacadeRequest(
		Long cursorId,
		Integer pageSize
) {
}
