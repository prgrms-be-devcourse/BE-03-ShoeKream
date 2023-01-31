package com.prgrms.kream.domain.style.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record GetFeedServiceResponse(
		Long id,
		Long authorId,
		String content,
		Long likes,
		List<Long> products,
		LocalDateTime createdAt,
		LocalDateTime updatedAt
) {
}
