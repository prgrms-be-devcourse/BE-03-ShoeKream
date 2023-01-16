package com.prgrms.kream.domain.style.dto;

import lombok.Builder;

public record FeedResponse(
		Long id,
		String content,
		String author
) {
	@Builder
	public FeedResponse(Long id, String content, String author) {
		this.id = id;
		this.content = content;
		this.author = author;
	}
}
