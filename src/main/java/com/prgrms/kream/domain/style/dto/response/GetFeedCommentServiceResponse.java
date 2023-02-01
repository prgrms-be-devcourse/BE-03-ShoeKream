package com.prgrms.kream.domain.style.dto.response;

import java.time.LocalDateTime;

public record GetFeedCommentServiceResponse(
		Long id,
		Long memberId,
		String content,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {
}
