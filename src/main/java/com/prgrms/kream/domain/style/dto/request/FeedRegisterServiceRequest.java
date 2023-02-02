package com.prgrms.kream.domain.style.dto.request;

import java.util.List;

public record FeedRegisterServiceRequest(
		String content,
		Long authorId,
		List<Long> productsIds
) {
}
