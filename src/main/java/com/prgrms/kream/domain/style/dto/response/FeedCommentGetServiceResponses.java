package com.prgrms.kream.domain.style.dto.response;

import java.util.List;

public record FeedCommentGetServiceResponses(
		List<FeedCommentGetServiceResponse> feedCommentGetServiceResponses,
		Long lastId
) {
}
