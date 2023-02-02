package com.prgrms.kream.domain.style.dto.response;

import java.util.List;

public record FeedGetServiceResponses(
		List<FeedGetServiceResponse> feedGetServiceResponses,
		Long lastId
) {
}
