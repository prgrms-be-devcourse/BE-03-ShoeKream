package com.prgrms.kream.domain.style.dto.request;

public record FeedCommentGetFacadeRequest(
		Long feedId,
		Long cursorId,
		Integer pageSize
) {
}
