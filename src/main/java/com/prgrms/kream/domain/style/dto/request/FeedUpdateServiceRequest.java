package com.prgrms.kream.domain.style.dto.request;

import java.util.List;

public record FeedUpdateServiceRequest(
		String content,
		List<Long> productIds
) {
}
