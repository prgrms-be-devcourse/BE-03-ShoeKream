package com.prgrms.kream.domain.style.dto;

import com.prgrms.kream.domain.member.model.Member;

import lombok.Builder;

public record CreateFeedRequestOfService(
		String content,
		Member author
) {
	@Builder
	public CreateFeedRequestOfService(String content, Member author) {
		this.content = content;
		this.author = author;
	}
}
