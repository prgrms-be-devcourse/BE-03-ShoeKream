package com.prgrms.kream.domain.style.dto.response;

import java.util.List;

public record FeedGetFacadeResponses(
		List<FeedGetFacadeResponse> feedGetFacadeResponses,
		Long lastId
) {
}
