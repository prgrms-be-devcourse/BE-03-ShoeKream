package com.prgrms.kream.domain.style.dto;

import lombok.Builder;

public record UpdateFeedRequestOfService(
		String content
) {
	@Builder
	public UpdateFeedRequestOfService(String content) {
		this.content = content;
	}
}
