package com.prgrms.kream.domain.style.dto.request;

public record GetFeedFacadeRequest(
		Long cursorId,
		Integer pageSize
) {
}
