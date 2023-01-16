package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.style.dto.CreateFeedRequestOfService;
import com.prgrms.kream.domain.style.dto.FeedResponse;
import com.prgrms.kream.domain.style.model.Feed;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StyleMapper {

	public static Feed toEntity(CreateFeedRequestOfService request) {
		return Feed.builder()
				.content(request.content())
				.author(request.author())
				.build();
	}

	public static FeedResponse toDto(Feed feed) {
		return FeedResponse.builder()
				.id(feed.getId())
				.content(feed.getContent())
				.author(feed.getAuthor().getName())
				.build();
	}

}
