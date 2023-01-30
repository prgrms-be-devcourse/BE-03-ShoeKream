package com.prgrms.kream.domain.style.dto.request;

import java.util.List;

public record UpdateFeedServiceRequest(
		String content,
		List<Long> productIds
) {
}
