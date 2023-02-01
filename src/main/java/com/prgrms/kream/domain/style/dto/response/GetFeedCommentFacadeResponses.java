package com.prgrms.kream.domain.style.dto.response;

import java.util.List;

public record GetFeedCommentFacadeResponses(
		List<GetFeedCommentFacadeResponse> getFeedCommentFacadeResponses,
		Long lastId
) {
}
