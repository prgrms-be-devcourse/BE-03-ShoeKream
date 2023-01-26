package com.prgrms.kream.domain.style.dto.response;

import java.time.LocalDateTime;

public record GetFeedServiceResponse(
		Long id,
		Long authorId,
		String content,
		Long likes,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {
}
