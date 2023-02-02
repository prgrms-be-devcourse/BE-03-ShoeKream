package com.prgrms.kream.domain.style.dto.request;

public record FeedCommentRegisterServiceRequest(
		String content,
		Long memberId,
		Long feedId
) {
}
