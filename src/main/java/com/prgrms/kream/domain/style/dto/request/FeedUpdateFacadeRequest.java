package com.prgrms.kream.domain.style.dto.request;

import java.util.List;

public record FeedUpdateFacadeRequest(
		String content,
		List<Long> productIds
) {
}
