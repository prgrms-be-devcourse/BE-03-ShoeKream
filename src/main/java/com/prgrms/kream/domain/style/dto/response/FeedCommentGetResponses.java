package com.prgrms.kream.domain.style.dto.response;

import java.util.List;

public record FeedCommentGetResponses(
		List<FeedCommentGetResponse> feedCommentGetResponses,
		Long lastId
) {
}
