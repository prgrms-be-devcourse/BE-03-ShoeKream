package com.prgrms.kream.domain.style.dto.response;

import java.util.List;

public record GetFeedResponses(
		List<GetFeedResponse> getFeedResponses,
		Long lastId
) {
}
