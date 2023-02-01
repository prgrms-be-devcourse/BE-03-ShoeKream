package com.prgrms.kream.common.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.prgrms.kream.domain.style.dto.request.FeedCommentGetFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedCommentGetRequest;
import com.prgrms.kream.domain.style.dto.request.FeedCommentGetServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedGetFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedGetRequest;
import com.prgrms.kream.domain.style.dto.request.FeedGetServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedLikeFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedLikeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedLikeServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedCommentRegisterFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedCommentRegisterRequest;
import com.prgrms.kream.domain.style.dto.request.FeedCommentRegisterServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedRegisterFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedRegisterRequest;
import com.prgrms.kream.domain.style.dto.request.FeedRegisterServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedUpdateFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedUpdateRequest;
import com.prgrms.kream.domain.style.dto.request.FeedUpdateServiceRequest;
import com.prgrms.kream.domain.style.dto.response.FeedCommentGetFacadeResponse;
import com.prgrms.kream.domain.style.dto.response.FeedCommentGetFacadeResponses;
import com.prgrms.kream.domain.style.dto.response.FeedCommentGetResponse;
import com.prgrms.kream.domain.style.dto.response.FeedCommentGetResponses;
import com.prgrms.kream.domain.style.dto.response.FeedCommentGetServiceResponse;
import com.prgrms.kream.domain.style.dto.response.FeedCommentGetServiceResponses;
import com.prgrms.kream.domain.style.dto.response.FeedGetFacadeResponse;
import com.prgrms.kream.domain.style.dto.response.FeedGetFacadeResponses;
import com.prgrms.kream.domain.style.dto.response.FeedGetResponse;
import com.prgrms.kream.domain.style.dto.response.FeedGetResponses;
import com.prgrms.kream.domain.style.dto.response.FeedGetServiceResponse;
import com.prgrms.kream.domain.style.dto.response.FeedGetServiceResponses;
import com.prgrms.kream.domain.style.dto.response.FeedRegisterFacadeResponse;
import com.prgrms.kream.domain.style.dto.response.FeedRegisterResponse;
import com.prgrms.kream.domain.style.dto.response.FeedRegisterServiceResponse;
import com.prgrms.kream.domain.style.dto.response.FeedUpdateFacadeResponse;
import com.prgrms.kream.domain.style.dto.response.FeedUpdateResponse;
import com.prgrms.kream.domain.style.dto.response.FeedUpdateServiceResponse;
import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.FeedComment;
import com.prgrms.kream.domain.style.model.FeedLike;
import com.prgrms.kream.domain.style.model.FeedProduct;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StyleMapper {

	public static FeedRegisterFacadeRequest toFeedRegisterFacadeRequest(FeedRegisterRequest feedRegisterRequest
	) {
		return new FeedRegisterFacadeRequest(
				feedRegisterRequest.content(),
				feedRegisterRequest.authorId(),
				feedRegisterRequest.images(),
				feedRegisterRequest.productIds()
		);
	}

	public static FeedRegisterServiceRequest toFeedRegisterServiceRequest(
			FeedRegisterFacadeRequest feedRegisterFacadeRequest) {
		return new FeedRegisterServiceRequest(
				feedRegisterFacadeRequest.content(),
				feedRegisterFacadeRequest.authorId(),
				feedRegisterFacadeRequest.productIds()
		);
	}

	public static Feed toFeed(FeedRegisterServiceRequest feedRegisterServiceRequest) {
		return Feed.builder()
				.content(feedRegisterServiceRequest.content())
				.authorId(feedRegisterServiceRequest.authorId())
				.likes(0L)
				.build();
	}

	public static List<FeedProduct> toFeedProducts(Long feedId, List<Long> productIds) {
		return productIds.stream()
				.map(productId -> FeedProduct.builder()
						.feedId(feedId)
						.productId(productId)
						.build())
				.toList();
	}

	public static FeedRegisterServiceResponse toFeedRegisterServiceResponse(Feed feed) {
		return new FeedRegisterServiceResponse(feed.getId());
	}

	public static FeedRegisterFacadeResponse toFeedRegisterFacadeResponse(
			FeedRegisterServiceResponse feedRegisterServiceResponse) {
		return new FeedRegisterFacadeResponse(feedRegisterServiceResponse.id());
	}

	public static FeedRegisterResponse toFeedRegisterResponse(FeedRegisterFacadeResponse feedRegisterFacadeResponse) {
		return new FeedRegisterResponse(feedRegisterFacadeResponse.id());
	}

	public static FeedGetFacadeRequest toFeedGetFacadeRequest(FeedGetRequest feedGetRequest) {
		return new FeedGetFacadeRequest(feedGetRequest.cursorId(), feedGetRequest.pageSize());
	}

	public static FeedGetServiceRequest toFeedGetServiceRequest(FeedGetFacadeRequest feedGetFacadeRequest) {
		return new FeedGetServiceRequest(feedGetFacadeRequest.cursorId(), feedGetFacadeRequest.pageSize());
	}

	public static FeedGetServiceResponse toFeedGetServiceResponse(Feed feed) {
		return new FeedGetServiceResponse(
				feed.getId(),
				feed.getAuthorId(),
				feed.getContent(),
				feed.getLikes(),
				feed.getProductIds(),
				feed.getCreatedAt(),
				feed.getUpdatedAt()
		);
	}

	public static FeedGetServiceResponses toFeedGetServiceResponses(List<Feed> feeds, Long lastId) {
		return new FeedGetServiceResponses(
				feeds.stream()
						.map(StyleMapper::toFeedGetServiceResponse)
						.toList(),
				lastId
		);
	}

	public static FeedGetFacadeResponse toFeedGetFacadeResponse(
			FeedGetServiceResponse feedGetServiceResponse,
			List<String> images) {
		return new FeedGetFacadeResponse(
				feedGetServiceResponse.id(),
				feedGetServiceResponse.authorId(),
				feedGetServiceResponse.content(),
				feedGetServiceResponse.likes(),
				feedGetServiceResponse.products(),
				images,
				feedGetServiceResponse.createdAt(),
				feedGetServiceResponse.updatedAt()
		);
	}

	public static FeedGetFacadeResponses toFeedGetFacadeResponses(
			List<FeedGetFacadeResponse> feedGetFacadeResponses,
			Long lastId) {
		return new FeedGetFacadeResponses(feedGetFacadeResponses, lastId);
	}

	public static FeedGetResponse toFeedGetResponse(FeedGetFacadeResponse feedGetFacadeResponse) {
		return new FeedGetResponse(
				feedGetFacadeResponse.id(),
				feedGetFacadeResponse.authorId(),
				feedGetFacadeResponse.content(),
				feedGetFacadeResponse.likes(),
				feedGetFacadeResponse.products(),
				feedGetFacadeResponse.images(),
				feedGetFacadeResponse.createdAt(),
				feedGetFacadeResponse.updatedAt()
		);
	}

	public static FeedGetResponses toFeedGetResponses(FeedGetFacadeResponses feedGetFacadeResponses) {
		return new FeedGetResponses(
				feedGetFacadeResponses.feedGetFacadeResponses().stream()
						.map(StyleMapper::toFeedGetResponse)
						.collect(Collectors.toList()),
				feedGetFacadeResponses.lastId()
		);
	}

	public static FeedUpdateFacadeRequest toFeedUpdateFacadeRequest(FeedUpdateRequest feedUpdateRequest) {
		return new FeedUpdateFacadeRequest(feedUpdateRequest.content(), feedUpdateRequest.productIds());
	}

	public static FeedUpdateServiceRequest toFeedUpdateServiceRequest(FeedUpdateFacadeRequest feedUpdateFacadeRequest) {
		return new FeedUpdateServiceRequest(feedUpdateFacadeRequest.content(), feedUpdateFacadeRequest.productIds());
	}

	public static FeedUpdateServiceResponse toFeedUpdateServiceResponse(Feed feed) {
		return new FeedUpdateServiceResponse(feed.getId());
	}

	public static FeedUpdateFacadeResponse toFeedUpdateFacadeResponse(
			FeedUpdateServiceResponse feedUpdateServiceResponse) {
		return new FeedUpdateFacadeResponse(feedUpdateServiceResponse.id());
	}

	public static FeedUpdateResponse toFeedUpdateResponse(FeedUpdateFacadeResponse feedUpdateFacadeResponse) {
		return new FeedUpdateResponse(feedUpdateFacadeResponse.id());
	}

	public static FeedLikeFacadeRequest toFeedLikeFacadeRequest(long id, FeedLikeRequest feedLikeRequest) {
		return new FeedLikeFacadeRequest(id, feedLikeRequest.memberId());
	}

	public static FeedLikeServiceRequest toFeedLikeServiceRequest(FeedLikeFacadeRequest feedLikeFacadeRequest) {
		return new FeedLikeServiceRequest(
				feedLikeFacadeRequest.feedId(),
				feedLikeFacadeRequest.memberId());
	}

	public static FeedLike toFeedLike(FeedLikeServiceRequest feedLikeServiceRequest) {
		return FeedLike.builder()
				.feedId(feedLikeServiceRequest.feedId())
				.memberId(feedLikeServiceRequest.memberId())
				.build();
	}

	public static FeedCommentRegisterFacadeRequest toFeedCommentRegisterFacadeRequest(
			Long id,
			FeedCommentRegisterRequest feedCommentRegisterRequest
	) {
		return new FeedCommentRegisterFacadeRequest(
				feedCommentRegisterRequest.content(),
				feedCommentRegisterRequest.memberId(),
				id
		);
	}

	public static FeedCommentRegisterServiceRequest toFeedCommentRegisterServiceRequest(
			FeedCommentRegisterFacadeRequest feedCommentRegisterFacadeRequest
	) {
		return new FeedCommentRegisterServiceRequest(
				feedCommentRegisterFacadeRequest.content(),
				feedCommentRegisterFacadeRequest.memberId(),
				feedCommentRegisterFacadeRequest.feedId()
		);
	}

	public static FeedComment toFeedComment(FeedCommentRegisterServiceRequest feedCommentRegisterServiceRequest) {
		return FeedComment.builder()
				.feedId(feedCommentRegisterServiceRequest.feedId())
				.memberId(feedCommentRegisterServiceRequest.memberId())
				.content(feedCommentRegisterServiceRequest.content())
				.build();
	}

	public static FeedCommentGetFacadeRequest toFeedCommentGetFacadeRequest(Long id, FeedCommentGetRequest feedCommentGetRequest) {
		return new FeedCommentGetFacadeRequest(
				id,
				feedCommentGetRequest.cursorId(),
				feedCommentGetRequest.pageSize()
		);
	}

	public static FeedCommentGetServiceRequest toFeedCommentGetServiceRequest(
			FeedCommentGetFacadeRequest feedCommentGetFacadeRequest) {
		return new FeedCommentGetServiceRequest(
				feedCommentGetFacadeRequest.feedId(),
				feedCommentGetFacadeRequest.cursorId(),
				feedCommentGetFacadeRequest.pageSize()
		);
	}

	public static FeedCommentGetServiceResponse toFeedCommentGetServiceResponse(FeedComment feedComment) {
		return new FeedCommentGetServiceResponse(
				feedComment.getId(),
				feedComment.getMemberId(),
				feedComment.getContent(),
				feedComment.getCreatedAt(),
				feedComment.getUpdatedAt()
		);
	}

	public static FeedCommentGetServiceResponses toFeedCommentGetServiceResponses(List<FeedComment> feedComments, Long lastId) {
		return new FeedCommentGetServiceResponses(
				feedComments.stream()
						.map(StyleMapper::toFeedCommentGetServiceResponse)
						.toList(),
				lastId
		);
	}

	public static FeedCommentGetFacadeResponse toFeedCommentGetFacadeResponse(
			FeedCommentGetServiceResponse feedCommentGetServiceResponse) {
		return new FeedCommentGetFacadeResponse(
				feedCommentGetServiceResponse.id(),
				feedCommentGetServiceResponse.memberId(),
				feedCommentGetServiceResponse.content(),
				feedCommentGetServiceResponse.createdAt(),
				feedCommentGetServiceResponse.updatedAt()
		);
	}

	public static FeedCommentGetFacadeResponses toFeedCommentGetFacadeResponses(
			FeedCommentGetServiceResponses feedCommentGetServiceResponses) {
		return new FeedCommentGetFacadeResponses(
				feedCommentGetServiceResponses.feedCommentGetServiceResponses().stream()
						.map(StyleMapper::toFeedCommentGetFacadeResponse)
						.toList(),
				feedCommentGetServiceResponses.lastId()
		);
	}

	public static FeedCommentGetResponse toFeedCommentGetResponse(
			FeedCommentGetFacadeResponse feedCommentGetFacadeResponse) {
		return new FeedCommentGetResponse(
				feedCommentGetFacadeResponse.id(),
				feedCommentGetFacadeResponse.memberId(),
				feedCommentGetFacadeResponse.content(),
				feedCommentGetFacadeResponse.createdAt(),
				feedCommentGetFacadeResponse.updatedAt()
		);
	}

	public static FeedCommentGetResponses toFeedCommentGetResponses(
			FeedCommentGetFacadeResponses feedCommentGetFacadeResponses) {
		return new FeedCommentGetResponses(
				feedCommentGetFacadeResponses.feedCommentGetFacadeResponses().stream()
						.map(StyleMapper::toFeedCommentGetResponse)
						.toList(),
				feedCommentGetFacadeResponses.lastId()
		);
	}

}
