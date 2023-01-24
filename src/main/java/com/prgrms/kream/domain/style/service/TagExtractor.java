package com.prgrms.kream.domain.style.service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.FeedTag;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TagExtractor {

	private static final Pattern pattern = Pattern.compile("#([a-zA-Zㄱ-힣0-9])+");

	public static Set<FeedTag> extract(Feed feed) {
		// 태그 추출
		Matcher matcher = pattern.matcher(feed.getContent());
		Set<String> tags = new HashSet<>();
		while (matcher.find()) {
			tags.add(matcher.group().replace("#", ""));
		}

		// 태그 변환
		return tags.stream()
				.map(tag -> FeedTag.builder()
						.feedId(feed.getId())
						.tag(tag)
						.build())
				.collect(Collectors.toUnmodifiableSet());
	}

}
