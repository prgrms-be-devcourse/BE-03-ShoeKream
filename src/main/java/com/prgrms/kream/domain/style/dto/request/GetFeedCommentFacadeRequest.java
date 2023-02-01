package com.prgrms.kream.domain.style.dto.request;

public record GetFeedCommentFacadeRequest(
		Long feedId,
		Long cursorId,
		Integer pageSize
) {
}
