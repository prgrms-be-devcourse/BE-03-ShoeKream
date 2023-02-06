package com.prgrms.kream.domain.style.facade;

import static com.prgrms.kream.common.mapper.StyleMapper.*;

import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.image.model.DomainType;
import com.prgrms.kream.domain.image.service.ImageService;
import com.prgrms.kream.domain.member.service.MemberService;
import com.prgrms.kream.domain.style.dto.request.FeedCommentGetFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedGetFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedLikeFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedCommentRegisterFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedRegisterFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedUpdateFacadeRequest;
import com.prgrms.kream.domain.style.dto.response.FeedCommentGetFacadeResponses;
import com.prgrms.kream.domain.style.dto.response.FeedGetFacadeResponses;
import com.prgrms.kream.domain.style.dto.response.FeedGetServiceResponses;
import com.prgrms.kream.domain.style.dto.response.FeedRegisterFacadeResponse;
import com.prgrms.kream.domain.style.dto.response.FeedUpdateFacadeResponse;
import com.prgrms.kream.domain.style.service.StyleService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StyleFacade {

	private final StyleService styleService;

	private final MemberService memberService;

	private final ImageService imageService;

	@Transactional
	public FeedRegisterFacadeResponse registerFeed(FeedRegisterFacadeRequest feedRegisterFacadeRequest) {
		FeedRegisterFacadeResponse feedRegisterFacadeResponse = toFeedRegisterFacadeResponse(
				styleService.registerFeed(
						toFeedRegisterServiceRequest(feedRegisterFacadeRequest)
				)
		);

		// 이미지 등록 서비스 호출
		imageService.registerImage(
				feedRegisterFacadeRequest.images(),
				feedRegisterFacadeResponse.id(),
				DomainType.FEED
		);

		return feedRegisterFacadeResponse;
	}

	@Transactional(readOnly = true)
	@Cacheable(
			cacheNames = "topPopular",
			key = "#feedGetFacadeRequest",
			condition = "{#feedGetFacadeRequest.cursorId() == null && #feedGetFacadeRequest.pageSize() == 10 && #feedGetFacadeRequest.sortType().name() == 'POPULAR'}"
	)
	public FeedGetFacadeResponses getAllFeeds(FeedGetFacadeRequest feedGetFacadeRequest) {
		return merge(styleService.getAllFeeds(toFeedGetServiceRequest(feedGetFacadeRequest)));
	}

	@Transactional(readOnly = true)
	public FeedGetFacadeResponses getAllFeedsByTag(FeedGetFacadeRequest feedGetFacadeRequest, String tag) {
		return merge(styleService.getAllFeedsByTag(toFeedGetServiceRequest(feedGetFacadeRequest), tag));
	}

	@Transactional(readOnly = true)
	public FeedGetFacadeResponses getAllFeedsByMemberId(FeedGetFacadeRequest feedGetFacadeRequest, Long memberId) {
		return merge(styleService.getAllFeedsByMemberId(toFeedGetServiceRequest(feedGetFacadeRequest), memberId));
	}

	@Transactional(readOnly = true)
	public FeedGetFacadeResponses getAllFeedsByProductId(FeedGetFacadeRequest feedGetFacadeRequest, Long productId) {
		return merge(styleService.getAllFeedsByProductId(toFeedGetServiceRequest(feedGetFacadeRequest), productId));
	}

	@Transactional
	public FeedUpdateFacadeResponse updateFeed(Long id, FeedUpdateFacadeRequest feedUpdateFacadeRequest) {
		return toFeedUpdateFacadeResponse(
				styleService.updateFeed(
						id,
						toFeedUpdateServiceRequest(feedUpdateFacadeRequest)
				)
		);
	}

	@Transactional
	public void deleteFeed(long id) {
		styleService.deleteFeed(id);
		imageService.deleteAllImagesByReference(id, DomainType.FEED);
	}

	@Transactional
	public void registerFeedLike(FeedLikeFacadeRequest feedLikeFacadeRequest) {
		styleService.registerFeedLike(
				toFeedLikeServiceRequest(feedLikeFacadeRequest)
		);
	}

	@Transactional
	public void deleteFeedLike(FeedLikeFacadeRequest feedLikeFacadeRequest) {
		styleService.deleteFeedLike(
				toFeedLikeServiceRequest(feedLikeFacadeRequest)
		);
	}

	@Transactional
	public void registerFeedComment(FeedCommentRegisterFacadeRequest feedCommentRegisterFacadeRequest) {
		styleService.registerFeedComment(
				toFeedCommentRegisterServiceRequest(feedCommentRegisterFacadeRequest)
		);
	}

	@Transactional(readOnly = true)
	public FeedCommentGetFacadeResponses getAllFeedComments(FeedCommentGetFacadeRequest feedCommentGetFacadeRequest) {
		return toFeedCommentGetFacadeResponses(
				styleService.getAllFeedComments(
						toFeedCommentGetServiceRequest(feedCommentGetFacadeRequest)
				)
		);
	}

	private FeedGetFacadeResponses merge(FeedGetServiceResponses feedGetServiceResponses) {
		return toFeedGetFacadeResponses(
				feedGetServiceResponses.feedGetServiceResponses().stream()
						.map(getFeedServiceResponse ->
								toFeedGetFacadeResponse(
										getFeedServiceResponse,
										imageService.getAllImages(getFeedServiceResponse.id(), DomainType.FEED)
								))
						.collect(Collectors.toList()),
				feedGetServiceResponses.lastId()
		);
	}

}
