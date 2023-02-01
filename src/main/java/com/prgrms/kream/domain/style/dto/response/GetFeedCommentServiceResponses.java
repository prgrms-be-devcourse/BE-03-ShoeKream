package com.prgrms.kream.domain.style.dto.response;

import java.util.List;

public record GetFeedCommentServiceResponses(
		List<GetFeedCommentServiceResponse> getFeedCommentServiceResponses,
		Long lastId
) {
}
