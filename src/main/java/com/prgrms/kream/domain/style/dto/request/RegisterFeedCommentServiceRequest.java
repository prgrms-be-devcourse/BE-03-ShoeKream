package com.prgrms.kream.domain.style.dto.request;

public record RegisterFeedCommentServiceRequest(
		String content,
		Long memberId,
		Long feedId
) {
}
