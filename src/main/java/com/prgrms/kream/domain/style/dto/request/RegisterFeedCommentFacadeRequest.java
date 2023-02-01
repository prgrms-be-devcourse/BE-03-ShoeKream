package com.prgrms.kream.domain.style.dto.request;

public record RegisterFeedCommentFacadeRequest(
		String content,
		Long memberId,
		Long feedId
) {
}
