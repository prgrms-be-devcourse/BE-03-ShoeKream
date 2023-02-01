package com.prgrms.kream.domain.style.dto.request;

public record GetFeedCommentServiceRequest(
		Long feedId,
		Long cursorId,
		Integer pageSize
) {
}
