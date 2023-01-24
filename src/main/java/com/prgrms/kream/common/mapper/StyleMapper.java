package com.prgrms.kream.common.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.prgrms.kream.domain.style.dto.request.GetFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.GetFeedRequest;
import com.prgrms.kream.domain.style.dto.request.GetFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.LikeFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.LikeFeedRequest;
import com.prgrms.kream.domain.style.dto.request.LikeFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedServiceRequest;
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
import com.prgrms.kream.domain.style.model.FeedLike;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StyleMapper {

	public static RegisterFeedFacadeRequest toRegisterFeedFacadeRequest(RegisterFeedRequest registerFeedRequest
	) {
		return new RegisterFeedFacadeRequest(
				registerFeedRequest.content(),
				registerFeedRequest.authorId(),
				registerFeedRequest.images()
		);
	}

	public static RegisterFeedServiceRequest toRegisterFeedServiceRequest(
			RegisterFeedFacadeRequest registerFeedFacadeRequest) {
		return new RegisterFeedServiceRequest(
				registerFeedFacadeRequest.content(),
				registerFeedFacadeRequest.authorId()
		);
	}

	public static Feed toFeed(RegisterFeedServiceRequest registerFeedServiceRequest) {
		return Feed.builder()
				.content(registerFeedServiceRequest.content())
				.authorId(registerFeedServiceRequest.authorId())
				.build();
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
		return new GetFeedFacadeRequest(getFeedRequest.id(), getFeedRequest.tag());
	}

	public static GetFeedServiceRequest toGetFeedServiceRequest(GetFeedFacadeRequest getFeedFacadeRequest) {
		return new GetFeedServiceRequest(List.of(getFeedFacadeRequest.id()));
	}

	public static GetFeedServiceRequest toGetFeedServiceRequest(List<Long> ids) {
		return new GetFeedServiceRequest(ids);
	}

	public static GetFeedServiceResponse toGetFeedServiceResponse(Feed feed) {
		return new GetFeedServiceResponse(
				feed.getId(),
				feed.getAuthorId(),
				feed.getContent(),
				feed.getCreatedAt(),
				feed.getUpdatedAt()
		);
	}

	public static GetFeedServiceResponses toGetFeedServiceResponses(List<Feed> feeds) {
		return new GetFeedServiceResponses(
				feeds.stream()
						.map(StyleMapper::toGetFeedServiceResponse)
						.collect(Collectors.toList())
		);
	}

	public static GetFeedFacadeResponse toGetFeedFacadeResponse(
			GetFeedServiceResponse getFeedServiceResponse,
			Long likes,
			List<String> images) {
		return new GetFeedFacadeResponse(
				getFeedServiceResponse.id(),
				getFeedServiceResponse.authorId(),
				getFeedServiceResponse.content(),
				likes,
				images,
				getFeedServiceResponse.createdAt(),
				getFeedServiceResponse.updatedAt()
		);
	}

	public static GetFeedFacadeResponses toGetFeedFacadeResponses(List<GetFeedFacadeResponse> getFeedFacadeResponses) {
		return new GetFeedFacadeResponses(getFeedFacadeResponses);
	}

	public static GetFeedResponse toGetFeedResponse(GetFeedFacadeResponse getFeedFacadeResponse) {
		return new GetFeedResponse(
				getFeedFacadeResponse.id(),
				getFeedFacadeResponse.authorId(),
				getFeedFacadeResponse.content(),
				getFeedFacadeResponse.likes(),
				getFeedFacadeResponse.images(),
				getFeedFacadeResponse.createdAt(),
				getFeedFacadeResponse.updatedAt()
		);
	}

	public static GetFeedResponses toGetFeedResponses(GetFeedFacadeResponses getFeedFacadeResponses) {
		return new GetFeedResponses(
				getFeedFacadeResponses.getFeedFacadeResponses().stream()
						.map(StyleMapper::toGetFeedResponse)
						.collect(Collectors.toList()));
	}

	public static UpdateFeedFacadeRequest toUpdateFeedFacadeRequest(UpdateFeedRequest updateFeedRequest) {
		return new UpdateFeedFacadeRequest(updateFeedRequest.content());
	}

	public static UpdateFeedServiceRequest toUpdateFeedServiceRequest(UpdateFeedFacadeRequest updateFeedFacadeRequest) {
		return new UpdateFeedServiceRequest(updateFeedFacadeRequest.content());
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

}
