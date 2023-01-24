package com.prgrms.kream.domain.style.dto.request;

public record RegisterFeedServiceRequest(
		String content,
		Long authorId
) {
}
