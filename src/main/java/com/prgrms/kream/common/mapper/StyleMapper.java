package com.prgrms.kream.common.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.prgrms.kream.domain.style.dto.request.GetFeedCommentFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.GetFeedCommentRequest;
import com.prgrms.kream.domain.style.dto.request.GetFeedCommentServiceRequest;
import com.prgrms.kream.domain.style.dto.request.GetFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.GetFeedRequest;
import com.prgrms.kream.domain.style.dto.request.GetFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.LikeFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.LikeFeedRequest;
import com.prgrms.kream.domain.style.dto.request.LikeFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedCommentFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedCommentRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedCommentServiceRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.response.GetFeedCommentFacadeResponse;
import com.prgrms.kream.domain.style.dto.response.GetFeedCommentFacadeResponses;
import com.prgrms.kream.domain.style.dto.response.GetFeedCommentResponse;
import com.prgrms.kream.domain.style.dto.response.GetFeedCommentResponses;
import com.prgrms.kream.domain.style.dto.response.GetFeedCommentServiceResponse;
import com.prgrms.kream.domain.style.dto.response.GetFeedCommentServiceResponses;
import com.prgrms.kream.domain.style.dto.response.GetFeedFacadeResponse;
import com.prgrms.kream.domain.style.dto.response.GetFeedFacadeResponses;
import com.prgrms.kream.domain.style.dto.response.GetFeedResponse;
import com.prgrms.kream.domain.style.dto.response.GetFeedResponses;
import com.prgrms.kream.domain.style.dto.response.GetFeedServiceResponse;
import com.prgrms.kream.domain.style.dto.response.GetFeedServiceResponses;
import com.prgrms.kream.domain.style.dto.response.RegisterFeedFacadeResponse;
import com.prgrms.kream.domain.style.dto.response.RegisterFeedResponse;
import com.prgrms.kream.domain.style.dto.response.RegisterFeedServiceResponse;
import com.prgrms.kream.domain.style.dto.response.UpdateFeedFacadeResponse;
import com.prgrms.kream.domain.style.dto.response.UpdateFeedResponse;
import com.prgrms.kream.domain.style.dto.response.UpdateFeedServiceResponse;
import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.FeedComment;
import com.prgrms.kream.domain.style.model.FeedLike;
import com.prgrms.kream.domain.style.model.FeedProduct;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StyleMapper {

	public static RegisterFeedFacadeRequest toRegisterFeedFacadeRequest(RegisterFeedRequest registerFeedRequest
	) {
		return new RegisterFeedFacadeRequest(
				registerFeedRequest.content(),
				registerFeedRequest.authorId(),
				registerFeedRequest.images(),
				registerFeedRequest.productIds()
		);
	}

	public static RegisterFeedServiceRequest toRegisterFeedServiceRequest(
			RegisterFeedFacadeRequest registerFeedFacadeRequest) {
		return new RegisterFeedServiceRequest(
				registerFeedFacadeRequest.content(),
				registerFeedFacadeRequest.authorId(),
				registerFeedFacadeRequest.productIds()
		);
	}

	public static Feed toFeed(RegisterFeedServiceRequest registerFeedServiceRequest) {
		return Feed.builder()
				.content(registerFeedServiceRequest.content())
				.authorId(registerFeedServiceRequest.authorId())
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

	public static RegisterFeedServiceResponse toRegisterFeedServiceResponse(Feed feed) {
		return new RegisterFeedServiceResponse(feed.getId());
	}

	public static RegisterFeedFacadeResponse toRegisterFeedFacadeResponse(
			RegisterFeedServiceResponse registerFeedServiceResponse) {
		return new RegisterFeedFacadeResponse(registerFeedServiceResponse.id());
	}

	public static RegisterFeedResponse toRegisterFeedResponse(RegisterFeedFacadeResponse registerFeedFacadeResponse) {
		return new RegisterFeedResponse(registerFeedFacadeResponse.id());
	}

	public static GetFeedFacadeRequest toGetFeedFacadeRequest(GetFeedRequest getFeedRequest) {
		return new GetFeedFacadeRequest(getFeedRequest.cursorId(), getFeedRequest.pageSize());
	}

	public static GetFeedServiceRequest toGetFeedServiceRequest(GetFeedFacadeRequest getFeedFacadeRequest) {
		return new GetFeedServiceRequest(getFeedFacadeRequest.cursorId(), getFeedFacadeRequest.pageSize());
	}

	public static GetFeedServiceResponse toGetFeedServiceResponse(Feed feed) {
		return new GetFeedServiceResponse(
				feed.getId(),
				feed.getAuthorId(),
				feed.getContent(),
				feed.getLikes(),
				feed.getProductIds(),
				feed.getCreatedAt(),
				feed.getUpdatedAt()
		);
	}

	public static GetFeedServiceResponses toGetFeedServiceResponses(List<Feed> feeds, Long lastId) {
		return new GetFeedServiceResponses(
				feeds.stream()
						.map(StyleMapper::toGetFeedServiceResponse)
						.toList(),
				lastId
		);
	}

	public static GetFeedFacadeResponse toGetFeedFacadeResponse(
			GetFeedServiceResponse getFeedServiceResponse,
			List<String> images) {
		return new GetFeedFacadeResponse(
				getFeedServiceResponse.id(),
				getFeedServiceResponse.authorId(),
				getFeedServiceResponse.content(),
				getFeedServiceResponse.likes(),
				getFeedServiceResponse.products(),
				images,
				getFeedServiceResponse.createdAt(),
				getFeedServiceResponse.updatedAt()
		);
	}

	public static GetFeedFacadeResponses toGetFeedFacadeResponses(
			List<GetFeedFacadeResponse> getFeedFacadeResponses,
			Long lastId) {
		return new GetFeedFacadeResponses(getFeedFacadeResponses, lastId);
	}

	public static GetFeedResponse toGetFeedResponse(GetFeedFacadeResponse getFeedFacadeResponse) {
		return new GetFeedResponse(
				getFeedFacadeResponse.id(),
				getFeedFacadeResponse.authorId(),
				getFeedFacadeResponse.content(),
				getFeedFacadeResponse.likes(),
				getFeedFacadeResponse.products(),
				getFeedFacadeResponse.images(),
				getFeedFacadeResponse.createdAt(),
				getFeedFacadeResponse.updatedAt()
		);
	}

	public static GetFeedResponses toGetFeedResponses(GetFeedFacadeResponses getFeedFacadeResponses) {
		return new GetFeedResponses(
				getFeedFacadeResponses.getFeedFacadeResponses().stream()
						.map(StyleMapper::toGetFeedResponse)
						.collect(Collectors.toList()),
				getFeedFacadeResponses.lastId()
		);
	}

	public static UpdateFeedFacadeRequest toUpdateFeedFacadeRequest(UpdateFeedRequest updateFeedRequest) {
		return new UpdateFeedFacadeRequest(updateFeedRequest.content(), updateFeedRequest.productIds());
	}

	public static UpdateFeedServiceRequest toUpdateFeedServiceRequest(UpdateFeedFacadeRequest updateFeedFacadeRequest) {
		return new UpdateFeedServiceRequest(updateFeedFacadeRequest.content(), updateFeedFacadeRequest.productIds());
	}

	public static UpdateFeedServiceResponse toUpdateFeedServiceResponse(Feed feed) {
		return new UpdateFeedServiceResponse(feed.getId());
	}

	public static UpdateFeedFacadeResponse toUpdateFeedFacadeResponse(
			UpdateFeedServiceResponse updateFeedServiceResponse) {
		return new UpdateFeedFacadeResponse(updateFeedServiceResponse.id());
	}

	public static UpdateFeedResponse toUpdateFeedResponse(UpdateFeedFacadeResponse updateFeedFacadeResponse) {
		return new UpdateFeedResponse(updateFeedFacadeResponse.id());
	}

	public static LikeFeedFacadeRequest toLikeFeedFacadeRequest(long id, LikeFeedRequest likeFeedRequest) {
		return new LikeFeedFacadeRequest(id, likeFeedRequest.memberId());
	}

	public static LikeFeedServiceRequest toLikeFeedServiceRequest(LikeFeedFacadeRequest likeFeedFacadeRequest) {
		return new LikeFeedServiceRequest(
				likeFeedFacadeRequest.feedId(),
				likeFeedFacadeRequest.memberId());
	}

	public static FeedLike toFeedLike(LikeFeedServiceRequest likeFeedServiceRequest) {
		return FeedLike.builder()
				.feedId(likeFeedServiceRequest.feedId())
				.memberId(likeFeedServiceRequest.memberId())
				.build();
	}

	public static RegisterFeedCommentFacadeRequest toRegisterFeedCommentFacadeRequest(
			Long id,
			RegisterFeedCommentRequest registerFeedCommentRequest
	) {
		return new RegisterFeedCommentFacadeRequest(
				registerFeedCommentRequest.content(),
				registerFeedCommentRequest.memberId(),
				id
		);
	}

	public static RegisterFeedCommentServiceRequest toRegisterFeedCommentServiceRequest(
			RegisterFeedCommentFacadeRequest registerFeedCommentFacadeRequest
	) {
		return new RegisterFeedCommentServiceRequest(
				registerFeedCommentFacadeRequest.content(),
				registerFeedCommentFacadeRequest.memberId(),
				registerFeedCommentFacadeRequest.feedId()
		);
	}

	public static FeedComment toFeedComment(RegisterFeedCommentServiceRequest registerFeedCommentServiceRequest) {
		return FeedComment.builder()
				.feedId(registerFeedCommentServiceRequest.feedId())
				.memberId(registerFeedCommentServiceRequest.memberId())
				.content(registerFeedCommentServiceRequest.content())
				.build();
	}

	public static GetFeedCommentFacadeRequest toGetFeedCommentFacadeRequest(Long id, GetFeedCommentRequest getFeedCommentRequest) {
		return new GetFeedCommentFacadeRequest(
				id,
				getFeedCommentRequest.cursorId(),
				getFeedCommentRequest.pageSize()
		);
	}

	public static GetFeedCommentServiceRequest toGetFeedCommentServiceRequest(GetFeedCommentFacadeRequest getFeedCommentFacadeRequest) {
		return new GetFeedCommentServiceRequest(
				getFeedCommentFacadeRequest.feedId(),
				getFeedCommentFacadeRequest.cursorId(),
				getFeedCommentFacadeRequest.pageSize()
		);
	}

	public static GetFeedCommentServiceResponse toGetFeedCommentServiceResponse(FeedComment feedComment) {
		return new GetFeedCommentServiceResponse(
				feedComment.getId(),
				feedComment.getMemberId(),
				feedComment.getContent(),
				feedComment.getCreatedAt(),
				feedComment.getUpdatedAt()
		);
	}

	public static GetFeedCommentServiceResponses toGetFeedCommentServiceResponses(List<FeedComment> feedComments, Long lastId) {
		return new GetFeedCommentServiceResponses(
				feedComments.stream()
						.map(StyleMapper::toGetFeedCommentServiceResponse)
						.toList(),
				lastId
		);
	}

	public static GetFeedCommentFacadeResponse toGetFeedCommentFacadeResponse(
			GetFeedCommentServiceResponse getFeedCommentServiceResponse) {
		return new GetFeedCommentFacadeResponse(
				getFeedCommentServiceResponse.id(),
				getFeedCommentServiceResponse.memberId(),
				getFeedCommentServiceResponse.content(),
				getFeedCommentServiceResponse.createdAt(),
				getFeedCommentServiceResponse.updatedAt()
		);
	}

	public static GetFeedCommentFacadeResponses toGetFeedCommentFacadeResponses(
			GetFeedCommentServiceResponses getFeedCommentServiceResponses) {
		return new GetFeedCommentFacadeResponses(
				getFeedCommentServiceResponses.getFeedCommentServiceResponses().stream()
						.map(StyleMapper::toGetFeedCommentFacadeResponse)
						.toList(),
				getFeedCommentServiceResponses.lastId()
		);
	}

	public static GetFeedCommentResponse toGetFeedCommentResponse(
			GetFeedCommentFacadeResponse getFeedCommentFacadeResponse) {
		return new GetFeedCommentResponse(
				getFeedCommentFacadeResponse.id(),
				getFeedCommentFacadeResponse.memberId(),
				getFeedCommentFacadeResponse.content(),
				getFeedCommentFacadeResponse.createdAt(),
				getFeedCommentFacadeResponse.updatedAt()
		);
	}

	public static GetFeedCommentResponses toGetFeedCommentResponses(
			GetFeedCommentFacadeResponses getFeedCommentFacadeResponses) {
		return new GetFeedCommentResponses(
				getFeedCommentFacadeResponses.getFeedCommentFacadeResponses().stream()
						.map(StyleMapper::toGetFeedCommentResponse)
						.toList(),
				getFeedCommentFacadeResponses.lastId()
		);
	}

}
