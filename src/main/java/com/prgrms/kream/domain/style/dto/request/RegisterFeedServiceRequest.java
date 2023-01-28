package com.prgrms.kream.domain.style.dto.request;

import java.util.List;

public record RegisterFeedServiceRequest(
		String content,
		Long authorId,
		List<Long> productsIds
) {
}
