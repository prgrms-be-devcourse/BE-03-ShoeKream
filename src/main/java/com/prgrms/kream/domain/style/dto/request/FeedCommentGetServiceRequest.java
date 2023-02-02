package com.prgrms.kream.domain.style.dto.request;

public record FeedCommentGetServiceRequest(
		Long feedId,
		Long cursorId,
		Integer pageSize
) {
}
