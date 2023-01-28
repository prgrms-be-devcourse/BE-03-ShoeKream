package com.prgrms.kream.domain.style.dto.request;

import java.util.List;

public record UpdateFeedFacadeRequest(
		String content,
		List<Long> productIds
) {
}
