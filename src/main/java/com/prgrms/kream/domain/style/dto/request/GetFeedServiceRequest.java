package com.prgrms.kream.domain.style.dto.request;

public record GetFeedServiceRequest(
		Long cursorId,
		Integer pageSize
) {
}
