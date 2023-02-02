package com.prgrms.kream.domain.style.dto.response;

import java.util.List;

public record FeedCommentGetFacadeResponses(
		List<FeedCommentGetFacadeResponse> feedCommentGetFacadeResponses,
		Long lastId
) {
}
