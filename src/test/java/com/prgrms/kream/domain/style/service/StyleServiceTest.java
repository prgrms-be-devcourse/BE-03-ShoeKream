package com.prgrms.kream.domain.style.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prgrms.kream.domain.member.model.Authority;
import com.prgrms.kream.domain.member.model.Member;
import com.prgrms.kream.domain.style.dto.request.FeedCommentGetServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedGetServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedLikeServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedCommentRegisterServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedRegisterServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedUpdateServiceRequest;
import com.prgrms.kream.domain.style.dto.request.SortType;
import com.prgrms.kream.domain.style.dto.response.FeedGetServiceResponses;
import com.prgrms.kream.domain.style.dto.response.FeedRegisterServiceResponse;
import com.prgrms.kream.domain.style.dto.response.FeedUpdateServiceResponse;
import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.FeedComment;
import com.prgrms.kream.domain.style.model.FeedLike;
import com.prgrms.kream.domain.style.model.FeedProduct;
import com.prgrms.kream.domain.style.model.FeedTag;
import com.prgrms.kream.domain.style.repository.FeedCommentRepository;
import com.prgrms.kream.domain.style.repository.FeedLikeRepository;
import com.prgrms.kream.domain.style.repository.FeedProductRepository;
import com.prgrms.kream.domain.style.repository.FeedRepository;
import com.prgrms.kream.domain.style.repository.FeedTagRepository;

@DisplayName("서비스 계층에서")
@ExtendWith(MockitoExtension.class)
class StyleServiceTest {

	@Mock
	private FeedRepository feedRepository;

	@Mock
	private FeedTagRepository feedTagRepository;

	@Mock
	private FeedLikeRepository feedLikeRepository;

	@Mock
	private FeedProductRepository feedProductRepository;

	@Mock
	private FeedCommentRepository feedCommentRepository;

	@InjectMocks
	private StyleService styleService;

	private static final Member MEMBER = Member.builder()
			.id(1L)
			.email("kimc980106@naver.com")
			.phone("01086107463")
			.password("qwer1234!")
			.isMale(true)
			.authority(Authority.ROLE_ADMIN)
			.build();

	private static final Feed FEED = Feed.builder()
			.id(1L)
			.content("이 피드의 태그는 #총 #두개 입니다.")
			.authorId(MEMBER.getId())
			.likes(0L)
			.build();

	private static final Set<FeedTag> FEED_TAGS = TagExtractor.extract(FEED);

	private static final FeedLike FEED_LIKE = FeedLike.builder()
			.id(1L)
			.feedId(FEED.getId())
			.memberId(MEMBER.getId())
			.build();

	private static final List<FeedProduct> FEED_PRODUCTS = List.of(
			FeedProduct.builder()
					.id(1L)
					.feedId(FEED.getId())
					.productId(1L)
					.build(),
			FeedProduct.builder()
					.id(2L)
					.feedId(FEED.getId())
					.productId(2L)
					.build()
	);

	private static final FeedComment FEED_COMMENT = FeedComment.builder()
			.feedId(FEED.getId())
			.memberId(MEMBER.getId())
			.content(FEED.getId() + "번 피드의 댓글입니다.")
			.build();

	@Test
	@DisplayName("피드를 등록할 수 있다.")
	void testRegister() {
		when(feedRepository.save(any(Feed.class))).thenReturn(FEED);
		when(feedTagRepository.saveAllBulk(Mockito.<FeedTag>anyList()))
				.thenReturn(FEED_TAGS.stream().map(FeedTag::getId).toList());
		when(feedProductRepository.saveAllBulk(Mockito.<FeedProduct>anyList()))
				.thenReturn(FEED_PRODUCTS.stream().map(FeedProduct::getId).toList());

		FeedRegisterServiceResponse feedResponse = styleService.registerFeed(getRegisterFeedServiceRequest());

		verify(feedRepository).save(any(Feed.class));
		verify(feedTagRepository).saveAllBulk(Mockito.<FeedTag>anyList());
		verify(feedProductRepository).saveAllBulk(Mockito.<FeedProduct>anyList());
		assertThat(feedResponse.id()).isEqualTo(1L);
	}

	@Test
	@DisplayName("피드 컨텐츠로부터 태그를 추출할 수 있다.")
	void testExtractTags() {
		// 주의 : FEED.getContent()의 값에 의존
		assertThat(FEED_TAGS)
				.extracting(FeedTag::getTag)
				.containsExactlyInAnyOrder("총", "두개");
	}

	@Test
	@DisplayName("피드를 수정할 수 있다.")
	void testUpdate() {
		when(feedRepository.save(any(Feed.class))).thenReturn(FEED);
		when(feedTagRepository.saveAllBulk(Mockito.<FeedTag>anyList()))
				.thenReturn(FEED_TAGS.stream().map(FeedTag::getId).toList());
		when(feedProductRepository.saveAllBulk(Mockito.<FeedProduct>anyList()))
				.thenReturn(FEED_PRODUCTS.stream().map(FeedProduct::getId).toList());
		when(feedRepository.findById(FEED.getId())).thenReturn(Optional.of(FEED));

		FeedUpdateServiceResponse feedUpdateServiceResponse =
				styleService.updateFeed(
						FEED.getId(),
						getUpdateFeedServiceRequest("이 피드의 태그는 총 #한개 입니다.")
				);

		verify(feedRepository).findById(FEED.getId());
		verify(feedRepository).save(any(Feed.class));
		verify(feedTagRepository).deleteAllByFeedId(FEED.getId());
		verify(feedTagRepository).saveAllBulk(Mockito.<FeedTag>anyList());
		verify(feedProductRepository).deleteAllByFeedId(FEED.getId());
		verify(feedProductRepository).saveAllBulk(Mockito.<FeedProduct>anyList());
		assertThat(feedUpdateServiceResponse.id()).isEqualTo(FEED.getId());
	}

	@Test
	@DisplayName("피드에 사용자의 좋아요를 등록할 수 있다.")
	void testLikeFeed() {
		when(feedLikeRepository.existsByFeedIdAndMemberId(FEED.getId(), MEMBER.getId())).thenReturn(false);
		when(feedRepository.findById(FEED.getId())).thenReturn(Optional.of(FEED));
		when(feedRepository.save(FEED)).thenReturn(FEED);
		when(feedLikeRepository.save(any())).thenReturn(FEED_LIKE);

		styleService.registerFeedLike(getLikeFeedServiceRequest());

		verify(feedLikeRepository).existsByFeedIdAndMemberId(FEED.getId(), MEMBER.getId());
		verify(feedLikeRepository).save(any());
		assertThat(FEED.getLikes()).isEqualTo(1L);
	}

	@Test
	@DisplayName("피드에 사용자의 좋아요를 취소할 수 있다.")
	void testUnlikeFeed() {
		when(feedLikeRepository.existsByFeedIdAndMemberId(FEED.getId(), MEMBER.getId())).thenReturn(true);
		when(feedRepository.findById(FEED.getId())).thenReturn(Optional.of(FEED));
		when(feedRepository.save(FEED)).thenReturn(FEED);
		doNothing().when(feedLikeRepository).deleteByFeedIdAndMemberId(FEED.getId(), MEMBER.getId());

		styleService.deleteFeedLike(getLikeFeedServiceRequest());

		verify(feedLikeRepository).existsByFeedIdAndMemberId(FEED.getId(), MEMBER.getId());
		verify(feedLikeRepository).deleteByFeedIdAndMemberId(FEED.getId(), MEMBER.getId());
		assertThat(FEED.getLikes()).isEqualTo(0L);
	}

	@Test
	@DisplayName("피드의 사용자 댓글을 등록할 수 있다.")
	void testRegisterFeedComment() {
		when(feedRepository.existsById(FEED.getId())).thenReturn(true);
		when(feedCommentRepository.save(any(FeedComment.class))).thenReturn(FEED_COMMENT);

		styleService.registerFeedComment(getRegisterFeedCommentServiceRequest());

		verify(feedRepository).existsById(FEED.getId());
		verify(feedCommentRepository).save(any(FeedComment.class));
	}

	@Test
	@DisplayName("피드의 댓글을 조회할 수 있다.")
	void testGetAllFeedComments() {
		FeedCommentGetServiceRequest feedCommentGetServiceRequest
				= new FeedCommentGetServiceRequest(FEED.getId(), null, 10);
		when(feedRepository.existsById(FEED.getId())).thenReturn(true);
		when(feedCommentRepository.findAllByFeedId(
				FEED.getId(),
				feedCommentGetServiceRequest.cursorId(),
				feedCommentGetServiceRequest.pageSize()
		)).thenReturn(List.of(FEED_COMMENT));

		styleService.getAllFeedComments(feedCommentGetServiceRequest);

		verify(feedRepository).existsById(FEED.getId());
		verify(feedCommentRepository).findAllByFeedId(
				FEED.getId(),
				feedCommentGetServiceRequest.cursorId(),
				feedCommentGetServiceRequest.pageSize()
		);
	}

	@Test
	@DisplayName("태그 기준으로 피드 식별자를 조회할 수 있다.")
	void testGetFeedsByTag() {
		String tag = FEED_TAGS.stream().findAny().get().getTag();

		when(feedRepository.findAllByTag(
				tag,
				getFeedServiceRequest().cursorId(),
				getFeedServiceRequest().pageSize(),
				SortType.NEWEST
		)).thenReturn(List.of(FEED));

		FeedGetServiceResponses feedGetServiceResponses = styleService.getAllFeedsByTag(
				getFeedServiceRequest(),
				tag);

		verify(feedRepository).findAllByTag(
				tag,
				getFeedServiceRequest().cursorId(),
				getFeedServiceRequest().pageSize(),
				SortType.NEWEST);
		assertThat(feedGetServiceResponses.feedGetServiceResponses()).isNotEmpty();
	}

	@Test
	@DisplayName("사용자 식별자를 기준으로 피드를 조회할 수 있다.")
	void testGetFeedsByMember() {
		when(feedRepository.findAllByMember(
				MEMBER.getId(),
				getFeedServiceRequest().cursorId(),
				getFeedServiceRequest().pageSize(),
				SortType.NEWEST
		)).thenReturn(List.of(FEED));

		FeedGetServiceResponses feedGetServiceResponses = styleService.getAllFeedsByMember(
				getFeedServiceRequest(),
				MEMBER.getId());

		verify(feedRepository).findAllByMember(
				MEMBER.getId(),
				getFeedServiceRequest().cursorId(),
				getFeedServiceRequest().pageSize(),
				SortType.NEWEST);
		assertThat(feedGetServiceResponses.feedGetServiceResponses()).isNotEmpty();
	}

	@Test
	@DisplayName("상품 식별자를 기준으로 피드를 조회할 수 있다.")
	void testGetFeedsByProduct() {
		FEED.setProductIds(Collections.emptyList());
		when(feedRepository.findAllByProduct(
				1L,
				getFeedServiceRequest().cursorId(),
				getFeedServiceRequest().pageSize(),
				SortType.NEWEST
		)).thenReturn(Collections.emptyList());

		FeedGetServiceResponses feedGetServiceResponses = styleService.getAllFeedsByProduct(
				getFeedServiceRequest(),
				1L);

		verify(feedRepository).findAllByProduct(
				1L,
				getFeedServiceRequest().cursorId(),
				getFeedServiceRequest().pageSize(),
				SortType.NEWEST);
		assertThat(feedGetServiceResponses.feedGetServiceResponses()).isEmpty();
	}

	@Test
	@DisplayName("피드에 등록된 상품 태그를 조회할 수 있다.")
	void testGetFeedProductsOnFeeds() {
		when(feedRepository.findAllByMember(
				MEMBER.getId(),
				getFeedServiceRequest().cursorId(),
				getFeedServiceRequest().pageSize(),
				SortType.NEWEST
		)).thenReturn(List.of(FEED));
		when(feedProductRepository.findAllByFeedId(FEED.getId())).thenReturn(FEED_PRODUCTS);

		FeedGetServiceResponses feedGetServiceResponses = styleService.getAllFeedsByMember(
				getFeedServiceRequest(),
				MEMBER.getId()
		);

		verify(feedProductRepository).findAllByFeedId(any(Long.class));
		assertThat(feedGetServiceResponses.feedGetServiceResponses().get(0).products()).isNotEmpty();
	}

	private FeedRegisterServiceRequest getRegisterFeedServiceRequest() {
		return new FeedRegisterServiceRequest(
				FEED.getContent(),
				MEMBER.getId(),
				FEED_PRODUCTS.stream()
						.map(FeedProduct::getProductId)
						.toList()
		);
	}

	private FeedGetServiceRequest getFeedServiceRequest() {
		return new FeedGetServiceRequest(FEED.getId(), 10, SortType.NEWEST);
	}

	private FeedUpdateServiceRequest getUpdateFeedServiceRequest(String content) {
		List<FeedProduct> updatedFeedProducts = new ArrayList<>(FEED_PRODUCTS);
		updatedFeedProducts.add(
				FeedProduct.builder()
						.id(3L)
						.feedId(FEED.getId())
						.productId(3L)
						.build()
		);

		return new FeedUpdateServiceRequest(
				content,
				updatedFeedProducts.stream()
						.map(FeedProduct::getProductId)
						.toList());
	}

	private FeedLikeServiceRequest getLikeFeedServiceRequest() {
		return new FeedLikeServiceRequest(FEED.getId(), MEMBER.getId());
	}

	private FeedCommentRegisterServiceRequest getRegisterFeedCommentServiceRequest() {
		return new FeedCommentRegisterServiceRequest(
				FEED_COMMENT.getContent(),
				FEED_COMMENT.getMemberId(),
				FEED_COMMENT.getFeedId()
		);
	}

}