package com.prgrms.kream.domain.style.dto.request;

import com.prgrms.kream.domain.member.model.Member;

public record RegisterFeedServiceRequest(
		String content,
		Member author
) {
}
