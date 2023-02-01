package com.prgrms.kream.domain.style.dto.response;

import java.util.List;

public record GetFeedCommentResponses(
		List<GetFeedCommentResponse> getFeedCommentResponses,
		Long lastId
) {
}
