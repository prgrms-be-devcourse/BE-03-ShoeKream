package com.prgrms.kream.domain.style.dto.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;

public record UpdateFeedRequest(
		@NotEmpty(message = "피드 본문이 비어있을 수 없습니다.")
		String content,

		List<Long> productIds
) {
}
