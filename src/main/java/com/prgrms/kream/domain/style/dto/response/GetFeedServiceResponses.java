package com.prgrms.kream.domain.style.dto.response;

import java.util.List;

public record GetFeedServiceResponses(
		List<GetFeedServiceResponse> getFeedServiceResponses,
		Long lastId
) {
}
