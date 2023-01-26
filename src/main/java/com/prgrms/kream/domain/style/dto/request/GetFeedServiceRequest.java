package com.prgrms.kream.domain.style.dto.request;

import java.util.List;

public record GetFeedServiceRequest(
		List<Long> ids
) {
}
