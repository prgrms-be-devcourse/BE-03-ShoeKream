package com.prgrms.kream.domain.style.dto.request;

public record FeedLikeServiceRequest(
		Long feedId,
		Long memberId
) {
}
