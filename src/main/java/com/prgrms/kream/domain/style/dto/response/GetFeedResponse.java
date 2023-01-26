package com.prgrms.kream.domain.style.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record GetFeedResponse(
		Long id,
		Long authorId,
		String content,
		Long likes,
		List<String> images,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {
}
