package com.prgrms.kream.domain.style.dto.request;

public record FeedCommentRegisterFacadeRequest(
		String content,
		Long memberId,
		Long feedId
) {
}
