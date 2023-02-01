package com.prgrms.kream.domain.style.dto.response;

import java.util.List;

public record FeedGetResponses(
		List<FeedGetResponse> feedGetResponses,
		Long lastId
) {
}
