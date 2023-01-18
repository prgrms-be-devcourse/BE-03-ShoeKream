package com.prgrms.kream.style.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prgrms.kream.domain.member.model.Member;
import com.prgrms.kream.domain.style.dto.CreateFeedRequestOfService;
import com.prgrms.kream.domain.style.dto.FeedResponse;
import com.prgrms.kream.domain.style.dto.UpdateFeedRequestOfService;
import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.FeedTag;
import com.prgrms.kream.domain.style.repository.FeedRepository;
import com.prgrms.kream.domain.style.repository.FeedTagRepository;
import com.prgrms.kream.domain.style.service.StyleService;
import com.prgrms.kream.domain.style.service.TagExtractor;

@DisplayName("서비스 계층에서")
@ExtendWith(MockitoExtension.class)
class StyleServiceTest {

	@Mock
	private FeedRepository feedRepository;

	@Mock
	private FeedTagRepository feedTagRepository;

	@InjectMocks
	private StyleService styleService;

	private final Member MEMBER = Member.builder()
			.id(1L)
			.name("김창규")
			.build();

	private final Feed FEED = Feed.builder()
			.id(1L)
			.content("이 피드의 태그는 #총 #두개 입니다.")
			.author(MEMBER)
			.build();

	private final Set<FeedTag> FEED_TAGS = TagExtractor.extract(FEED);

	@Test
	@DisplayName("피드를 등록할 수 있다.")
	void testRegister() {
		when(feedRepository.save(any())).thenReturn(FEED);
		when(feedTagRepository.saveAll(any())).thenReturn(FEED_TAGS.stream().toList());
		FeedResponse feedResponse = styleService.register(getCreateRequest());

		assertThat(feedResponse.author()).isEqualTo(MEMBER.getName());
		assertThat(feedResponse.content()).isEqualTo(FEED.getContent());
	}

	@Test
	@DisplayName("피드 컨텐츠로부터 태그를 추출할 수 있다.")
	void testExtractTags() {
		// 주의 : FEED.getContent()의 값에 의존
		assertThat(FEED_TAGS)
				.extracting(FeedTag::getTag)
				.containsExactlyInAnyOrder("#총", "#두개");
	}

	@Test
	@DisplayName("피드를 수정할 수 있다.")
	void testUpdate() {
		when(feedRepository.save(any())).thenReturn(FEED);
		when(feedTagRepository.saveAll(any())).thenReturn(FEED_TAGS.stream().toList());
		FeedResponse feedResponse = styleService.register(getCreateRequest());
		when(feedRepository.findById(FEED.getId())).thenReturn(Optional.of(FEED));

		FeedResponse updatedFeedResponse = styleService.update(
				FEED.getId(),
				getUpdateRequest("이 피드의 태그는 총 #한개 입니다.")
		);

		assertThat(updatedFeedResponse.author()).isEqualTo(MEMBER.getName());
		assertThat(updatedFeedResponse.content()).isEqualTo(FEED.getContent());
		assertThat(updatedFeedResponse.author()).isEqualTo(feedResponse.author());
		assertThat(updatedFeedResponse.content()).isNotEqualTo(feedResponse.content());
	}

	private CreateFeedRequestOfService getCreateRequest() {
		return CreateFeedRequestOfService.builder()
				.content(FEED.getContent())
				.author(MEMBER)
				.build();
	}

	private UpdateFeedRequestOfService getUpdateRequest(String content) {
		return UpdateFeedRequestOfService.builder()
				.content(content)
				.build();
	}

}